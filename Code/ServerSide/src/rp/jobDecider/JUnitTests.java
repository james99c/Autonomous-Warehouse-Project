package rp.jobDecider;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;


public class JUnitTests {

	@Test
	public void testItem() {
		Item item = new Item("aa", 3.45f, 1.07f, 4, 3);
		
		float reward = item.getItemReward();
		
		float weight = item.getItemWeight();
		
		int x = item.getItemXPos();
		
		int y = item.getItemYPos();
		
		assertEquals(3.45f, reward, 0e-1);
		assertEquals(1.07f, weight, 0e-1);
		assertEquals(4, x);
		assertEquals(3, y);
	}

	@Test
	public void testTask() {
		Task task = new Task("ab", 7);
		
		String id = task.getTaskID();
		
		int quantity = task.getTaskQuantity();
		
		assertEquals("ab", id);
		assertEquals(7, quantity);
	}

	@Test
	public void testJob() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task("aa", 5));
		tasks.add(new Task("ab", 2));
		tasks.add(new Task("bd", 7));

		HashMap<String, Item> items = new HashMap<String, Item>();
		items.put("aa", new Item("aa", 2.05f, 1.67f, 2, 1));
		items.put("ab", new Item("ab", 5.90f, 2.00f, 7, 4));
		items.put("bd", new Item("bd", 10.15f, 0.50f, 5, 1));

		Job job = new Job(10200, tasks, items, true);
		int id = job.getJobID();
		
		HashMap<String, Item> items1 = job.getItems();
		
		int numberOfTasks1 = job.getNumberOfTasks();
		int numberOfTasks2 = 14;
		
		float reward = job.getJobReward();
		
		float weight = job.getJobWeight();
		
		boolean cancel = job.getJobCancel();
		
		float rewardDivWeight = job.getRewardDivWeight();
		
		assertEquals(10200, id);
		assertSame(items, items1);
		assertEquals(numberOfTasks2, numberOfTasks1);
		assertEquals(93.10f, reward, 0.001);
		assertEquals(15.85f, weight, 0.001);
		assertTrue(cancel);
		assertEquals(5.87f, rewardDivWeight, 0.01);
	}
}
