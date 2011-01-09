package wiibugger.pc.nxt;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTInfo;

/**
 * Represents an NXT device with in- and output stream
 * @author kevin
 *
 */
public class NXTDevice {
	
	/**
	 * Trys to connect to an NXT and returns NXTDevice-Object
	 * @return NXTDevice-object if successful, null if can't find any.
	 */
	public static NXTDevice connectToNXT() {
		// TODO NXTDevice.connectToNXT
		return null;
	}
	private DataOutputStream dataOut;
	
	private NXTInfo info;
	
	public void send(byte data) throws IOException {
		dataOut.write(data);
		dataOut.flush();
	}
	
	public void send(int data) throws IOException {
		dataOut.write(data);
		dataOut.flush();
	}
	
	public void send(short data) throws IOException {
		dataOut.write(data);
		dataOut.flush();
	}
	
	@Override
	public String toString() {
		return info.deviceAddress;
	}
	
}
