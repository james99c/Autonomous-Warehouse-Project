package DataObjects;

import Interfaces.JobInterface;

public class Job implements JobInterface{
	public Job(){
		
	}
	@Override
	public Location getStartingLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Location getGoalLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public float getRewardForJob() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
