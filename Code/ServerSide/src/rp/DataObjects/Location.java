package rp.DataObjects;

public class Location {
	private int x;
	private int y;
	public Location(Integer _x, Integer _y){
		this.x = _x;
		this.y = _y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	@Override
	public String toString() {
		return "" + this.x + ", " + this.y + "\n";
	}
}
