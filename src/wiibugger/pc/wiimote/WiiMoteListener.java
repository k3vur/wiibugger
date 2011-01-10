package wiibugger.pc.wiimote;

import wiibugger.pc.Wiibugger;
import wiiremotej.WiiRemote;
import wiiremotej.event.WRAccelerationEvent;
import wiiremotej.event.WRStatusEvent;
import wiiremotej.event.WiiRemoteAdapter;

/**
 * This class handles the Wiimote input
 */

public class WiiMoteListener extends WiiRemoteAdapter
{
	
	// TODO implement the actual WiimoteListener
    
    public void accelerationInputReceived(WRAccelerationEvent evt) {

    	WiiRemote wiimote = evt.getSource();
        
    	if (wiimote == Wiibugger.getWiimote1()) {
    		System.out.print("Wiimote 1: ");
    	} else if (wiimote == Wiibugger.getWiimote2()) {
    		System.out.print("Wiimote 2: ");
    	}
    	
    	System.out.println("Input received!");
    	
    }
    
    public void disconnected()
    {
        System.out.println("Remote disconnected... Please Wii again.");
        System.exit(0);
    }
      
    public void statusReported(WRStatusEvent evt)
    {
        System.out.println("Battery level: " + (double)evt.getBatteryLevel()/2+ "%");
        System.out.println("Continuous: " + evt.isContinuousEnabled());
        System.out.println("Remote continuous: " + evt.getSource().isContinuousEnabled());
    }
      
}