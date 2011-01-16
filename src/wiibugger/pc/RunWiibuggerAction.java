package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiibugger.pc.ui.UserInterface;

public class RunWiibuggerAction extends AbstractAction {


	private static final long serialVersionUID = 1L;

	public RunWiibuggerAction() {
		super("Run Wiibugger!");
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Starting Wiibugger...");
		UserInterface.getMainWindow().setVisible(false);
		Wiibugger.initNXTs();
		Wiibugger.startNXTMessager();
		Wiibugger.initWiimotes();
		UserInterface.showRunningWindow();
	}

	public void enableIfReady() {
		if (Wiibugger.readyToRun()) {
			this.setEnabled(true);
		}
	}
}