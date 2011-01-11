package wiibugger.pc;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import wiibugger.pc.nxt.NXTDevice;

public class RunWiibuggerAction extends AbstractAction implements Runnable {


	private static final long serialVersionUID = 1L;

	private DeviceList<NXTDevice> nxtDevices;

	public RunWiibuggerAction() {
		super("Run Wiibugger!");
		//this.setEnabled(false);
		// TODO UNCOMMENT THIS BEFORE RELEASING!
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		this.run();
		
		Wiibugger.startNXTMessager();
		Wiibugger.initWiimotes();
		// TODO add some fancy message that wiibugger is running
	}

	public void enableIfReady() {
		if (Wiibugger.readyToRun()) {
			this.setEnabled(true);
		}
	}

	
	// TODO place this in a better location (own class or so)
	@Override
	public void run() {
		
		System.out.println("Wiibugger started...");
		
		nxtDevices = Wiibugger.getNXTList();
		
		NXTDevice nxt = (NXTDevice)nxtDevices.getElementAt(0);

		try {
			nxt.send((short)(Math.random()*360));
		} catch (IOException e) {
			System.out.println("could not write short");
			e.printStackTrace();
		}
		
	}

}