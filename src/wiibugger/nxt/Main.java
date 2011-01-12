package wiibugger.nxt;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class Main {
	
	public static final byte MOTOR_A = 1;
	public static final byte MOTOR_B = 2;
	public static final byte MOTOR_C = 3;

	static BTConnection btConnection;
	static DataInputStream inStream;
	// DataOutputStream outStream;
	static boolean running;
	public static void main(String[] args) {
		System.out.println("Welcome to\nWiibugger!\n");
		Button.ESCAPE.addButtonListener(new ExitListener());
		
		System.out.println("Waiting for\nconnection...\n");
		btConnection = Bluetooth.waitForConnection();
		
		System.out.println("connected");
		inStream = btConnection.openDataInputStream();
		
		running = true;
		
		byte motor;
		short speed;
		while(running) {
			try {
				motor = inStream.readByte();
				speed = inStream.readShort();
				processMessage(motor, speed);
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
	}
	
	public static void processMessage(byte motor, short speed) {
		switch(motor) {
		case MOTOR_A:
			Motor.A.forward();
			break;
		case MOTOR_B:
			Motor.A.stop();
			break;
		case MOTOR_C:
			Motor.C.forward();
			break;
		default:
			System.out.println("Motor does not exist");
		}
	}
}
