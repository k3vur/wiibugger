package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RunWiibuggerAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public RunWiibuggerAction() {
		super("Run Wiibugger!");
		this.setEnabled(false);
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
