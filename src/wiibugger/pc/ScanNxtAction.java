package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiibugger.pc.nxt.NXTScanner;

public class ScanNxtAction extends AbstractAction implements Runnable {

	private static final long serialVersionUID = 1L;

	public ScanNxtAction() {
		super("Scan");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setEnabled(false);
		NXTScanner.scan(Wiibugger.getNXTList(), this);
		
	}
	
	@Override
	public void run() {
		this.setEnabled(true);
	}
}
