package Interfaces;

import DataObjects.Location;

public interface JobInterface {
	public Location getStartingLocation();
	public Location getGoalLocation();
	public float getRewardForJob();
	public float getWeight();
}
