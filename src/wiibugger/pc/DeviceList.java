package wiibugger.pc;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import wiiremotej.WiiRemote;

public class DeviceList<DeviceType> extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<DeviceType> deviceList;
	
	public DeviceList() {
		this.deviceList = new ArrayList<DeviceType>();
	}

	public void add(DeviceType remote) {
		this.deviceList.add(remote);
		this.fireIntervalAdded(this, getSize()-1, getSize()-1);
	}

	public void clear() {
		if (this.deviceList.size() > 0) {
			this.deviceList.clear();
			this.fireIntervalRemoved(this, 0, getSize()-1);
		}
	}
	
	@Override
	public Object getElementAt(int i) {
		return this.deviceList.get(i);
	}
	
	@Override
	public int getSize() {
		return this.deviceList.size();
	}
	
	public void remove(int i) {
		this.deviceList.remove(i);
		this.fireIntervalRemoved(this, i, i);
	}
	
	public void remove(WiiRemote remote) {
		int index = this.deviceList.indexOf(remote);
		if (index != -1) {
			remove(index);
		}
	}

}
