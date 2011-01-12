package wiibugger.pc;

import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;
import wiibugger.pc.ui.UserInterface;
import wiibugger.pc.wiimote.WiimoteDevice;

public class Wiibugger {

//	static NXTConnector connector;
//	static DataOutputStream nxtOut;
	
	public static final String applicationTitle = "Wiibugger";
	
	private static DeviceList<NXTDevice> nxtList;
	private static WiimoteDevice wiimote1, wiimote2; // TODO rename the two wiimotes?
	
	private static DeviceList<WiimoteDevice> wiimoteList;
			
	public static void disconnectAllDevices() {
		disconnectWiimotes();
		disconnectNXTs();
	}
	
	private static void disconnectNXTs() {
		System.out.println("Disconnecting NXTs...");
		
		for (NXTDevice nxt: getNXTList().toArrayList()) {
			nxt.close();
		}
		getNXTList().clear();
	}
	
	public static void disconnectWiimotes() {
		System.out.println("Disconnecting Wiimotes...");

		for (WiimoteDevice wiimote : getWiimoteList().toArrayList()) {
			wiimote.disconnect();
		}
		getWiimoteList().clear();
	}

	public static void disconnectWiimotesExcept(WiimoteDevice[] wiimotes) {
		System.out.println("Disconnecting Wiimotes...");
		
		DeviceList<WiimoteDevice> wiimoteList = getWiimoteList();
		boolean removeCurrWiimote = true;
		for (WiimoteDevice currWiimote: wiimoteList.toArrayList()) {
			
			for (WiimoteDevice dontRemoveThis: wiimotes) {
				if (currWiimote == dontRemoveThis) {
					removeCurrWiimote = false;
				}
			}
			
			if (removeCurrWiimote) {
				wiimoteList.remove(currWiimote);
				currWiimote.disconnect();
			} else {
				removeCurrWiimote = true;
			}
		}
	}

	public static void exit() {
		disconnectAllDevices();
		System.out.println("Exiting...");
		System.exit(0);
	}

	public static DeviceList<NXTDevice> getNXTList() {
		if (Wiibugger.nxtList == null) {
			Wiibugger.nxtList = new DeviceList<NXTDevice>();
		}
		return Wiibugger.nxtList;
	}
	
	public static WiimoteDevice getWiimote1() {
		return Wiibugger.wiimote1;
	}

	public static WiimoteDevice getWiimote2() {
		return Wiibugger.wiimote2;
	}

	public static DeviceList<WiimoteDevice> getWiimoteList() {
		if (Wiibugger.wiimoteList == null) {
			Wiibugger.wiimoteList = new DeviceList<WiimoteDevice>();
		}
		return Wiibugger.wiimoteList;
	}

	public static boolean initWiimotes() {
		
		if (wiimote1 == null || wiimote2 == null) {
			return false;
		}
				
		wiimote1.enableEventHandling();
		wiimote1.setAccelerometerEnabled(true);
		
		wiimote2.enableEventHandling();
		wiimote2.setAccelerometerEnabled(true);
		// TODO enable Buttons on one of the wiimotes (driving!)
		
		return true;
	}

	public static void main(String[] args) {
		
		/*
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
    	//System.setProperty("bluecove.stack.first", "widcomm");
  
    	
    	UserInterface.init();

	}

	public static boolean readyToRun() {
		return (wiimote1 != null && wiimote2 != null);
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

	public static void setWiimote1(WiimoteDevice wiimote) {
		
		if (wiimote1 != null) {
			wiimote1.setLEDLights(new boolean[] { true, true, true, true });
		}
		wiimote1 = wiimote;
		wiimote1.setLEDLights(new boolean[] { true, false, false, false });
		
		if (wiimote1 == wiimote2) {
			wiimote2 = null;
		}
		
		System.out.println("Set Wiimote 1 to " + wiimote1.getBluetoothAddress());
	}

	public static void setWiimote2(WiimoteDevice wiimote) {

		if (wiimote2 != null) {				
			wiimote2.setLEDLights(new boolean[] { true, true, true, true });
		}
		wiimote2 = wiimote;
		wiimote2.setLEDLights(new boolean[] { false, false, false, true });
		
		if (wiimote2 == wiimote1) {
			wiimote1 = null;
		}
		
		System.out.println("Set Wiimote 2 to " + wiimote2.getBluetoothAddress());
	}

	public static void startNXTMessager() {
		NXTMessager.getNXTMessager().start();
	}

	public static boolean isWindows(){
		 
		String os = System.getProperty("os.name").toLowerCase();
	    return (os.indexOf( "win" ) >= 0); 
 
	}
 
	public static boolean isMac(){
 
		String os = System.getProperty("os.name").toLowerCase();
	    return (os.indexOf( "mac" ) >= 0); 
 
	}
 
	public static boolean isLinux(){
 
		String os = System.getProperty("os.name").toLowerCase();
	    return (os.indexOf("linux") >= 0);
 
	}
	
}
