package wiibugger.pc;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;
import wiibugger.pc.ui.UserInterface;
import wiibugger.pc.wiimote.WiimoteDevice;
import wiibugger.pc.wiimote.wiiusej.WiiuseJListener;
import wiiusej.WiiUseApiManager;

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
		stopNXTMessager();
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
		
		/* TODO reenable if you want the set1/2 buttons back
		if (wiimote1 == null || wiimote2 == null) {
			return false;
		}
		*/
		
		if(Wiibugger.getWiimoteList().getSize() < 2)
			return false;
		
		wiimote1 = (WiimoteDevice) Wiibugger.getWiimoteList().getElementAt(0);
		wiimote1.setLEDLights(new boolean[] { true, false, false, false });
		wiimote1.setAccelerometerEnabled(true);
		wiimote1.enableEventHandling(WiimoteDevice.WIIMOTE_RIGHT);
		

		wiimote2 = (WiimoteDevice) Wiibugger.getWiimoteList().getElementAt(1);
		wiimote2.setLEDLights(new boolean[] { false, false, false, true });
		wiimote2.setAccelerometerEnabled(true);
		wiimote2.enableEventHandling(WiimoteDevice.WIIMOTE_LEFT);
		// TODO enable Buttons on one of the wiimotes (driving!)
		
		return true;
	}

	public static void main(String[] args) {
		
		/*
		 * Some things can only be run as 32 bit mode (like Bluecove).
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
    	
    	if (!LocalDevice.isPowerOn()) {
    		System.out.println(
    				"Bluetooth seems to be turned off.\n" +
    				"Please turn it on before running Wiibugger.");
    		System.exit(0);
    	}
    	
    	try {
			LocalDevice bluetoothDevice = LocalDevice.getLocalDevice();
			System.out.println(
					"Your bluetooth-device:\n" +
					"Name:\t" +bluetoothDevice.getFriendlyName() + "\n" +
					"Adress:\t" + bluetoothDevice.getBluetoothAddress() + ")");
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	
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
	
	public static void stopNXTMessager() {
		NXTMessager.getNXTMessager().quit();
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

	public static void initNXTs() {
		for (NXTDevice nxt: nxtList.toArrayList()) {
			nxt.open();
		}
	}
	
}
