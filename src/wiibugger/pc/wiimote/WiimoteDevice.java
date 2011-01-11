package wiibugger.pc.wiimote;

import wiiremotej.event.WiiRemoteListener;

public abstract class WiimoteDevice {

	public abstract void setLEDLights(boolean[] LEDs);
	
	public abstract void addWiiRemoteListener(WiiRemoteListener listener);
	
	public abstract void setAccelerometerEnabled(boolean enabled);
	
	public abstract void removeWiiRemoteListener(WiiRemoteListener listener);

	public abstract void disconnect();

	public abstract String getBluetoothAddress();
	
}
