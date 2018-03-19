package rp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

import rp.DataObjects.Direction;
import rp.DataObjects.GridPoint;
import rp.JobDeciderTest.Item;
import rp.JobDeciderTest.JobObject;
import rp.DataObjects.Job;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.DataObjects.RobotInformation;
import rp.Interfaces.RoutePlannerInterface;
import rp.RoutePlannerExtra.SearchTree;
import javafx.util.Pair;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class RoutePlanner implements RoutePlannerInterface {
	private ArrayList<Integer> jobs;
	private ArrayList<GridPoint> route;
	private ArrayList<Location> prevLocation;
	private Map map;

	private static final Logger logger = LogManager.getLogger(RoutePlanner.class);

	public RoutePlanner(Map _map) {
		prevLocation = new ArrayList<Location>();
		this.map = _map;
		SearchTree.currentMap = map;

	}

	public Pair<ArrayList<GridPoint>, Direction> findIndividualRoute(Location currentLocation, Location goalLocation,
			Direction _robotsDirection) {
		// ArrayList<Location> outputVariable = new ArrayList<Location>();
		SearchTree searchTree = new SearchTree(currentLocation, currentLocation, 0f, goalLocation, null,
				new ArrayList<Pair<GridPoint, Direction>>(), _robotsDirection);
		searchTree.search();
		ArrayList<Pair<GridPoint, Direction>> searchTreeOutput = searchTree.getOutputVariable();
		ArrayList<GridPoint> output = new ArrayList<GridPoint>();
		for (Pair<GridPoint, Direction> item : searchTreeOutput) {
			output.add(item.getKey());
		}

		int index;
		Float[] second = new Float[] {};
		Float[] first = new Float[] {};
		long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - map.startOfTime;
		
		// I apologise for being so lazy
		if (output.size() == 1) {
			map.getGridPoint(output.get(0).getLocation().getX(), output.get(0).getLocation().getY())
			.setUnAvailability(output.get(0).getTimeFrames());
		} else {
			for (index = 1; index < output.size(); index++) {
				ArrayList<Float[]> timeFramesTwo = output.get(index).getTimeFrames();
				ArrayList<Float[]> timeFramesOne = output.get(index - 1).getTimeFrames();
				second = timeFramesTwo.get(timeFramesTwo.size() - 1);
				first = timeFramesOne.get(timeFramesOne.size() - 1);
				System.out.println((currentTime + first[0]) + " : " + (currentTime + second[1]));
				map.getGridPoint(output.get(index - 1).getLocation().getX(), output.get(index - 1).getLocation().getY())
						.setUnAvailability(new Float[] { currentTime + first[0], currentTime + second[1] });
			}
			map.getGridPoint(output.get(index - 1).getLocation().getX(), output.get(index - 1).getLocation().getY())
					.setUnAvailability(new Float[] { currentTime + second[0], currentTime + second[1] });
		}

		// need to adjust the time frames
		return new Pair<ArrayList<GridPoint>, Direction>(output,
				searchTreeOutput.get(searchTreeOutput.size() - 1).getValue());
	}

	// public ArrayList<Location> findEntireRoute(String _robotID, Location
	// _robotLocation, Job job){
	// RobotInformation robotInfo = map.getRobotInformation(_robotID);
	// findRoute()
	// }
	//

	public ArrayList<GridPoint> findOverallRoute(Location _startLocation, Job _job, String robotName) {
		HashMap<String, Item> itemMap = _job.getItemMap();
		ArrayList<String> itemsID = _job.getItems();
		ArrayList<Location> itemsLocation = new ArrayList<>();
		ArrayList<GridPoint> route = new ArrayList<>();

		for (String s : itemsID) {
			Item item = itemMap.get(s);
			itemsLocation.add(new Location(item.getX(), item.getY()));
		}
		Location startLocation = _startLocation;
		Location goalLocation = findShortestDistance(startLocation, itemsLocation);

		Pair<ArrayList<GridPoint>, Direction> output = findIndividualRoute(startLocation, goalLocation,
				map.getRobotInformation(robotName).direction);
		route.addAll(output.getKey());
		startLocation = goalLocation;
		itemsLocation.remove(startLocation);
		while (!itemsLocation.isEmpty()) {
			goalLocation = findShortestDistance(startLocation, itemsLocation);
			output = findIndividualRoute(startLocation, goalLocation, output.getValue());
			route.addAll(output.getKey());
			startLocation = goalLocation;
			itemsLocation.remove(startLocation);
		}
		return route;
	}

	public static void main(String[] args) {
		ArrayList<Job> jobs = new ArrayList<>();
		ArrayList<JobObject> jobObj = new ArrayList<>();
		HashMap<String, Item> itemMap = new HashMap<>();
		ArrayList<Location> blocks = new ArrayList<>();

		Map map = new Map(4, 4, blocks);
		map.addRobot("Awesome", new Location(4, 4), Direction.NORTH);
		map.addRobot("Fantastic", new Location(0, 0), Direction.NORTH);

		jobObj.add(new JobObject("a", 1));
		jobObj.add(new JobObject("b", 1));
		jobObj.add(new JobObject("c", 1));
		jobObj.add(new JobObject("d", 1));
		jobObj.add(new JobObject("e", 1));
		jobObj.add(new JobObject("f", 1));
		itemMap.put("a", new Item(1f, 1f, 1, 0));
		itemMap.put("b", new Item(1f, 1f, 1, 3));
		itemMap.put("c", new Item(1f, 1f, 2, 1));
		itemMap.put("d", new Item(1f, 1f, 3, 4));
		itemMap.put("e", new Item(1f, 1f, 4, 0));
		itemMap.put("f", new Item(1f, 1f, 4, 2));

		jobs.add(new Job(1, jobObj, itemMap, false));

		RoutePlanner planner = new RoutePlanner(map);
		ArrayList<GridPoint> route = planner.findOverallRoute(new Location(4, 4), jobs.get(0), "Awesome");
		System.out.println(route);
		ArrayList<JobObject> j1 = new ArrayList<>();
		ArrayList<JobObject> j2 = new ArrayList<>();
		// j1.add(jobObj.get(0));
		// j1.add(jobObj.get(2));
		// j1.add(jobObj.get(3));
		// j2.add(jobObj.get(1));
		// j2.add(jobObj.get(4));
		// j2.add(jobObj.get(5));
		// jobs.add(new Job(1, j1, itemMap, false));
		//// jobs.add(new Job(2, j2, itemMap, false));
		// ArrayList<GridPoint> r1 = planner.findOverallRoute(new Location(4,4),
		// jobs.get(0), "Awesome");
		// ArrayList<GridPoint> r2 = planner.findOverallRoute(new Location(0,0),
		// jobs.get(1), "Fantastic");
		// System.out.println(r1);
		// System.out.println("-----");
		// System.out.println(r2);

		// ArrayList<GridPoint> route = rp.findIndividualRoute(new Location(1,0), new
		// Location(1,4), Direction.NORTH).getKey();
		// System.out.println("time frames");
		// System.out.println(map.getGridPoint(1, 1).getTimeFrames());
		// ArrayList<GridPoint> route2 = rp.findIndividualRoute(new Location(0,1), new
		// Location(4,1), Direction.EAST).getKey();
		// System.out.println(route);
		// for(GridPoint g: route) {
		// System.out.println(g.getLocation().getX() + " : " + g.getLocation().getY() +
		// " | [" + g.getTimeFrames().get(0)[0] + ", " + g.getTimeFrames().get(0)[1]);
		// }
		// System.out.println(route2);
		// for(GridPoint g: route2) {
		// System.out.println(g.getLocation().getX() + " : " + g.getLocation().getY() +
		// " | [" + g.getTimeFrames().get(0)[0] + ", " + g.getTimeFrames().get(0)[1]);
		// }

	}

	private Location getLastItemFromArrayList(ArrayList<Location> list) {
		int size = list.size();
		return list.get(size - 1);
	}

	private Location findShortestDistance(Location start, ArrayList<Location> rest) {
		int startX = start.getX();
		int startY = start.getY();
		Location shortestLocation = new Location(startX, startY);
		float shortestDistance = Float.MAX_VALUE;

		for (Location e : rest) {
			float distance = straightDistance(start, e);
			if (distance < shortestDistance) {
				shortestDistance = distance;
				shortestLocation = e;
			}
		}

		return shortestLocation;
	}

	/* The straight line distance between two locations */
	private float straightDistance(Location x, Location y) {
		int xX = x.getX();
		int xY = x.getY();
		int yX = y.getX();
		int yY = y.getY();

		return (float) Math.sqrt(Math.pow(yY - xY, 2) + Math.pow(yX - xX, 2));
	}
}
