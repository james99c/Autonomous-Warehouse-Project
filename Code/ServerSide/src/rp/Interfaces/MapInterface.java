package rp.Interfaces;

import java.util.ArrayList;

import com.sun.tools.javac.util.Pair;

import DataObjects.Location;

public interface MapInterface {
	public Integer getNumberOfRobots();
	public ArrayList<Pair<Integer, Location>> getRobotsAndIDs();
	public Location getRobotLocation(Integer robotID);
	
	// make this route unaccessible by other robots for each location is unaccessible for 2 time frames each
	public void blockRoute(ArrayList<Location> location, Integer RouteID);
	
	// make this route available by other robots
	public void unBlockRouteByID(Integer routeID);
	
	public void updateRobotLocation(Integer RobotID, Location newLocation);
	
	// returns the robots current route
	public ArrayList<Location> getRobotsRoute(Integer RobotID);
	
	// returns a list of surrounding locations which the robot can move to from that location
	public ArrayList<Location> getSurroundingLocations(Location location);
	
	// returns the heuristic cost from one location to the other
	public Integer getHeuristicCost(Location startLocation, Location goalLocation);
}
