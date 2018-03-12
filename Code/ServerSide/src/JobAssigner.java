import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.log4j.Logger;

import DataObjects.GridPoint;
import DataObjects.Job;
import DataObjects.Location;
import DataObjects.Map;
import Interfaces.JobAssignerInterface;


public class JobAssigner implements JobAssignerInterface{
	
	final static Logger logger = Logger.getLogger(JobAssigner.class);
	
	private ArrayList<Job> jobs;
	// creates map of 5x5 with no unavailable co-ordinates
	private Map map = new Map(5,5,new ArrayList());
	private RoutePlanner routePlanner = new RoutePlanner(map);
	
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
		return jobMap;
	}
	
	public ArrayList<Location> getRoute(Job _job) {
		ArrayList<GridPoint> gridRoute = routePlanner.findRoute(_job.getStartLocation(),_job.getGoalLocation());
		ArrayList<Location> route = new ArrayList<Location>();
		for (int i = 0; i < gridRoute.size(); i ++) {
			route.add(new Location(gridRoute.get(i).getLocation().getX(),gridRoute.get(i).getLocation().getY()));
			
		}
		return route;
	}

}
