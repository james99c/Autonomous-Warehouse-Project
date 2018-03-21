package rp.networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;


/**
 * 
 * Sends routes to the robot to execute
 * 
 * @author James
 *
 */
public class ServerSender extends Thread {

	/**
	 * Stores the output stream to the robot
	 */
	private DataOutputStream output;
	/**
	 * Stores the name of the robot
	 */
	private String robotName;
	/**
	 * Stores the queue for the robot
	 */
	private BlockingQueue<String> clientQueue;
	/**
	 * Stores the associated server receiver	CURRENTLY NOT IN USE!
	 */
	private ServerReceiver receiver;
	/**
	 * Stores the logger for this class
	 */
	final static Logger logger = Logger.getLogger(ServerSender.class);
	
	private float itemsWeight = 0.0f;
	
	private String weightAsString = "";

	
	/**
	 * 
	 * Constructor for a new server sender
	 * 
	 * @param newReceiver The associated server receiver
	 * @param newRobotCommInfo The communication info for the robot
	 * @param newClientQueue The queue for the robot
	 */
	public ServerSender(ServerReceiver newReceiver, CommInfo newRobotCommInfo, BlockingQueue<String> newClientQueue) {
		this.receiver = newReceiver;
		this.output = newRobotCommInfo.getOutputStream();
		this.robotName = newRobotCommInfo.getRobotName();
		this.clientQueue = newClientQueue;
		receiver.giveSender(this);
	}

	
	/**
	 * Starts running the thread
	 */
	public void run() {
		/*
		 * If there is a route available in the robots queue then get it and send it
		 */
		while (true) {

			try {
				weightAsString = String.format("%f", itemsWeight);
				String route = clientQueue.take();
				System.out.println(route);
				output.writeInt(weightAsString.length());
				output.writeBytes(weightAsString);
				output.writeInt(route.length());
				output.writeBytes(route);
				output.flush();
				System.out.println("We're writing  ---- " + route + " ---- " + robotName);
				receiver.addCurrentRoute(route);
			}
			catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	public void newItemsWeight(Float newWeight) {
		itemsWeight = newWeight;
	}

}
