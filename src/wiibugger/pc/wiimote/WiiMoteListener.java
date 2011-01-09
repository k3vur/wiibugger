package wiibugger.pc.wiimote;

import wiiremotej.WiiRemote;
import wiiremotej.event.WRAccelerationEvent;
import wiiremotej.event.WRStatusEvent;
import wiiremotej.event.WiiRemoteAdapter;

/**
 * Implements WiiRemoteListener and acts as a general test class. Note that you can ignore the main method pretty much, as it mostly has to do with the graphs and GUIs.
 * At the very end though, there's an example of how to connect to a remote and how to prebuffer audio files.
 * @author Michael Diamond
 * @version 1/05/07
 */

public class WiiMoteListener extends WiiRemoteAdapter
{
    private static boolean accelerometerSource = true; //true = wii remote, false = nunchuk
    private WiiRemote remote;
    public double roll;
    
    public WiiMoteListener(WiiRemote remote)
    {
        this.remote = remote;
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
        System.out.println("Remote continuous: " + remote.isContinuousEnabled());
    }
      
    public void accelerationInputReceived(WRAccelerationEvent evt)
    {

        if (accelerometerSource)
        {
        	this.roll = evt.getRoll();
        }
        
    }
      
}