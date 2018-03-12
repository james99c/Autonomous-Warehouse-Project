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


    private Location getLastItemFromArrayList(ArrayList<Location> list) {
        int size = list.size();
        return list.get(size-1);
    }
}
