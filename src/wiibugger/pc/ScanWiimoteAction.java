package wiibugger.pc;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import wiibugger.pc.ui.UserInterface;
import wiibugger.pc.wiimote.WiimoteDevice;
import wiibugger.pc.wiimote.WiimoteScanner;

public class ScanWiimoteAction extends AbstractAction implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public ScanWiimoteAction() {
		super("Scan");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setEnabled(false);
		UserInterface.getScanNXTAction().setEnabled(false);
		DeviceList<WiimoteDevice> wiimoteList = Wiibugger.getWiimoteList();
		int numberToScan = 2 - wiimoteList.getSize();
		if (numberToScan > 0) {
			WiimoteScanner.scan(wiimoteList, numberToScan, this);
		}
	}

	@Override
	public void run() {
		this.setEnabled(true);
		UserInterface.getScanNXTAction().setEnabled(true);
	}
}
