package wiibugger.pc.wiimote;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import wiiremotej.WiiRemote;

public class DeviceList<DeviceType> extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<DeviceType> wiimoteList;
	
	public DeviceList() {
		this.wiimoteList = new ArrayList<DeviceType>();
	}

	@Override
	public Object getElementAt(int i) {
		return this.wiimoteList.get(i);
	}

	@Override
	public int getSize() {
		return this.wiimoteList.size();
	}
	
	public void add(DeviceType remote) {
		this.wiimoteList.add(remote);
		this.fireIntervalAdded(this, getSize()-1, getSize()-1);
	}
	
	public void remove(WiiRemote remote) {
		int index = this.wiimoteList.indexOf(remote);
		if (index != -1) {
			remove(index);
		}
	}
	
	public void remove(int i) {
		this.wiimoteList.remove(i);
		this.fireIntervalRemoved(this, i, i);
	}
	
	public void clear() {
		this.wiimoteList.clear();
		this.fireIntervalRemoved(this, 0, getSize()-1);
	}

}
