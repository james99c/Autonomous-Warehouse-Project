package rp.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTInfo;

/**
 * 
 * Object to store the information about a given robot
 * 
 * @author James
 *
 */
public class CommInfo {
	
	String robotName;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	/**
	 * 
	 * Constructor for new comm info
	 * 
	 * @param newRobotName The name of the robot
	 * @param newInputStream The robot's input stream
	 * @param newOutputStream The robot's output stream
	 */
	public CommInfo(String newRobotName, DataInputStream newInputStream, DataOutputStream newOutputStream) {
		this.robotName = newRobotName;
		this.inputStream = newInputStream;
		this.outputStream = newOutputStream;
	}
	
	/**
	 * 
	 * Get the name of the robot
	 * 
	 * @return The name of the robot as a string
	 */
	public String getRobotName() {
		return this.robotName;
	}
	
	/**
	 * 
	 * Get the input stream if the robot
	 * 
	 * @return The input stream as a data input stream
	 */
	public DataInputStream getInputStream() {
		return this.inputStream;
	}
	
	/**
	 * 
	 * Get the output stream if the robot
	 * 
	 * @return The output stream as a data input stream
	 */
	public DataOutputStream getOutputStream() {
		return this.outputStream;
	}
	
	
}
