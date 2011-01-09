package wiibugger.pc.nxt;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents an NXT device with in- and output stream
 * @author kevin
 *
 */
public class NXTDevice {
	
	private static DataOutputStream dataOut;
	
	/**
	 * Trys to connect to an NXT and returns NXTDevice-Object
	 * @return NXTDevice-object if successful, null if can't find any.
	 */
	public static NXTDevice connectToNXT() {
		// TODO NXTDevice.connectToNXT
		return null;
	}
	
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
	
}
