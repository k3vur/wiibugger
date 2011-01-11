package wiibugger.pc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;

import wiibugger.pc.Wiibugger;
import wiibugger.pc.wiimote.WiimoteDevice;

public class SetWiimoteListener implements ActionListener {

	private JList wiimoteList;
	
	public SetWiimoteListener(JList wiimoteList) {
		this.wiimoteList = wiimoteList;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		JButton source = (JButton) evt.getSource();
		WiimoteDevice selectedWiimote = (WiimoteDevice) wiimoteList.getSelectedValue();
		
		if (selectedWiimote != null) {
			if (source == UserInterface.getSetWiimote1Button()) {
				Wiibugger.setWiimote1(selectedWiimote);
			} else if (source == UserInterface.getSetWiimote2Button()) {
				Wiibugger.setWiimote2(selectedWiimote);
			}
		} else {
			System.out.println("Please select a Wiimote");
		}
		
		UserInterface.getRunWiibuggerAction().enableIfReady();
	}

}
