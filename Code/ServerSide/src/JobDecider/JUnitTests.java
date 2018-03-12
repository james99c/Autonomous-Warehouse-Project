package JobDecider;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import DataObjects.Job;

public class JUnitTests {

	@Test
	public void testItem() {
		Item item = new Item(3.45f, 1.07f, 4, 3);
		float reward = item.getReward();
		float weight = item.getWeight();
		int x = item.getX();
		int y = item.getY();
		assertEquals(3.45f, reward, 0e-1);
		assertEquals(1.07f, weight, 0e-1);
		assertEquals(4, x);
		assertEquals(3, y);
	}

	@Test
	public void testJobObject() {
		JobObject job = new JobObject("ab", 7);
		String id = job.getID();
		int quantity = job.getQuantity();
		assertEquals("ab", id);
		assertEquals(7, quantity);
	}

	@Test
	public void testJob() {
		ArrayList<JobObject> jobs = new ArrayList<JobObject>();
		jobs.add(new JobObject("aa", 5));
		jobs.add(new JobObject("ab", 2));
		jobs.add(new JobObject("bd", 7));

		HashMap<String, Item> items = new HashMap<String, Item>();
		items.put("aa", new Item(2.05f, 1.67f, 2, 1));
		items.put("ab", new Item(5.90f, 2.00f, 7, 4));
		items.put("bd", new Item(10.15f, 0.50f, 5, 1));

		Job job = new Job(10200, jobs, items, true);
		int id = job.getID();
		ArrayList<String> items1 = job.getItems();
		ArrayList<String> items2 = new ArrayList<String>();
		items2.add("aa");
		items2.add("ab");
		items2.add("bd");
		ArrayList<Integer> numberOfItems1 = job.getNumberOfItems();
		ArrayList<Integer> numberOfItems2 = new ArrayList<Integer>();
		numberOfItems2.add(5);
		numberOfItems2.add(2);
		numberOfItems2.add(7);
		float reward = job.getJobReward();
		float weight = job.getJobWeight();
		boolean cancel = job.getCancellation();
		ArrayList<JobObject> jobs1 = job.getJob();
		assertEquals(10200, id);
		assertArrayEquals(items1.toArray(), items2.toArray());
		assertArrayEquals(numberOfItems1.toArray(), numberOfItems2.toArray());
		assertEquals(reward, 93.10f, 0.001);
		assertEquals(weight, 15.85f, 0.001);
		assertTrue(cancel);
		assertEquals(jobs1, jobs);
	}
}
