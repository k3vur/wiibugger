package wiibugger.nxt;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class Main {

	static BTConnection btConnection;
	static DataInputStream inStream;
	// DataOutputStream outStream;
	
	public static void main(String[] args) {
		System.out.println("Welcome to\nWiibugger!\n");
		Button.ESCAPE.addButtonListener(new ExitListener());
	
		System.out.println("Waiting for\nconnection...\n");
		btConnection = Bluetooth.waitForConnection();
		
		System.out.println("connected");
		inStream = btConnection.openDataInputStream();
		
		int angle;
		try {
			angle = (int)inStream.readShort();
			Motor.A.rotateTo(angle);
			System.out.println(angle);
		} catch (IOException e) {
			System.out.println("IOException");
		}
		Button.waitForPress();
	}
}
