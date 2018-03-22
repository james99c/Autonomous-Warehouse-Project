package rp.networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import rp.DataObjects.*;
import rp.jobDecider.Item;
import rp.jobDecider.Job;
import weka.classifiers.functions.SGDText.Count;
import rp.GUI;
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
	private RouteConversion routeConverter = new RouteConversion();
	/**
	 * Stores the direction the robot is facing
	 */
	private Direction direction = Direction.NORTH;
	/**
	 * Stores the list of items that need to be picked up for the current job
	 */
	private ArrayList<Item> items = new ArrayList<>();
	/**
	 * Stores the logger for this class
	 */
	final static Logger logger = Logger.getLogger(ServerReceiver.class);

	private boolean finishedRoute = false;
	
	private String currentRoute = "";
	
	private String robotRoute = "";
	
	private ServerSender sender;
	
	private GUI pcInterface;
	
	private int count = 0;

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
			RoutePlanner rp, Location location, GUI gui) {
		this.robotInfo = newCommInfo;
		this.inputStream = newCommInfo.getInputStream();
		this.clientTable = clientTable;
		this.map = map;
		this.jobAssigner = jobAssigner;
		this.rPlanner = rp;
		this.robotsLocation = location;
		this.pcInterface = gui;
	}

	/**
	 * Starts running the thread
	 */
	public void run() {
		System.out.println("85");
		logger.debug("Server comm is active");
		try {
			
			clientTable.getQueue(robotInfo.getRobotName()).offer("5");

			// Read the route sent by the robote
			while (true) {
				
				int length = inputStream.readInt();
				byte[] array = new byte[length];
				inputStream.read(array);
				String answer = new String(array);
				System.out.println(answer);

				/*
				 * If the robot is not sending us any info and the robots queue is empty then
				 * give it a route, else read what the robot tells us and act on the info
				 */
				if ((answer == null || answer.equals(""))) {
					System.out.println("No answer");
					if (clientTable.getQueue(robotInfo.getRobotName()).isEmpty()) {
						getNewRoute();
					}

				}
				else if(answer.equals("5")) {
					System.out.println("Let's go");
					if (clientTable.getQueue(robotInfo.getRobotName()).isEmpty()) {
						getNewRoute();
					}
				}
				else if(answer.equals("f")) {
					System.out.println("Robot is full");
					finishedRoute = true;
					if (clientTable.getQueue(robotInfo.getRobotName()).isEmpty()) {
						getNewRoute();
					}
				}
				else {
					instruction = answer;
					robotRoute += instruction;
					if (instruction.equals("0")) {
						map.updateRobotsLocation(robotInfo.robotName, routeAsLocations.remove(0));
						direction = map.getRobotInformation(robotInfo.robotName).direction;
						System.out.println("NEW LOCATION --------------------------------- " + map.getRobotInformation(robotInfo.robotName).location);
						System.out.println(map.getRobotInformation(robotInfo.robotName).direction);
					}
					logger.debug("Robot's instruction: " + answer);
					
					if (robotRoute.equals(currentRoute)) {
						finishedRoute = true;
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
			if (finishedRoute) {
				rPlanner.findRouteToDropOff(robotInfo.robotName);
				String convertedRoute = routeConverter.convertRoute(robotsLocation, direction, routeAsLocations);
				System.out.println("First time");
				clientTable.getQueue(robotInfo.getRobotName()).offer(convertedRoute);
				finishedRoute = false;
			}
			else {
				System.out.println(robotsLocation.getX());
				System.out.println(robotsLocation.getY());
				currentJob = jobAssigner.assignJob(robotsLocation, robotInfo.getRobotName());

				for (String itemName : currentJob.getItems().keySet()) {
					Item item = currentJob.getItems().get(itemName);
					items.add(item);
				}
				System.out.println("Get route");
				System.out.println(items.get(0).getItemXPos());
				System.out.println(items.get(0).getItemYPos());
				routeAsLocations = rPlanner.findRouteToItem(robotInfo.getRobotName(), items.get(0));
				for(Location testLocation : routeAsLocations) {
					System.out.println(testLocation.toString());
				}
				
				System.out.println(robotsLocation);
				System.out.println(direction);
				String convertedRoute = routeConverter.convertRoute(robotsLocation, direction, routeAsLocations);
				System.out.println("Second time");
				System.out.println(convertedRoute);
				sender.newItemsWeight(items.get(0).getItemWeight());
				items.remove(0);
				clientTable.getQueue(robotInfo.getRobotName()).offer(convertedRoute);
				pcInterface.setJobID(robotInfo.robotName, currentJob.getJobID());
			}
		} 
		else {

		/*
		 * Get the route in terms of locations that must be reached and convert it to
		 * movement instructions
	`	 */
		System.out.println("Second route planner");
		currentJob = jobAssigner.assignJob(robotsLocation, robotInfo.getRobotName());

		for (String itemName : currentJob.getItems().keySet()) {
			Item item = currentJob.getItems().get(itemName);
			items.add(item);
		}
		routeAsLocations = rPlanner.findRouteToItem(robotInfo.getRobotName(), items.remove(0));
		//items.remove(0);
		String convertedRoute = routeConverter.convertRoute(robotsLocation, direction, routeAsLocations);
		System.out.println("Third time");
		System.out.println(convertedRoute);
		pcInterface.setJobID(robotInfo.robotName, currentJob.getJobID());
		clientTable.getQueue(robotInfo.getRobotName()).offer(convertedRoute);
		}
	}
	
	
	
	public void addCurrentRoute(String currentRoute) {
		this.currentRoute = currentRoute;
	}
	
	
	
	
	public void giveSender(ServerSender sender) {
		this.sender = sender;
	}
	
	
	

}
