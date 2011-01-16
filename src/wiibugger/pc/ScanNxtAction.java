package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiibugger.pc.nxt.NXTScanner;
import wiibugger.pc.ui.UserInterface;

public class ScanNxtAction extends AbstractAction implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public ScanNxtAction() {
		super("Scan");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setEnabled(false);
		UserInterface.getScanWiimotesAction().setEnabled(false);
		NXTScanner.scan(Wiibugger.getNXTList(), this);
		
	}
	
	@Override
	public void run() {
		this.setEnabled(true);
		UserInterface.getScanWiimotesAction().setEnabled(true);
		UserInterface.getNXTList().repaint();
		UserInterface.getRunWiibuggerAction().enableIfReady();
	}
}
