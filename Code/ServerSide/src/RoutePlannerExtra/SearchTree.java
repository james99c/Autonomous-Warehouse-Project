package RoutePlannerExtra;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import DataObjects.GridPoint;
import DataObjects.Location;
import DataObjects.Map;

public class SearchTree implements Comparable {
    private static final Logger logger = LogManager.getLogger(SearchTree.class);
	Location currentLocation, previousLocation, goalLocation;
	Float currentCost, heuristicCost, totalCost;
	SearchTree parentNode;
	ArrayList<GridPoint> currentPath;
	ArrayList<SearchTree> childNodes = new ArrayList<SearchTree>();

	static Map currentMap;
	static ArrayList<GridPoint> outputVariable = new ArrayList<GridPoint>();
	static ArrayList<SearchTree> usableLeafNodes = new ArrayList<SearchTree>();

	public SearchTree(Location _currentLocation, Location _previousLocation,
			float _currentCost, Location _goalLocation, SearchTree _parentNode,
			ArrayList<GridPoint> _currentPath) {
		this.currentLocation = _currentLocation;
		this.previousLocation = _previousLocation;
		this.goalLocation = _goalLocation;
		this.parentNode = _parentNode;
		this.currentPath = _currentPath;
		this.currentCost = _currentCost;
		this.heuristicCost = getHeuristicCost(_currentLocation, _goalLocation);
		this.totalCost = _currentCost + heuristicCost;
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
			ArrayList<GridPoint> NewLocationList = currentMap
					.getAvailableLocations(currentLocation, new Float[] {
							currentCost, currentCost + 1 });
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
				for (GridPoint gp : NewLocationList) {
					// need to have time frame and currentCost as the estimated
					// time

					ArrayList<GridPoint> newPath = new ArrayList<GridPoint>(
							currentPath);
					newPath.add(gp);
//					System.out.println(currentPath);
//					for (int i = 0; i < newPath.size(); i++) {
//						System.out.println(newPath.get(i).getLocation().getX()
//								+ " : " + newPath.get(i).getLocation().getY());
//					}

					this.childNodes
							.add(new SearchTree(gp.getLocation(),
									this.currentLocation, currentCost
											+ (timeFrameDifference(gp
													.getTimeFrames())),
									this.goalLocation, this, newPath));
				}
				// then sort usableLeafNodes from lowest to highest totalCost
				usableLeafNodes.addAll(childNodes);
				Collections.sort(usableLeafNodes);
//				for (int i = 0; i < usableLeafNodes.size(); i++) {
//					System.out.println(usableLeafNodes.get(i).totalCost);
//				}
				ArrayList<SearchTree> newList = new ArrayList<SearchTree>();
				SearchTree smallestNode = usableLeafNodes.get(0);

				// System.out.println();
				// while (usableLeafNodes.size() > 1) {
				// Integer indexOfSmallest = 0;
				// for (int i = 0; i < usableLeafNodes.size(); i++) {
				// if (usableLeafNodes.get(i).totalCost <
				// smallestNode.totalCost) {
				// smallestNode = usableLeafNodes.get(i);
				// indexOfSmallest = i;
				// }
				// }
				// newList.add(smallestNode);
				// usableLeafNodes.remove(indexOfSmallest);
				// }
				// usableLeafNodes = newList;

//				System.out.println(usableLeafNodes);
				// searches the child node with the lowest total cost
//				System.out.println(currentLocation.getX() + " : " + currentLocation.getY());
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

	public ArrayList<GridPoint> getOutputVariable() {
		ArrayList<GridPoint> ret = new ArrayList<GridPoint>(this.outputVariable);
		this.outputVariable.clear();
		return ret;
	}

	public static void setMap(Map _map) {
		currentMap = _map;
	}

	public Float getHeuristicCost(Location currentLocation,
			Location goalLocation) {
		Integer changeInX = Math.abs(goalLocation.getX()
				- currentLocation.getX());
		Integer changeInY = Math.abs(goalLocation.getY()
				- currentLocation.getY());
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
