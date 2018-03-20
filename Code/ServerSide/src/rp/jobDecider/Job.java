package rp.jobDecider;

import java.util.ArrayList;
import java.util.HashMap;

public class Job {
	
	int ID;
	boolean cancel;
	ArrayList<Task> tasks = new ArrayList<>();
	HashMap<String, Item> items = new HashMap<>();
	
	public Job(int ID, ArrayList<Task> tasks, HashMap<String, Item> items, boolean cancel) {
		this.ID = ID;
		this.tasks = tasks;
		this.items = items;
		this.cancel = cancel;
	}
	
	public int getJobID() {
		return ID;
	}
	
	public boolean getJobCancel() {
		return cancel;
	}
	
	public ArrayList<Task> getJobTasks(){
		return tasks;
	}
	
	public HashMap<String, Item> getItems(){
		return items;
	}
	
	public Float getJobReward() {
		Float reward =  0f;
		
		for(Task tasks : tasks) {
			reward += tasks.getTaskQuantity() * items.get(tasks.getTaskID()).getItemReward();
		}
		
		return reward;
	}
	
	public Float getJobWeight() {
		Float weight =  0f;
		
		for(Task tasks : tasks) {
			weight += tasks.getTaskQuantity() * items.get(tasks.getTaskID()).getItemWeight();
		}
		
		return weight;
	}
	
	public int getNumberOfTasks() {
		int total = 0;
		
		for(Task tasks : tasks) {
			total += tasks.getTaskQuantity();
		}
		
		return total;
	}
	
	public Float getRewardDivWeight() {
		return getJobReward()/getJobWeight();
	}
	

}
