package JobDecider;

import java.util.ArrayList;

public class JobNumber {
	
	private int id;
	private ArrayList<JobObject> job;

	public JobNumber(int id, ArrayList<JobObject> job){
		this.id = id;
		this.job = job;
	}
	
	public int getID(){
		return id;
	}
	public ArrayList<JobObject> getJob(){
		return job;
	}





}
