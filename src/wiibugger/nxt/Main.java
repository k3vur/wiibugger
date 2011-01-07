package wiibugger.nxt;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class Main {

	static BTConnection btConnection;
	static DataInputStream inStream;
	// DataOutputStream outStream;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Wiibugger");
		
		
		while (true) {
			System.out.println("Waiting for connection...");
			btConnection = Bluetooth.waitForConnection();
			inStream = btConnection.openDataInputStream();
			
			while(true) {
				int angle;
				try {
					angle = (int)inStream.readShort();
					Motor.A.rotateTo(angle);
				} catch (IOException e) {
					System.out.println("IOException");
				}
			}
			
		}
	}

}
