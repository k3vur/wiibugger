package wiibugger.pc.wiimote.wiiusej;

import wiibugger.pc.DeviceList;
import wiibugger.pc.wiimote.WiimoteDevice;
import wiibugger.pc.wiimote.WiimoteScanner;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class WiiuseJScanner extends WiimoteScanner {

	protected WiiuseJScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		super(wiimoteList, numberOfScans, callback);
	}
	
	public static WiiuseJScanner getScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {		
		return new WiiuseJScanner(wiimoteList, numberOfScans, callback);
	}
	
	@Override
	public void run() {
		System.out.println("Scanning for Wiimotes using WiiuseJ...");
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(2, true);
		
		if(wiimotes.length == 0) {
			System.out.println("No Wiimote Found");
			return;
		}
		for(int i = 0; i < wiimotes.length; i++) {
			System.out.println("Found wiimote " + wiimotes[i].getId());
		}
		
		System.out.println("Finished scanning");
	}

}
