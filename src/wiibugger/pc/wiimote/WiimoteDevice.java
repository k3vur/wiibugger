package wiibugger.pc.wiimote;


public interface WiimoteDevice {

	public static final int WIIMOTE_RIGHT = 0;
	public static final int WIIMOTE_LEFT = 1;
	
	public abstract void setLEDLights(boolean[] LEDs);
	
	public abstract void enableEventHandling(int leftOrRight);
	
	public abstract void setAccelerometerEnabled(boolean enabled);
	
	public abstract void disableEventHandling();

	public abstract void disconnect();

	public abstract String getBluetoothAddress();
	
}
