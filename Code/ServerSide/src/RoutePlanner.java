import java.util.ArrayList;

import DataObjects.Location;
import DataObjects.Map;
import Interfaces.RoutePlannerInterface;
import RoutePlannerExtra.SearchTree;



public class RoutePlanner implements RoutePlannerInterface{
    private ArrayList<Integer> jobs;
    private ArrayList<GridPoint> route;
    private ArrayList<Location> prevLocation;

    public RoutePlanner() {
        prevLocation = new ArrayList<Location>();
    }

    public ArrayList<GridPoint> findRoute(Location currentLocation, Location goalLocation, Map currentMap){
        //ArrayList<Location> outputVariable = new ArrayList<Location>();
        SearchTree searchTree = new Search(
            currentLocation,
            getLastItemFromArrayList(prevLocation),
            goalLocation,
            this,
            this.route,
            0
        );
        searchTree.setMap(currentMap);

        searchTree.search();
        ArrayList<GridPoint> outputVariable = searchTree.getOutputVariable();
        return outputVariable;
    }

    private <E> getLastItemFromArrayList(ArrayList<E> list) {
        int size = list.size();
        return (E) list.get(size-1);
    }
}
