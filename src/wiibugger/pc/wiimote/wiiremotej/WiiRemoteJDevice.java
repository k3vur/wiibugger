package wiibugger.pc.wiimote.wiiremotej;

import java.io.IOException;

import wiibugger.pc.wiimote.WiimoteDevice;
import wiiremotej.WiiRemote;

public class WiiRemoteJDevice implements WiimoteDevice {

	private WiiRemote wiimote;
	
	private WiiRemoteJListener listener;
	
	public WiiRemoteJDevice(WiiRemote wiimote) {
		this.wiimote = wiimote;
	}

	@Override
	public void setLEDLights(boolean[] LEDs) {
		try {
			wiimote.setLEDLights(LEDs);
		} catch (IllegalArgumentException e) {
			System.out.println("Illegal LED states");
		} catch (IOException e) {
			setLEDLights(LEDs);
		}
	}

	@Override
	public void enableEventHandling(int wiimotePosition) {
		listener = new WiiRemoteJListener(wiimotePosition);
		wiimote.addWiiRemoteListener(listener);
	}

	@Override
	public void disableEventHandling() {
		if (listener != null) {
			wiimote.removeWiiRemoteListener(listener);
		}
	}
	
	@Override
	public void setAccelerometerEnabled(boolean enabled) {
		try {
			wiimote.setAccelerometerEnabled(enabled);
		} catch (Exception e) {
			setAccelerometerEnabled(enabled);
		}
	}

	@Override
	public void disconnect() {
		wiimote.disconnect();
	}

	@Override
	public String getBluetoothAddress() {
		return wiimote.getBluetoothAddress();
	}
	
	@Override
	public String toString() {
		return wiimote.getBluetoothAddress();
	}

}
