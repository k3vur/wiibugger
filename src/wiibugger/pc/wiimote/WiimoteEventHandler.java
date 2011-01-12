package wiibugger.pc.wiimote;

import wiibugger.pc.Wiibugger;
import wiibugger.pc.nxt.NXTDevice;
import wiibugger.pc.nxt.NXTMessage;
import wiibugger.pc.nxt.NXTMessager;

public class WiimoteEventHandler {

	public static void AccelerationEvent(float x, float y, float z, int wiimoteNumber) {
		NXTMessager messager = NXTMessager.getNXTMessager();
		NXTDevice nxt = (NXTDevice) Wiibugger.getNXTList().getElementAt(wiimoteNumber);
		
		// TODO
		byte motor = 0;
		short speed = 0;
		messager.send(new NXTMessage(nxt, motor, speed));
	}
}
