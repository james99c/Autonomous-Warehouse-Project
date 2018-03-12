import java.util.ArrayList;

import DataObjects.GridPoint;
import DataObjects.Location;
import DataObjects.Map;
import Interfaces.RoutePlannerInterface;
import RoutePlannerExtra.SearchTree;


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

    public ArrayList<GridPoint> findRoute(Location currentLocation, Location goalLocation){
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
    
    public static void main(String[] args) {
    		ArrayList<Location> unavailable = new ArrayList<Location>();
//    		unavailable.add(new Location(2,3));
//    		unavailable.add(new Location(2,2));
//    		unavailable.add(new Location(3,2));
    		
		Map map = new Map(5,5, unavailable);
		RoutePlanner search = new RoutePlanner(map);
		ArrayList<GridPoint> route = search.findRoute(new Location(0,0), new Location(3,4));
		for(int i = 0; i < route.size(); i++) {
			logger.debug(route.get(i).getLocation().getX() + " : " + route.get(i).getLocation().getY() );
		}
	}


    private Location getLastItemFromArrayList(ArrayList<Location> list) {
        int size = list.size();
        return list.get(size-1);
    }
}
