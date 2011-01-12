package wiibugger.pc.wiimote;


public abstract class WiimoteDevice {

	public abstract void setLEDLights(boolean[] LEDs);
	
	public abstract void enableEventHandling();
	
	public abstract void setAccelerometerEnabled(boolean enabled);
	
	public abstract void disableEventHandling();

	public abstract void disconnect();

	public abstract String getBluetoothAddress();
	
}
