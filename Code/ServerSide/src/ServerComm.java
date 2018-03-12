import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

//import com.sun.deploy.util.SessionState.Client;

import lejos.pc.comm.NXTInfo;

/**
 * 
 * Reads routes sent by the robot
 * 
 * Extension: check route sent by robot
 * to route originally sent to see if
 * they match
 * 
 * @author James
 *
 */
public class ServerComm extends Thread {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	CommInfo robotInfo;
	private ClientTable clientTable;

	/**
	 * 
	 * Constructor for a new server comm
	 * 
	 * @param newCommInfo Info about the specific robot including its name and comm streams
	 * @param clientTable The client table currently in use by the server
	 */
	public ServerComm(CommInfo newCommInfo, ClientTable clientTable) {
		this.robotInfo = newCommInfo;
		this.inputStream = newCommInfo.getInputStream();
		this.outputStream = newCommInfo.getOutputStream();
		this.clientTable = clientTable;
		
	}

	public void run() {
		System.out.println("Server comm is active");
		try {
			/*
			 * Read the route sent by the robot.
			 * If the route is valid print it
			 */
			while (true) {
					int length = inputStream.readInt();
					byte[] array = new byte[length];
					inputStream.read(array);
					String answer = new String(array);
					
					if (answer == null || answer.equals("")) {
					}
					else {
						System.out.println("Robot's route: " + answer);
					}
					
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
