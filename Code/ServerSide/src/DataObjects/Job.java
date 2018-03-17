package DataObjects;

import JobDecider.Item;
import JobDecider.JobObject;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import DataObjects.Location;

public class Job {
	private int id;
	private ArrayList<JobObject> job;
	private Reader reader;
	private HashMap<String, Item> items;
	private boolean cancel;
	private float reward;
	private Location startLocation;
	private Location goalLocation;
	
	public Job(int id, ArrayList<JobObject> job, HashMap<String, Item> items, boolean cancel){
		this.id = id;
		this.job = job;
		this.items = items;
		this.cancel = cancel;
		this.startLocation = startLocation;
		this.goalLocation = goalLocation;
	}
	
	public Job(float reward){
		this.reward = reward;
	}
	
	public float getReward(){
		return reward;
	}
	
	public int getID() {
		return id;
	}
	

	public ArrayList<String> getItems() {
		ArrayList<String> item = new ArrayList<String>();
		for(JobObject job : job){
			item.add(job.getID());
		}
		return item;
	}
	
	public ArrayList<Integer> getNumberOfItems() {
		ArrayList<Integer> item = new ArrayList<Integer>();
		for(JobObject job : job){
			item.add(job.getQuantity());
		}
		return item;
	}
	
	public float getJobReward() {
		float reward = 0;
		for(int i=0; i < getItems().size(); i++) 
			reward += items.get(job.get(i).getID()).getReward() * job.get(i).getQuantity();
		return reward;
		
	}
	
	public float getJobWeight() {
		float weight = 0;
		for(int i=0; i < getItems().size(); i++) 
			weight += items.get(job.get(i).getID()).getWeight() * job.get(i).getQuantity();;
		return weight;
	}
	public boolean getCancellation(){
		
		return cancel;
	}

		
		

		
		
		public ArrayList<JobObject> getJob(){
			return job;
		}
	}


