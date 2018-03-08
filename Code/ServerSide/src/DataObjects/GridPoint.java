package DataObjects;

import java.util.ArrayList;

public class GridPoint {
	
	private ArrayList<Integer[]> timeFrames = new ArrayList<Integer[]>();
	private Location location;
	private Boolean unavailable;
	// timeFrame {StartTime, EndTime}
	// if completely unavailable have anything as time frame
	GridPoint(Location _location,Boolean _unavailable){
		this.location = _location;
		this.unavailable = _unavailable;
	}
	
	public Boolean getAvailability(float[] timeFrame){
		if(unavailable){
			return false;
		}
		else{
			for(Integer[] a:this.timeFrames){
				if (a[0] < timeFrame[0] && a[1] > timeFrame[0] | a[0] < timeFrame[1] && a[1] > timeFrame[1]| timeFrame[0] < a[0] && ){
					return false;
				}
			}
		}
		return true;
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
