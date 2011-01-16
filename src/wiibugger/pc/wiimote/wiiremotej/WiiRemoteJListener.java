package wiibugger.pc.wiimote.wiiremotej;

import wiibugger.pc.wiimote.WiimoteEventHandler;
import wiiremotej.WiiRemoteExtension;
import wiiremotej.event.WRAccelerationEvent;
import wiiremotej.event.WRButtonEvent;
import wiiremotej.event.WRCombinedEvent;
import wiiremotej.event.WRExtensionEvent;
import wiiremotej.event.WRIREvent;
import wiiremotej.event.WRStatusEvent;
import wiiremotej.event.WiiRemoteListener;

public class WiiRemoteJListener implements WiiRemoteListener {

	private static WiiRemoteJListener listener;
	
	public static WiiRemoteJListener getListener() {
		if (listener == null) {
			listener = new WiiRemoteJListener();
		}
		return listener;
	}
	
	@Override
	public void IRInputReceived(WRIREvent evt) { }

	@Override
	public void accelerationInputReceived(WRAccelerationEvent evt) {
//		double xAcceleration = evt.getXAcceleration();
//		double yAcceleration = evt.getYAcceleration();
//		double zAcceleration = evt.getZAcceleration();
		
		//System.out.println("x: " + xAcceleration + "y: " + yAcceleration + "z: " + zAcceleration);
	}

	@Override
	public void buttonInputReceived(WRButtonEvent evt) {
		if (evt.wasPressed(WRButtonEvent.A)) WiimoteEventHandler.buttonPressed(WiimoteEventHandler.A_BUTTON, 0);
		
		if (evt.wasPressed(WRButtonEvent.B)) WiimoteEventHandler.buttonPressed(WiimoteEventHandler.B_BUTTON, 0);
		
		if (evt.wasReleased(WRButtonEvent.A)) WiimoteEventHandler.buttonReleased(WiimoteEventHandler.A_BUTTON, 0);
		
		if (evt.wasReleased(WRButtonEvent.B)) WiimoteEventHandler.buttonReleased(WiimoteEventHandler.B_BUTTON, 0);
	}

	@Override
	public void combinedInputReceived(WRCombinedEvent evt) { }

	@Override
	public void disconnected() { }

	@Override
	public void extensionConnected(WiiRemoteExtension extension) { }

	@Override
	public void extensionDisconnected(WiiRemoteExtension extension) { }

	@Override
	public void extensionInputReceived(WRExtensionEvent evt) { }

	@Override
	public void extensionPartiallyInserted() { }

	@Override
	public void extensionUnknown() { }

	@Override
	public void statusReported(WRStatusEvent evt) { }

}
