package wiibugger.pc.nxt;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

/**
 * Represents an NXT device with in- and output stream
 * @author kevin
 *
 */
public class NXTDevice {
	
	private DataOutputStream dataOut;
	private NXTInfo info;
	
	public NXTDevice(NXTInfo info) {
		this.info = info;
	}
	
	/**
	 * Trys to connect to an NXT and returns NXTDevice-Object
	 * @return NXTDevice-object if successful, null if can't find any.
	 */
	public static NXTDevice[] connectToNXT() {
			
		NXTComm communication = null;
		try {
			communication = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e) {
			System.out.println("Could not create bluetooth communication to a NXT...");
			e.printStackTrace();
			return null;
		}

		NXTInfo[] nxtInfo = null;
		try {
			nxtInfo = communication.search(null ,NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e) {
			System.out.println("Could not get Information of any NXT Device...");
			e.printStackTrace();
			return null;
		}

		NXTDevice[] nxtDevices = new NXTDevice[nxtInfo.length];
		
		for(int i = 0; i < nxtInfo.length; i++) {
			nxtDevices[i] = new NXTDevice(nxtInfo[i]);
		}
		return nxtDevices;
		
	}
	
	public boolean open() {
		System.out.println("Open connection to NXT " + info.deviceAddress + "...");
		
		try {
			NXTMessager.getNXTMessager().connectTo("btspp://" + info.deviceAddress);
			System.out.println("Created Outputstream to NXT " + info.deviceAddress + "...");
			return true;
		} catch (IOException e) {
			System.out.println("Could not connect to " + info.deviceAddress);
			return false;
		}
	}
	
	public String getName() {
		return info.name;
	}
	
	@Override
	public String toString() {
		return info.name;
	}
	
}
