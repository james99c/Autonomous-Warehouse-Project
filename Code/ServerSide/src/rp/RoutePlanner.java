package rp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

import rp.DataObjects.Direction;
import rp.DataObjects.GridPoint;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.DataObjects.RobotInformation;
import rp.Interfaces.RoutePlannerInterface;
import rp.RoutePlannerExtra.SearchTree;
import rp.jobDecider.Item;
import javafx.util.Pair;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class RoutePlanner implements RoutePlannerInterface {
	private Map map;
	private static final Location DROP_OFF_ONE = new Location(4,7);
	private static final Location DROP_OFF_TWO = new Location(7,7);

	private static final Logger logger = LogManager.getLogger(RoutePlanner.class);

	public RoutePlanner(Map _map) {
		this.map = _map;
		SearchTree.currentMap = map;

	}

	public synchronized Pair<ArrayList<GridPoint>, Direction> findIndividualRoute(Location currentLocation, Location goalLocation,
			Direction _robotsDirection) {
		SearchTree searchTree = new SearchTree(currentLocation, currentLocation, 0f, goalLocation, null,
				new ArrayList<Pair<GridPoint, Direction>>(), _robotsDirection);
		searchTree.search();
		ArrayList<Pair<GridPoint, Direction>> searchTreeOutput = searchTree.getOutputVariable();
		ArrayList<GridPoint> output = new ArrayList<GridPoint>();
		for (Pair<GridPoint, Direction> item : searchTreeOutput) {
			output.add(item.getKey());
		}

		int index;
		Float[] third = new Float[] {};
		Float[] first = new Float[] {};
		long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - map.startOfTime;
		
		// I apologise for being so lazy
		if (output.size() == 1) {
			map.getGridPoint(output.get(0).getLocation().getX(), output.get(0).getLocation().getY())
			.setUnAvailability(output.get(0).getTimeFrames());
		} 
		else if(output.size() == 2) {
			ArrayList<Float[]> timeFramesTwo = output.get(1).getTimeFrames();
			ArrayList<Float[]> timeFramesOne = output.get(0).getTimeFrames();
			Float[] second = timeFramesTwo.get(timeFramesTwo.size() - 1);
			first = timeFramesOne.get(timeFramesOne.size() - 1);
			
			map.getGridPoint(output.get(0).getLocation().getX(), output.get(0).getLocation().getY())
				.setUnAvailability(new Float[] {currentTime + first[0], currentTime + second[1]});
			
			map.getGridPoint(output.get(1).getLocation().getX(), output.get(0).getLocation().getY())
				.setUnAvailability(output.get(1).getTimeFrames());
		}
		else {
			for (index = 2; index < output.size(); index++) {
				ArrayList<Float[]> timeFramesThree = output.get(index).getTimeFrames();
				ArrayList<Float[]> timeFramesOne = output.get(index - 2).getTimeFrames();
				third = timeFramesThree.get(timeFramesThree.size() - 1);
				first = timeFramesOne.get(timeFramesOne.size() - 1);
				map.getGridPoint(output.get(index - 2).getLocation().getX(), output.get(index - 2).getLocation().getY())
						.setUnAvailability(new Float[] { currentTime + first[0], currentTime + third[1] });
			}
			
			map.getGridPoint(output.get(index - 2).getLocation().getX(), output.get(index - 2).getLocation().getY())
					.setUnAvailability(new Float[] { currentTime + output.get(index - 2).getTimeFrames().get(0)[0], currentTime + third[1] });
			
			map.getGridPoint(output.get(index - 1).getLocation().getX(), output.get(index - 1).getLocation().getY())
			.setUnAvailability(new Float[] { currentTime + third[0], currentTime + third[1] + 3 });
		}

		// need to adjust the time frames

		
		
		return new Pair<ArrayList<GridPoint>, Direction>(output,
				searchTreeOutput.get(searchTreeOutput.size() - 1).getValue());
	}

 
	public ArrayList<Location> findRouteToItem(String _robotName, Item _item){
		Pair<ArrayList<GridPoint>, Direction> outputty = findIndividualRoute(map.getRobotInformation(_robotName).location, new Location( _item.getItemXPos() ,_item.getItemYPos()) , map.getRobotInformation(_robotName).direction);
		map.getRobotInformation(_robotName).direction = outputty.getValue();
		ArrayList<GridPoint> gridPointRoute = outputty.getKey();
		ArrayList<Location> locationRoute = new ArrayList<>();
		for(GridPoint gr: gridPointRoute) {
			locationRoute.add(gr.getLocation());
		}
		return locationRoute;
	}
	
	public ArrayList<Location> findRouteToDropOff(String _robotName){
		Integer distanceToPointOne = heuristic(map.getRobotInformation(_robotName).location, DROP_OFF_ONE);
		Integer distanceToPointTwo = heuristic(map.getRobotInformation(_robotName).location, DROP_OFF_TWO);
		Location betterDropOff;
		if(distanceToPointOne > distanceToPointTwo) {
			betterDropOff = DROP_OFF_ONE;
		}else {
			betterDropOff = DROP_OFF_TWO;
		}
		ArrayList<GridPoint> gridPointRoute = findIndividualRoute(map.getRobotInformation(_robotName).location,betterDropOff, map.getRobotInformation(_robotName).direction).getKey();
		ArrayList<Location> locationRoute = new ArrayList<>();
		for(GridPoint gr: gridPointRoute) {
			locationRoute.add(gr.getLocation());
		}
		return locationRoute;
		
	}
//	public ArrayList<GridPoint> findOverallRoute(Location _startLocation, Job _job, String robotName) {
//		HashMap<String, Item> itemMap = _job.getItemMap();
//		ArrayList<String> itemsID = _job.getItems();
//		ArrayList<Location> itemsLocation = new ArrayList<>();
//		ArrayList<GridPoint> route = new ArrayList<>();
//
//		for (String s : itemsID) {
//			Item item = itemMap.get(s);
//			itemsLocation.add(new Location(item.getX(), item.getY()));
//		}
//		Location startLocation = _startLocation;
//		Location goalLocation = findShortestDistance(startLocation, itemsLocation);
//
//		Pair<ArrayList<GridPoint>, Direction> output = findIndividualRoute(startLocation, goalLocation,
//				map.getRobotInformation(robotName).direction);
//		route.addAll(output.getKey());
//		startLocation = goalLocation;
//		itemsLocation.remove(startLocation);
//		while (!itemsLocation.isEmpty()) {
//			goalLocation = findShortestDistance(startLocation, itemsLocation);
//			output = findIndividualRoute(startLocation, goalLocation, output.getValue());
//			route.addAll(output.getKey());
//			startLocation = goalLocation;
//			itemsLocation.remove(startLocation);
//		}
//	
//		return route;
//	}

	public static void main(String[] args) {
		ArrayList<Location> blocks = new ArrayList<>();
		blocks.add(new Location(1,1));
		blocks.add(new Location(1,2));
		blocks.add(new Location(1,3));
		blocks.add(new Location(1,4));
		Map map = new Map(6, 6, blocks);
		RoutePlanner rp = new RoutePlanner(map);

		 ArrayList<GridPoint> route = rp.findIndividualRoute(new Location(0,0), new
		 Location(0,6), Direction.NORTH).getKey();
		 System.out.println("found first route");

		 ArrayList<GridPoint> route2 = rp.findIndividualRoute(new Location(0,6), new
		 Location(0,0), Direction.SOUTH).getKey();

		 System.out.println("found second route");
		 
		 for(GridPoint g: route) {
			 System.out.println(g.getLocation().getX() + " : " + g.getLocation().getY());
			 
			 Float[] timeFrame = map.getGridPoint(g.getLocation().getX(), g.getLocation().getY()).getTimeFrames().get(0);
			 System.out.println("[ " + timeFrame[0] + " , " + timeFrame[1] + " ]");
		 }
	
		 System.out.println("second route:");
		 for(GridPoint g: route2) {
			 System.out.println(g.getLocation().getX() + " : " + g.getLocation().getY());
			 
			 Float[] timeFrame = map.getGridPoint(g.getLocation().getX(), g.getLocation().getY()).getTimeFrames().get(0);
			 System.out.println("[ " + timeFrame[0] + " , " + timeFrame[1] + " ]");
		 }

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

	private Integer heuristic(Location _startLocation, Location _endLocation) {
		int x1 = _startLocation.getX();
		int y1 = _startLocation.getX();
		
		int x2 = _endLocation.getX();
		int y2 = _endLocation.getY();
		
		return Math.abs(x2-x1) + Math.abs(y2-y1);
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
