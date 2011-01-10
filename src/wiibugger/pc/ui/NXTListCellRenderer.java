package wiibugger.pc.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import wiibugger.pc.nxt.NXTDevice;
import wiiremotej.WiiRemote;

public class NXTListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		String labelText = ((NXTDevice) value).toString();
		
		if (labelText == null) {
			labelText = "No Adress"; 
		}
		
		label.setText(labelText);
		
		return label;	
	}
}
