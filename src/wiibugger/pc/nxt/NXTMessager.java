package wiibugger.pc.nxt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import lejos.pc.comm.NXTConnector;
import wiibugger.communication.NXTMessage;



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
	
	private NXTConnector connector;
	
	private DataOutputStream out;
	
	private LinkedBlockingQueue<NXTMessage> messageQueue;
	
	private boolean sending;
	
	private NXTMessager() {
		/*
		 * Just a dummy to make the constructor private
		 */
		super();
		connector = new NXTConnector();
		sending = true;
		messageQueue = new LinkedBlockingQueue<NXTMessage>();
	}
	
	private void deliverNextMessage() throws InterruptedException, IOException {
		currMessage = messageQueue.take();
		out.writeShort(currMessage.getOutput());
	}

	public void run() {
		
		out = connector.getDataOut();
		
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
		if (message != null) {
			messageQueue.offer(message);
		}
	}

	public void quit() {
		System.out.println("Quitting NXT-messager");
		sending = false;
		messageQueue.clear();
		send(new NXTMessage((short) 0));
		try {
			connector.close();
		} catch (IOException e) {
			System.out.println("Could not disconnect NXTs correctly");
		}
	}
	
	public void connectTo(String addr) throws IOException {
		if (!connector.connectTo(addr)) {
			throw new IOException();
		}
	}
}
