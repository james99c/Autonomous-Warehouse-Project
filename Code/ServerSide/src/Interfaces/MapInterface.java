package Interfaces;

import java.util.ArrayList;

import com.sun.tools.javac.util.Pair;

import DataObjects.Location;

public interface MapInterface {
	public Integer getNumberOfRobots();
	public ArrayList<Pair<Integer, Location>> getRobotsAndIDs();
	public Location getRobotLocation(Integer robotID);
}
