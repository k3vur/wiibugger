package wiibugger.pc;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class DeviceList<DeviceType> extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<DeviceType> deviceList;
	
	public DeviceList() {
		this.deviceList = new ArrayList<DeviceType>();
	}

	public void add(DeviceType remote) {
		this.deviceList.add(remote);
		int index = deviceList.indexOf(remote);
		this.fireIntervalAdded(this, index, index);
	}

	public void clear() {
		int size = this.deviceList.size();
		if (size > 0) {
			this.deviceList.clear();
			this.fireIntervalRemoved(this, 0, size-1);
		}
	}
	
	@Override
	public DeviceType getElementAt(int i) {
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
	
	public void remove(DeviceType remote) {
		int index = this.deviceList.indexOf(remote);
		if (index != -1) {
			remove(index);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DeviceType> toArrayList() {
		return (ArrayList<DeviceType>) deviceList.clone();
	}

}
