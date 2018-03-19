package rp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

import rp.DataObjects.Direction;
import rp.DataObjects.GridPoint;
import rp.DataObjects.Job;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.DataObjects.RobotInformation;
import rp.Interfaces.RoutePlannerInterface;
import rp.RoutePlannerExtra.SearchTree;
import javafx.util.Pair;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class RoutePlanner implements RoutePlannerInterface{
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

    public Pair<ArrayList<GridPoint>,Direction> findRoute(Location currentLocation, Location goalLocation, Direction _robotsDirection){
        //ArrayList<Location> outputVariable = new ArrayList<Location>();
        SearchTree searchTree = new SearchTree(
            currentLocation,
            currentLocation,
            0f,
            goalLocation,
            null,
            new ArrayList<Pair<GridPoint,Direction>>(),
            _robotsDirection
        );
        searchTree.search();
        ArrayList<Pair<GridPoint, Direction>> searchTreeOutput = searchTree.getOutputVariable();
        ArrayList<GridPoint> output = new ArrayList<GridPoint>();
        for(Pair<GridPoint,Direction> item : searchTreeOutput) {
        		output.add(item.getKey());
        }

        int index;
        Float[] second = new Float[] {};
        Float[] first = new Float[] {};
        long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - map.startOfTime;
        System.out.println(currentTime);
		for (index = 1; index < output.size(); index++) {
			ArrayList<Float[]> timeFramesTwo = output.get(index).getTimeFrames();
			ArrayList<Float[]> timeFramesOne = output.get(index - 1).getTimeFrames();
			 second =  timeFramesTwo.get(timeFramesTwo.size() - 1);
			 first = timeFramesOne.get(timeFramesOne.size() -1);
			System.out.println((currentTime+first[0]) + " : " + (currentTime + second[1]) );
			map.getGridPoint(output.get(index - 1).getLocation().getX(), output.get(index - 1).getLocation().getY() ).setUnAvailability(new Float[] {currentTime + first[0], currentTime + second[1]});
		}
		map.getGridPoint(output.get(index - 1).getLocation().getX(), output.get(index - 1).getLocation().getY()).setUnAvailability(new Float[] {currentTime + second[0], currentTime +second[1]});

        // need to adjust the time frames
        return output;
    }
<<<<<<< HEAD
    
    public ArrayList<Location> findEntireRoute(String _robotID, Location _robotLocation, Job job){
    		RobotInformation robotInfo = map.getRobotInformation(_robotID);
    		findRoute()
    }
    
=======

    public ArrayList<Location> findEntireRoute(String _robotID, Location _robotLocation);

    public ArrayList<GridPoint> findRoute(Location _startLocation, Job _job, String robotName){
    	HashMap<String, rp.JobDecider.Item> itemMap = _job.getItemMap();
    	ArrayList<String> itemsID = _job.getItems();
    	ArrayList<Location> itemsLocation = new ArrayList<>();
    	ArrayList<GridPoint> route = new ArrayList<>();

    	for (String s: itemsID) {
    		rp.JobDecider.Item item = itemMap.get(s);
    		itemsLocation.add(new Location(item.getX(), item.getY()));
    	}
    	Location startLocation = _startLocation;
    	while (!itemsLocation.isEmpty()) {
    		Location goalLocation = findShortestDistance(startLocation, itemsLocation);
    		route.addAll(findIndividualRoute(startLocation, goalLocation));
    		startLocation = goalLocation;
    		itemsLocation.remove(startLocation);
    	}
    	return route;

    	/*
		Item[] listOfItems = new Item[] {};
		_job.getItems().toArray(listOfItems);
		ArrayList<GridPoint> totalRoute = new ArrayList<GridPoint>();
		ArrayList<Location> output = new ArrayList<Location>();
		// sort the array as to which points it's going to go to first
		totalRoute.addAll(findIndividualRoute(_startLocation, new Location(listOfItems[0].getX(), listOfItems[1].getY())));
		for(int i = 0; i < (listOfItems.length - 1); i++ ) {
			totalRoute.addAll(findIndividualRoute(new Location(listOfItems[i].getX(), listOfItems[i].getY()),new Location(listOfItems[i+1].getX(), listOfItems[i+1].getY())));
		}
    	 */
    }

>>>>>>> d7da38b023a9a1ae90da547d2ae591aa6baa44e8
    public static void main(String[] args) {
    		ArrayList<Location> unavailable = new ArrayList<Location>();


		Map map = new Map(5,5, unavailable);

		RoutePlanner rp = new RoutePlanner(map);
//		ArrayList<GridPoint> route = rp.findRoute(new Location(1,0), new Location(1,4), Direction.NORTH);
//		System.out.println("time frames");
//		System.out.println(map.getGridPoint(1, 1).getTimeFrames());
//		ArrayList<GridPoint> route2 = rp.findRoute(new Location(0,1), new Location(4,1), Direction.EAST);
//		System.out.println(route);
//		for(GridPoint g: route) {
//			System.out.println(g.getLocation().getX() + " : " + g.getLocation().getY() + " | [" + g.getTimeFrames().get(0)[0] + ", " + g.getTimeFrames().get(0)[1]);
//		}
//		System.out.println(route2);
//		for(GridPoint g: route2) {
//			System.out.println(g.getLocation().getX() + " : " + g.getLocation().getY() + " | [" + g.getTimeFrames().get(0)[0] + ", " + g.getTimeFrames().get(0)[1]);
//		}

	}


    private Location getLastItemFromArrayList(ArrayList<Location> list) {
        int size = list.size();
        return list.get(size-1);
    }

    private Location findShortestDistance(Location start, ArrayList<Location> rest) {
    	int startX = start.getX();
    	int startY = start.getY();
    	Location shortestLocation = new Location(startX, startY);
    	float shortestDistance = Float.MAX_VALUE;

    	for (Location e: rest) {
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

    	return (float)Math.sqrt(Math.pow(yY-xY, 2) + Math.pow(yX-xX, 2));
    }
}
