package wiibugger.pc.nxt;

import wiibugger.pc.DeviceList;

public class NXTScanner extends Thread {

	public static NXTScanner scan(DeviceList<NXTDevice> nxtList) {
		return scan(nxtList, null);
	}
	public static NXTScanner scan(DeviceList<NXTDevice> nxtList, Runnable callback) {
		NXTScanner scanner = new NXTScanner(nxtList, callback);
		scanner.start();
		return scanner;
	}

	private Runnable callback;
	
	private DeviceList <NXTDevice> nxtList;
	
	public NXTScanner(DeviceList<NXTDevice> nxtList, Runnable callback) {
		this.nxtList = nxtList;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		System.out.println("Scanning for NXT's...");
		
		NXTDevice[] nxtDevices = NXTDevice.connectToNXT();
		if(nxtDevices != null) {
			for(NXTDevice dev : nxtDevices) {
				nxtList.add(dev);
			}
		} else
			System.out.println("No NXT found. Connect NXT Bluetooth with PC, turn it on and scan again.");
		
		if(callback != null) {
			callback.run();
		}		
	}
}
