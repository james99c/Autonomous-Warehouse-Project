package Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import DataObjects.Job;

public interface JobAssignerInterface {

	public ArrayList<Job> sortJobs();
	public HashMap<String, ArrayList<Job>> outputJobs(ArrayList<Job> sortedJobs);
}
