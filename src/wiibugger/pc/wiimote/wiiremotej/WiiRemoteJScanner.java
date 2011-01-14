package wiibugger.pc.wiimote.wiiremotej;

import java.io.IOException;

import wiibugger.pc.DeviceList;
import wiibugger.pc.wiimote.WiimoteDevice;
import wiibugger.pc.wiimote.WiimoteScanner;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;

public class WiiRemoteJScanner extends WiimoteScanner {

	protected WiiRemoteJScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfWiimotes, Runnable callback) {
		super(wiimoteList, numberOfWiimotes, callback);
	}
	
	public static WiiRemoteJScanner getScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfWiimotes, Runnable callback) {		
		return new WiiRemoteJScanner(wiimoteList, numberOfWiimotes, callback);
	}

	private int foundDevices;
	
	/**
	 * Scan for wiimotes
	 */
	@Override
	public void run() {
		System.out.println("Scanning for Wiimotes using WiiRemoteJ...");
		
		foundDevices = 0;
		
		/*
		 * Stop scanning after 10 seconds
		 */
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				foundDevices = numberOfWiimotes; 
			}
		}.start();
		
		while(foundDevices < numberOfWiimotes) {
			WiiRemote remote = null;
			
			try {
				remote = WiiRemoteJ.findRemote();
				if (remote != null) {
					remote.setLEDLights(new boolean[] { true, true, true, true });
				}
			} catch (IOException e) {
				System.out.println("OH NOEZ CONNECT");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (remote != null) {
				wiimoteList.add(new WiiRemoteJDevice(remote));
				foundDevices++;
			}
		}
		
		System.out.println("Finished scanning");
		callback.run();
	}
	
}
