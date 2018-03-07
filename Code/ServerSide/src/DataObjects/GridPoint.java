package DataObjects;

import java.util.ArrayList;

public class GridPoint {
	
	private ArrayList<Integer[]> timeFrames = new ArrayList<Integer[]>();
	
	// timeFrame {StartTime, EndTime}
	// if completely unavailable have anything as time frame
	GridPoint(Location location,Boolean unavailable){

	}
	
	public Boolean getAvailability(ArrayList<Integer[]> timeFrame){
		if(unavailable){
			return false;
		}
	}
	
	public void removeTimeFrames(){
		timeFrames.clear();
	}
	
	// set time frame for which it is unavailable
	public void setUnAvailability(Integer[] timeFrame){
		timeFrames.add(timeFrame);
	}
	
	// returns the time frames for which it is all unavailable
	public ArrayList<Integer[]> getTimeFrames(){
		return timeFrames;
	}
}
