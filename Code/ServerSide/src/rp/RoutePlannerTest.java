package rp;

import DataObjects.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;

public class RoutePlannerTest {

	// Map = no obstables
	@Test
	public void test1() {
		Map map = new Map(5, 5, new ArrayList<Location>());
		RoutePlanner planner = new RoutePlanner(map);
		Location start;
		Location end;
		start = new Location(0, 0);
		end = new Location(3, 4);

		ArrayList<GridPoint> desired = new ArrayList<GridPoint>();
		desired.add(new GridPoint(0, 1, false));
		desired.add(new GridPoint(0, 2, false));
		desired.add(new GridPoint(0, 3, false));
		desired.add(new GridPoint(0, 4, false));
		desired.add(new GridPoint(1, 4, false));
		desired.add(new GridPoint(2, 4, false));
		desired.add(new GridPoint(3, 4, false));

		ArrayList<GridPoint> result = planner.findRoute(start, end);
		Assert.assertTrue(compareList(desired, result));
	}

	@Test
	public void test2() {
		// obstacles in 0,3 and 3,0
		ArrayList<Location> unavailable = new ArrayList<Location>();
		unavailable.add(new Location(0, 3));
		unavailable.add(new Location(3, 0));
		Map map = new Map(5, 5, unavailable);
		RoutePlanner planner = new RoutePlanner(map);
		Location start = new Location(0, 0);
		Location end = new Location(3, 4);
		ArrayList<GridPoint> desired = new ArrayList<GridPoint>();
		desired.add(new GridPoint(0, 1, false));
		desired.add(new GridPoint(0, 2, false));
		desired.add(new GridPoint(1, 2, false));
		desired.add(new GridPoint(1, 3, false));
		desired.add(new GridPoint(1, 4, false));
		desired.add(new GridPoint(2, 4, false));
		desired.add(new GridPoint(3, 4, false));

		ArrayList<GridPoint> result = planner.findRoute(start, end);
		Assert.assertTrue(compareList(desired, result));
	}

	@Test
	public void test3() {

	}

	private static boolean compareList(ArrayList<GridPoint> a, ArrayList<GridPoint> b) {
		if (a.size() != b.size()) return false;
		int size = a.size();

		for (int i = 0; i < size; i++) {
			Location p1 = a.get(i).getLocation();
			Location p2 = b.get(i).getLocation();

			if (p1.getX() != p2.getX() || p1.getY() != p2.getY())
				return false;
		}

		return true;
	}
}