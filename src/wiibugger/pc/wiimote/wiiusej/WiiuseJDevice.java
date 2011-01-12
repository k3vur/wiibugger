package wiibugger.pc.wiimote.wiiusej;

import wiibugger.pc.wiimote.WiimoteDevice;
import wiibugger.pc.wiimote.WiimoteDeviceListener;
import wiiremotej.event.WiiRemoteListener;
import wiiusej.Wiimote;

public class WiiuseJDevice extends WiimoteDevice{

	private Wiimote wiimote;
	private WiiuseJListener listener;

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
	public void addWiiRemoteListener() {
		WiiuseJListener listener = new WiiuseJListener();
		wiimote.addWiiMoteEventListeners(listener);
	}

	@Override
	public void removeWiiRemoteListener() {
		wiimote.removeWiiMoteEventListeners(listener);
	}
}
