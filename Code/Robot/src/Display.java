import lejos.nxt.LCD;
import lejos.nxt.Button;

public class Display {
	String jobID = "No job";
	String status = "Idle";
	String route = "No route";
	int weight = 0;
	int num = 0;
	public void show(){
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 0);
		//LCD.drawInt(weight, 4, 7);
		LCD.drawString(route, 4, 3);
		//LCD.drawString("< pick  cancel >", 6, 0);
	}
	public void showWithNum() {
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 0);
		LCD.drawString(route, 4, 3);
		LCD.drawInt(num, 6, 7);
	}
	public void updateID(String x) {
		jobID = x;
		this.show();
	}
	
	public void updateStatus(String x) {
		status = x;
		this.show();
	}
	
	//public void updateWeight() {
		//Update weight
	//}
	
	public void updateRoute(String x) {
		route = x;
		this.show();		
	}
	
	public void waitForInput() {
		boolean waiting = true;
		this.updateStatus("Pick jobs");
		this.showWithNum();
		while(waiting) {
			if (Button.waitForAnyPress() == 2 ) {
				--num;
				this.showWithNum();
			}
			if (Button.waitForAnyPress() == 4) {
				++num;
				this.showWithNum();
			}
			if (Button.waitForAnyPress() == 1) {
				//Pass the information to the client
				waiting = false;
				this.show();
			}
			if (Button.waitForAnyPress() == 8) {
				this.cancelJob();
			}
		}
	}
	public void cancelJob() {
		//Cancel the proposed job
	}
		
		
	
	
	
	
}
