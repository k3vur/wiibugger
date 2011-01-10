package wiibugger.pc.nxt;

import wiibugger.pc.DeviceList;

public class NXTScanner extends Thread {

	public static NXTScanner scan(DeviceList<NXTDevice> nxtList, int numberOfScans) {
		return scan(nxtList, numberOfScans, null);
	}
	public static NXTScanner scan(DeviceList<NXTDevice> nxtList, int numberOfScans, Runnable callAfterFinish) {
		NXTScanner scanner = new NXTScanner(nxtList, numberOfScans, callAfterFinish);
		scanner.start();
		return scanner;
	}

	private Runnable callAfterFinish;
	
	private int numberOfScans;
	
	private DeviceList <NXTDevice> nxtList;
	
	public NXTScanner(DeviceList<NXTDevice> nxtList, int numberOfScans, Runnable callAfterFinish) {
		this.nxtList = nxtList;
		this.numberOfScans = numberOfScans;
		this.callAfterFinish = callAfterFinish;
	}
	
	@Override
	public void run() {
		System.out.println("Scanning for NXT's...");
		
		//for(int i = 0; i < numberOfScans; i++) {
			NXTDevice[] nxtDevices = NXTDevice.connectToNXT();
			if(nxtDevices != null) {
				for(NXTDevice dev : nxtDevices) {
					nxtList.add(dev);
				}
			}
		//}
		
		if(callAfterFinish != null) {
			callAfterFinish.run();
		}
		System.out.println("Finished scanning for NXT's");
		
        //BTConnection btc = Bluetooth.waitForConnection(5000, BTConnection.PACKET);
        /*
        while(btc == null) {	
           	System.out.println("No NXT found...");
           	System.out.println("Scanning again...");
        	btc = Bluetooth.waitForConnection(5000, BTConnection.PACKET);
        }
        System.out.println("Found NXT @address " + btc.getAddress() + " ...");
		*/
	}
}
