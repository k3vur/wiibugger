package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class WiiuseJScanner extends WiimoteScanner {

	protected WiiuseJScanner(DeviceList<WiimoteDevice> wiimoteList, int numberOfScans, Runnable callback) {
		super(wiimoteList, numberOfScans, callback);
	}
	
	@Override
	public void run() {
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(2, true);
		
		if(wiimotes.length == 0) {
			System.out.println("No Wiimote Found");
			return;
		}
		for(int i = 0; i < wiimotes.length; i++) {
			System.out.println("Found wiimote " + wiimotes[i].getId());
		}
	}

}
