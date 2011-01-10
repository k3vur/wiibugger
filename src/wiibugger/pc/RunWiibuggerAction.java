package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RunWiibuggerAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public RunWiibuggerAction() {
		super("Run Wiibugger!");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Wiibugger.startNXTMessager();
		Wiibugger.initWiimotes();
	}

}
