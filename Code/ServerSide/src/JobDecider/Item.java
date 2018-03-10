package JobDecider;

public class Item {
	
	private Float reward;
	private Float weight;
	private int x;
	private int y;

	public Item(Float reward, Float weight, int x, int y){
		this.reward = reward;
		this.weight = weight;
		this.x = x;
		this.y = y;
	}
	
	public Float getWeight(){
		return weight;
	}
	
	public Float getReward(){
		return reward;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

}
