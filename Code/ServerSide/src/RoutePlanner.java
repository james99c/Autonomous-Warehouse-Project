import java.util.ArrayList;

import DataObjects.GridPoint;
import DataObjects.Location;
import DataObjects.Map;
import Interfaces.RoutePlannerInterface;
import RoutePlannerExtra.SearchTree;



public class RoutePlanner implements RoutePlannerInterface{
    private ArrayList<Integer> jobs;
    private ArrayList<GridPoint> route;
    private ArrayList<Location> prevLocation;
    private Map map;

    public RoutePlanner(Map _map) {
        prevLocation = new ArrayList<Location>();
        this.map = _map;
        SearchTree.setMap(map);
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
    	Map map = new Map(5,5, new ArrayList<Location>());
    	RoutePlanner routeSearch = new RoutePlanner(map);
    	System.out.println(routeSearch.findRoute(new Location(0,0), new Location(5,5)));
    	//		new RoutePlanner();
//		new Map(5,5, new ArrayList<Location>());
	}

    private Location getLastItemFromArrayList(ArrayList<Location> list) {
        int size = list.size();
        return list.get(size-1);
    }
}
