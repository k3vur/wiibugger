package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiibugger.pc.Wiibugger;
import wiibugger.pc.wiimote.wiiremotej.WiiRemoteJScanner;
import wiibugger.pc.wiimote.wiiusej.WiiuseJScanner;


public abstract class WiimoteScanner extends Thread {
	
	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfWiimotes) {
		return scan(wiimoteList, numberOfWiimotes, null);
	}

	public static WiimoteScanner scan(DeviceList<WiimoteDevice> wiimoteList, int numberOfWiimotes, Runnable callAfterFinish) {
		WiimoteScanner scanner = getScanner(wiimoteList, numberOfWiimotes, callAfterFinish);
		scanner.start();
		return scanner;
	}
	
	public static WiimoteScanner getScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfWiimotes, Runnable callback) {
		WiimoteScanner scanner;
		
		if (Wiibugger.isMac()) {
			scanner = WiiRemoteJScanner.getScanner(wiimoteList, numberOfWiimotes, callback);
		} else {
			scanner = WiiuseJScanner.getScanner(wiimoteList, numberOfWiimotes, callback);
		}
		
		return scanner;
	}

	protected Runnable callback;
	
	protected int numberOfWiimotes;
	
	protected DeviceList<WiimoteDevice> wiimoteList;
	
	protected WiimoteScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfWiimotes, Runnable callback) {
		this.wiimoteList = wiimoteList;
		this.numberOfWiimotes = numberOfWiimotes;
		this.callback = callback;
	}	
}