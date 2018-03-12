public class Display {

	//int weight = 0;
	public static void main(String[] args){
		String jobID = "No job";
		String status = "Idle";
		String route = "No route";
		//int weight = 0;
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 3)
		//LCD.drawInt(weight, 4, 7);
		LCD.drawString(route, 4, 3);
		LCD.drawString("< pick  cancel >", 6, 0);
	}
	public void updateID() {
		//Update new jobID 
	}
	
	public void updateStatus() {
		//Update status
	}
	
	public void updateWeight() {
		//Update weight
	}
	
	public void updateRoute() {
		//Update route
	}
	
}



}
