package wiibugger.pc;

import java.io.IOException;

import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;
import wiibugger.pc.ui.UserInterface;
import wiibugger.pc.wiimote.WiimoteListener;
import wiiremotej.WiiRemote;

public class Wiibugger {

//	static NXTConnector connector;
//	static DataOutputStream nxtOut;
	
	public static final String applicationTitle = "Wiibugger";
	
	private static DeviceList<NXTDevice> nxtList;
	private static WiiRemote wiimote1, wiimote2; // TODO rename the two wiimotes?
	
	private static DeviceList<WiiRemote> wiimoteList;
	
	private static WiimoteListener wiimoteListener;
		
	public static void disconnectAllDevices() {
		disconnectWiimotes();
		disconnectNXTs();
	}
	
	private static void disconnectNXTs() {
		// TODO Wiibugger.disconnectNXTs()		
	}
	
	public static void disconnectWiimotes() {
		System.out.println("Disconnecting Wiimotes...");

		for (WiiRemote wiimote : getWiimoteList().toArrayList()) {
			wiimote.disconnect();
		}
		getWiimoteList().clear();
	}

	public static void disconnectWiimotesExcept(WiiRemote[] wiimotes) {
		System.out.println("Disconnecting Wiimotes...");
		
		DeviceList<WiiRemote> wiimoteList = getWiimoteList();
		boolean removeCurrWiimote = true;
		for (WiiRemote currWiimote: wiimoteList.toArrayList()) {
			
			for (WiiRemote dontRemoveThis: wiimotes) {
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
	
	public static DeviceList<WiiRemote> getWiimoteList() {
		if (Wiibugger.wiimoteList == null) {
			Wiibugger.wiimoteList = new DeviceList<WiiRemote>();
		}
		return Wiibugger.wiimoteList;
	}

	public static void initWiimotes() {
		wiimoteListener = new WiimoteListener();
		
		try {
			wiimote1.setLEDIlluminated(0, true);
			wiimote1.addWiiRemoteListener(wiimoteListener);
			wiimote1.setAccelerometerEnabled(true);
			
			wiimote2.setLEDIlluminated(3, true);
			wiimote2.addWiiRemoteListener(wiimoteListener);
			wiimote2.setAccelerometerEnabled(true);
			// TODO enable Buttons on one of the wiimotes (driving!)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    	System.setProperty("bluecove.stack.first", "widcomm");
    	
    	UserInterface.init();

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

}
