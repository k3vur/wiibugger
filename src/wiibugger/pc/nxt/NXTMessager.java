package wiibugger.pc.nxt;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import wiibugger.communication.NXTMessage;
import wiibugger.pc.Wiibugger;



public class NXTMessager extends Thread {

	public static int interval = 10;
	
	private static NXTMessager messager;
	
	public static NXTMessager getNXTMessager() {
		if (messager == null || messager.getState() == Thread.State.TERMINATED) {
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
	
	private void deliverNextMessage() throws InterruptedException, IOException {
		currMessage = messageQueue.take();
		NXTDevice nxt = Wiibugger.getNXTList().getElementAt(currMessage.getNxtDevice());
		
		System.out.println(nxt.getName());
		nxt.send(currMessage.getOutput());
	}

	public void run() {
		while (sending) {					
			try {
				deliverNextMessage();
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

	public void quit() {
		sending = false;
		messageQueue.clear();
		System.out.println("Quitting NXT-messager");
		send(new NXTMessage((short) 0));
	}
}
