package rp;
import java.util.ArrayList;
import java.util.HashMap;

import rp.DataObjects.GridPoint;
import rp.DataObjects.Item;
import rp.DataObjects.Job;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.Interfaces.RoutePlannerInterface;
import rp.RoutePlannerExtra.SearchTree;


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

    public ArrayList<GridPoint> findIndividualRoute(Location currentLocation, Location goalLocation){
        //ArrayList<Location> outputVariable = new ArrayList<Location>();
        SearchTree searchTree = new SearchTree(
            currentLocation,
            currentLocation,
            0f,
            goalLocation,
            null,
            new ArrayList<GridPoint>()
        );     
        searchTree.search();
        ArrayList<GridPoint> outputVariable = searchTree.getOutputVariable();
        
        return outputVariable;
    }
    
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
    
    public static void main(String[] args) {
    		ArrayList<Location> unavailable = new ArrayList<Location>();
//    		unavailable.add(new Location(2,3));
//    		unavailable.add(new Location(2,2));
//    		unavailable.add(new Location(3,2));
    		
		Map map = new Map(5,5, unavailable);
		RoutePlanner search = new RoutePlanner(map);
		
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
