package wiibugger.pc.wiimote;

import java.io.IOException;

import wiibugger.pc.DeviceList;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;


public class WiimoteScanner extends Thread {
	
	public static WiimoteScanner scan(DeviceList<WiiRemote> wiimoteList, int numberOfScans) {
		return scan(wiimoteList, numberOfScans, null);
	}

	public static WiimoteScanner scan(DeviceList<WiiRemote> wiimoteList, int numberOfScans, Runnable callAfterFinish) {
		WiimoteScanner scanner = new WiimoteScanner(wiimoteList, numberOfScans, callAfterFinish);
		scanner.start();
		return scanner;
	}
	
	private Runnable callAfterFinish;
	
	private int numberOfScans;
	
	private DeviceList<WiiRemote> wiimoteList;
	
	private WiimoteScanner(DeviceList<WiiRemote> wiimoteList, int numberOfScans, Runnable callAfterFinish) {
		this.wiimoteList = wiimoteList;
		this.numberOfScans = numberOfScans;
		this.callAfterFinish = callAfterFinish;
	}
	
	/**
	 * Scan for wiimotes
	 */
	@Override
	public void run() {
		System.out.println("Scanning for Wiimotes...");
		
		WiiRemote remote = null;
		for (int i = 0; i < numberOfScans; i++) {
			
			try {
				remote = WiiRemoteJ.findRemote();
			} catch (Exception e) {
				// TODO: handle findRemote() exception
				System.out.println("Exception while finding Wiimotes");
			}
			
			if (remote != null) {
				try {
					remote.setLEDLights(new boolean[] { true, true, true, true });
				} catch (Exception e) {
					System.out.println("Could not set LED for " + remote.getBluetoothAddress());
				} 
				wiimoteList.add(remote);
				remote = null;
			}
			
		}
		
		if (callAfterFinish != null) {
			callAfterFinish.run();
		}
		
		System.out.println("Finished scanning");
	}
	
}
