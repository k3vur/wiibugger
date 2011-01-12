package wiibugger.pc.wiimote.wiiremotej;

import java.io.IOException;

import wiibugger.pc.wiimote.WiimoteDevice;
import wiiremotej.WiiRemote;

public class WiiRemoteJDevice implements WiimoteDevice {

	private WiiRemote wiimote;
	
	public WiiRemoteJDevice(WiiRemote wiimote) {
		this.wiimote = wiimote;
	}

	@Override
	public void setLEDLights(boolean[] LEDs) {
		try {
			wiimote.setLEDLights(LEDs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void enableEventHandling() {
		wiimote.addWiiRemoteListener(WiiRemoteJListener.getListener());
	}

	@Override
	public void setAccelerometerEnabled(boolean enabled) {
		try {
			wiimote.setAccelerometerEnabled(enabled);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void disableEventHandling() {
		wiimote.removeWiiRemoteListener(WiiRemoteJListener.getListener());
	}

	@Override
	public void disconnect() {
		wiimote.disconnect();
	}

	@Override
	public String getBluetoothAddress() {
		return wiimote.getBluetoothAddress();
	}

}
