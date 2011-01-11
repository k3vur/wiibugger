package wiibugger.pc.wiimote;

import java.util.EventObject;

import wiibugger.pc.Wiibugger;
import wiiremotej.event.WRStatusEvent;

/**
 * This class handles the Wiimote input
 */

public class WiimoteDeviceListener
{
	
	// TODO implement the actual WiimoteListener
	
	
    
    public void accelerationInputReceived(EventObject evt) {

    	WiimoteDevice wiimote;
    	if (evt.getSource() instanceof WiimoteDevice) {
    		wiimote = (WiimoteDevice) evt.getSource();
    	} else {
    		return;
    	}
        
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