package rp.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

//import org.apache.log4j.Logger;
/**
 * 
 * Sends routes to the robot to execute
 * 
 * @author James
 *
 */
public class ServerSender extends Thread {
	
	private DataOutputStream output;
	private String robotName;
	private BlockingQueue<String> clientQueue;
	//final static Logger logger = Logger.getLogger(RobotConnector.class);
	
	public ServerSender(CommInfo newRobotCommInfo, BlockingQueue<String> newClientQueue) {
		this.output = newRobotCommInfo.getOutputStream();
		this.robotName = newRobotCommInfo.getRobotName();
		this.clientQueue = newClientQueue;
	}

	public void run() {
		try {
			/*
			 * If there is a route available in the robots
			 * queue then take it and send it
			 */
			while (true) {
					String route = clientQueue.take();
					output.writeInt(route.length());
					output.writeBytes(route);
					output.flush();
					System.out.println("We're writing ints! ---------- " + route);
			}
		}
		catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}
