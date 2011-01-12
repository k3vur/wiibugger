package wiibugger.pc.nxt;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;



public class NXTMessager extends Thread {

	public static int interval = 10;
	
	private static NXTMessager messager;
	
	public static NXTMessager getNXTMessager() {
		if (messager == null) {
			messager = new NXTMessager();
		}
		return messager;
	}
	
	private NXTMessage currMessage;
	
	private LinkedBlockingQueue<NXTMessage> messageQueue;
	
	private boolean sending;
	
	private NXTMessager() {
		/*
		 * Just a dummy to make the constructor private
		 */
		super();
		sending = true;
		messageQueue = new LinkedBlockingQueue<NXTMessage>();
	}
	
	synchronized private void deliverNextMessage() throws InterruptedException, IOException {
		currMessage = messageQueue.take();
		currMessage.device.send(currMessage.motor);
		currMessage.device.send(currMessage.speed);
	}

	public void run() {
		while (sending) {					
			try {
				deliverNextMessage();
				Thread.sleep(NXTMessager.interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void send(NXTMessage message) {
		messageQueue.offer(message);
	}
}
