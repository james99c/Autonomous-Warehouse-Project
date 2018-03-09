package DataObjects;

import Interfaces.JobInterface;

public class Job implements JobInterface{
	
	private float rewardForJob;
	
	public Job(float rewardForJob){
		this.rewardForJob = rewardForJob;
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
		return rewardForJob;
	}
	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
