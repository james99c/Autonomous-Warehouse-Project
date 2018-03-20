package rp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import rp.jobDecider.Item;
import rp.jobDecider.Job;
import rp.jobDecider.Task;


public class JobAssignerTest {
	
	private static ArrayList<Job> jobs = new ArrayList<>();

	@Test
	public void testAssign() {
	ArrayList<Task> jobObj = new ArrayList<>();
	HashMap<String, Item> itemMap = new HashMap<>();


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
	

	jobs.add(new Job(1, jobObj, itemMap, false));
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
