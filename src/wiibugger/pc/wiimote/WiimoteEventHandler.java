package wiibugger.pc.wiimote;

import wiibugger.communication.NXTMessage;
import wiibugger.pc.Wiibugger;
import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;

public class WiimoteEventHandler {

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;

	private static boolean motorARunning = false;
	private static boolean motorBRunning = false;
	
	
	public static void buttonPressed(int button, int leftOrRight) {
		if(leftOrRight == WiimoteDevice.WIIMOTE_LEFT)
			buttonPressedLeft(button);
		else
			buttonPressedRight(button);
	}
	
	private static void buttonPressedLeft(int button) {
		switch(button) {
		case A_BUTTON:
			motorBRunning = true;
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_FORWARD, (short)0));
			break;
		case B_BUTTON:
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_BACKWARD, (short)0));
			
			break;
		}				
	}
	
	private static void buttonPressedRight(int button) {
		switch(button) {
		case A_BUTTON:
			motorARunning = true;
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage., (short)0));
			break;
		case B_BUTTON:
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage.MOTOR_BACKWARD, (short)0));
			
			break;
		}		
	}
	public static void buttonReleased(int button, int leftOrRight) {
		if(leftOrRight == WiimoteDevice.WIIMOTE_LEFT)
			buttonReleasedLeft(button);
		else
			buttonReleasedRight(button);
	}
	private static void buttonReleasedLeft(int button) {
		switch(button) {
		case A_BUTTON:
			motorBRunning = false;
			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_STOP, (short)0));
			break;
		case B_BUTTON:
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_STOP, (short)0));
			
			break;
		}					
	}
	private static void buttonReleasedRight(int button) {
		switch(button) {
		case A_BUTTON:
			motorARunning = false;
			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage.MOTOR_STOP, (short)0));
			break;
		case B_BUTTON:
//			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage.MOTOR_STOP, (short)0));
			
			break;
		}				
	}
	
	public static void accelerationEvent(float x, float y, float z, int wiimoteNumber) {
		NXTMessager messager = NXTMessager.getNXTMessager();
		NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(wiimoteNumber);

	}
	
	public static void orientationEvent(float x, float y, int leftOrRight) {
		if(x < -90)
			x = -90;
		else if(x > 90)
			x = 90;
		
		if(y > 90)
			y = -180;
		else if( y > 0)
			y = 0;
		
		System.out.println("x/roll after cut: " + x + " and y/pitch " + y);
		
		if(leftOrRight == WiimoteDevice.WIIMOTE_LEFT) 
			orientationEventLeft(x,y);
		else
			orientationEventRight(x,y);
		
	}

	public static void orientationEventLeft(float x, float y) {
		if(motorBRunning) {
			short speed = calculateSpeedY(y);
			System.out.println("Speed left: " + speed);
			NXTMessage msg = new NXTMessage((short)WiimoteDevice.WIIMOTE_LEFT, NXTMessage.PORT_B, NXTMessage.MOTOR_FORWARD, speed);
			NXTMessager.getNXTMessager().send(msg);
		}
	}
	
	public static void orientationEventRight(float x, float y) {
		if(motorARunning) {
			short speed = calculateSpeedY(y);
			System.out.println("speed right: " + speed);
			NXTMessage msg = new NXTMessage((short)WiimoteDevice.WIIMOTE_RIGHT, NXTMessage.PORT_A, NXTMessage.MOTOR_FORWARD, speed);
			NXTMessager.getNXTMessager().send(msg);
	
		}
	}

	private static short calculateSpeedY(float y) {
		return (short) ((150f/90f)*(y+90f));
	}
}
