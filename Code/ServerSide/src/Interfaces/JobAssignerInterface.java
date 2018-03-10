package Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import DataObjects.Job;

public interface JobAssignerInterface {

	public void sortJobs();
	public HashMap<String, ArrayList<Job>> outputJobs(ArrayList<Job> sortedJobs);
}
