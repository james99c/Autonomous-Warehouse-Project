package rp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rp.DataObjects.Direction;
import rp.DataObjects.GridPoint;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
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
    
    public ArrayList<Location> findEntireRoute(String _robotID, Location _robotLocation, )
    
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
}
