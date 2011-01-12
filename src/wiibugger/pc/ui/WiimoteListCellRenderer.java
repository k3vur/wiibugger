package wiibugger.pc.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import wiibugger.pc.wiimote.WiimoteDevice;

/**
 * Overrides getListCellRendererComponent because WiiRemote doesn't have a toString() method.
 * This Class is used for the WiimoteList, it displays the Wiimotes Bluetooth-adress or "No Adress".
 *
 */
public class WiimoteListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		String labelText = ((WiimoteDevice) value).getBluetoothAddress();
		
		if (labelText == null) {
			labelText = "No Adress"; 
		}
		
		label.setText(labelText);
		
		return label;
	}
	
}
