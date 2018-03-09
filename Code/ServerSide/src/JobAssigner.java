import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import DataObjects.Job;
import Interfaces.JobAssignerInterface;


public class JobAssigner implements JobAssignerInterface{
	
	private ArrayList<Job> jobs;
	
	public JobAssigner(ArrayList<Job> unsortedJobs){
		this.jobs = unsortedJobs;
	}

	@Override
	public void sortJobs() {
		Collections.sort(jobs, new Comparator<Job>(){
		     public int compare(Job o1, Job o2){
		         if(o1.getRewardForJob() == o2.getRewardForJob())
		             return 0;
		         return o1.getRewardForJob() > o2.getRewardForJob() ? -1 : 1;
		     }
		});
	}
	
	public ArrayList<Job> getJobs(){
		return jobs;
	}

	@Override
	public HashMap<String, ArrayList<Job>> outputJobs(ArrayList<Job> sortedJobs) {
		HashMap<String, ArrayList<Job>> jobMap = new HashMap<String, ArrayList<Job>>();
		jobMap.put("robot1", jobs);
		return jobMap;
	}

}
