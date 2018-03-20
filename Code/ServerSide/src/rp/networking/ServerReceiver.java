package rp.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import rp.DataObjects.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import lejos.pc.comm.NXTInfo;
import rp.DataObjects.GridPoint;


/**
 * 
 * Reads routes sent by the robot
 * 
 * Extension: check route sent by robot
 * to route originally sent to see if
 * they match
 * 
 * @author James
 *
 */
public class ServerReceiver extends Thread {

	private DataInputStream inputStream;
	CommInfo robotInfo;
	private ClientTable clientTable;
	private String overallRoute = "";
	private String instruction = "";
	private Map map;
	//final static Logger logger = Logger.getLogger(RobotConnector.class);

	/**
	 * 
	 * Constructor for a new server comm
	 * 
	 * @param newCommInfo Info about the specific robot including its name and comm streams
	 * @param clientTable The client table currently in use by the server
	 */
	public ServerReceiver(CommInfo newCommInfo, ClientTable clientTable, Map map) {
		this.robotInfo = newCommInfo;
		this.inputStream = newCommInfo.getInputStream();
		this.clientTable = clientTable;
		this.map = map;
	}

	public void run() {
		System.out.println("Server comm is active");
		try {
			/*
			 * Read the route sent by the robot.
			 * If the route is valid print it
			 */
			while (true) {
					int length = inputStream.readInt();
					byte[] array = new byte[length];
					inputStream.read(array);
					String answer = new String(array);
					
					if (answer == null || answer.equals("")) {
					}
					else {
						if (answer.length() > 1) {
							overallRoute = answer;
						}
						else {
							instruction = answer;
							if(instruction.equals("0")) {
								map.updateRobotsLocation(robotInfo.robotName, overallRoute.remove(0));
							}
							System.out.println("Robot's instruction: " + answer);
						}
						
					}
					
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	private void giveRoute(ArrayList<GridPoint> route) {
		//this.overallRoute = route;
		
	}
	
	
}
