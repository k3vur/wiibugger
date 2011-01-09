package wiibugger.pc;

import java.io.DataOutputStream;
import java.io.IOException;

import wiibugger.pc.wiimote.WiiMoteListener;
import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteJ;

import lejos.pc.comm.NXTConnector;

public class Main {

	static NXTConnector connector;
	static DataOutputStream nxtOut;
	
	public static void main(String[] args) {
    	/*
    	 * Wiimote uses pcm (something like Bluetooth "ports") < 4069
    	 * which is not recommended and checked by bluecove.
    	 * We have to turn that check off.
    	 */
    	System.setProperty("bluecove.jsr82.psm_minimum_off","true");
    	
    	/*
    	 * Wiimote needs Widcomm Bluetooth stack under windows to communicate
    	 */
    	System.setProperty("bluecove.stack.first", "widcomm");
		
    	
    	/*
    	 * Connect to NXT
    	 */
		connector = new NXTConnector();
		
		// Connect to any nxt 
		if (!connector.connectTo("btspp://")) {
			System.out.println("I haz error. bluetooth sux");
			System.exit(1);
		}
				
		nxtOut = connector.getDataOut();
		System.out.println("Connected to NXT");
		
		/*
		 * Wii Connection
		 */
		System.out.println("Trying to connect wo WiiMote");
        //Find and connect to a Wii Remote
        WiiRemote remote = null;
        while (remote == null) {
            try {
                remote = WiiRemoteJ.findRemote();
            } catch(Exception e) {
                remote = null;
                e.printStackTrace();
                System.out.println("Failed to connect remote. Trying again.");
            }
        }
        
        WiiMoteListener wiiListener = new WiiMoteListener(remote);
        remote.addWiiRemoteListener(wiiListener);
        try {
			remote.setAccelerometerEnabled(true);
            remote.setLEDIlluminated(0, true);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			try {
				nxtOut.writeShort((int)(wiiListener.roll*180/Math.PI));
				System.out.println(wiiListener.roll);
				nxtOut.flush();
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
