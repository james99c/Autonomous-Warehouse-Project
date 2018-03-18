package rp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.log4j.Logger;

import rp.DataObjects.GridPoint;
import rp.DataObjects.Job;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.Interfaces.JobAssignerInterface;
import rp.JobDecider.Item;
import rp.JobDecider.Reader;


public class JobAssigner implements JobAssignerInterface{
	
	final static Logger logger = Logger.getLogger(JobAssigner.class);
	
	private static ArrayList<Job> jobs;
	// creates map of 5x5 with no unavailable co-ordinates
	private Map map;
	private RoutePlanner routePlanner;
	private Reader jobDecider;
	
	public JobAssigner (Map map){
		jobs = Reader.;
		this.map = map;
		routePlanner = new RoutePlanner(map);
	}
	
	public ArrayList<Location> assignJob(Location currentLocation, String robotName){
		if(jobs.isEmpty()){
			return null;
		}
		Job bestJob = getBestJob(currentLocation);
		ArrayList<Location> route = getRoute(currentLocation, bestJob, robotName);
		return route;
	}
	
	public Job getBestJob(Location currentLocation){
		int currentX = currentLocation.getX();
		int currentY = currentLocation.getY();
		float bestDistance = Integer.MAX_VALUE;
		Job bestJob = null;
		for(int i = 0; i < 3 || i < jobs.size(); i++){
			Job job = jobs.get(i);
			HashMap<String, Item> map = job.getItemMap();
			float distance = 0;
			for(Item item: map.values()){
				float differenceX = currentX - item.getX();
				float differenceY = currentY - item.getY();
				distance += Math.sqrt((differenceX * differenceX) + (differenceY * differenceY));
			}
			float averageDistance = distance / map.size();
			if(averageDistance < bestDistance){
				bestDistance = averageDistance;
				bestJob = job;
			}
		}
		jobs.remove(bestJob);
		return bestJob;
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
	public ArrayList<Location> getRoute(Location _startLocation, Job job, String robotName) {
		ArrayList<GridPoint> gridRoute = routePlanner.findRoute(_startLocation, job, robotName );
		ArrayList<Location> route = new ArrayList<Location>();
		for (int i = 0; i < gridRoute.size(); i ++) {
			route.add(new Location(gridRoute.get(i).getLocation().getX(),gridRoute.get(i).getLocation().getY()));
			
		}
		return route;
	}
	/*
	public static void main(String[] args) {
		JobAssigner ja = new JobAssigner(new ArrayList<Job>());
		ArrayList<Location> route = ja.getRoute(new Location(0,0), new Location(3,3));
		for(int i = 0; i < route.size(); i++) {
			logger.debug(route.get(i).getX() + " : " + route.get(i).getY() );
		}
	}
	 */	
}
