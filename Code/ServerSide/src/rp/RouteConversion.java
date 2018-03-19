package rp;

import java.awt.Point;

import lejos.robotics.navigation.DifferentialPilot;

public class RouteConversion {

	private Point[] coordinates;
	private int i = 0;
	private Point currentPosition;
	private Point nextPosition;
	private DifferentialPilot m_pilot;
	String route = "";

	public RouteConversion(Point[] coordinates) {
		this.coordinates = coordinates;
	}

	public String convertRoute() {
		currentPosition = new Point(0, 0);
		String  pointing = "North";
		while(i < coordinates.length) {
			
			double currentY = currentPosition.getY();
			double currentX = currentPosition.getX();
			
			
			// route: forward = 0; left = 1 ; right = 2; backwards = 3
			// pointing: north = 0; west = 1 ; east = 2; south = 3
		
			nextPosition = coordinates[i];
			double nextX = nextPosition.getX();
			double nextY = nextPosition.getY();
			 i++;
			
			double changeInX = nextX - currentX;
			double changeInY = nextY - currentY;
			System.out.println(pointing);
			System.out.println(changeInX + " : " + changeInY);
			// facing east
			if(changeInX == 1) {
				if(pointing.equals("East")) {
					route += "0";
				}
				else if (pointing.equals("West")) {
					route += "110";
				}
				
				else if (pointing.equals("South")) {
					route += "10";
				}
				else {
					route += "20";
				}
				pointing = "East";
				
			}
			else if(changeInX == -1) {
				if(pointing.equals("East")) {
					route += "110";
				}
				else if (pointing.equals("West")) {
					route += "0";
				}
				
				else if (pointing.equals("South")) {
					route += "20";
				}
				else {
					route += "10";
				}
				pointing = "West";
			}
			else if(changeInY == 1) {
				if(pointing.equals("East")) {
					route += "10";
				}
				else if (pointing.equals("West")) {
					route += "20";
				}
				
				else if (pointing.equals("South")) {
					route += "110";
				}
				else {
					route += "0";
				}
				pointing = "North";
			}
			else {
				if(pointing.equals("East")) {
					route += "20";
				}
				else if (pointing.equals("West")) {
					route += "10";
				}
				
				else if (pointing.equals("South")) {
					route += "0";
				}
				else {
					route += "110";
				}
				pointing = "South";
			}
//			if(pointing == "East") {
//				//turn left
//				if (currentX < nextX && currentY==nextY) {
//					route += '1';
//					route += '0';
//					pointing = "North";
//				}	
//				//forward
//				if (currentX == nextX && currentY<nextY){
//					route += '0';
//				}
//				//turn right
//				if (currentX > nextX && currentY==nextY) {
//					route += '2';
//					route += '0';
//					pointing = "South";
//				}
//				
//				//go back
//				if (currentX == nextX && currentY>nextY) {
//					route += '3';
//					route += '0';
//					pointing = "West";
//				}
//	
//	
//			}
//			else if(pointing == "North") {
//				//turn left
//				if (currentX == nextX && currentY>nextY) {
//					route += '1';
//					route += '0';
//					pointing = "West";
//				}	
//				//forward
//				if (currentX < nextX && currentY==nextY){
//					route += '0';
//					}
//				
//				//turn right
//				if (currentX == nextX && currentY<nextY) {
//					route += '2';
//					route += '0';
//					pointing = "East";
//				}
//				
//				//go back
//				if (currentX > nextX && currentY==nextY) {
//					route += '3';
//					route += '0';
//					pointing = "South";
//				}
//			}
//			else if(pointing == "West") {
//				//turn left
//				if (currentX > nextX && currentY==nextY) {
//					route += '1';
//					route += '0';
//					pointing = "South";
//				}	
//				//forward
//				if (currentX == nextX && currentY>nextY){
//					route += '0';			}
//				
//				//turn right
//				if (currentX < nextX && currentY==nextY) {
//					route += '2';
//					route += '0';
//					pointing = "North";
//				}
//				
//				//go back
//				if (currentX == nextX && currentY<nextY) {
//					route += '3';
//					route += '0';
//					pointing ="East";
//				}
//			}
//			else if(pointing == "South") {
//				//turn left
//				if (currentX == nextX && currentY<nextY) {
//					route += '1';
//					route += '0';
//					pointing = "East";
//				}	
//				//forward
//				if (currentX > nextX && currentY==nextY){
//					route += '0';
//					}
//				
//				//turn right
//				if (currentX == nextX && currentY>nextY) {
//					route += '2';
//					route += '0';
//					pointing = "West";
//				}
//				
//				//go back
//				if (currentX < nextX && currentY==nextY) {
//					route += '3';
//					route += '0';
//					pointing = "North";
//				}
//			}
//			currentX = nextX;
//			currentY = nextY;
//			i++;
			currentPosition = nextPosition;
			}
		return route;
	}
	
}
