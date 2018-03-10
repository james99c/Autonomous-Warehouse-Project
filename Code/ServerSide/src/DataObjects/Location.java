package DataObjects;

public class Location {
	private int x;
	private int y;
	Location(Integer _x, Integer _y){
		this.x = _x;
		this.y = _y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
