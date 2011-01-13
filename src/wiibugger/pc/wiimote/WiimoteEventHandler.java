package wiibugger.pc.wiimote;

import wiibugger.communication.NXTMessage;
import wiibugger.pc.Wiibugger;
import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessager;

public class WiimoteEventHandler {

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static void AccelerationEvent(float x, float y, float z, int wiimoteNumber) {
		NXTMessager messager = NXTMessager.getNXTMessager();
		NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(wiimoteNumber);

	}
	public static void ButtonPressed(int button) {
		switch(button) {
		case A_BUTTON:
			
			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage.MOTOR_FORWARD, (short)0));
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_FORWARD, (short)0));
			
			break;
		}
	}
	public static void ButtonReleased(int button) {
		switch(button) {
		case A_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_A , NXTMessage.MOTOR_STOP, (short)0));
			
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage((short)0, NXTMessage.PORT_B , NXTMessage.MOTOR_STOP, (short)0));
			
			break;
		}		
	}
}
