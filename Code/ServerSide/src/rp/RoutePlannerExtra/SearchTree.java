package rp.RoutePlannerExtra;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import rp.DataObjects.Direction;
import rp.DataObjects.GridPoint;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import javafx.util.Pair;

public class SearchTree implements Comparable {
	private static final Logger logger = LogManager.getLogger(SearchTree.class);
	Location currentLocation, previousLocation, goalLocation;
	Float currentCost, heuristicCost, totalCost;
	SearchTree parentNode;
	ArrayList<Pair<GridPoint,Direction>> currentPath;
	ArrayList<SearchTree> childNodes = new ArrayList<SearchTree>();
	Direction currentDirection;

	public static Map currentMap;
	static ArrayList<Pair<GridPoint,Direction>> outputVariable = new ArrayList<Pair<GridPoint,Direction>>();
	static ArrayList<SearchTree> usableLeafNodes = new ArrayList<SearchTree>();

	public SearchTree(Location _currentLocation, Location _previousLocation, float _currentCost, Location _goalLocation,
			SearchTree _parentNode, ArrayList<Pair<GridPoint, Direction>> _currentPath, Direction _currentDirection) {
		this.currentLocation = _currentLocation;
		this.previousLocation = _previousLocation;
		this.goalLocation = _goalLocation;
		this.parentNode = _parentNode;
		this.currentPath = _currentPath;
		this.currentCost = _currentCost;
		this.heuristicCost = getHeuristicCost(_currentLocation, _goalLocation);
		this.totalCost = _currentCost + heuristicCost;
		this.currentDirection = _currentDirection;
	}

	// searches this particular node for goal
	public void search() {
		// if not at goal
		if (!(getHeuristicCost(currentLocation, goalLocation) == 0)) { // need map to
			// calculate
			// how long
			// it's
			// gonna
			// take to
			// move that
			// junction
			ArrayList<Pair<GridPoint, Direction>> NewLocationList = currentMap.getAvailableLocations(currentLocation,
					 currentCost, currentDirection );
			// if there are no movement options
			if (NewLocationList.size() == 0) {
				// if there is another usable node
				if (usableLeafNodes.size() > 0) {
					usableLeafNodes.remove(0).search();
					return;
				} else { // when there are no other usable nodes
					System.out.println("there are no paths to the object");
					return;
				}

			} else { // when there are movement options
				for (Pair<GridPoint,Direction> gp : NewLocationList) {
					// need to have time frame and currentCost as the estimated
					// time

					ArrayList<Pair<GridPoint,Direction>> newPath = new ArrayList<Pair<GridPoint,Direction>>(currentPath);
					newPath.add(gp);
					// System.out.println(currentPath);
					// for (int i = 0; i < newPath.size(); i++) {
					// System.out.println(newPath.get(i).getLocation().getX()
					// + " : " + newPath.get(i).getLocation().getY());
					// }

					this.childNodes.add(new SearchTree(gp.getKey().getLocation(), this.currentLocation,
							currentCost + (timeFrameDifference(gp.getKey().getTimeFrames())), this.goalLocation, this, newPath, newPath.get(newPath.size() - 1).getValue()));
				}
				// then sort usableLeafNodes from lowest to highest totalCost
				usableLeafNodes.addAll(childNodes);
				Collections.sort(usableLeafNodes);

				ArrayList<SearchTree> newList = new ArrayList<SearchTree>();
				SearchTree smallestNode = usableLeafNodes.get(0);

				// System.out.println(usableLeafNodes);
				// searches the child node with the lowest total cost
				// System.out.println(currentLocation.getX() + " : " + currentLocation.getY());
				usableLeafNodes.remove(0).search();
				return;
			}
		} else { // at goal location
			// has found the ideal route
			if (usableLeafNodes.get(0).totalCost > this.totalCost) {
				this.outputVariable = currentPath;
				this.usableLeafNodes.clear();
				// ends the search
				return;
			} else {
				// searches the next best leaf node
				usableLeafNodes.remove(0).search();
				return;
			}
		}
	}

	public ArrayList<Pair<GridPoint,Direction>> getOutputVariable() {
		ArrayList<Pair<GridPoint,Direction>> ret = new ArrayList<Pair<GridPoint,Direction>>(this.outputVariable);
		this.outputVariable.clear();
		return ret;
	}

	public Float getHeuristicCost(Location currentLocation, Location goalLocation) {
		Integer changeInX = Math.abs(goalLocation.getX() - currentLocation.getX());
		Integer changeInY = Math.abs(goalLocation.getY() - currentLocation.getY());
		return (Float) (changeInX.floatValue() + changeInY.floatValue());
	}

	public int compareTo(SearchTree compareTree) {
		float compareage = ((SearchTree) compareTree).totalCost;

		/* For Ascending order */
		return (int) (this.totalCost - compareage);
	}

	@Override
	public int compareTo(Object compareTree) {
		Float compareage = ((SearchTree) compareTree).totalCost;

		/* For Ascending order */
		// hope that something doesn't arrive within the same second
		return (int) (this.totalCost - compareage);
	}

	private float timeFrameDifference(ArrayList<Float[]> listOfTimeFrame) {
		int size = listOfTimeFrame.size();
		Float[] timeFrame = listOfTimeFrame.get(size - 1);
		return timeFrame[1] - timeFrame[0];
	}
}
