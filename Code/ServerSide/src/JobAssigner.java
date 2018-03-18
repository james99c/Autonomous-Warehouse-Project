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
	
	private static ArrayList<Job> jobs;
	// creates map of 5x5 with no unavailable co-ordinates
	private Map map = new Map(5,5,new ArrayList());
	private RoutePlanner routePlanner = new RoutePlanner(map);
	
	public JobAssigner(ArrayList<Job> unsortedJobs){
		jobs = unsortedJobs;
	}
	
	public ArrayList<Location> assignJob(Location currentLocation){
		Job bestJob = jobs.get(0);
		jobs.remove(0);
		ArrayList<Location> route = getRoute(bestJob);
		return route;
	}
	/*
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
	*/
	public ArrayList<Location> getRoute(Location _startLocation, Location _endLocation) {
		ArrayList<GridPoint> gridRoute = routePlanner.findRoute(_startLocation, _endLocation);
		ArrayList<Location> route = new ArrayList<Location>();
		for (int i = 0; i < gridRoute.size(); i ++) {
			route.add(new Location(gridRoute.get(i).getLocation().getX(),gridRoute.get(i).getLocation().getY()));
			
		}
		return route;
	}
	
	public static void main(String[] args) {
		JobAssigner ja = new JobAssigner(new ArrayList<Job>());
		ArrayList<Location> route = ja.getRoute(new Location(0,0), new Location(3,3));
		for(int i = 0; i < route.size(); i++) {
			logger.debug(route.get(i).getX() + " : " + route.get(i).getY() );
		}
	}

}
