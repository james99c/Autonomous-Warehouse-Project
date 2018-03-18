package rp.DataObjects;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTInfo;
import rp.networking.CommInfo;

/**
 * @author James
 *
 */
public class RobotConnector {
	
	
	private final NXTInfo robotInfo;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	
	/**
	 * 
	 * Constructor for a new robot connector
	 * 
	 * @param newRobot
	 *            The bluetooth info about the new robot
	 */
	public RobotConnector(NXTInfo newRobot) {
		this.robotInfo = newRobot;
	}
	
	
	/**
	 * 
	 * Gets the name of the robot
	 * 
	 * @return The name of the robot as a string
	 */
	public String getName() {
		return robotInfo.name;
	}


	/**
	 * 
	 * Attempt to establish communications between the server and the robot
	 * 
	 * @param newComm
	 *            Communication interface with the robot
	 * @return The communication info for the robot including its input and output
	 *         streams
	 * @throws NXTCommException
	 *             The server failed to connect to the robot
	 */
	public CommInfo connect(NXTComm newComm, String robotName) {
		try {
			if (newComm.open(robotInfo)) {
				inputStream = new DataInputStream(newComm.getInputStream());
				outputStream = new DataOutputStream(newComm.getOutputStream());
			}
		}
		catch (NXTCommException e) {
			System.out.println("Couldn't connect to " + robotName);
			e.printStackTrace();
		}
		return new CommInfo(robotName, inputStream, outputStream);
	}

}
