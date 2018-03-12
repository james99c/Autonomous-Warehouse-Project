
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class RobotConnector {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private final NXTInfo robotInfo;
	private static String route = "01020";

	/**
	 * 
	 * Constructor for a new robot connector
	 * 
	 * @param newRobot
	 *            The bluetooth info about the new robot
	 */
	public RobotConnector(NXTInfo newRobot) {
		robotInfo = newRobot;
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
	public CommInfo connect(NXTComm newComm, String robotName) throws NXTCommException {
		if (newComm.open(robotInfo)) {
			inputStream = new DataInputStream(newComm.getInputStream());
			outputStream = new DataOutputStream(newComm.getOutputStream());
		}
		return new CommInfo(robotName, inputStream, outputStream);
	}

	public static void main(String[] args) {
		ClientTable clientTable = new ClientTable();
		try {

			// Add the info about the robot(s)
			NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "Pisces", "001653155F35"),
					// new NXTInfo(NXTCommFactory.BLUETOOTH, "Gemini", "001653182F7A"),
					new NXTInfo(NXTCommFactory.BLUETOOTH, "Sagittarius", "00165317B913") };

			ArrayList<RobotConnector> connections = new ArrayList<>(robots.length);

			for (NXTInfo robot : robots) {
				connections.add(new RobotConnector(robot));
			}

			/*
			 * Establish a communication interface with the robot, then attempt to connect
			 * to that robot and start a thread to communicate with it
			 */
			for (int i = 0; i < connections.size(); i++) {
				NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				CommInfo robotCommInfo = connections.get(i).connect(nxtComm, robots[i].name);
				clientTable.add(robotCommInfo.getRobotName());
				(new ServerComm(robotCommInfo, clientTable)).start();
				(new ServerSender(robotCommInfo, clientTable.getQueue(robotCommInfo.getRobotName()))).start();

				System.out.println("Threads started!");

				BlockingQueue<String> recipientsQueue = clientTable.getQueue(robotCommInfo.getRobotName());

				if (recipientsQueue != null) {
					System.out.println("Trying to offer a route to " + robotCommInfo.getRobotName());
					recipientsQueue.offer(route);
					System.out.println("Successfully offered: " + route);
				}

			}

		}
		catch (NXTCommException e) {
			e.printStackTrace();
		}

	}
}
