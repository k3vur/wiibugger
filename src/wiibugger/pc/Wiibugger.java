package wiibugger.pc;

import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.ui.UserInterface;
import wiiremotej.WiiRemote;

public class Wiibugger {

//	static NXTConnector connector;
//	static DataOutputStream nxtOut;
	
	private static DeviceList<NXTDevice> nxtList;
	private static DeviceList<WiiRemote> wiimoteList;
	
	public static DeviceList<NXTDevice> getNXTList() {
		if (Wiibugger.nxtList == null) {
			Wiibugger.nxtList = new DeviceList<NXTDevice>();
		}
		return Wiibugger.nxtList;
	}

	public static DeviceList<WiiRemote> getWiimoteList() {
		if (Wiibugger.wiimoteList == null) {
			Wiibugger.wiimoteList = new DeviceList<WiiRemote>();
		}
		return Wiibugger.wiimoteList;
	}

	public static void main(String[] args) {
		
		/**
		 * Some things can only be run as 32 bit mode (like Blue cove).
		 * Exit if run as 64 bit mode.
		 */
		if (Wiibugger.runsAs64Bit()) {
			System.out.println(
					"Wiibugger can only run in 32 bit mode\n" +
					"because it needs some 32 bit Libraries such as Bluecove.\n" +
					"\n" +
					"Make sure to run it in 32 bit mode by calling it with\n" + 
					"\"java -d32 Wiibugger\"");
			System.exit(0);
		}
		
    	/*
    	 * Wiimote uses pcm (something like Bluetooth "ports") < 4069
    	 * which is not recommended and checked by bluecove.
    	 * We have to turn that check off.
    	 */
    	System.setProperty("bluecove.jsr82.psm_minimum_off","true");
    	
    	/*
    	 * Wiimote needs Widcomm Bluetooth stack under windows to communicate
    	 */
    	System.setProperty("bluecove.stack.first", "widcomm");
    	
    	UserInterface.init();
    	
		
		/*
		 * Wii Connection
		 */
//		System.out.println("Trying to connect wo WiiMote");
//        //Find and connect to a Wii Remote
//        WiiRemote remote = null;
//        while (remote == null) {
//            try {
//                remote = WiiRemoteJ.findRemote();
//            } catch(Exception e) {
//                remote = null;
//                e.printStackTrace();
//                System.out.println("Failed to connect remote. Trying again.");
//            }
//        }

	}
	
	/**
	 * Returns true if the program is being run in 64 bit mode,
	 * and false if it isn't
	 * 
	 * @return true, if run in 64 bit mode, false if not.
	 */
	public static boolean runsAs64Bit() {
		return System.getProperty("sun.arch.data.model").indexOf("64") != -1;
	}

}
