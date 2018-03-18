package rp;

import java.util.ArrayList;

import DataObjects.Direction;
import DataObjects.GridPoint;
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

    public ArrayList<GridPoint> findRoute(Location currentLocation, Location goalLocation, Direction _robotsDirection){
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
		for (index = 1; index < output.size(); index++) {
			ArrayList<Float[]> allTimeFramesOne = output.get(index-1).getTimeFrames();
			ArrayList<Float[]> allTimeFramesTwo = output.get(index).getTimeFrames();
			second = allTimeFramesTwo.get(allTimeFramesTwo.size() - 1);
			first = allTimeFramesOne.get(allTimeFramesOne.size() -1);
			GridPoint gp = output.get(index-1);
			
			map.getGridpoint(gp.getLocation().getX(), gp.getLocation().getY()).setUnAvailability(new Float[] {first[0], second[1]});
		}
		map.getGridpoint(output.get(index).getLocation().getX(), output.get(index).getLocation().getY()).setUnAvailability(
				new Float[] {second[0],second[1]}
				);
        // need to adjust the time frames
        return output;
    }
    
    public static void main(String[] args) {
    		ArrayList<Location> unavailable = new ArrayList<Location>();
//    		unavailable.add(new Location(2,3));
//    		unavailable.add(new Location(2,2));
//    		unavailable.add(new Location(3,2));
    		
//		Map map = new Map(5,5, unavailable);
//		RoutePlanner search = new RoutePlanner(map);
//		ArrayList<GridPoint> route = search.findRoute(new Location(0,0), new Location(3,4));
//		for(int i = 0; i < route.size(); i++) {
//			logger.debug(route.get(i).getLocation().getX() + " : " + route.get(i).getLocation().getY() );
//		}
	}


    private Location getLastItemFromArrayList(ArrayList<Location> list) {
        int size = list.size();
        return list.get(size-1);
    }
}
