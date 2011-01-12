package wiibugger.pc.wiimote;

import wiiremotej.WiiRemote;

public class WiiRemoteJDevice extends WiimoteDevice {

	private WiiRemote wiiRemoteJ;
	
	public WiiRemoteJDevice(WiiRemote wiimote) {
		this.wiiRemoteJ = wiimote;
	}

	@Override
	public void setLEDLights(boolean[] LEDs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWiiRemoteListener(WiimoteDeviceListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAccelerometerEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeWiiRemoteListener(WiimoteDeviceListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getBluetoothAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
