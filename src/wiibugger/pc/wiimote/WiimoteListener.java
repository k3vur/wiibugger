package wiibugger.pc.wiimote;

import wiiremotej.event.WRAccelerationEvent;
import wiiremotej.event.WRStatusEvent;
import wiiremotej.event.WiiRemoteAdapter;

/**
 * This class handles the Wiimote input
 */

public class WiimoteListener extends WiiRemoteAdapter
{
	
	// TODO implement the actual WiimoteListener
	
    private static boolean accelerometerSource = true; //true = wii remote, false = nunchuk
    public double roll;
    
    public void accelerationInputReceived(WRAccelerationEvent evt) {

        if (accelerometerSource) {
        	this.roll = evt.getRoll();
        }
        
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