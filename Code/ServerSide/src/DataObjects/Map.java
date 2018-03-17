package DataObjects;

import java.util.ArrayList;

public class Map {
	private GridPoint[][] map;
	private Integer height;
	private Integer width;
	private ArrayList<RobotInformation> robots;

	
	public Map(Integer _height, Integer _width, ArrayList<Location> unAvailableLocations){
		
		this.height = _height + 1;
		this.width = _width + 1;
		this.map = new GridPoint[width][height];
		
		// generates all the gridpoints for locations
		for (int x=0 ; x < width ; x++){
			for( int y=0; y < height; y++){
				this.map[x][y] = new GridPoint(x, y, false);
				
			}
		}
		
		// sets unavailable locations
		for (Location a: unAvailableLocations){
			this.map[a.getX()][a.getY()] = new GridPoint(a.getX(),a.getY(), true);
		}
		
	}
	
	
	public void addRobot(String _robotID, Location _location, Direction _direction) {
		robots.add( new RobotInformation(_robotID, _location, _direction));
	}
	
	public void updateRobotsLocation(String _robotID, Location _newLocation) {
		RobotInformation toUpdate = null;
		for(RobotInformation robot : this.robots) {
			if(robot.robotID.equals(_robotID)) {
				toUpdate = robot;
				break;
			}
		}
		assert(toUpdate != null);
		
		Location oldLocation = toUpdate.location;
		toUpdate.location = _newLocation;
		Location changeInLocation = new Location( _newLocation.getX() - oldLocation.getX(), _newLocation.getY() - oldLocation.getY());
		
		
	}
	
	public void blockRoute(ArrayList<GridPoint> route){
		for (GridPoint a: route){
			this.map[a.getLocation().getX()][a.getLocation().getY()].setUnAvailability(a.getTimeFrames());
		}
		
	}
	public void unBlockRoute(ArrayList<GridPoint> route){
		for (GridPoint a: route){
			this.map[a.getLocation().getX()][a.getLocation().getY()].setUnAvailability(a.getTimeFrames());
		}
		
	}
	public void clearTimeFrames(){
		for (int x=0 ; x < this.width; x++){
			for( int y=0; y <this.height; y++){

				this.map[x][y].removeTimeFrames();
				
			}
		}
	}
	public ArrayList<GridPoint> getAvailableLocations(Location location, Float[] timeFrame){
		ArrayList<GridPoint> surroundingLocations = new ArrayList<GridPoint>();
		int x = location.getX();
		int y = location.getY();
		try {
			surroundingLocations.add(map[x+1][y]);
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		try {
			surroundingLocations.add(map[x-1][y]);
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		try {
			surroundingLocations.add(map[x][y+1]);
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		try {
			surroundingLocations.add(map[x][y-1]);
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		
		
		ArrayList<GridPoint> output = new ArrayList<GridPoint>();
		for (GridPoint a: surroundingLocations){
			if(a.getAvailability(timeFrame)){
				a.setUnAvailability(timeFrame); 
				output.add(a);
			}
		}
		
		return output;
	}
}
