package rp;

import java.awt.Point;
import java.util.ArrayList;

import rp.DataObjects.Direction;
import rp.DataObjects.Location;

/**
 * 
 * @author written by Aled
 *		   refactored by Anthony
 */

public class RouteConversion {

	/**	The array holding the list of coordinates for the robot to travel to */
	private ArrayList<Location> coordinates;
	/** The position in the array */
	private int i = 0;
	/** The current position of the robot */
	private Location currentPosition;
	/** The next position of the robot */
	private Location nextPosition;
	/** Stores the route for the robot:
	 *  forwards = 0
	 *  left = 1 
	 *  right = 2
	 *  backwards = 3
	*/
	String route = "";

	/**
	 * Constructs a new route conversion object
	 */
	public RouteConversion() {
		
	}

	/**
	 * Converts the coordinates from the array into strings of movements
	 * 
	 * @param coordinates A point array containing coordinates the robot must travel to
	 * @return route The route the robot will take
	 */
	public String convertRoute(Location _startLocation, Direction direction, ArrayList<Location> coordinates) {
		
		this.coordinates = coordinates;
		
		currentPosition = _startLocation;
		// Initial direction robot is looking
		Direction pointing = direction;
		
		pointing = Direction.NORTH;
		System.out.println(pointing == Direction.NORTH);
		System.out.println(pointing == Direction.SOUTH);
		System.out.println(pointing == Direction.EAST);
		System.out.println(pointing == Direction.WEST);
		
		
		/*
		 * While there are still coordinates
		 * define movements for the robot
		 * to take
		 */
		while (i < coordinates.size()) {
			System.out.println("route = " + route);
			double currentX = currentPosition.getX();
			double currentY = currentPosition.getY();
			
			nextPosition = coordinates.get(i);
			double nextX = nextPosition.getX();
			double nextY = nextPosition.getY();
			i++;

			double changeInX = nextX - currentX;
			double changeInY = nextY - currentY;
			System.out.println(pointing);
			System.out.println(changeInX + " : " + changeInY);
			
			// The robot wants to go East
			if (changeInX == 1) {
				if(pointing == Direction.EAST) {
					route += "0";
					System.out.println("I'm hee");
				}
				else if(pointing == Direction.WEST) {
					route += "30";
				}
				else if(pointing == Direction.SOUTH) {
					route += "10";
				}
				else {
					route += "20";
				}
				pointing = Direction.EAST;
			}
			else if (changeInX == -1) {
				if(pointing == Direction.EAST) {
					route += "30";
				}
				else if(pointing == Direction.WEST) {
					route += "0";
				}
				else if(pointing == Direction.SOUTH) {
					route += "20";
				}
				else {
					route += "10";
				}
				pointing = Direction.WEST;
			}
			else if (changeInY == 1) {
				if(pointing == Direction.EAST) {
					route += "10";
				}
				else if(pointing == Direction.WEST) {
					route += "20";
				}
				else if(pointing == Direction.SOUTH) {
					route += "30";
				}
				else {
					route += "0";
				}
				pointing = Direction.NORTH;
			}
			else {
				if(pointing == Direction.EAST) {
					route += "20";
				}
				else if(pointing == Direction.WEST) {
					route += "10";
				}
				else if(pointing == Direction.SOUTH) {
					route += "0";
				}
				else {
					route += "30";
				}
				pointing = Direction.SOUTH;
			}
			currentPosition = nextPosition;
		}
		return route;
	}

}
