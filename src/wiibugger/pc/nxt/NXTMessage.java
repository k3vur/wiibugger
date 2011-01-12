package wiibugger.pc.nxt;

public class NXTMessage {
	
	NXTDevice device;
	byte motor;
	short speed;
	
	public NXTMessage(NXTDevice device, byte motor, short speed) {
		this.device = device;
		this.motor = motor;
		this.speed = speed;
	}
}
