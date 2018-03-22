package rp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import rp.DataObjects.Location;
import rp.jobDecider.Item;
import rp.jobDecider.Job;
import rp.jobDecider.Task;


public class JobAssignerTest {

	@Test
	public void testAssign() {
	JobAssigner mock1 = new JobAssigner();
	ArrayList<Task> jobObj = new ArrayList<>();
	HashMap<String, Item> itemMap = new HashMap<>();
	HashMap<String, Item> itemMap2 = new HashMap<>();
	HashMap<String, Item> itemMap3 = new HashMap<>();

	jobObj.add(new Task("a", 1));
	jobObj.add(new Task("b", 1));
	jobObj.add(new Task("c", 1));
	jobObj.add(new Task("d", 1));
	jobObj.add(new Task("e", 1));
	jobObj.add(new Task("f", 1));
	itemMap.put("a", new Item(null, 1f, 1f, 1, 0));
	itemMap.put("b", new Item(null, 1f, 1f, 1, 3));
	itemMap.put("c", new Item(null, 1f, 1f, 2, 1));
	itemMap.put("d", new Item(null, 1f, 1f, 3, 4));
	itemMap.put("e", new Item(null, 1f, 1f, 4, 0));
	itemMap.put("f", new Item(null, 1f, 1f, 4, 2));
	
	itemMap2.put("a", new Item(null, 1f, 1f, 20, 11));
	itemMap2.put("b", new Item(null, 1f, 1f, 40, 32));
	itemMap2.put("c", new Item(null, 1f, 1f, 27, 11));
	itemMap2.put("d", new Item(null, 1f, 1f, 31, 48));
	itemMap2.put("e", new Item(null, 1f, 1f, 45, 0));
	itemMap2.put("f", new Item(null, 1f, 1f, 4, 21));
	
	itemMap3.put("a", new Item(null, 1f, 1f, 100, 0));
	itemMap3.put("b", new Item(null, 1f, 1f, 12, 36));
	itemMap3.put("c", new Item(null, 1f, 1f, 28, 19));
	itemMap3.put("d", new Item(null, 1f, 1f, 301, 42));
	itemMap3.put("e", new Item(null, 1f, 1f, 40, 0));
	itemMap3.put("f", new Item(null, 1f, 1f, 44, 200));
	
	Job job1 = new Job(1, jobObj, itemMap, false);
	Job job2 = new Job(1, jobObj, itemMap2, false);
	Job job3 = new Job(1, jobObj, itemMap3, false);
	mock1.jobs.add(job1);
	mock1.jobs.add(job2);
	mock1.jobs.add(job3);
	assertEquals("", job1, mock1.assignJob(new Location(0,0), "mockBot") );
	}
	
	@Test
	public void testAssignEdge() {
	JobAssigner mock1 = new JobAssigner();
	ArrayList<Task> jobObj = new ArrayList<>();
	HashMap<String, Item> itemMap = new HashMap<>();
	HashMap<String, Item> itemMap2 = new HashMap<>();
	HashMap<String, Item> itemMap3 = new HashMap<>();

	jobObj.add(new Task("a", 1));
	jobObj.add(new Task("b", 1));
	jobObj.add(new Task("c", 1));
	jobObj.add(new Task("d", 1));
	jobObj.add(new Task("e", 1));
	jobObj.add(new Task("f", 1));
	itemMap.put("a", new Item(null, 1f, 1f, 1, 1));
	itemMap.put("b", new Item(null, 1f, 1f, 1, 2));
	itemMap.put("c", new Item(null, 1f, 1f, 1, 3));
	itemMap.put("d", new Item(null, 1f, 1f, 1, 4));
	itemMap.put("e", new Item(null, 1f, 1f, 1, 5));
	itemMap.put("f", new Item(null, 1f, 1f, 1, 6));
	
	itemMap2.put("a", new Item(null, 1f, 1f, 2, 1));
	itemMap2.put("b", new Item(null, 1f, 1f, 2, 2));
	itemMap2.put("c", new Item(null, 1f, 1f, 2, 3));
	itemMap2.put("d", new Item(null, 1f, 1f, 2, 4));
	itemMap2.put("e", new Item(null, 1f, 1f, 2, 5));
	itemMap2.put("f", new Item(null, 1f, 1f, 2, 6));
	
	itemMap3.put("a", new Item(null, 1f, 1f, 1, 2));
	itemMap3.put("b", new Item(null, 1f, 1f, 2, 3));
	itemMap3.put("c", new Item(null, 1f, 1f, 3, 4));
	itemMap3.put("d", new Item(null, 1f, 1f, 4, 5));
	itemMap3.put("e", new Item(null, 1f, 1f, 5, 6));
	itemMap3.put("f", new Item(null, 1f, 1f, 6, 7));
	
	Job job1 = new Job(1, jobObj, itemMap, false);
	Job job2 = new Job(1, jobObj, itemMap2, false);
	Job job3 = new Job(1, jobObj, itemMap3, false);
	mock1.jobs.add(job1);
	mock1.jobs.add(job2);
	mock1.jobs.add(job3);
	assertEquals("", job1, mock1.assignJob(new Location(0,0), "mockBot") );
	}
	
	/*
	@Test
	public void testSort() {
		Job job1 = new Job((float) 5.0);
		Job job2 = new Job((float) 10.0);
		Job job3 = new Job((float) 15.0);
		Job job4 = new Job((float) 5.1);
		Job job5 = new Job((float) 100.0);
		ArrayList<Job> unsortedJobs = new ArrayList<Job>();
		unsortedJobs.add(job1);
		unsortedJobs.add(job2);
		unsortedJobs.add(job3);
		unsortedJobs.add(job4);
		unsortedJobs.add(job5);
		ArrayList<Job> sortedJobs = new ArrayList<Job>();
		sortedJobs.add(job5);
		sortedJobs.add(job3);
		sortedJobs.add(job2);
		sortedJobs.add(job4);
		sortedJobs.add(job1);
		JobAssigner jobAssigner = new JobAssigner(unsortedJobs);
		jobAssigner.sortJobs();
		assertEquals("job lists should be equal",sortedJobs,jobAssigner.getJobs());
	}
	
	@Test
	public void testOutput(){
		
		Job job1 = new Job((float) 5.0);
		Job job2 = new Job((float) 10.0);
		Job job3 = new Job((float) 15.0);
		ArrayList<Job> sortedJobs = new ArrayList<Job>();
		JobAssigner jobAssigner = new JobAssigner(sortedJobs);
		sortedJobs.add(job3);
		sortedJobs.add(job2);
		sortedJobs.add(job1);
		HashMap<String, ArrayList<Job>> jobMap = new HashMap<String, ArrayList<Job>>();
		jobMap.put("robot1", sortedJobs);
		HashMap<String, ArrayList<Job>> jobMap1 = jobAssigner.outputJobs(sortedJobs);
		assertEquals("hash map created correctly", jobMap, jobMap1);
	}
*/
}
