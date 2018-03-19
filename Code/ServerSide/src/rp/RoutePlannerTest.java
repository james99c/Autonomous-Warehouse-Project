package rp;

import rp.DataObjects.*;

import rp.JobDeciderTest.*;
import rp.JobDeciderTest.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.*;

public class RoutePlannerTest {
	/*
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
	*/

	@Test
	public void test3() {
		ArrayList<Job> jobs = new ArrayList<>();
		ArrayList<JobObject> jobObj = new ArrayList<>();
		HashMap<String, Item> itemMap = new HashMap<>();
		ArrayList<Location> blocks = new ArrayList<>();
		Location startLocation = new Location(4, 4);
		Map map = new Map(4, 4, blocks);
		map.addRobot("Awesome", startLocation, Direction.NORTH);
		map.addRobot("Fantastic", new Location(0,0), Direction.NORTH);

		jobObj.add(new JobObject("a", 1));
		jobObj.add(new JobObject("b", 1));
		jobObj.add(new JobObject("c", 1));
		jobObj.add(new JobObject("d", 1));
		jobObj.add(new JobObject("e", 1));
		jobObj.add(new JobObject("f", 1));
		itemMap.put("a", new Item(1f, 1f, 1, 0));
		itemMap.put("b", new Item(1f, 1f, 1, 3));
		itemMap.put("c", new Item(1f, 1f, 2, 1));
		itemMap.put("d", new Item(1f, 1f, 3, 4));
		itemMap.put("e", new Item(1f, 1f, 4, 0));
		itemMap.put("f", new Item(1f, 1f, 4, 2));

		//jobs.add(new Job(1, jobObj, itemMap, false));

		RoutePlanner planner = new RoutePlanner(map);
		//ArrayList<GridPoint> route = planner.findRoute(startLocation, jobs.get(0), "Awesome");
		//System.out.println(route);
		ArrayList<JobObject> j1 = new ArrayList<>();
		ArrayList<JobObject> j2 = new ArrayList<>();
		j1.add(jobObj.get(0));
		j1.add(jobObj.get(2));
		j1.add(jobObj.get(3));
		j2.add(jobObj.get(1));
		j2.add(jobObj.get(4));
		j2.add(jobObj.get(5));
		jobs.add(new Job(1, j1, itemMap, false));
		jobs.add(new Job(2, j2, itemMap, false));
		ArrayList<GridPoint> r1 = planner.findOverallRoute(startLocation, jobs.get(0), "Awesome");
		ArrayList<GridPoint> r2 = planner.findOverallRoute(new Location(0,0), jobs.get(1), "Fantastic");
		System.out.println(r1);
		System.out.println("-----");
		System.out.println(r2);
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
