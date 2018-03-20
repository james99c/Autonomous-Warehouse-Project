package rp.networking;

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
	private ServerReceiver receiver;
	//final static Logger logger = Logger.getLogger(RobotConnector.class);
	
	public ServerSender(ServerReceiver newReceiver, CommInfo newRobotCommInfo, BlockingQueue<String> newClientQueue) {
		this.receiver = newReceiver;
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
				//receiver.giveRoute(newRoute);
					String route = clientQueue.take();
					output.writeInt(route.length());
					output.writeBytes(route);
					output.flush();
					System.out.println("We're writing  ---- " + route + " ---- " + robotName);
			}
		}
		catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}
