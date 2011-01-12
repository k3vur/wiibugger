package wiibugger.pc.wiimote.wiiremotej;

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
	public void IRInputReceived(WRIREvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accelerationInputReceived(WRAccelerationEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buttonInputReceived(WRButtonEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void combinedInputReceived(WRCombinedEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void extensionConnected(WiiRemoteExtension extension) {
		// TODO Auto-generated method stub

	}

	@Override
	public void extensionDisconnected(WiiRemoteExtension extension) {
		// TODO Auto-generated method stub

	}

	@Override
	public void extensionInputReceived(WRExtensionEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void extensionPartiallyInserted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void extensionUnknown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void statusReported(WRStatusEvent evt) {
		// TODO Auto-generated method stub

	}

}
