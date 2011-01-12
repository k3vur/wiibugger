package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiibugger.pc.Wiibugger;
import wiibugger.pc.wiimote.wiiremotej.WiiRemoteJScanner;
import wiibugger.pc.wiimote.wiiusej.WiiuseJScanner;


public abstract class WiimoteScanner extends Thread {
	
	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans) {
		return scan(wiimoteList, numberOfScans, null);
	}

	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callAfterFinish) {
		WiimoteScanner scanner = getScanner(wiimoteList, numberOfScans, callAfterFinish);
		scanner.start();
		return scanner;
	}
	
	public static WiimoteScanner getScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		WiimoteScanner scanner;
		
		if (Wiibugger.isMac()) {
			scanner = WiiRemoteJScanner.getScanner(wiimoteList, numberOfScans, callback);
		} else {
			scanner = WiiuseJScanner.getScanner(wiimoteList, numberOfScans, callback);
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
