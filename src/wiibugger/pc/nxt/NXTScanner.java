package wiibugger.pc.nxt;

import wiibugger.pc.DeviceList;

public class NXTScanner extends Thread {

	public static NXTScanner scan(DeviceList<NXTDevice> nxtList) {
		return scan(nxtList, null);
	}
	public static NXTScanner scan(DeviceList<NXTDevice> nxtList, Runnable callAfterFinish) {
		NXTScanner scanner = new NXTScanner(nxtList, callAfterFinish);
		scanner.start();
		return scanner;
	}

	private Runnable callAfterFinish;
	
	private DeviceList <NXTDevice> nxtList;
	
	public NXTScanner(DeviceList<NXTDevice> nxtList, Runnable callAfterFinish) {
		this.nxtList = nxtList;
		this.callAfterFinish = callAfterFinish;
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
		
		if(callAfterFinish != null) {
			callAfterFinish.run();
		}		
	}
}
