
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class RobotConnector {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private final NXTInfo robotInfo;
	
	
	/**
	 * 
	 * Constructor for a new robot connector
	 * 
	 * @param newRobot The bluetooth info about the new robot
	 */
	public RobotConnector(NXTInfo newRobot) {
		robotInfo = newRobot;
	}
	
	/**
	 * 
	 * Attempt to establish communications between the server and the robot
	 * 
	 * @param newComm Communication interface with the robot
	 * @return The communication info for the robot including its input and output streams
	 * @throws NXTCommException The server failed to connect to the robot
	 */
	public CommInfo connect(NXTComm newComm) throws NXTCommException {
		if (newComm.open(robotInfo)) {
			inputStream = new DataInputStream(newComm.getInputStream());
			outputStream = new DataOutputStream(newComm.getOutputStream());
		}
		return new CommInfo(inputStream, outputStream);
	}

	
	public static void main(String[] args) {
		ClientTable clientTable = new ClientTable();
		try {

			// Add the info about the robot(s)
			NXTInfo[] robots = {
					new NXTInfo(NXTCommFactory.BLUETOOTH, "Pisces", "001653155F35"),
					new NXTInfo(NXTCommFactory.BLUETOOTH, "Gemini", "001653182F7A"),
					//new NXTInfo(NXTCommFactory.BLUETOOTH, "Sagittarius", "00165317B913")
					};
			

			ArrayList<RobotConnector> connections = new ArrayList<>(robots.length);

			
			for (NXTInfo robot : robots) {
				connections.add(new RobotConnector(robot));
			}

			/*
			 *  Establish a communication interface with the robot,
			 *  then attempt to connect to that robot and start
			 *  a thread to communicate with it
			 */
			for (RobotConnector connection : connections) {
				NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				CommInfo robotCommInfo = connection.connect(nxtComm);
				(new ServerComm(robotCommInfo, clientTable.getQueue(connection))).start();
				
			}

		}
		catch (NXTCommException e) {
			e.printStackTrace();
		}

	}
}
