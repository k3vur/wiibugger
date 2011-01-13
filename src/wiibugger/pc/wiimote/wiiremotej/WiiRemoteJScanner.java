package wiibugger.pc.wiimote.wiiremotej;

import wiibugger.pc.DeviceList;
import wiibugger.pc.wiimote.WiimoteDevice;
import wiibugger.pc.wiimote.WiimoteScanner;
import wiiremotej.WiiDevice;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;
import wiiremotej.event.WiiDeviceDiscoveredEvent;
import wiiremotej.event.WiiDeviceDiscoveryListener;

public class WiiRemoteJScanner extends WiimoteScanner implements WiiDeviceDiscoveryListener {

	protected WiiRemoteJScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		super(wiimoteList, numberOfScans, callback);
	}
	
	public static WiiRemoteJScanner getScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {		
		return new WiiRemoteJScanner(wiimoteList, numberOfScans, callback);
	}

	/**
	 * Scan for wiimotes
	 */
	@Override
	public void run() {
		System.out.println("Scanning for Wiimotes using WiiRemoteJ...");
		
		WiiRemoteJ.findRemotes(this, 2);
		try {
			Thread.sleep(15000);
			WiiRemoteJ.stopFind();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void findFinished(int numberFound) {
		System.out.println("Finished scanning");
		callback.run();
	}

	@Override
	public void wiiDeviceDiscovered(WiiDeviceDiscoveredEvent evt) {
		WiiDevice device = evt.getWiiDevice();
		if (device instanceof WiiRemote) {
			WiiRemoteJDevice remote = new WiiRemoteJDevice((WiiRemote) device);
			try {
				remote.setLEDLights(new boolean[] { true, true, true, true });
			} catch (Exception e) {
				System.out.println("Could not set LED for " + remote.getBluetoothAddress());
			} 
			wiimoteList.add(remote);
		}
	}
	
}
