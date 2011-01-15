package wiibugger.pc.wiimote;

import wiibugger.communication.NXTMessage;
import wiibugger.pc.nxt.NXTMessager;

public class WiimoteEventHandler {

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	
	private static short NXT_MOVE = -1;
	private static short NXT_ARM = -1;

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
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_MOVE, NXTMessage.PORT_B , NXTMessage.MOTOR_FORWARD, (short)0));
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
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_MOVE, NXTMessage.PORT_A , NXTMessage.MOTOR_FORWARD, (short)0));
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
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_ARM, NXTMessage.PORT_A , NXTMessage.MOTOR_FLOAT, (short)0));
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_MOVE, NXTMessage.PORT_C , NXTMessage.MOTOR_STOP, (short)0));
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_MOVE, NXTMessage.PORT_B , NXTMessage.MOTOR_STOP, (short)0));
			break;
		}					
	}
	private static void buttonReleasedRight(int button) {
		switch(button) {
		case A_BUTTON:
			rightSensing = false;
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_ARM, NXTMessage.PORT_B , NXTMessage.MOTOR_FLOAT, (short)0));
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_ARM, NXTMessage.PORT_C , NXTMessage.MOTOR_FLOAT, (short)0));

			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage((short)NXT_MOVE, NXTMessage.PORT_A , NXTMessage.MOTOR_STOP, (short)0));
			
			break;
		}				
	}
	
	public static void accelerationEvent(float x, float y, float z, int wiimoteNumber) {
		//NXTMessager messager = NXTMessager.getNXTMessager();
		//NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(wiimoteNumber);

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
		
		System.out.println("left or right" + leftOrRight);
		if(leftOrRight == WiimoteDevice.WIIMOTE_LEFT) 
			orientationEventLeft(x,y);
		else
			orientationEventRight(x,y);
		
	}

	public static void orientationEventLeft(float x, float y) {
		if(leftSensing) {
			short speedUpDown = calculateSpeedY(y);
			short speedLeftRight = calculateSpeedX(x);
			
			NXTMessage msg = null;
			short direction = -1;
			if(Math.abs(speedUpDown) > Math.abs(speedLeftRight)) {
				if(speedUpDown > 0) {
					direction = NXTMessage.MOTOR_FORWARD;
				}
				else {
					speedUpDown *= -1;
					direction = NXTMessage.MOTOR_BACKWARD;
				}
				msg = new NXTMessage(NXT_ARM, NXTMessage.PORT_A, direction, speedUpDown);
			} else {
				if(speedLeftRight > 0) 
					direction = NXTMessage.MOTOR_FORWARD;
				else {
					speedLeftRight *= -1;
					direction = NXTMessage.MOTOR_BACKWARD;
				}
				msg = new NXTMessage(NXT_MOVE, NXTMessage.PORT_A, direction, speedLeftRight);					
			}
			NXTMessager.getNXTMessager().send(msg);
		}
		
	}
	
	public static void orientationEventRight(float x, float y) {
		if(rightSensing) {
			short speedUpDown = calculateSpeedY(y);
			short speedLeftRight = calculateSpeedX(x);
			
			NXTMessage msg = null;
			short direction = -1;
			if(Math.abs(speedUpDown) > Math.abs(speedLeftRight)) {
				
				if(speedUpDown > 0) {
					direction = NXTMessage.MOTOR_FORWARD;
				}
				else {
					speedUpDown *= -1;
					direction = NXTMessage.MOTOR_BACKWARD;
				}
				msg = new NXTMessage(NXT_ARM, NXTMessage.PORT_B, direction, speedUpDown);
			} else {
				if(speedLeftRight > 0) 
					direction = NXTMessage.MOTOR_FORWARD;
				else {
					speedLeftRight *= -1;
					direction = NXTMessage.MOTOR_BACKWARD;
				}
				msg = new NXTMessage(NXT_MOVE, NXTMessage.PORT_C, direction, speedLeftRight);					
			}
			NXTMessager.getNXTMessager().send(msg);
	
		}
	}

	private static short calculateSpeedY(float y) {
		return (short) ((50f/90f)*(y+90f));
	}
	private static short calculateSpeedX(float x) {
		return (short) ((50f/90f)*(x));
		
	}
	
	public static void setNXTMove(int id) {
		NXT_MOVE = (short)id;
	}
	
	public static void setNXTArm(int id) {
		NXT_ARM = (short)id;
	}
	
}
