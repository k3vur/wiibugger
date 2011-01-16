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

	private int leftOrRight;
	private boolean sendMotion;
	
	public WiiRemoteJListener(int wiimotePosition) {
		this.leftOrRight = wiimotePosition;
		
		/*
		 * Only send motion event every 100 milliseconds
		 */
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
						enableSendMotion();
					} catch (InterruptedException e) {}
				}
			}
		}.start();
	}
	
	synchronized private void enableSendMotion() {
		this.sendMotion = true;
	}
	
	synchronized private void disableSendMotion() {
		this.sendMotion = false;
	}
	
	@Override
	public void IRInputReceived(WRIREvent evt) { }

	@Override
	public void accelerationInputReceived(WRAccelerationEvent evt) {
		if (sendMotion) {
			disableSendMotion();
			WiimoteEventHandler.orientationEvent(evt.getXAcceleration(), evt.getYAcceleration(), evt.getZAcceleration(), leftOrRight);
		}
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
