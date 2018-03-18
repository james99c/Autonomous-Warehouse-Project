package Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import DataObjects.Job;
import DataObjects.Location;

public interface JobAssignerInterface {

	//public void sortJobs();
	//public HashMap<String, ArrayList<Job>> outputJobs(ArrayList<Job> sortedJobs);
	public ArrayList<Location> assignJob(Location currentLocation);
}
