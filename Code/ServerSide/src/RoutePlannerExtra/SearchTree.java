package RoutePlannerExtra;

import java.util.ArrayList;
import java.util.Collections;

import DataObjects.GridPoint;
import DataObjects.Location;
import DataObjects.Map;

public class SearchTree implements Comparable {
  Location currentLocation, previousLocation, goalLocation;
  Float currentCost, heuristicCost, totalCost;
  SearchTree parentNode;
  ArrayList<GridPoint> currentPath, outputVariable;
  ArrayList<SearchTree> childNodes = new ArrayList<SearchTree>();
  
  static Map currentMap;
  static ArrayList<SearchTree> usableLeafNodes = new ArrayList<SearchTree>();

  SearchTree(Location _currentLocation, Location _previousLocation, float _currentCost, Location _goalLocation,
      SearchTree _parentNode, ArrayList<GridPoint> _currentPath, ArrayList<GridPoint> _outputVariable) {
    this.currentLocation = _currentLocation;
    this.previousLocation = _previousLocation;
    this.goalLocation = _goalLocation;
    this.parentNode = _parentNode;
    this.currentPath = _currentPath;
    this.outputVariable = _outputVariable;
    this.currentCost = _currentCost;
    this.heuristicCost = getHeuristicCost(_currentLocation, _goalLocation);
    this.totalCost = _currentCost + heuristicCost;
  }

  // searches this particular node for goal
  private void search() {
    // if not at goal
    if (!(this.currentLocation.equals(this.goalLocation))) {					// need map to calculate how long it's gonna take to move that junction
      ArrayList<GridPoint> NewLocationList = currentMap.getAvailableLocations(currentLocation, new Float[] {currentCost, currentCost + 1} );
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
        for (int i = 0; i < NewLocationList.size(); i++) {
          // need to have time frame and currentCost as the estimated time
          
          ArrayList<GridPoint> newPath = (ArrayList<GridPoint>) currentPath;
          newPath.add(NewLocationList.get(i));
          
          this.childNodes.add(new SearchTree(NewLocationList.get(i).getLocation(), this.currentLocation,
              currentCost + (NewLocationList.get(i).getTimeFrames().get(-1)[1] - NewLocationList.get(i).getTimeFrames().get(-1)[0]), this.goalLocation, this,
              newPath, outputVariable));
        }
        */
        for (Location e: newLocationList)
          this.childNodes.add(
            new SearchTree(e, this.currentLocation, currentCost + 1, this.goalLocation, 
                this, currentPath.add(new GridPoint(e, Timeframe), this.inputVariable, 
                this.usableLeafNodes)
            )
          );
        // then sort child nodes from lowest to highest totalCost
        usableLeafNodes.addAll(childNodes);
        Collections.sort(usableLeafNodes);
        // searches the child node with the lowest total cost
        usableLeafNodes.remove(0).search();
        return;

      }
    } else { // at goal location
      // has found the ideal route
      if (usableLeafNodes.get(0).currentCost >= this.currentCost) {
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

  private Float getHeuristicCost(Location currentLocation, Location goalLocation) {
    Integer changeInX = Math.abs(goalLocation.getX() - currentLocation.getX());
    Integer changeInY = Math.abs(goalLocation.getY() - currentLocation.getY());
    return (Float) (changeInX.floatValue() + changeInY.floatValue());
  }

  public int compareTo(SearchTree compareTree) {
    float compareage = ((SearchTree) compareTree).totalCost;

    /* For Ascending order*/
    return (int) (this.totalCost - compareage);
  }

@Override
public int compareTo(Object compareTree) {
	  Float compareage = ((SearchTree) compareTree).totalCost;

	    /* For Ascending order*/
	  	// hope that something doesn't arrive within the same second
	    return (int) (this.totalCost - compareage);
}
}
