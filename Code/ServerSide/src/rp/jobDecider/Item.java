package jobDecider;

public class Item {
	
	Float reward;
	Float weight;
	String ID;
	int x;
	int y;
	
	public Item(String ID, Float reward, Float weight, int x, int y) {
		this.reward = reward;
		this.weight = weight;
		this.ID = ID;
		this.x = x;
		this.y = y;
	}
	
	public Float getItemReward() {
		return reward;
	}
	
	public String getItemID() {
		return ID;
	}
	
	public Float getItemWeight() {
		return weight;
	}
	
	public int getItemXPos() {
		return x;
	}
	
	public int getItemYPos() {
		return y;
	}

}
