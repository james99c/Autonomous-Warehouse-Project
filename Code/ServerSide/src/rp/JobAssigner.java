package rp;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import rp.DataObjects.GridPoint;
import rp.jobDecider.Item;
import rp.jobDecider.Job;
import rp.DataObjects.Location;
import rp.DataObjects.Map;
import rp.Interfaces.JobAssignerInterface;
import rp.jobDecider.Reader;

/**
 * 
 * Job Assigner
 * 
 * @author Jacob, Cameron
 *
 */

public class JobAssigner implements JobAssignerInterface{
	
	final static Logger logger = Logger.getLogger(JobAssigner.class);
	
	private static ArrayList<Job> jobs = new ArrayList<>();
	// creates map of 5x5 with no unavailable co-ordinates
	private Map map;
	private RoutePlanner routePlanner;
	private Reader jobDecider;
	
	public JobAssigner (Map map){
		jobDecider.startReading();
        jobs = jobDecider.getJobs();
		this.map = map;
		routePlanner = new RoutePlanner(map);
		/*
		ArrayList<JobObject> jobObj = new ArrayList<>();
		HashMap<String, Item> itemMap = new HashMap<>();


		jobObj.add(new JobObject("a", 1));
		jobObj.add(new JobObject("b", 1));
		jobObj.add(new JobObject("c", 1));
		jobObj.add(new JobObject("d", 1));
		jobObj.add(new JobObject("e", 1));
		jobObj.add(new JobObject("f", 1));
		itemMap.put("a", new Item(1f, 1f, 1, 0));
		itemMap.put("b", new Item(1f, 1f, 1, 3));
		itemMap.put("c", new Item(1f, 1f, 2, 1));
		itemMap.put("d", new Item(1f, 1f, 3, 4));
		itemMap.put("e", new Item(1f, 1f, 4, 0));
		itemMap.put("f", new Item(1f, 1f, 4, 2));


		jobs.add(new Job(1, jobObj, itemMap, false));
		*/
	}
	
	public Job assignJob(Location currentLocation, String robotName){
		if(jobs.isEmpty()){
			logger.debug("jobs list is empty");
			return null;
		}
		Job bestJob = getBestJob(currentLocation);
		return bestJob;
		//ArrayList<Location> route = getRoute(currentLocation, bestJob, robotName);
		//return route;
	}
	
	public Job getBestJob(Location currentLocation){
		int currentX = currentLocation.getX();
		int currentY = currentLocation.getY();
		float bestDistance = Integer.MAX_VALUE;
		Job bestJob = null;
		for(int i = 0; i < 3 || i < jobs.size(); i++){
			Job job = jobs.get(i);
			HashMap<String, Item> map = job.getItems();
			float distance = 0;
			for(Item item: map.values()){
				float differenceX = currentX - item.getItemXPos();
				float differenceY = currentY - item.getItemYPos();
				distance += Math.sqrt((differenceX * differenceX) + (differenceY * differenceY));
			}
			float averageDistance = distance / map.size();
			if(averageDistance < bestDistance){
				bestDistance = averageDistance;
				bestJob = job;
			}
		}
		if(bestJob != null){
			jobs.remove(bestJob);
		}
		logger.debug("found best job, removing it from list to give to robot");
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
	/*
	public ArrayList<Location> getRoute(Location _startLocation, Job job, String robotName) {
		ArrayList<GridPoint> gridRoute = routePlanner.findOverallRoute(_startLocation, job, robotName );
		ArrayList<Location> route = new ArrayList<Location>();
		for (int i = 0; i < gridRoute.size(); i ++) {
			route.add(new Location(gridRoute.get(i).getLocation().getX(),gridRoute.get(i).getLocation().getY()));
			
		}
		return route;
	}
	*/
}
