import lejos.nxt.Button;
import lejos.nxt.LCD;
/**
 * 
 * The robot display
 * 
 * @author paul
 * 
 */
public class Display {
    
    /**
     * The ID of the current job
     */
	String jobID = "No job";
	
	/**
	 * The status of the robot
	 */
	String status = "Idle";
	
	/**
	 * The number of items to be picked
	 */
	int num = 0;
	
	/**
	 * Display the job ID and status on the screen
	 */
	public void show(){
		LCD.clear();
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 0);
		//LCD.drawInt(weight, 4, 7);
		//LCD.drawString("< pick  cancel >", 6, 0);
	}
	
	/**
	 * Display the jobID and status along with the 
	 * number of items to be picked
	 */
	public void showItemNumber() {
		LCD.clear();
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 0);
		LCD.drawInt(num, 6, 7);
	}
	
	/**
	 * Update the ID of the current job
	 * @param x The job ID to be displayed
	 */
	public void updateID(String x) {
		jobID = x;
		this.show();
	}
	
	/**
	 * Update the status of the robot
	 * @param x The status to be displayed
	 */
	public void updateStatus(String x) {
		status = x;
		this.show();
	}
	
	//public void updateWeight() {
		//Update weight
	//}
	
	/**
	 * The method to be called when the robot
	 * is to pick the items
	 * @return The number of items being picked, -1 indicates cancel
	 */
	public float pickItem(float currentWeight, float itemWeight) {
		boolean waiting = true;
		float originalWeight = currentWeight;
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
			    if(currentWeight + itemWeight < 50.0f) {
			        ++num;
				    this.showItemNumber();
				    currentWeight = currentWeight + itemWeight;
			    }
			    else {
			        isFull = true;
			    }
			}
			if (buttonPress == 1) { //Middle Button
				//Pass the information to the client
				waiting = false;
				this.show();
			}
			if (buttonPress == 8) { //Bottom Button
				return originalWeight;
			}
		}
		return currentWeight;
	}
	
	/** 
	 * The method to be called when the robot must drop
	 * the items at the destination
	 * @return True if the items are dropped, False if canceled
	 */
    public boolean dropItem() {
        int buttonPress = Button.waitForAnyPress();
        this.updateStatus("Drop items");
        if(buttonPress == 1) {
            return true;
        }
        if(buttonPress == 8) {
            return false;
        }
    }
}
