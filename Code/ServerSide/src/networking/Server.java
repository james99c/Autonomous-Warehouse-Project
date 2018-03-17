package networking;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

import org.apache.log4j.Logger;

import com.intel.bluetooth.BlueCoveConfigProperties;

import DataObjects.RobotConnector;

/**
 * @author James
 *
 */
public class Server {

	private static HashMap<String, RobotConnector> connections;
	private static ClientTable clientTable;
	// final static Logger logger = Logger.getLogger(RobotConnector.class);

	public static void main(String[] args) {

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
			(new ServerReceiver(robotCommInfo, clientTable)).start();
			(new ServerSender(robotCommInfo, clientTable.getQueue(robotCommInfo.getRobotName()))).start();
		}
		catch (NXTCommException e) {
			System.out.println("Couldn't create bluetooth connection to robot");
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
			System.out.println("Trying to offer a route to Pisces");
			recipientsQueue.offer("000000020");
			System.out.println("Successfully offered: 000000020");
		}

		BlockingQueue<String> recipientsQueue2 = clientTable.getQueue("Gemini");
		if (recipientsQueue2 != null) {
			System.out.println("Trying to offer a route to Gemini");
			recipientsQueue2.offer("00100");
			System.out.println("Successfully offered: 00100");
		}

		BlockingQueue<String> recipientsQueue3 = clientTable.getQueue("Sagittarius");
		if (recipientsQueue3 != null) {
			System.out.println("Trying to offer a route to Sagittarius");
			recipientsQueue3.offer("00200");
			System.out.println("Successfully offered: 00200");
		}
	}

}
