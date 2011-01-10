package wiibugger.pc;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import wiibugger.pc.nxt.NXTDevice;

public class RunWiibuggerAction extends AbstractAction implements Runnable {


	private static final long serialVersionUID = 1L;

	private DeviceList<NXTDevice> nxtDevices;
	
	// TODO place this in a better location (own class or so)
	@Override
	public void run() {
		
		System.out.println("Wiibugger started...");
		
		nxtDevices = Wiibugger.getNXTList();
		
		NXTDevice nxt = (NXTDevice)nxtDevices.getElementAt(0);
		while(true) {
			try {
				nxt.send((short)(Math.random()*360));
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println("could not write short");
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.run();
		this.setEnabled(false);
		
	}

}
