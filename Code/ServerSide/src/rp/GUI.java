package rp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;


import rp.DataObjects.Location;

import rp.Interfaces.GUIInterface;


/**
 * 
 * PC Warehouse Interface
 * 
 * @author Paul
 *
 */
public class GUI {
    /**
     * The first frame to be displayed
     * Shows robots connecting
     */
	private JFrame frame1;
	/**
	 * The second frame to be displayed
	 * Here the starting coordinates are input
	 */
	private JFrame frame2;
	/**
	 * The third frame to be displayed
	 * Shows the robots and their current jobs
	 * Allows the user to reconnect disconnected robots
	 */
	private JFrame frame3;
	/**
	 * The starting location of the first robot
	 */
	Location location1;
	/**
	 * The starting location of the second robot
	 */
	Location location2;
	/**
	 * The starting location of the third robot
	 */
	Location location3;
	/**
	 * The number of robots connected
	 */
	Integer robotsConnected = 0;
	/**
	 * the labels displaying the current job
	 * they must be declared here as they are referred to by
	 * a method
	 */
	JLabel joblabel1 = new JLabel("No job");
	JLabel joblabel2 = new JLabel("No job");
	JLabel joblabel3 = new JLabel("No job");
	/**
	 * A label displaying the number of connected robots
	 */
	JLabel counterLabel;
	/**
	 * The server
	 */
	private Server server;
	
	/**
	 * A boolean showing whether the second frame is finished
	 * with, so the starting coordinates can be passed
	 */
	boolean GUIFinished = false;
	/**
	 * A hashmap storing the robot name and its starting
	 * location
	 */
	HashMap<String, Location> locations = new HashMap<>();
	/**
	 * An ArrayList of the names of the connected robots
	 */
	ArrayList<String> connectedRobots = new ArrayList<String>();
	/**
	 * A HashMap storing the robot names and their jobs
	 */
    HashMap<String, Integer> robotJobs = new HashMap<>();
	
	/**
	 * 
	 * The constructor for the GUI, where a server is created
	 */
	public GUI(Server _server) {
		// 
		this.server = _server;
	}

    /**
     * 
     * The method which starts and runs the first frame of the GUI
     * It shows the robots connecting
     */
	public void runGUI() {
        //The frame is declared
		frame1 = new JFrame();
		
		//The panels for the first frame are declared and the layout set
		JPanel panel11 = new JPanel();
		JPanel panel12 = new JPanel();
		JPanel panel13 = new JPanel();
		frame1.setLayout(new FlowLayout());
		
		//A label saying the robots are connecting is created
		JLabel connectingLabel = new JLabel("Connecting to robots");
		/* A label showing the number of robots connected is added.
		It had to be declared outside this method
		*/
		counterLabel = new JLabel(robotsConnected.toString() + " Robots connected");
	    
	    /* The done button
	    The actionlistener waits for the button to be clicked,
	    then disposes of this frame and calls the runFrame2 method
	    */
		JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override            
            public void actionPerformed(ActionEvent e) {
            	frame1.dispose();
            	runFrame2();
                    }} );
        
        //The labels are added to their corresponding panels
		panel11.add(connectingLabel);
		panel12.add(counterLabel);
		panel13.add(doneButton);
		
		//The panels are added to the frame
		frame1.add(panel11);
        frame1.add(panel12);
        frame1.add(panel13);
        //The closeOperation, title and size are set, and the frame displayed
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setTitle("Warehouse GUI");
        frame1.setPreferredSize(new Dimension(280, 280));
        frame1.pack();
        frame1.setVisible(true);
	}

    /**
     * 
     * The method which starts and runs the second frame
     * This is called by the runGUI method
     * It shows the robots which are connected, and prompts
     * the user to input their start locations
     * 
     */
	public void runFrame2() {
	    //The frame is declared
		frame2 = new JFrame();
		
		//The various panels are declared and the layout set
        JPanel panel21 = new JPanel();
        JPanel panel22 = new JPanel();
        JPanel panel23 = new JPanel();
        JPanel panel24 = new JPanel();
        JPanel panel25 = new JPanel();
        JPanel panel26 = new JPanel();
        frame2.setLayout(new FlowLayout());
        
        //This label acts as a title
        JLabel title1 = new JLabel("Connected Robots");
        /* Labels displaying the names of the connected robots
        These must first be instantiated empty, to prevent a null
        pointed exception when referring to the arraylist containing
        the names of the connected robots.
        They are then set, but must be inside the if statements to prevent
        more null pointer exceptions
        */
        JLabel connected1 = new JLabel("");
        JLabel connected2 = new JLabel("");
        JLabel connected3 = new JLabel("");
        JLabel noneConnected =  new JLabel("No robots connected");
        if(robotsConnected > 0) {
        	connected1.setText(connectedRobots.get(0));
        }
        if(robotsConnected > 1) {
        	connected2.setText(connectedRobots.get(1));
        }
        if(robotsConnected > 2) {
        	connected3.setText(connectedRobots.get(2));
        }
        
        /*
        Text fields for the user to input the start locations and labels to
        indicate this
        The dimensions of the text fields must be set to avoid them being
        too small
        */
        JLabel enterX = new JLabel("Enter x co-ordinate");
        JLabel enterY = new JLabel("Enter y co-ordinate");
        JTextField x1 = new JTextField();
        JTextField x2 = new JTextField();
        JTextField x3 = new JTextField();
        JTextField y1 = new JTextField();
        JTextField y2 = new JTextField();
        JTextField y3 = new JTextField();
        x1.setPreferredSize(new Dimension(24,24));
        x2.setPreferredSize(new Dimension(24,24));
        x3.setPreferredSize(new Dimension(24,24));
        y1.setPreferredSize(new Dimension(24,24));
        y2.setPreferredSize(new Dimension(24,24));
        y3.setPreferredSize(new Dimension(24,24));
        
        /*
        A button for the user to press once they've finished
        inputting the coordinates
        Upon pressing, the frame is disposed of.
        Then, for each connected robot the values inputted as the
        coordinates are first made into ints, then added to the appropriate 
        location object, which is then added to the locations HashMap.
        The if statements checking the number of robots connected prevent 
        a null pointer exception
        After this a call is made to the server to start the robots
        */
        JButton doneButton2 = new JButton("Done");
        doneButton2.addActionListener(new ActionListener() {
            @Override            
            public void actionPerformed(ActionEvent e) {
            	frame2.dispose();
            	if(robotsConnected > 0) {
            		String tx1 = x1.getText();
            		String ty1 = y1.getText();
            		int vx1 = Integer.parseInt(tx1);
            		int vy1 = Integer.parseInt(ty1);
            		location1 = new Location(vx1, vy1);
            		locations.put(connectedRobots.get(0), location1);
            	}
            	if(robotsConnected > 1) {
            		String tx2 = x2.getText();
            		String ty2 = y2.getText();
            		int vx2 = Integer.parseInt(tx2);
            		int vy2 = Integer.parseInt(ty2);
            		location2 = new Location(vx2, vy2);
            		locations.put(connectedRobots.get(1), location2);
            	}
            	if(robotsConnected > 2) {
            		String tx3 = x3.getText();
                	String ty3 = y3.getText();
                	int vx3 = Integer.parseInt(tx3);
                	int vy3 = Integer.parseInt(ty3);
                	location3 = new Location(vx3, vy3);
                	locations.put(connectedRobots.get(2), location3);
            	}
            	server.startRobots(locations);
                    }} );
                    
        //The title is added to its panel
        panel21.add(title1);
        
        //The information for the first robot is added
        panel22.add(connected1);
        panel22.add(enterX);
        panel22.add(x1);
        panel22.add(enterY);
        panel22.add(y1);
        
        //And for the second
        panel23.add(connected2);
        panel23.add(enterX);
        panel23.add(x2);
        panel23.add(enterY);
        panel23.add(y2);
        
        //And the third
        panel24.add(connected3);
        panel24.add(enterX);
        panel24.add(x3);
        panel24.add(enterY);
        panel24.add(y3);
        
        //This panel will only be displayed if no robots are connected
		panel25.add(noneConnected);
		
		//The done button is added to its panel
		panel26.add(doneButton2);
		
		/* The panels are added to the frame. 
		Depending on the number of robots connected different panels are added
		If no robots are connected a panel is added to show this
		*/
		frame2.add(panel21);
        if(robotsConnected > 0) {frame2.add(panel22); }
        if(robotsConnected > 1) {frame2.add(panel23); }
        if(robotsConnected > 2) {frame2.add(panel24); }
        if(robotsConnected == 0) {frame2.add(panel25); }
        frame2.add(panel26);
        //The closeOperation, title and dimensions are set, and the frame created
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Warehouse GUI");
        frame2.setPreferredSize(new Dimension(350, 280));
        frame2.pack();
        frame2.setVisible(true);
       
       /**
        * 
        * The method which creates the third frame.
        * This frame shows all the robots, and their respective jobs.
        * It also gives the user the option to reconnect a robot or cancel
        * the current job
        * 
        */
	}
	public void runFrame3() {
	        // The frame is created
	        frame3 = new JFrame();
	        
	        //The required panels are created
	        JPanel panel31 = new JPanel();
	        JPanel panel32 = new JPanel();
	        JPanel panel33 = new JPanel();
	        JPanel panel34 = new JPanel();
	        JPanel panel35 = new JPanel();
	        
	        //A label acting as a title
	        JLabel title = new JLabel("Current Jobs");
	        
	        // The labels showing the robot names are created, and the layout set
	        JLabel robotlabel1 = new JLabel("");
	        JLabel robotlabel2 = new JLabel("");
	        JLabel robotlabel3 = new JLabel("");
			frame3.setLayout(new FlowLayout());
	        
	        /* The labels showing the robot names have their text set 
	        This must be done to avoid null pointer exceptions when 
	        referring to the connectedRobots ArrayList
	        */
	        if (robotsConnected > 0) {
	        	robotlabel1.setText(connectedRobots.get(0));
	        }
	        if (robotsConnected > 1) {
	        	robotlabel2.setText(connectedRobots.get(1));
	        }
	        if (robotsConnected > 2) {
	        	robotlabel3.setText(connectedRobots.get(2));
	        }
	        
	        //A label saying that no robots are connected
	        JLabel noneConnected =  new JLabel("No robots connected");
	        
	       /* JButton cancel1 = new JButton("Cancel job");
	        cancel1.addActionListener(new ActionListener() {
	            @Override            
	            public void actionPerformed(ActionEvent e) {
	            	//Cancel the robot's operation
	                    }} );
	        
	        JButton cancel2 = new JButton("Cancel job");
	        cancel2.addActionListener(new ActionListener() {
	            @Override            
	            public void actionPerformed(ActionEvent e) {
	            	//Cancel the robot's operation
	                    }} ); 
	        
	        JButton cancel3 = new JButton("Cancel job");
	        cancel3.addActionListener(new ActionListener() {
	            @Override            
	            public void actionPerformed(ActionEvent e) {
	            	//Cancel the robot's operation
	                    }} ); 
	                    */
	                    
	        //A button to reconnect the first robot
	        JButton reconnect1 = new JButton("Reconnect");
	        reconnect1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		//Reconnect the robot
	        	}
	        } );
	        
	         //A button to reconnect the second robot
	        JButton reconnect2 = new JButton("Reconnect");
	        reconnect1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		//Reconnect the robot
	        	}
	        } );
	        
	         //A button to reconnect the third robot
	        JButton reconnect3 = new JButton("Reconnect");
	        reconnect1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		//Reconnect the robot
	        	}
	        } );
	        	
	        //The title is added to the panel
	        panel31.add(title);
	        
	        //The items regarding the first robot are added to a panel
	        panel32.add(robotlabel1);
	        panel32.add(joblabel1);
	        panel32.add(cancel1);
	        panel32.add(reconnect1);
	        
	        //The items regarding the second robot are added to a panel
	        panel33.add(robotlabel2);
	        panel33.add(joblabel2);
	        panel33.add(cancel2);
	        panel33.add(reconnect2);
	        
	        //The items regarding the third robot are added to a panel
	        panel34.add(robotlabel3);
	        panel34.add(joblabel3);
	        panel34.add(cancel3);
	        panel34.add(reconnect3);
	        
	        //A panel to display if no robots are connected is created
	        panel35.add(noneConnected);
	        
	        //The panels are added to the frame, as appropriate
	        frame3.add(panel31);
	        if(robotsConnected > 0) {frame3.add(panel32); }
	        if(robotsConnected > 1) {frame3.add(panel33); }
	        if(robotsConnected > 2) {frame3.add(panel34); }
	        if(robotsConnected == 0) {frame3.add(panel35); }
	        
	        //The closeOperation, title and size are set, and the frame is made
	        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame3.setTitle("Warehouse GUI");
	        frame3.setPreferredSize(new Dimension(400, 350));
	        frame3.pack();
	        frame3.setVisible(true);
	}
	
	/**
	 * 
	 * A method to add a connected robot's name to the appropriate ArrayLists
	 * 
	 * @param robotName The name of the connected robot
	 * 
	 */
	public void connectRobot(String robotName) {
		robotsConnected++;
		counterLabel.setText(robotsConnected.toString() + " Robots connected");
		connectedRobots.add(robotName);
		
	}
	
	/**
	 * 
	 * A method to retrieve the location of a specified robot
	 * 
	 * @param robot The name of the robot you're looking for
	 * 
	 * @return A location object from the HashMap corresponding to the robot
	 * 
	 */
	public Location getLocation(String robot) {
		return locations.get(robot);
	}
	
	/**
	 * 
	 * Check if the user has finished inputting the coordinates
	 * 
	 * @return True if the done button on the second frame has been pressed
	 */
	public boolean getGUIFinished() {
		return GUIFinished;
	}
	
	/**
	 * 
	 * A method to set the labels in the GUI to correspond with the current jobs
	 * 
	 * @param robot The name of the robot
	 * @param ID The ID of the current job
	 * 
	 */
	public void setJobID(String robot, int ID) {
		if (robotJobs.containsKey(robot)) {
			robotJobs.put(robot, ID);
			if (robotsConnected > 0) {
				joblabel1.setText(robotJobs.get(robot).toString());
		}
			if (robotsConnected > 1) {
				joblabel1.setText(robotJobs.get(connectedRobots.get(0)).toString());
				joblabel2.setText(robotJobs.get(connectedRobots.get(1)).toString());
			}
			if (robotsConnected > 2) {
				joblabel1.setText(robotJobs.get(connectedRobots.get(0)).toString());
				joblabel2.setText(robotJobs.get(connectedRobots.get(1)).toString());
				joblabel3.setText(robotJobs.get(connectedRobots.get(2)).toString());
			}
	}
}
}
