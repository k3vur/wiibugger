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
	
	public static void accelerationEvent(float x, float y, float z, int wiimoteNumber) {
		//NXTMessager messager = NXTMessager.getNXTMessager();
		//NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(wiimoteNumber);

	}
	
	public static void orientationEvent(float x, float y, int leftOrRight) {
		
		// catch to big angles of wiimote
		if(x < -90)
			x = -90;
		else if(x > 90)
			x = 90;
		
		if(y > 90)
			y = -180;
		else if( y > 0)
			y = 0;
		
		if(leftOrRight == WiimoteDevice.WIIMOTE_LEFT) 
			orientationEventLeft(x,y);
		else
			orientationEventRight(x,y);
		
	}

	public static void orientationEventLeft(float x, float y) {
		if (!leftSensing) return;
		
		short speedUpDown = calculateSpeedY(y);
		short speedLeftRight = calculateSpeedX(x);
		
		NXTMessage msg = null;
		short direction = -1;
		
		// Check which movement is higher (only one move direction is detected)
		if (Math.abs(speedUpDown) > Math.abs(speedLeftRight)) {
			
			// FORWARD
			if (speedUpDown > 0) {
				direction = NXTMessage.MOTOR_FORWARD;
				
			// BACKWARD
			} else {
				speedUpDown *= -1;
				direction = NXTMessage.MOTOR_BACKWARD;
			}
			msg = new NXTMessage(NXTMessage.ARM, NXTMessage.WHOLE_ARM, direction, speedUpDown);
			
		// MOVE AROUND X-AXIS
		} else {
			
			// FORWARD
			if (speedLeftRight > 0) { 
				direction = NXTMessage.MOTOR_FORWARD;
				
			// BACKWARD
			} else {
				speedLeftRight *= -1;
				direction = NXTMessage.MOTOR_BACKWARD;
			}
			msg = new NXTMessage(NXTMessage.MOVE, NXTMessage.TURN, direction, speedLeftRight);					
		}
		NXTMessager.getNXTMessager().send(msg);
	}
	
	public static void orientationEventRight(float x, float y) {
		if (!rightSensing) return;
		
		short speedUpDown = calculateSpeedY(y);
		short speedLeftRight = calculateSpeedX(x);
		
		NXTMessage msg = null;
		short direction = -1;
		
		// MOVE AROUND Y-AXIS
		if(Math.abs(speedUpDown) > Math.abs(speedLeftRight)) {
			
			// FORWARD
			if(speedUpDown > 0) {
				direction = NXTMessage.MOTOR_FORWARD;
				
			// BACKWARD
			} else {
				speedUpDown *= -1;
				direction = NXTMessage.MOTOR_BACKWARD;
			}
			msg = new NXTMessage(NXTMessage.ARM, NXTMessage.ARM_MIDDLE, direction, speedUpDown);
			
		// MOVE AROUND X-AXIS
		} else {
			
			// FORWARD
			if(speedLeftRight > 0) { 
				direction = NXTMessage.MOTOR_FORWARD;
			
			// BACKWARD
			} else {
				speedLeftRight *= -1;
				direction = NXTMessage.MOTOR_BACKWARD;
			}
			msg = new NXTMessage(NXTMessage.ARM, NXTMessage.CLAW, direction, speedLeftRight);					
		}
		NXTMessager.getNXTMessager().send(msg);
	}

	private static short calculateSpeedY(float y) {
		return (short) ((50f/90f)*(y+90f));
	}
	private static short calculateSpeedX(float x) {
		return (short) ((50f/90f)*(x));
		
	}	
}
