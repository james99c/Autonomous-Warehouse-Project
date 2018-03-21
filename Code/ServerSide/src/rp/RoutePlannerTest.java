package rp;

import rp.DataObjects.*;
import rp.jobDecider.Item;
import rp.jobDecider.Job;
import rp.jobDecider.Task;
import rp.JobDecider.*;

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
		ArrayList<Task> tasks = new ArrayList<>();
		HashMap<String, Item> itemMap = new HashMap<>();
		ArrayList<Location> blocks = new ArrayList<>();
		
		ArrayList<Location> route = new ArrayList<>();
		
		Location startLocation = new Location(0, 0);
		
		Map map = new Map(4, 4, blocks);
		map.addRobot("Awesome", startLocation, Direction.NORTH);
		map.addRobot("Fantastic", new Location(0,0), Direction.NORTH);
		RoutePlanner planner = new RoutePlanner(map);

		//tasks.add(new Task("a", 1));
		tasks.add(new Task("b", 1));
		tasks.add(new Task("c", 1));
//		tasks.add(new Task("d", 1));
//		tasks.add(new Task("e", 1));
//		tasks.add(new Task("f", 1));
		itemMap.put("a", new Item("a", 1f, 1f, 1, 0));
		itemMap.put("b", new Item("b", 1f, 1f, 1, 3));
		itemMap.put("c", new Item("c", 1f, 1f, 2, 1));
		itemMap.put("d", new Item("d", 1f, 1f, 3, 4));  
		itemMap.put("e", new Item("e", 1f, 1f, 4, 0));
		itemMap.put("f", new Item("f", 1f, 1f, 4, 2));

		jobs.add(new Job(1, tasks, itemMap, false));
		
		for (Task t: tasks) {
			route = planner.findRouteToItem("Awesome", itemMap.get(t.getTaskID()));
			int lastIndex = route.size() - 1;
			map.updateRobotsLocation("Awesome", route.get(lastIndex));
			System.out.println(route);
		}
	}
	
	@Test
	public void test4_crash() {
		/*
		System.out.println("---TEST 4---");
		ArrayList<Job> jobs = new ArrayList<>();
		ArrayList<JobObject> jobObj1 = new ArrayList<>();
		ArrayList<JobObject> jobObj2 = new ArrayList<>();
		HashMap<String, Item> itemMap = new HashMap<>();
		ArrayList<Location> blocks = new ArrayList<>();
		Map map = new Map(4, 4, blocks);
		RoutePlanner planner = new RoutePlanner(map);
		map.addRobot("Awesome", new Location(0,2), Direction.EAST);
		map.addRobot("Fantastic", new Location(2,0), Direction.NORTH);

		jobObj1.add(new JobObject("a", 1));
		jobObj2.add(new JobObject("b", 1));
		itemMap.put("a", new Item(1f, 1f, 3, 2));
		itemMap.put("b", new Item(1f, 1f, 2, 3));
		
		jobs.add(new Job(1, jobObj1, itemMap, false));
		jobs.add(new Job(2, jobObj2, itemMap, false));
		ArrayList<GridPoint> r1 = planner.findOverallRoute(new Location(0,2), jobs.get(0), "Awesome");
		ArrayList<GridPoint> r2 = planner.findOverallRoute(new Location(2,0), jobs.get(1), "Fantastic");
		
		System.out.println(r1);
		System.out.println("---");
		System.out.println(r2);
		System.out.println();
		*/
	}
	
	@Test
	public void test5_noRoute() {
		/*
		System.out.println("---TEST 5---");
		ArrayList<Job> jobs = new ArrayList<>();
		ArrayList<JobObject> jobObj = new ArrayList<>();
		HashMap<String, Item> itemMap = new HashMap<>();
		ArrayList<Location> blocks = new ArrayList<>();
		blocks.add(new Location(0,1));
		blocks.add(new Location(1,0));
		Location startLocation = new Location(0, 0);
		Map map = new Map(4, 4, blocks);
		RoutePlanner planner = new RoutePlanner(map);
		
		map.addRobot("Awesome", startLocation, Direction.NORTH);
		
		jobObj.add(new JobObject("b", 1));
		itemMap.put("b", new Item(1f, 1f, 1, 3));
		
		jobs.add(new Job(1, jobObj, itemMap, false));
		ArrayList<GridPoint> r = planner.findOverallRoute(startLocation, jobs.get(0), "Awesome");
		System.out.println(r);
		System.out.println();;
		*/
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
