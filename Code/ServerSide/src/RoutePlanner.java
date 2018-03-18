import java.util.ArrayList;

import DataObjects.Direction;
import DataObjects.GridPoint;
import DataObjects.Item;
import DataObjects.Job;
import DataObjects.Location;
import DataObjects.Map;
import Interfaces.RoutePlannerInterface;
import RoutePlannerExtra.SearchTree;
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

<<<<<<< HEAD
    public ArrayList<GridPoint> findRoute(Location currentLocation, Location goalLocation, Direction _robotsDirection){
=======
    public ArrayList<GridPoint> findIndividualRoute(Location currentLocation, Location goalLocation){
>>>>>>> testing
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
<<<<<<< HEAD
        ArrayList<Pair<GridPoint, Direction>> searchTreeOutput = searchTree.getOutputVariable();
        ArrayList<GridPoint> output = new ArrayList<GridPoint>();
        for(Pair<GridPoint,Direction> item : searchTreeOutput) {
        		output.add(item.getKey());
        }
        // need to adjust the time frames
        return output;
=======
        ArrayList<GridPoint> outputVariable = searchTree.getOutputVariable();
        
        return outputVariable;
>>>>>>> testing
    }
    
    public ArrayList<Location> findRoute(Location _startLocation, Job _job, String robotName){
    		Item[] listOfItems = new Item[] {};
    		_job.getItems().toArray(listOfItems);
    		ArrayList<GridPoint> totalRoute = new ArrayList<GridPoint>();
    		ArrayList<Location> output = new ArrayList<Location>();
    		// sort the array as to which points it's going to go to first
    		totalRoute.addAll(findIndividualRoute(_startLocation, new Location(listOfItems[0].getX(), listOfItems[1].getY())));
    		for(int i = 0; i < (listOfItems.length - 2); i++ ) {
    			totalRoute.addAll(findIndividualRoute(new Location(listOfItems[i].getX(), listOfItems[i].getY()),new Location(listOfItems[i+1].getX(), listOfItems[i+1].getY())));
    		}
    }
    
    public static void main(String[] args) {
    		ArrayList<Location> unavailable = new ArrayList<Location>();
//    		unavailable.add(new Location(2,3));
//    		unavailable.add(new Location(2,2));
//    		unavailable.add(new Location(3,2));
    		
<<<<<<< HEAD
//		Map map = new Map(5,5, unavailable);
//		RoutePlanner search = new RoutePlanner(map);
//		ArrayList<GridPoint> route = search.findRoute(new Location(0,0), new Location(3,4));
//		for(int i = 0; i < route.size(); i++) {
//			logger.debug(route.get(i).getLocation().getX() + " : " + route.get(i).getLocation().getY() );
//		}
=======
		Map map = new Map(5,5, unavailable);
		RoutePlanner search = new RoutePlanner(map);
		
>>>>>>> testing
	}


    private Location getLastItemFromArrayList(ArrayList<Location> list) {
        int size = list.size();
        return list.get(size-1);
    }
}
