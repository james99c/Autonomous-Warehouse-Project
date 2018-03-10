import java.util.ArrayList;

import DataObjects.Location;
import DataObjects.Map;
import Interfaces.RoutePlannerInterface;
import RoutePlannerExtra.SearchTree;



public class RoutePlanner implements RoutePlannerInterface{
    private ArrayList<Integer> jobs;

    private ArrayList<Location> route;

    public ArrayList<Location> findRoute(Location currentLocation, Location goalLocation, Map currentMap){
       ArrayList<Location> outputVariable = new ArrayList<Location>();
       
       return outputVariable;
    }
}
