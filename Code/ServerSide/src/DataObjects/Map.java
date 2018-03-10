package DataObjects;

import java.util.ArrayList;

public class Map {
	private GridPoint[][] map;
	private Integer height;
	private Integer width;

	
	Map(Integer _height, Integer _width, ArrayList<Location> unAvailableLocations){
		this.map = new GridPoint[_width][_height];
		this.height = _height;
		this.width = _width;
		
		// generates all the gridpoints for locations
		for (int x=0 ; x < width; x++){
			for( int y=0; y < height; y++){
				this.map[x][y] = new GridPoint(x, y, false);
				
			}
		}
		
		// sets unavailable locations
		for (Location a: unAvailableLocations){
			this.map[a.getX()][a.getY()] = new GridPoint(a.getX(),a.getY(), true);
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
	
	public ArrayList<GridPoint> getAvailableLocations(Location location, Float[] timeFrame){
		ArrayList<GridPoint> surroundingLocations = new ArrayList<GridPoint>();
		try {
			surroundingLocations.add(map[location.getX() + 1][location.getY()]);
			surroundingLocations.add(map[location.getX() - 1][location.getY()]);
			surroundingLocations.add(map[location.getX() ][location.getY() + 1]);
			surroundingLocations.add(map[location.getX() ][location.getY() - 1]);
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
