package wiibugger.pc.wiimote.wiiremotej;

import wiibugger.pc.wiimote.WiimoteDevice;
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
	public void addWiiRemoteListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAccelerometerEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeWiiRemoteListener() {
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
