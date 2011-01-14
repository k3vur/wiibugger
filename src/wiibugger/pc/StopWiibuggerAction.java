package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiibugger.pc.nxt.NXTMessager;

public class StopWiibuggerAction extends AbstractAction {


	private static final long serialVersionUID = 1L;

	public StopWiibuggerAction() {
		super("Stop Wiibugger :-(");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		NXTMessager.getNXTMessager().quit();
		System.out.println("Stopped Wiibugger...");
	}
}

