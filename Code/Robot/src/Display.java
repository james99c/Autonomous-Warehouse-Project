import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Display {
	String jobID = "No job";
	String status = "Idle";
	String route = "No route";
	int weight = 0;
	int num = 0;
	public void show(){
		LCD.clear();
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 0);
		//LCD.drawInt(weight, 4, 7);
		LCD.drawString(route, 4, 3);
		//LCD.drawString("< pick  cancel >", 6, 0);
	}
	public void showItemNumber() {
		LCD.clear();
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
	
	public int pickItem() {
		boolean waiting = true;
		this.updateStatus("Pick jobs");
		this.showItemNumber();
		int buttonPress;
		while(waiting) {
			buttonPress = Button.waitForAnyPress();
			if (buttonPress == 2 ) { //Left Button
				--num;
				this.showItemNumber();
			}
			if (buttonPress == 4) { //Right Button
				++num;
				this.showItemNumber();
			}
			if (buttonPress == 1) { //Middle Button
				//Pass the information to the client
				waiting = false;
				this.show();
			}
			if (buttonPress == 8) { //Bottom Button
				return -1;
			}
		}
		return num;
	}

}
