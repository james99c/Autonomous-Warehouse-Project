import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import DataObjects.Job;


public class JobAssignerTest {

	@Test
	public void test() {
		Job job1 = new Job(5);
		Job job2 = new  Job(10);
		Job job3 = new Job(15);
		ArrayList<Job> unsortedJobs = new ArrayList<Job>();
		unsortedJobs.add(job1);
		unsortedJobs.add(job2);
		unsortedJobs.add(job3);
		ArrayList<Job> sortedJobs = new ArrayList<Job>();
		unsortedJobs.add(job3);
		unsortedJobs.add(job2);
		unsortedJobs.add(job1);
		JobAssigner jobAssigner = new JobAssigner(unsortedJobs);
		jobAssigner.sortJobs();
		assertEquals("job lists should be equal",sortedJobs,jobAssigner.getJobs());
	}

}
