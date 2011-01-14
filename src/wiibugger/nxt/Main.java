package wiibugger.nxt;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import wiibugger.communication.NXTMessage;


public class Main {

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
	
	public static void processMessage(short input) {
		if (input == NXTMessage.CLOSE_MESSAGE) {
			running = false;
			return;
		}
		
		NXTMessage msg = new NXTMessage(input);
		
		System.out.println("msg value: " + msg.getValue());
		
		short port = msg.getPort();
		if(port == NXTMessage.PORT_A || port == NXTMessage.PORT_B || port == NXTMessage.PORT_C)
			motorAction(msg);
		
	}
	
	private static void motorAction(NXTMessage msg) {
		Motor motor = null;
		switch(msg.getPort()) {
		case NXTMessage.PORT_A:
			motor = Motor.A;
			break;
		case NXTMessage.PORT_B:
			motor = Motor.B;
			break;
		case NXTMessage.PORT_C:
			motor = Motor.C;
			break;
		default:
			System.out.println("unkown port");
			return;
		}
		
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
		case NXTMessage.MOTOR_FLOAT:
			motor.flt();
			break;
		default:
			System.out.println("motor action unkown");
		}
	}
}
