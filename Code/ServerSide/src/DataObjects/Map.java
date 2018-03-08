package DataObjects;

import java.util.ArrayList;

public class Map {
	private GridPoint[][] map;
	
	Map(Integer height, Integer width, ArrayList<Location> unAvailableLocations){
		this.map = new GridPoint[width][height];
		
		// generates
		for (int x=0 ; x < width; x++){
			for( int y=0; y < height; y++){

				this.map[x][y] = new GridPoint(x,y, new ArrayList<Integer[]>(), false);
				
			}
		}
		
		// sets unavailable locations
		for (Location a: unAvailableLocations){
			this.map[a.getX()][a.getY()] = new GridPoint(x,y, new ArrayList<Integer[]>(), true);;
		}
		
	}
	
	
	public void blockRoute(ArrayList<GridPoint> route){
		for (GridPoint a: route){
			this.map[a.getX()][a.getY()].setUnAvailability(a.getTimeFrames());
		}
		
	}
	public void unBlockRoute(ArrayList<GridPoint> route){
		for (GridPoint a: route){
			this.map[a.getX()][a.getY()].setUnAvailability(a.getTimeFrames());
		}
		
	}
	
	public void clearTimeFrames(){
		for (int x=0 ; x < width; x++){
			for( int y=0; y < height; y++){

				this.map[x][y].removeTimeFrames();
				
			}
		}
	}
	
	public ArrayList<GridPoint> getAvailableLocations(Location location, Integer[] timeFrame){
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
				output.add(a);
			}
		}
		
		return output;
	}
}
