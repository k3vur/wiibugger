package wiibugger.pc;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import wiibugger.pc.nxt.NXTDevice;

public class RunWiibuggerAction extends AbstractAction {


	private static final long serialVersionUID = 1L;

	private DeviceList<NXTDevice> nxtDevices;

	public RunWiibuggerAction() {
		super("Run Wiibugger!");
		//this.setEnabled(false);
		// TODO UNCOMMENT THIS BEFORE RELEASING!
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		Wiibugger.startNXTMessager();
		Wiibugger.initWiimotes();
		// TODO add some fancy message that wiibugger is running
	}

	public void enableIfReady() {
		if (Wiibugger.readyToRun()) {
			this.setEnabled(true);
		}
	}
}