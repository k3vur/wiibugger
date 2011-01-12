package wiibugger.pc.nxt;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

/**
 * Represents an NXT device with in- and output stream
 * @author kevin
 *
 */
public class NXTDevice {
	
	private DataOutputStream dataOut;
	private NXTComm communication;
	private NXTInfo info;
	
	public NXTDevice(NXTComm communication, NXTInfo info) {
		this.info = info;
		this.communication = communication;
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
			nxtDevices[i] = new NXTDevice(communication, nxtInfo[i]);
		}
		return nxtDevices;
		
	}
	
	public boolean open() {
		System.out.println("Open connection to NXT " + info.deviceAddress + "...");

		try {
			this.communication.open(info);
		} catch (NXTCommException e) {
			System.out.println("Could not open connection to NXT " + info.deviceAddress + "...");
			e.printStackTrace();
			return false;
		}
		
		dataOut = new DataOutputStream(communication.getOutputStream());
		
		if(dataOut == null) {
			System.out.println("DataOutputStream could not be created on NXT " + info.deviceAddress);
			return false;
		} 
		System.out.println("Created Outputstream to NXT " + info.deviceAddress + "...");		
		return true;
	}
	
	public boolean close() {
		
		try {
			this.communication.close();
		} catch (IOException e) {
			System.out.println("Could not close connection of NXT " + info.deviceAddress);
			e.printStackTrace();
		}
		return true;
	}
	
	public void send(byte data) throws IOException {
		if(dataOut == null) {
			this.open();
		}
		dataOut.writeByte(data);
		dataOut.flush();
	}
	
	public void send(int data) throws IOException {
		if(dataOut == null) {
			this.open();
		}
		dataOut.writeInt(data);
		dataOut.flush();
	}
	
	public void send(short data) throws IOException {
		if(dataOut == null) {
			this.open();
		}
		dataOut.writeShort(data);
		dataOut.flush();
	}
	
	@Override
	public String toString() {
		return info.deviceAddress;
	}
	
}
