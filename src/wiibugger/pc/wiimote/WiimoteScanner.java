package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiibugger.pc.Wiibugger;


public abstract class WiimoteScanner extends Thread {
	
	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans) {
		return scan(wiimoteList, numberOfScans, null);
	}

	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callAfterFinish) {
		WiimoteScanner scanner = getScanner(wiimoteList, numberOfScans, callAfterFinish);
		scanner.start();
		return scanner;
	}
	
	private static WiimoteScanner getScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		WiimoteScanner scanner;
		
		if (Wiibugger.isMac()) {
			scanner = new WiiRemoteJScanner(wiimoteList, numberOfScans, callback);
		} else {
			scanner = new WiiuseJScanner(wiimoteList, numberOfScans, callback);
		}
		
		return scanner;
	}

	protected Runnable callback;
	
	protected int numberOfScans;
	
	protected DeviceList<WiimoteDevice> wiimoteList;
	
	protected WiimoteScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		this.wiimoteList = wiimoteList;
		this.numberOfScans = numberOfScans;
		this.callback = callback;
	}
	
}
