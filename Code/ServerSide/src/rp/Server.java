package rp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import org.apache.log4j.Logger;

import com.intel.bluetooth.BlueCoveConfigProperties;

import rp.DataObjects.Direction;
import rp.DataObjects.GridPoint;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.DataObjects.RobotConnector;
import rp.networking.ClientTable;
import rp.networking.CommInfo;
import rp.networking.ServerReceiver;
import rp.networking.ServerSender;

/**
 * @author James
 *
 */
public class Server {

	/**
	 * HashMap containing the robots and their connections
	 */
	private static HashMap<String, RobotConnector> connections;
	/**
	 * The client table for the server
	 */
	private static ClientTable clientTable;
	/**
	 * The logger for debug/error messages
	 */
	final static Logger logger = Logger.getLogger(RobotConnector.class);
	/**
	 * Stores the route in terms of the locations the robot must reach
	 */
	private static ArrayList<Location> test = new ArrayList<>();
	/**
	 * THe actual route to be performed by the robot
	 */
	static String actualRoute;
	/**
	 * The pc interface for the server
	 */
	private static GUI pcInterface;
	/**
	 * The map to store the robots' location
	 */
	private static Map map;
	

	public static void main(String[] args) {
		
		// Create a new GUI object and run it to start the PC interface
		pcInterface = new GUI(this);
		pcInterface.runGUI();
		

		
		//actualRoute = testRoute();
		logger.debug("------------------------Converted route:" + actualRoute);

		

		clientTable = new ClientTable();

		// Add the hard-coded info about the robots to be used
		NXTInfo[] robots = addRobotInfo();

		// Add each robot to a list of robot connectors
		connections = new HashMap<>();
		for (NXTInfo robot : robots) {
			connections.put(robot.name, new RobotConnector(robot));
		}

		// Initialise communications to each robot
		for (String robotName : connections.keySet()) {
			initComms(robotName);
			pcInterface.connectRobot(robotName);
			
		}
		
		while(!pcInterface.getGUIFinished() ) {
			logger.trace("While");
		}
		pcInterface.runFrame3();
		
		
		map = new Map(10, 10, test);
		for(String robotName : connections.keySet()) {
			System.out.println(robotName);
			map.addRobot(robotName, new Location(0, 0), Direction.NORTH);
		}
		
		JobAssigner jobAssigner = new JobAssigner(map);
		ArrayList<Location> route = jobAssigner.assignJob(new Location(0, 0), "Pisces");
		System.out.println(route);

		// Give each robot their first route to get them up and running
		getFirstRoutes(clientTable);

	}
	

	/**
	 * 
	 * Adds hard-coded info about robots to a list
	 * 
	 * @return A list containing info about robots
	 */
	private static NXTInfo[] addRobotInfo() {
		NXTInfo[] newRobots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "Pisces", "001653155F35"),
		new NXTInfo(NXTCommFactory.BLUETOOTH, "Gemini", "001653182F7A"),
		new NXTInfo(NXTCommFactory.BLUETOOTH, "Sagittarius", "00165317B913") };
		return newRobots;
	}

	
	/**
	 * 
	 * Establish a communication interface with the robot, then attempt to connect
	 * to that robot and start a thread to maintain communications
	 * 
	 * @param robotName
	 *            The name of the robot to communicate with
	 */
	private static void initComms(String robotName) {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			CommInfo robotCommInfo = connections.get(robotName).connect(nxtComm, robotName);
			clientTable.add(robotCommInfo.getRobotName());
			ServerReceiver receiver = new ServerReceiver(robotCommInfo, clientTable, map);
			receiver.start();
			(new ServerSender(receiver, robotCommInfo, clientTable.getQueue(robotCommInfo.getRobotName()))).start();
		}
		catch (NXTCommException e) {
			logger.error("Couldn't create bluetooth connection to robot");
			e.printStackTrace();
		}

	}
	

	/**
	 * 
	 * Test method to check whether multiple robots can receive and execute routes
	 * at the same time
	 * 
	 * @param clientTable
	 *            The client table containing the robots
	 */
	private static void getFirstRoutes(ClientTable clientTable) {
		BlockingQueue<String> recipientsQueue = clientTable.getQueue("Pisces");
		if (recipientsQueue != null) {
			logger.debug("Trying to offer a route to Pisces");
			recipientsQueue.offer(actualRoute);
			logger.debug("Successfully offered: " + actualRoute);
		}
		
		 BlockingQueue<String> recipientsQueue2 = clientTable.getQueue("Gemini");
		 if (recipientsQueue2 != null) {
		 logger.debug("Trying to offer a route to Gemini");
		 recipientsQueue2.offer(actualRoute);
		 logger.debug("Successfully offered: " + actualRoute);
		 }
		
		 BlockingQueue<String> recipientsQueue3 = clientTable.getQueue("Sagittarius");
		 if (recipientsQueue3 != null) {
		 logger.debug("Trying to offer a route to Sagittarius");
		 recipientsQueue3.offer(actualRoute);
		 logger.debug("Successfully offered: " + actualRoute);
		 }
	}
	
	
	
	
	private static String testRoute(ArrayList<GridPoint> test) {
		
		ArrayList<GridPoint> route = test;
		for (GridPoint point : route) {
			logger.debug(point.getLocation().getX() + " : " + point.getLocation().getY());
		}

		Point[] pointList = new Point[route.size()];

		for (int i = 0; i < route.size(); i++) {
			pointList[i] = new Point(route.get(i).getLocation().getX(), route.get(i).getLocation().getY());
		}
		logger.debug("Starting");
		for (Point point : pointList) {
			logger.debug(point);
		}

		logger.debug("------------Feedback from route converison:");
		RouteConversion newRoute = new RouteConversion(pointList);
		return newRoute.convertRoute();
	}
	
	
	void startRobots(HashMap<String, Location> robotLocations) {
		
		JobAssigner jobAssigner = new JobAssigner(map);
		for (String robotName : robotLocations.keySet()) {
			;
			RouteConversion newRoute = new RouteConversion(jobAssigner.assignJob(robotLocations.get(robotName), robotName));
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
