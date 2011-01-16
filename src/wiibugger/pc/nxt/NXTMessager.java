package wiibugger.pc.nxt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
	
	private ArrayList<DataOutputStream> out;
	
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
		out = new ArrayList<DataOutputStream>();
	}
	
	private void deliverNextMessage() throws InterruptedException, IOException {
		currMessage = messageQueue.take();
		for (DataOutputStream stream: out) {			
			stream.writeShort(currMessage.getOutput());
			stream.flush();
		}
	}

	public void run() {
				
		while (sending) {					
			try {
				deliverNextMessage();
			} catch (InterruptedException e) {
				System.out.println("Could not wait for next Message");
			} catch (IOException e) {
				if (e.getMessage().equals("Stream closed")) {
					System.out.println("Stream closed");
					sending = false;
				} else {
					System.out.println("Error while sending");
				}
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
		send(new NXTMessage(NXTMessage.CLOSE_MESSAGE));
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
		out.add(connector.getDataOut());
	}
}
