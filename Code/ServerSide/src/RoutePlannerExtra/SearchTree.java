package RoutePlannerExtra;

import java.util.ArrayList;
import DataObjects.GridPoint;
import DataObjects.Location;

public class SearchTree {
  Location currentLocation, previousLocation, goalLocation;
  Float currentCost, heuristicCost, totalCost;
  SearchTree parentNode;
  ArrayList<GridPoint> currentPath, outputVariable;
  ArrayList<SearchTree> childNodes = new ArrayList<SearchTree>();

  static ArrayList<SearchTree> usableLeafNodes = new ArrayList<SearchTree>();

  SearchTree(Location _currentLocation, Location _previousLocation, float currentCost, Location goalLocation,
      SearchTree parentNode, ArrayList<GridPoint> currentPath, ArrayList<GridPoint> outputVariable) {
    this.currentLocation = currentLocation;
    this.previousLocation = previousLocation;
    this.goalLocation = goalLocation;
    this.parentNode = parentNode;
    this.currentPath = currentPath;
    this.outputVariable = outputVariable;
    this.currentCost = currentCost;
    this.heuristicCost = getHeuristicCost(currentLocation, goalLocation);
    this.totalCost = currentCost + heuristicCost;
  }

  private void searchForGoal() {
    if(!(this.currentLocation.equals(this.goalLocation))){
      NewLocationList = Map.findAllMovementOptions(currentLocation, currentCost);
      // if there are no movement options
      if(NewLocationList.size() == 0){

          if(searchTree.size() > 0){
          usableLeafNodes.remove(0).search();
          return;
        } else{
          System.out.println("there are no paths to the object");
          return;
        }

      }
      else{
        for (int i = 0; i < NewLocationList.size(); i++){
          // need a way to calculate the cost of moving to the child node                                                                                                 // need to have time frame and currentCost
          this.childNodes.add(
            new SearchTree(NewLocationList.get(i), this.currentLocation, currentCost + 1, this.goalLocation, this, currentPath.add(GridPoint(NewLocationList.get(i), Timeframe), this.inputVariable, this.usableLeafNodes));
        }
        // then sort child nodes from lowest to highest totalCost
        usableLeafNodes.addAll(childNodes);

        // look up how to use this
        // usableLeafNodes.sort( < SearchTree.currentCost);

        usableLeafNodes.remove(0).search();
        return;

      }
    }
    else{  // at goal location
      if(usableLeafNodes.get(0).currentCost >= this.currentCost){
        this.inputVariable = currentPath;
        this.usableLeafNodes.empty();
        return;
      }
      else{
        usableLeafNodes.get(0).remove().search();
        return;
      }
    }
  }

  private Float getHeuristicCost(Location currentLocation, Location goalLocation) {
    int changeInX = Math.abs(goalLocation.getX() - currentLocation.getX());
    int changeInY = Math.abs(goalLocation.getY() - currentLocation.getY());
    return changeInX + changeInY;
  }
}
