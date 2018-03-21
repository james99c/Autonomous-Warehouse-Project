package rp;

import java.awt.Point;
import java.util.ArrayList;

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
	public String convertRoute(Location _startLocation, String direction, ArrayList<Location> coordinates) {
		
		this.coordinates = coordinates;
		
		currentPosition = _startLocation;
		// Initial direction robot is looking
		String pointing = direction	;
		
		/*
		 * While there are still coordinates
		 * define movements for the robot
		 * to take
		 */
		while (i < coordinates.size()) {

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
				switch (pointing) {
				case "East":
					route += "0";
				case "West":
					route += "30";
				case "South":
					route += "10";
				default:
					route += "20";
				}
				pointing = "East";
			}
			// The robot wants to go West
			else if (changeInX == -1) {
				switch (pointing) {
				case "East":
					route += "30";
				case "West":
					route += "0";
				case "South":
					route += "20";
				default:
					route += "10";
				}

				pointing = "West";
			}
			// The robot wants to go North
			else if (changeInY == 1) {
				switch (pointing) {
				case "East":
					route += "10";
				case "West":
					route += "20";
				case "South":
					route += "30";
				default:
					route += "0";
				}

				pointing = "North";
			}
			// The robot wants to go South
			else {
				switch (pointing) {
				case "East":
					route += "20";
				case "West":
					route += "10";
				case "South":
					route += "0";
				default:
					route += "30";
				}
				pointing = "South";
			}
			currentPosition = nextPosition;
		}
		return route;
	}

}
