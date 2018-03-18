package rp.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import rp.DataObjects.Job;
import rp.DataObjects.Location;

public interface JobAssignerInterface {

	//public void sortJobs();
	//public HashMap<String, ArrayList<Job>> outputJobs(ArrayList<Job> sortedJobs);
	public ArrayList<Location> assignJob(Location currentLocation, String robotName);
	public Job getBestJob(Location currentLocation);
	public ArrayList<Location> getRoute(Location _startLocation, Job job, String robotName);
}
