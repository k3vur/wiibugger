package wiibugger.nxt;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import wiibugger.communication.NXTMessage;

public class Main {

	static BTConnection btConnection;
	static DataInputStream inStream;
	static short mode;
	static boolean running;
	
	public static void main(String[] args) {
		System.out.println("Welcome to\nWiibugger!\n");
		Button.ESCAPE.addButtonListener(new ExitListener());
				
		mode = getMode();		
		
		System.out.println("Waiting for\nconnection...\n");
		btConnection = Bluetooth.waitForConnection();
		System.out.println("connected");
		
		inStream = btConnection.openDataInputStream();
		
		Motor.A.setPower(100);
		Motor.B.setPower(100);
		Motor.C.setPower(100);
		
		Sound.beep();
		running = true;
		short input;
		while(running) {
			try {
				input = inStream.readShort();
				processMessage(input);
			} catch (IOException e) {
				break;
			}
		}
	}
	
	private static short getMode() {
		System.out.println("Left = arm\n, right = move");
		int pressedButton = Button.waitForPress();
		
		switch (pressedButton) {
		case Button.ID_LEFT:
			System.out.println("Set mode to ARM");
			return NXTMessage.ARM;
		case Button.ID_RIGHT:
			System.out.println("Set mode to MOVE");
			return NXTMessage.MOVE;
		default:
			System.out.println("Wrong button");
			return getMode();
		}
	}

	public static void processMessage(short input) {
				
		// Close Program
		if (input == NXTMessage.CLOSE_MESSAGE) {
			running = false;
			return;
		}
		
		NXTMessage msg = new NXTMessage(input);
		
		if (msg.getNxt() == mode) {
			motorAction(msg);
		}
		
	}
	
	private static void motorAction(NXTMessage msg) {
		
		/*
		 * get the right motor
		 */
		Motor motor = null;
		switch(msg.getPort()) {
		case NXTMessage.MOTOR_A:
			motor = Motor.A;
			break;
		case NXTMessage.MOTOR_B:
			motor = Motor.B;
			break;
		case NXTMessage.MOTOR_C:
			motor = Motor.C;
			break;
		default:
			System.out.println("unkown port");
			return;
		}
		
		/*
		 * execute right operation
		 */
		switch(msg.getOperation()) {
		case NXTMessage.MOTOR_FORWARD:
			if(msg.getValue() != 0)
				motor.setSpeed(msg.getValue());
			motor.forward();
			break;
		case NXTMessage.MOTOR_BACKWARD:
			if(msg.getValue() != 0)
				motor.setSpeed(msg.getValue());
			motor.backward();
			break;
		case NXTMessage.MOTOR_STOP:
			motor.stop();
			break;
		case NXTMessage.MOTOR_ROTATE:
			motor.rotate(msg.getValue());
			break;
		case NXTMessage.MOTOR_ROTATE_TO:
			motor.rotateTo(msg.getValue());
			break;
		case NXTMessage.MOTOR_FLOAT:
			motor.flt();
			break;
		default:
			System.out.println("motor action unkown");
		}
	}
}
