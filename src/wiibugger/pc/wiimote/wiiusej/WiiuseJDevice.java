package wiibugger.pc.wiimote.wiiusej;

import wiibugger.pc.wiimote.WiimoteDevice;
import wiiusej.Wiimote;

public class WiiuseJDevice extends WiimoteDevice{

	private Wiimote wiimote;
	private WiiuseJListener listener;

	WiiuseJDevice(Wiimote wiimote) {
		this.wiimote = wiimote;
	}
	
	@Override
	public void disconnect() {
		wiimote.disconnect();
	}

	@Override
	public String getBluetoothAddress() {
		return wiimote.toString();
	}

	@Override
	public void setAccelerometerEnabled(boolean enabled) {
		// not needed i guess
	}

	@Override
	public void setLEDLights(boolean[] LEDs) {
		wiimote.setLeds(LEDs[0], LEDs[1], LEDs[2], LEDs[4]);
	}

	@Override
	public void enableEventHandling() {
		WiiuseJListener listener = new WiiuseJListener();
		wiimote.addWiiMoteEventListeners(listener);
	}

	@Override
	public void disableEventHandling() {
		wiimote.removeWiiMoteEventListeners(listener);
	}
	
	@Override
	public String toString() {
		return this.wiimote.getId()+"";
	}
}
