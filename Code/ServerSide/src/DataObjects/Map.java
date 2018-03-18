package DataObjects;

import java.util.ArrayList;

import javafx.util.Pair;



public class Map {
	private GridPoint[][] map;
	private Integer height;
	private Integer width;
	private ArrayList<RobotInformation> robots;
	private final float STRAIGHT_TIME = 1.0f;
	private final float ROTATE_90_TIME = 0.5f;
	private final float ROTATE_180_TIME = 0.5f;
	
	

	
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
		if(changeInLocation.getY() == 1) {
			toUpdate.direction = Direction.NORTH;
			return;
		}
		if(changeInLocation.getX() == 1) {
			toUpdate.direction = Direction.EAST;
			return;
		}
		if(changeInLocation.getY() == -1) {
			toUpdate.direction = Direction.SOUTH;
			return;
		}
		if(changeInLocation.getX() == -1) {
			toUpdate.direction = Direction.WEST;
			return;
		}
		
		
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
	
	// returns an array of how long it takes to get to each location, however the time frames are
	// not perfect for how long each node needs to be blocked off for as we need to account for error
	public ArrayList<Pair<GridPoint, Direction>> getAvailableLocations(Location _location, Float _time, Direction _direction){
		ArrayList<GridPoint> surroundingLocations = new ArrayList<GridPoint>();
		int x = _location.getX();
		int y = _location.getY();
		try {
			surroundingLocations.add(map[x+1][y].clone());
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		try {
			surroundingLocations.add(map[x-1][y].clone());
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		try {
			surroundingLocations.add(map[x][y+1].clone());
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		try {
			surroundingLocations.add(map[x][y-1].clone());
			
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
		
		
		ArrayList<Pair<GridPoint,Direction>> output = new ArrayList<Pair<GridPoint,Direction>>();
		ArrayList<Float[]> unavailableTime = new ArrayList<>();
		ArrayList<GridPoint> unavailablePoint = new ArrayList<>();
		for (GridPoint a: surroundingLocations){
			Float [] newTimeFrame = null;
			Direction newDirection = null;
			if((a.getLocation().getX() - x) == 1) {
				newDirection = Direction.EAST;
				if(_direction == Direction.EAST) {
					newTimeFrame = new Float[] {_time, STRAIGHT_TIME};
				}
				else if (_direction == Direction.WEST){
					newTimeFrame = new Float[] {_time, ROTATE_180_TIME};
				}
				else {
					newTimeFrame = new Float[] {_time, ROTATE_90_TIME};
				}
			}
			else if((a.getLocation().getX() - x) == -1) {
				newDirection = Direction.WEST;
				if(_direction == Direction.WEST) {
					newTimeFrame = new Float[] {_time, STRAIGHT_TIME};
				}
				else if (_direction == Direction.EAST){
					newTimeFrame = new Float[] {_time, ROTATE_180_TIME};
				}
				else {
					newTimeFrame = new Float[] {_time, ROTATE_90_TIME};
				}
			}
			else if((a.getLocation().getY() - y) == 1) {
				newDirection = Direction.NORTH;
				if(_direction == Direction.NORTH) {
					newTimeFrame = new Float[] {_time, STRAIGHT_TIME};
				}
				else if (_direction == Direction.SOUTH){
					newTimeFrame = new Float[] {_time, ROTATE_180_TIME};
				}
				else {
					newTimeFrame = new Float[] {_time, ROTATE_90_TIME};
				}
			}
			else if((a.getLocation().getY() - y) == -1) {
				newDirection = Direction.SOUTH;
				if(_direction == Direction.SOUTH) {
					newTimeFrame = new Float[] {_time, STRAIGHT_TIME};
				}
				else if (_direction == Direction.NORTH){
					newTimeFrame = new Float[] {_time, ROTATE_180_TIME};
				}
				else {
					newTimeFrame = new Float[] {_time, ROTATE_90_TIME};
				}
			}
			
			assert(newTimeFrame != null);
			assert(newDirection != null);
			
			if(a.isAvailable(System.currentTimeMillis() + _time)){
				//a.setUnAvailability(newTimeFrame); 
				unavailableTime.add(newTimeFrame);
				unavailablePoint.add(a);
				output.add(new Pair<GridPoint, Direction>(a, newDirection));
			}
		}
		
		int index;
		for (index = 1; index < unavailableTime.size(); index++) {
			Float[] second = unavailableTime.get(index);
			Float[] first = unavailableTime.get(index-1);
			GridPoint gp = unavailablePoint.get(index-1);
			
			gp.setUnAvailability(new Float[] {first[0], second[1]});
		}
		
		unavailablePoint.get(index-1).setUnAvailability(new Float[] {
				unavailableTime.get(index-1)[0], unavailableTime.get(index-1)[1]});
		
		return output;
	}
}
