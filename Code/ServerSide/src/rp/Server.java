package rp;

import java.util.ArrayList;
import java.util.HashMap;

import rp.DataObjects.*;
import rp.networking.*;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import org.apache.log4j.Logger;


/**
 * 
 * Server for the program
 * Ensures communication with the robots and the various subsystems
 * 
 * @author James
 *
 */
public class Server {

	/**
	 * Stores the robots and their connections
	 */
	private static HashMap<String, RobotConnector> connections;
	/**
	 * Stores the client table for the server
	 */
	private static ClientTable clientTable;
	/**
	 * Stores the logger for this class
	 */
	final static Logger logger = Logger.getLogger(RobotConnector.class);
	/**
	 * Stores the route in terms of the locations the robot must reach
	 */
	private static ArrayList<Location> obstacles = new ArrayList<>();
	/**
	 * Stores the PC interface for the server
	 */
	private static GUI pcInterface;
	/**
	 * Stores the map which in turn stores the robots' location
	 */
	private static Map map;
	/**
	 * Stores the name and communication info for a robot
	 */
	private static HashMap<String, CommInfo> robotBTConnections = new HashMap<>();
	

	public static void main(String[] args) {
		
		// Add the locations of the obstacles and then create the map
		for(int i = 1; i < 11; i+=3) {
			for(int c = 1; c < 6; c++) {
				obstacles.add(new Location(i, c));
			}
		}
		map = new Map(7, 11, obstacles);
		
		// Create a new GUI object and run it to start the PC interface
		pcInterface = new GUI(new Server());
		pcInterface.runGUI();

		
		// Create the client table to store the robots and their routes
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

	}
	

	/**
	 * 
	 * Adds hard-coded info about robots to a list
	 * 
	 * @return A list containing info about robots
	 */
	private static NXTInfo[] addRobotInfo() {
		NXTInfo[] newRobots = {
		//new NXTInfo(NXTCommFactory.BLUETOOTH, "Pisces", "001653155F35"),
//		new NXTInfo(NXTCommFactory.BLUETOOTH, "Gemini", "001653182F7A"),
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
			robotBTConnections.put(robotName, robotCommInfo);
		}
		catch (NXTCommException e) {
			logger.error("Couldn't create bluetooth connection to robot");
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 
	 * Start threads to continually communicate between the
	 * server and the robots, also add each robot to the
	 * map
	 * 
	 * @param robotLocations The start locations of the robots
	 */
	void startRobots(HashMap<String, Location> robotLocations) {
		JobAssigner jobAssigner = new JobAssigner(map);
		RoutePlanner rp = new RoutePlanner(map);
		
		for(String robotName : robotLocations.keySet()) {
			clientTable.add(robotBTConnections.get(robotName).getRobotName());
			ServerReceiver receiver = new ServerReceiver(robotBTConnections.get(robotName), clientTable, map, jobAssigner, rp, robotLocations.get(robotName));
			
			(new ServerSender(receiver, robotBTConnections.get(robotName), clientTable.getQueue(robotBTConnections.get(robotName).getRobotName()))).start();
			receiver.start();
			
			map.addRobot(robotName, robotLocations.get(robotName), Direction.NORTH);
		}		
		
	}	

}
