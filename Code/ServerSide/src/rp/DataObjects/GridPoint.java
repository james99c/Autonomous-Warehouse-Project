package rp.DataObjects;

import java.util.ArrayList;

public class GridPoint {

	private ArrayList<Float[]> timeFrames;
	private Location location;
	private Boolean unavailable;

	// timeFrame {StartTime, EndTime}
	// if completely unavailable have anything as time frame
	public GridPoint(int x, int y, Boolean _unavailable) {
		this.location = new Location(x, y);
		this.unavailable = _unavailable;
		this.timeFrames = new ArrayList<Float[]>();
	}

	GridPoint(Location _location, Boolean _unavailable) {
		this.location = _location;
		this.unavailable = _unavailable;
		this.timeFrames = new ArrayList<Float[]>();
	}

	public Boolean isAvailable(Float[] timeFrame) {
		if (unavailable) {
			return false;
		} else {
			// for some reason every gridpoint is not triggering this
			for (Float[] a : this.timeFrames) {
				System.out.println("I have time frames");
				if (a[0] < timeFrame[0] && a[1] > timeFrame[0]
						| a[0] < timeFrame[1] && a[1] > timeFrame[1]
						| timeFrame[0] < a[0] && timeFrame[1] > a[1]
						| timeFrame[0] > a[1] && timeFrame[1] < a[1]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Boolean isAvailable(Float time) {
		// I am receiving a wierd number for times
//		System.out.println(time);
		if(unavailable) {
			return false;
		}
		else {
			for(Float[] a : this.timeFrames) {
				System.out.println("I have time frames");
				System.out.println(a[0] + " , " + a[1]);
				System.out.println(time);
				if(time >= a[0] && time <= a[1]) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		return true;
	}

	public void removeTimeFrames() {
		timeFrames.clear();
	}

	// set time frame for which it is unavailable
	public void setUnAvailability(Float[] _timeFrame) {
		timeFrames.add(_timeFrame);
	}
	
	public void setUnAvailability(ArrayList<Float[]> _timeFrames){
		for(Float[] a: _timeFrames){
			timeFrames.add(a);
		}
	}

	public Location getLocation() {
		return this.location;
	}

	// returns the time frames for which it is an unavailable location
	public ArrayList<Float[]> getTimeFrames() {

		return timeFrames;
	}

	public GridPoint clone() {
		GridPoint newGridPoint = new GridPoint(this.location, this.unavailable);
		newGridPoint.setUnAvailability(this.timeFrames);
		return newGridPoint;
	}
}
