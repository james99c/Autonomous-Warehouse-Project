package rp.jobDecider;

public class Task {
	
	String taskID;
	int quantity;
	
	public Task(String taskID, int quantity) {
		this.taskID = taskID;
		this.quantity = quantity;
	}
	
	public String getTaskID() {
		return taskID;
	}
	
	public int getTaskQuantity() {
		return quantity;
	}
}
