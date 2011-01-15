package wiibugger.pc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;

import wiibugger.pc.Wiibugger;

public class SetNXTListener implements ActionListener {

	private JList nxtList;
	
	public SetNXTListener(JList nxtList) {
		this.nxtList = nxtList;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		JButton source = (JButton) evt.getSource();
		int selectedNxt = nxtList.getSelectedIndex();
		
		if (selectedNxt >= 0) {
			if (source == UserInterface.getSetNXTArmButton()) {
				Wiibugger.setNxtArm(selectedNxt);
			} else if (source == UserInterface.getSetNXTMoveButton()) {
				Wiibugger.setNxtMove(selectedNxt);
			}
		} else {
			System.out.println("Please select a Wiimote");
		}
		
		UserInterface.getRunWiibuggerAction().enableIfReady();
	}

}
