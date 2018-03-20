package rp.JobDeciderTest;


public class JobObject {
	
	private String id;
	private int quantity;
	
	public JobObject(String id, int quantity){
		this.id = id;
		this.quantity = quantity;
		}
	public String getID() {
		return id;
	}

	public int  getQuantity(){
		return quantity;
	}
}
