package wiibugger.pc.wiimote.wiiusej;

import wiibugger.pc.wiimote.WiimoteDevice;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.utils.WiimoteListener;

public class WiiuseJDevice extends WiiUseApiManager implements WiimoteDevice{

	private Wiimote wiimote;
	private WiimoteListener listener;

	WiiuseJDevice(Wiimote wiimote) {
		this.wiimote = wiimote;
	}
	
	@Override
	public void disconnect() {
		wiimote.setLeds(false, false, false, false);
		wiimote.deactivateMotionSensing();
		wiimote.disconnect();
		
	}

	@Override
	public String getBluetoothAddress() {
		return wiimote.getId()+"";
	}

	@Override
	public void setAccelerometerEnabled(boolean enabled) {
		this.activateSmoothing(wiimote.getId());
		this.activateMotionSensing(wiimote.getId());
	}

	@Override
	public void setLEDLights(boolean[] LEDs) {
		wiimote.setLeds(LEDs[0], LEDs[1], LEDs[2], LEDs[3]);
	}

	@Override
	public void enableEventHandling(int leftOrRight) {

		listener = new WiiuseJListener(leftOrRight);
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
