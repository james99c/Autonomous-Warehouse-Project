package rp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import rp.DataObjects.Job;


public class JobAssignerTest {
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
