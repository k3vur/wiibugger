package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;


public class WiimoteScanner extends Thread {
	
	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans) {
		return scan(wiimoteList, numberOfScans, null);
	}

	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callAfterFinish) {
		WiimoteScanner scanner = new WiimoteScanner(wiimoteList, numberOfScans, callAfterFinish);
		scanner.start();
		return scanner;
	}
	
	private Runnable callback;
	
	private int numberOfScans;
	
	private DeviceList<WiimoteDevice> wiimoteList;
	
	private WiimoteScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		this.wiimoteList = wiimoteList;
		this.numberOfScans = numberOfScans;
		this.callback = callback;
	}
	
	/**
	 * Scan for wiimotes
	 */
	@Override
	public void run() {
		System.out.println("Scanning for Wiimotes...");
		
		String os = System.getProperty("os.name"); 
		if(os.equals("Windows 7")) {
			scanWiiuseJ();
		} else {
			scanWiiRemoteJ();
		}
	}
	
	private void scanWiiuseJ() {
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(2, true);
		
		if(wiimotes.length == 0) {
			System.out.println("No Wiimote Found");
			return;
		}
		for(int i = 0; i < wiimotes.length; i++) {
			System.out.println("Found wiimote " + wiimotes[i].getId());
		}
	}

	private void scanWiiRemoteJ() {
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
		
		if (callback != null) {
			callback.run();
		}		
		System.out.println("Finished scanning");
	}
	
}
