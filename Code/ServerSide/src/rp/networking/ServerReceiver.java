package rp.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import rp.DataObjects.*;
import rp.jobDecider.Item;
import rp.jobDecider.Job;
import rp.JobAssigner;
import rp.RouteConversion;
import rp.RoutePlanner;

import org.apache.log4j.Logger;
import lejos.pc.comm.NXTInfo;

/**
 * 
 * Reads routes sent by the robot
 * 
 * Extension: check route sent by robot to route originally sent to see if they
 * match
 * 
 * @author James
 *
 */
public class ServerReceiver extends Thread {

	/**
	 * Stores the input stream from the robot
	 */
	private DataInputStream inputStream;
	/**
	 * Stores the communication info for the robot
	 */
	private CommInfo robotInfo;
	/**
	 * Stores the client table for the robots
	 */
	private ClientTable clientTable;
	/**
	 * Stores the overall route the robot will perform
	 */
	private String overallRoute = "";
	/**
	 * Stores the last instruction the robot performed
	 */
	private String instruction = "";
	/**
	 * Stores the map which in turn stores the robots' location
	 */
	private Map map;
	/**
	 * Stores the job assigner
	 */
	private JobAssigner jobAssigner;
	/**
	 * Stores the route planner
	 */
	private RoutePlanner rPlanner;
	/**
	 * Stores the current location of the robot
	 */
	private Location robotsLocation;
	/**
	 * Stores the current job the robot is performing
	 */
	private Job currentJob;
	/**
	 * Stores the current route as a list of locations that need to be travelled to
	 */
	private ArrayList<Location> routeAsLocations = new ArrayList<>();
	/**
	 * Stores the route converter
	 */
	private RouteConversion routeConverter;
	/**
	 * Stores the direction the robot is facing
	 */
	private String direction = "North";
	/**
	 * Stores the list of items that need to be picked up for the current job
	 */
	private ArrayList<Item> items = new ArrayList<>();
	/**
	 * Stores the logger for this class
	 */
	final static Logger logger = Logger.getLogger(ServerReceiver.class);

	/**
	 * 
	 * Constructor for a new server receiver
	 * 
	 * @param newCommInfo
	 *            The communication info for the robot
	 * @param clientTable
	 *            The client table currently in use by the server
	 * @param rp
	 * @param jobAssigner
	 */
	public ServerReceiver(CommInfo newCommInfo, ClientTable clientTable, Map map, JobAssigner jobAssigner,
			RoutePlanner rp) {
		this.robotInfo = newCommInfo;
		this.inputStream = newCommInfo.getInputStream();
		this.clientTable = clientTable;
		this.map = map;
		this.jobAssigner = jobAssigner;
		this.rPlanner = rp;
	}

	/**
	 * Starts running the thread
	 */
	public void run() {
		logger.debug("Server comm is active");
		try {

			// Read the route sent by the robote
			while (true) {
				int length = inputStream.readInt();
				byte[] array = new byte[length];
				inputStream.read(array);
				String answer = new String(array);

				/*
				 * If the robot is not sending us any info and the robots queue is empty then
				 * give it a route, else read what the robot tells us and act on the info
				 */
				if ((answer == null || answer.equals(""))) {
					if (clientTable.getQueue(robotInfo.getRobotName()).isEmpty()) {
						getNewRoute();
					}

				}
				else {
					if (answer.length() > 1) {
						overallRoute = answer;
					}
					else {
						instruction = answer;
						if (instruction.equals("0")) {
							map.updateRobotsLocation(robotInfo.robotName, overallRoute.remove(0));
							direction = map.getDirection();
						}
						logger.debug("Robot's instruction: " + answer);
					}

				}

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the next route for the robot and adds it to its queue
	 */
	private void getNewRoute() {
		// If there are no more items to retrieve in the current job then get a new job
		if (items.isEmpty()) {
			currentJob = jobAssigner.assignJob(robotsLocation, robotInfo.getRobotName());

			for (String itemName : currentJob.getItems().keySet()) {
				Item item = currentJob.getItems().get(itemName);
				items.add(item);
			}
		}

		/*
		 * Get the route in terms of locations that must be reached and convert it to
		 * movement instructions
		 */
		routeAsLocations = rPlanner.getRoute(items.get(0));
		items.remove(0);
		String convertedRoute = routeConverter.convertRoute(robotsLocation, direction, routeAsLocations);

		clientTable.getQueue(robotInfo.getRobotName()).offer(convertedRoute);
	}

}
