package wiibugger.pc;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;
import wiibugger.pc.ui.UserInterface;
import wiibugger.pc.wiimote.WiimoteDevice;

public class Wiibugger {

//	static NXTConnector connector;
//	static DataOutputStream nxtOut;
	
	public static final String applicationTitle = "Wiibugger";
	
	private static DeviceList<NXTDevice> nxtList;
	
	private static DeviceList<WiimoteDevice> wiimoteList;
			
	public static void disconnectAllDevices() {
		disconnectWiimotes();
		disconnectNXTs();
	}
	
	public static void disconnectNXTs() {
		System.out.println("Disconnecting NXTs...");
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

	public static DeviceList<WiimoteDevice> getWiimoteList() {
		if (Wiibugger.wiimoteList == null) {
			Wiibugger.wiimoteList = new DeviceList<WiimoteDevice>();
		}
		return Wiibugger.wiimoteList;
	}

	public static void initWiimotes() throws IllegalStateException {
		
		if(Wiibugger.getWiimoteList().getSize() < 2) throw new IllegalStateException();
		
		WiimoteDevice wiimoteRight = (WiimoteDevice) Wiibugger.getWiimoteList().getElementAt(0);
		wiimoteRight.setLEDLights(new boolean[] { false, false, false, true});
		wiimoteRight.setAccelerometerEnabled(true);
		wiimoteRight.enableEventHandling(WiimoteDevice.WIIMOTE_RIGHT);
		

		WiimoteDevice wiimoteLeft = (WiimoteDevice) Wiibugger.getWiimoteList().getElementAt(1);
		wiimoteLeft.setLEDLights(new boolean[] { true, false, false, false});
		wiimoteLeft.setAccelerometerEnabled(true);
		wiimoteLeft.enableEventHandling(WiimoteDevice.WIIMOTE_LEFT);
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
					"Adress:\t" + bluetoothDevice.getBluetoothAddress());
		} catch (BluetoothStateException e) {
			System.out.println("Could not detect local Bluetooth Device");
		}	
    	
    	UserInterface.init();

	}

	public static boolean readyToRun() {
		return (wiimoteList.getSize() >= 2) && (nxtList.getSize() >= 2);
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
