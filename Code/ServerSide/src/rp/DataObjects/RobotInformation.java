package rp.DataObjects;

public class RobotInformation {
	public String robotID;
	public Location location;
	public Direction direction;
	
	public RobotInformation(String _robotID, Location _location, Direction _direction) {
		this.robotID = _robotID;
		this.location = _location;
		this.direction = _direction;
	}
	
}
