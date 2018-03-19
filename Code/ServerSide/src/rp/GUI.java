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
 * PC Interface
 * 
 * @author Paul
 *
 */
public class GUI {
	private JFrame frame1;
	private JFrame frame2;
	private JFrame frame3;
	String job1;
	String job2;
	String job3;
	Location location1;
	Location location2;
	Location location3;
	Integer robotsConnected = 0;
	JLabel counterLabel;
	
	String robot1 = "";
	String robot2 = "";
	String robot3 = "";
	
	boolean GUIFinished = false;
	HashMap<String, Location> locations = new HashMap<>();
	ArrayList<String> connectedRobots = new ArrayList<String>();
	
	//String filePath = "";
	
	
	public GUI() {
        
       
	}
	public void runGUI() {
		frame1 = new JFrame();
		
		JPanel panel11 = new JPanel();
		JPanel panel12 = new JPanel();
		JPanel panel13 = new JPanel();
		frame1.setLayout(new FlowLayout());
		
		JLabel connectingLabel = new JLabel("Connecting to robots");
		counterLabel = new JLabel(robotsConnected.toString() + " Robots connected");
		
		JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override            
            public void actionPerformed(ActionEvent e) {
            	frame1.dispose();
            	runFrame2();
                    }} );
        
		panel11.add(connectingLabel);
		panel12.add(counterLabel);
		panel13.add(doneButton);
		
		frame1.add(panel11);
        frame1.add(panel12);
        frame1.add(panel13);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setTitle("Warehouse GUI");
        frame1.setPreferredSize(new Dimension(280, 280));
        frame1.pack();
        frame1.setVisible(true);
	}
	
	public void runFrame2() {
		frame2 = new JFrame();
        JPanel panel21 = new JPanel();
        JPanel panel22 = new JPanel();
        JPanel panel23 = new JPanel();
        JPanel panel24 = new JPanel();
        JPanel panel25 = new JPanel();
        JPanel panel26 = new JPanel();
        frame2.setLayout(new FlowLayout());
        
        JLabel title1 = new JLabel("Connected Robots");
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
            	GUIFinished = true;
                    }} );
        
        panel21.add(title1);
        
        panel22.add(connected1);
        panel22.add(enterX);
        panel22.add(x1);
        panel22.add(enterY);
        panel22.add(y1);
        
        panel23.add(connected2);
        panel23.add(enterX);
        panel23.add(x2);
        panel23.add(enterY);
        panel23.add(y2);
        
        panel24.add(connected3);
        panel24.add(enterX);
        panel24.add(x3);
        panel24.add(enterY);
        panel24.add(y3);
        
		panel25.add(noneConnected);
		
		panel26.add(doneButton2);
		
		frame2.add(panel21);
        if(robotsConnected > 0) {frame2.add(panel22); }
        if(robotsConnected > 1) {frame2.add(panel23); }
        if(robotsConnected > 2) {frame2.add(panel24); }
        if(robotsConnected == 0) {frame2.add(panel25); }
        frame2.add(panel26);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Warehouse GUI");
        frame2.setPreferredSize(new Dimension(350, 280));
        frame2.pack();
        frame2.setVisible(true);
          
	}
	public void runFrame3() {
		 String testString = "Test job ID";
	        job1 = testString;
	        job2 = testString;
	        job3 = testString;
	        JLabel robotlabel1 = new JLabel("");
	        JLabel robotlabel2 = new JLabel("");
	        JLabel robotlabel3 = new JLabel("");
			frame3 = new JFrame();
			frame3.setLayout(new FlowLayout());
			JPanel panel31 = new JPanel();
	        JPanel panel32 = new JPanel();
	        JPanel panel33 = new JPanel();
	        JPanel panel34 = new JPanel();
	        JPanel panel35 = new JPanel();
	        
	        JLabel title = new JLabel("Current Jobs");
	        //JLabel fileName = new JLabel("No file selected");
	        if (robotsConnected > 0) {
	        	robotlabel1.setText(connectedRobots.get(0));
	        }
	        if (robotsConnected > 1) {
	        	robotlabel2.setText(connectedRobots.get(1));
	        }
	        if (robotsConnected > 2) {
	        	robotlabel3.setText(connectedRobots.get(2));
	        }
	        JLabel joblabel1 = new JLabel(job1);
	        JLabel joblabel2 = new JLabel(job2);
	        JLabel joblabel3 = new JLabel(job3);
	        JLabel noneConnected =  new JLabel("No robots connected");
	        
	        JButton cancel1 = new JButton("Cancel job");
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
	        JButton reconnect1 = new JButton("Reconnect");
	        reconnect1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		//Reconnect the robot
	        	}
	        } );
	        
	        JButton reconnect2 = new JButton("Reconnect");
	        reconnect1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		//Reconnect the robot
	        	}
	        } );
	        
	        JButton reconnect3 = new JButton("Reconnect");
	        reconnect1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		//Reconnect the robot
	        	}
	        } );
	        		
	        /*JButton readFile = new JButton("Select file");
	        readFile.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		JFileChooser chooser1 = new JFileChooser();
	                int result = chooser1.showOpenDialog(frame);
	                if(result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile1 = chooser1.getSelectedFile();
	                    filePath = selectedFile1.getAbsolutePath();
	                    fileName.setText(filePath);
	                	}
	        	}
	        } ); */
	        
	        panel31.add(title);
	        //panel1.add(readFile);
	        //panel1.add(fileName);
	        
	        panel32.add(robotlabel1);
	        panel32.add(joblabel1);
	        panel32.add(cancel1);
	        panel32.add(reconnect1);
	        
	        panel33.add(robotlabel2);
	        panel33.add(joblabel2);
	        panel33.add(cancel2);
	        panel33.add(reconnect2);
	        
	        panel34.add(robotlabel3);
	        panel34.add(joblabel3);
	        panel34.add(cancel3);
	        panel34.add(reconnect3);
	        
	        panel35.add(noneConnected);
	        
	        frame3.add(panel31);
	        if(robotsConnected > 0) {frame3.add(panel32); }
	        if(robotsConnected > 1) {frame3.add(panel33); }
	        if(robotsConnected > 2) {frame3.add(panel34); }
	        if(robotsConnected == 0) {frame3.add(panel35); }
	        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame3.setTitle("Warehouse GUI");
	        frame3.setPreferredSize(new Dimension(400, 350));
	        frame3.pack();
	        frame3.setVisible(true);
	}
	public void connectRobot(String robotName) {
		robotsConnected++;
		counterLabel.setText(robotsConnected.toString() + " Robots connected");
		connectedRobots.add(robotName);
		
	}
	public Location getLocation(String robot) {
		return locations.get(robot);
	}
	public boolean getGUIFinished() {
		return GUIFinished;
	}
}
