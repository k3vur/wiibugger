package wiibugger.pc.wiimote;

import wiibugger.pc.DeviceList;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;


public class WiimoteScanner extends Thread {

	public static WiimoteScanner scan(DeviceList<WiiRemote> wiimoteList, int numberOfScans) {
		WiimoteScanner scanner = new WiimoteScanner(wiimoteList, numberOfScans);
		scanner.start();
		return scanner;
	}
	private int numberOfScans;
	
	private DeviceList<WiiRemote> wiimoteList;
	
	private WiimoteScanner(DeviceList<WiiRemote> wiimoteList, int numberOfScans) {
		this.wiimoteList = wiimoteList;
		this.numberOfScans = numberOfScans;
	}
	
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
				wiimoteList.add(remote);
				remote = null;
			}
		}
	}
	
}
