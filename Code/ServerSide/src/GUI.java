import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import Interfaces.GUIInterface;


public class GUI {
	private JFrame frame;
	String job1;
	String job2;
	String job3;
	//String filePath = "";
	
	
	public GUI() {
		String testString = "Test job ID";
        job1 = testString;
        job2 = testString;
        job3 = testString;
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        
        JLabel title = new JLabel("Current Jobs");
        //JLabel fileName = new JLabel("No file selected");
        JLabel robotlabel1 = new JLabel("Robot 1");
        JLabel robotlabel2 = new JLabel("Robot 2");
        JLabel robotlabel3 = new JLabel("Robot 3");
        JLabel joblabel1 = new JLabel(job1);
        JLabel joblabel2 = new JLabel(job2);
        JLabel joblabel3 = new JLabel(job3);
        
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
        
        
        panel1.add(title);
        //panel1.add(readFile);
        //panel1.add(fileName);
        
        panel2.add(robotlabel1);
        panel2.add(joblabel1);
        panel2.add(cancel1);
        
        panel3.add(robotlabel2);
        panel3.add(joblabel2);
        panel3.add(cancel2);
        
        panel4.add(robotlabel3);
        panel4.add(joblabel3);
        panel4.add(cancel3);
        
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Warehouse GUI");
        frame.setPreferredSize(new Dimension(280, 280));
        frame.pack();
        frame.setVisible(true);
	}
	public static void main(String[] args) {
        GUI g = new GUI();
        		
	} 
}
