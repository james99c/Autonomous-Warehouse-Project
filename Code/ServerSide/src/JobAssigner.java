import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.log4j.Logger;

import DataObjects.Job;
import Interfaces.JobAssignerInterface;


public class JobAssigner implements JobAssignerInterface{
	
	final static Logger logger = Logger.getLogger(JobAssigner.class);
	
	private ArrayList<Job> jobs;
	
	public JobAssigner(ArrayList<Job> unsortedJobs){
		this.jobs = unsortedJobs;
	}

	@Override
	public void sortJobs() {
		logger.debug("sorting job based on reward value");
		Collections.sort(jobs, new Comparator<Job>(){
		     public int compare(Job o1, Job o2){
		         if(o1.getReward() == o2.getReward())
		             return 0;
		         return o1.getReward() > o2.getReward() ? -1 : 1;
		     }
		});
	}
	
	public ArrayList<Job> getJobs(){
		return jobs;
	}

	@Override
	public HashMap<String, ArrayList<Job>> outputJobs(ArrayList<Job> sortedJobs) {
		logger.debug("converting sorted jobs list into a hashmap");
		HashMap<String, ArrayList<Job>> jobMap = new HashMap<String, ArrayList<Job>>();
		jobMap.put("robot1", jobs);
		logger.debug(jobMap.toString());
		return jobMap;
	}

}
