package wiibugger.pc.wiimote;

import wiibugger.communication.NXTMessage;
import wiibugger.pc.nxt.NXTMessager;

public class WiimoteEventHandler {

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;

	private static boolean leftSensing = false;
	private static boolean rightSensing = false;
	
	public static void buttonPressed(int button, int leftOrRight) {
		if(leftOrRight == WiimoteDevice.WIIMOTE_LEFT)
			buttonPressedLeft(button);
		else
			buttonPressedRight(button);
	}
		
	private static void buttonPressedLeft(int button) {
		switch(button) {
		case A_BUTTON:
			leftSensing = true;
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_FORWARD, (short)0));
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.MOVE, NXTMessage.LEFT_WHEEL , NXTMessage.MOTOR_FORWARD, (short)0));
			break;
		}				
	}
	
	private static void buttonPressedRight(int button) {
		switch(button) {
		case A_BUTTON:
			rightSensing = true;
			//NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage., (short)0));
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.MOVE, NXTMessage.RIGHT_WHEEL , NXTMessage.MOTOR_FORWARD, (short)0));
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
			leftSensing = false;
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.ARM, NXTMessage.WHOLE_ARM, NXTMessage.MOTOR_FLOAT, (short)0));
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.MOVE, NXTMessage.TURN, NXTMessage.MOTOR_STOP, (short)0));
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.MOVE, NXTMessage.LEFT_WHEEL , NXTMessage.MOTOR_STOP, (short)0));
			break;
		}					
	}
	private static void buttonReleasedRight(int button) {
		switch(button) {
		case A_BUTTON:
			rightSensing = false;
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.ARM, NXTMessage.ARM_MIDDLE , NXTMessage.MOTOR_FLOAT, (short)0));
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.ARM, NXTMessage.CLAW , NXTMessage.MOTOR_FLOAT, (short)0));

			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.MOVE, NXTMessage.RIGHT_WHEEL , NXTMessage.MOTOR_STOP, (short)0));
			
			break;
		}				
	}
	
	public static void orientationEvent(double xForce, double yForce, double zForce, int leftOrRight) {
		
		double x = xForce;
		double y = zForce; // motion in y-direction causes zForce changing

		/*
		 * Compensate too big Angles
		 * 
		 * When turning over 90 degrees in any direction,
		 * yForce is > 0 but zForce and xForce decrease => add them.
		 */
		if (yForce > 1) {
			if (y > 0) y += yForce;
			else y -= yForce;
			
			if (x > 0) x += yForce;
			else x -= yForce;
		}
		
		short speedY = calculateSpeed(y);
		short speedX = calculateSpeed(x);
		
		short directionY;
		if (speedY > 0) {
			directionY = NXTMessage.MOTOR_FORWARD;
		} else {
			speedY *= -1;
			directionY = NXTMessage.MOTOR_BACKWARD;
		}
		
		short directionX;
		if (speedX > 0) {
			directionX = NXTMessage.MOTOR_FORWARD;
		} else {
			speedX *= -1;
			directionX = NXTMessage.MOTOR_BACKWARD;
		}
		
		
		/*
		 * Determine action
		 */
		if (leftOrRight == WiimoteDevice.WIIMOTE_LEFT) {
			if (Math.abs(y) > Math.abs(x)) orientationEventLeftY(speedY, directionY);
			else orientationEventLeftX(speedX, directionX);
		} else {
			if (Math.abs(y) > Math.abs(x)) orientationEventRightY(speedY, directionY);
			else orientationEventRightX(speedX, directionX);
		}
		
	}
	
	public static void orientationEventLeftY(short speed, short direction) {
		if (!leftSensing) return;		
		NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.ARM, NXTMessage.WHOLE_ARM, direction, speed));
	}
	
	public static void orientationEventLeftX(short speed, short direction) {
		if (!leftSensing) return;
		NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.MOVE, NXTMessage.TURN, direction, speed));
	}
	
	public static void orientationEventRightY(short speed, short direction) {
		if (!rightSensing) return;
		NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.ARM, NXTMessage.ARM_MIDDLE, direction, speed));		
	}
	
	public static void orientationEventRightX(short speed, short direction) {
		if (!rightSensing) return;
		NXTMessager.getNXTMessager().send(new NXTMessage(NXTMessage.ARM, NXTMessage.CLAW, direction, speed));		
	}

	private static short calculateSpeed(double gForce) {
		return (short) (50 * gForce);
	}
}
