package wiibugger.pc.wiimote;

import wiibugger.nxt.Main;
import wiibugger.pc.Wiibugger;
import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessage;
import wiibugger.pc.nxt.NXTMessager;

public class WiimoteEventHandler {

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static void AccelerationEvent(float x, float y, float z, int wiimoteNumber) {
		NXTMessager messager = NXTMessager.getNXTMessager();
		NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(wiimoteNumber);
		
		// TODO
		byte motor = 0;
		short speed = 0;
		messager.send(new NXTMessage(nxt, motor, speed));
	}
	public static void ButtonEvent(int button) {
		NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(0);
		switch(button) {
		case A_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage(nxt, Main.MOTOR_A , (short)0));
			break;
		case B_BUTTON:
			NXTMessager.getNXTMessager().send(new NXTMessage(nxt, Main.MOTOR_B , (short)0));
			break;
		}
	}
}
