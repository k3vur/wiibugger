package wiibugger.pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTConnector;

import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;
import wiibugger.pc.ui.UserInterface;
import wiibugger.pc.wiimote.WiiMoteListener;
import wiiremotej.WiiRemote;

public class Wiibugger {

//	static NXTConnector connector;
//	static DataOutputStream nxtOut;
	
	public static final String applicationTitle = "Wiibugger";
	
	private static DeviceList<NXTDevice> nxtList;
	private static WiiRemote wiimote1, wiimote2; // TODO rename the two wiimotes?
	
	private static DeviceList<WiiRemote> wiimoteList;
	
	private static WiiMoteListener wiimoteListener;
		
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
	
	public static WiiRemote getWiimote1() {
		return Wiibugger.wiimote1;
	}

	public static WiiRemote getWiimote2() {
		return Wiibugger.wiimote2;
	}

	public static DeviceList<WiiRemote> getWiimoteList() {
		if (Wiibugger.wiimoteList == null) {
			Wiibugger.wiimoteList = new DeviceList<WiiRemote>();
		}
		return Wiibugger.wiimoteList;
	}

	public static boolean initWiimotes() {
		
		if (wiimote1 == null || wiimote2 == null) {
			return false;
		}
		
		wiimoteListener = new WiiMoteListener();
		
		try {
			wiimote1.addWiiRemoteListener(wiimoteListener);
			wiimote1.setAccelerometerEnabled(true);
			
			wiimote2.addWiiRemoteListener(wiimoteListener);
			wiimote2.setAccelerometerEnabled(true);
			// TODO enable Buttons on one of the wiimotes (driving!)
		} catch (IOException e) {
			wiimote1.removeWiiRemoteListener(wiimoteListener);
			wiimote2.removeWiiRemoteListener(wiimoteListener);
			return false;
		}
		
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

	public static void setWiimote1(WiiRemote wiimote) {
		
		try {
			if (wiimote1 != null) {
				wiimote1.setLEDLights(new boolean[] { true, true, true, true });
			}
			wiimote1 = wiimote;
			wiimote1.setLEDLights(new boolean[] { true, false, false, false });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (wiimote1 == wiimote2) {
			wiimote2 = null;
		}
		
		System.out.println("Set Wiimote 1 to " + wiimote1.getBluetoothAddress());
	}

	public static void setWiimote2(WiiRemote wiimote) {

		try {
			if (wiimote2 != null) {				
				wiimote2.setLEDLights(new boolean[] { true, true, true, true });
			}
			wiimote2 = wiimote;
			wiimote2.setLEDLights(new boolean[] { false, false, false, true });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (wiimote2 == wiimote1) {
			wiimote1 = null;
		}
		
		System.out.println("Set Wiimote 2 to " + wiimote2.getBluetoothAddress());
	}

	public static void startNXTMessager() {
		NXTMessager.getNXTMessager().start();
	}
	
}
