package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiiremotej.WiiRemote;

public class ScanWiimoteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public ScanWiimoteAction() {
		super("Scan");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Wiibugger.getWiimoteList().add(new WiiRemote(null));
	}

}
