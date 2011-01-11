package wiibugger.pc.wiimote;


public abstract class WiimoteDevice {

	public abstract void setLEDLights(boolean[] LEDs);
	
	public abstract void addWiiRemoteListener(WiimoteDeviceListener listener);
	
	public abstract void setAccelerometerEnabled(boolean enabled);
	
	public abstract void removeWiiRemoteListener(WiimoteDeviceListener listener);

	public abstract void disconnect();

	public abstract String getBluetoothAddress();
	
}
