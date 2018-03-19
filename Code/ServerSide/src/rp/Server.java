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

	private static HashMap<String, RobotConnector> connections;
	private static ClientTable clientTable;
	final static Logger logger = Logger.getLogger(RobotConnector.class);
	private static ArrayList<Location> test = new ArrayList<>();

	public static void main(String[] args) {

		
		String actualRoute = testRoute();
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
			g.connectRobot(robotName);
		}

		// Test routes
		testRoutes(clientTable);

	}

	/**
	 * 
	 * Adds hard-coded info about robots to a list
	 * 
	 * @return A list containing info about robots
	 */
	private static NXTInfo[] addRobotInfo() {
		NXTInfo[] newRobots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "Pisces", "001653155F35"), };
		// new NXTInfo(NXTCommFactory.BLUETOOTH, "Gemini", "001653182F7A"),
		// new NXTInfo(NXTCommFactory.BLUETOOTH, "Sagittarius", "00165317B913") };
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
			(new ServerReceiver(robotCommInfo, clientTable)).start();
			(new ServerSender(robotCommInfo, clientTable.getQueue(robotCommInfo.getRobotName()))).start();
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
	private static void testRoutes(ClientTable clientTable) {
		BlockingQueue<String> recipientsQueue = clientTable.getQueue("Pisces");
		if (recipientsQueue != null) {
			logger.debug("Trying to offer a route to Pisces");
			recipientsQueue.offer(actuaRoute);
			logger.debug("Successfully offered: " + actuaRoute);
		}

		// BlockingQueue<String> recipientsQueue2 = clientTable.getQueue("Gemini");
		// if (recipientsQueue2 != null) {
		// logger.debug("Trying to offer a route to Gemini");
		// recipientsQueue2.offer("00100");
		// logger.debug("Successfully offered: 00100");
		// }
		//
		// BlockingQueue<String> recipientsQueue3 = clientTable.getQueue("Sagittarius");
		// if (recipientsQueue3 != null) {
		// logger.debug("Trying to offer a route to Sagittarius");
		// recipientsQueue3.offer("00200");
		// logger.debug("Successfully offered: 00200");
		// }
	}
	
	
		// ALED'S TEST CODE FOR ROUTE CONVERSION
		// double changeInX = nextX - currentX;
		// double changeInY = nextY - currentY;
		// if(changeInX == 1) {
		// route += '2';
		// }
		// else if(changeInX == -1) {
		// route += '1';
		// }
		// else {
		// route += '0';
		// }
	
	
	
	
	private static String testRoute() {
		
		test.add(new Location(1, 1));
		test.add(new Location(1, 2));
		test.add(new Location(1, 3));
		test.add(new Location(1, 4));
		test.add(new Location(1, 5));
		
		GUI g = new GUI();
		Map map = new Map(10, 10, test);
		RoutePlanner rPlanner = new RoutePlanner(map);
		ArrayList<GridPoint> route = rPlanner.findRoute(new Location(0, 0), new Location(2, 5), Direction.NORTH);
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
		RouteConversion routeNew = new RouteConversion(pointList);
		return routeNew.convertRoute();
	}
	
	
	
	
	
	

}
