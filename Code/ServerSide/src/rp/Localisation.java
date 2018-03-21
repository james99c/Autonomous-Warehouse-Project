package rp;

import rp.DataObjects.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Localisation {
	// private ArrayList<GridPoint> possibleGridPoint;
	private Map map;
	private GridPoint[][] arrayMap;

	public Localisation(Map _map) {
		this.map = _map;
		this.arrayMap = this.map.getMapArray();
	}

	// gets all gridpoints with the same distances around it
	public ArrayList<GridPoint> getMatchingGridPoints(ArrayList<Integer> _surroundingDistances,
			ArrayList<GridPoint> possibleGridPoints) {
		ArrayList<GridPoint> output = new ArrayList<>();
		ArrayList<Integer> modifiedSD = (ArrayList<Integer>) _surroundingDistances.clone();
		for (GridPoint gr : possibleGridPoints) {
			modifiedSD = (ArrayList<Integer>) _surroundingDistances.clone();
			Integer[] distancesAtGridpoint = getDistancesAtGridPoint(gr);
			ArrayList<Integer> dAtGArray = new ArrayList<>();
			dAtGArray.addAll(Arrays.asList(distancesAtGridpoint));
			Boolean found = true;
			System.out.println(dAtGArray);
			System.out.println("-----");
			System.out.println(_surroundingDistances);
			while (dAtGArray.size() > 0 && found) {
				found = false;
				for (Integer i : modifiedSD) {
					if (dAtGArray.contains(i)) {
						modifiedSD.remove(i); // the number not the index
						dAtGArray.remove(i); // the number not the index
						found = true;
						break;
					}
				}
			}
			if (dAtGArray.size() == 0) {
				output.add(gr);
			}
		}
		return output;

	}

	public Integer[] getDistancesAtGridPoint(GridPoint point) {
		// indexes of the output
		// [0] = East
		// [1] = West
		// [2] = South
		// [3] = North
		Integer[] output = new Integer[] { 0, 0, 0, 0 };
		int robotX = point.getLocation().getX();
		int robotY = point.getLocation().getY();

		// East
		while (robotX + output[0] + 1 < map.getWidth() && arrayMap[robotX + output[0] + 1][robotY].isAvailable(-1.0f)) {
			++output[0];
		}
		// West
		while (robotX - output[1] - 1 >= 0 && arrayMap[robotX - output[1] - 1][robotY].isAvailable(-1.0f)) {
			++output[1];
		}
		// South
		while (robotY - output[2] - 1 >= 0 && arrayMap[robotX][robotY - output[2]].isAvailable(-1.0f)) {
			++output[2];
		}
		// North
		while (robotY + output[3] + 1 < map.getHeight() && arrayMap[robotX][robotY + output[3] + 1].isAvailable(-1.0f)) {
			++output[3];
		}

		return output;
	}
}
