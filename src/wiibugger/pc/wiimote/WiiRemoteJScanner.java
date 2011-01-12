package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;

public class WiiRemoteJScanner extends WiimoteScanner {

	protected WiiRemoteJScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		super(wiimoteList, numberOfScans, callback);
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
				WiiRemoteJDevice wiimoteDevice = new WiiRemoteJDevice(remote);
				try {
					wiimoteDevice.setLEDLights(new boolean[] { true, true, true, true });
				} catch (Exception e) {
					System.out.println("Could not set LED for " + wiimoteDevice.getBluetoothAddress());
				} 
				wiimoteList.add(wiimoteDevice);
				remote = null;
				wiimoteDevice = null;
			}
			
		}
		
		if (callback != null) {
			callback.run();
		}		
		System.out.println("Finished scanning");
	}
	
}
