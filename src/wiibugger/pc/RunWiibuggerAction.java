package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiibugger.pc.ui.UserInterface;

public class RunWiibuggerAction extends AbstractAction {


	private static final long serialVersionUID = 1L;

	public RunWiibuggerAction() {
		super("Run Wiibugger!");
		this.setEnabled(false);
		// TODO UNCOMMENT THIS BEFORE RELEASING!
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Starting Wiibugger...");
		UserInterface.getMainWindow().setVisible(false);
		Wiibugger.initNXTs();
		Wiibugger.startNXTMessager();
		Wiibugger.initWiimotes();
		UserInterface.showRunningWindow();
		// TODO add some fancy message that wiibugger is running
	}

	public void enableIfReady() {
		if (Wiibugger.readyToRun()) {
			this.setEnabled(true);
		}
	}
}