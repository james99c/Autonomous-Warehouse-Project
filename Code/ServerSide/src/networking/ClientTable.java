package networking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * Stores the robots and their corresponding routes
 * 
 * @author James
 *
 */
public class ClientTable {

	private ConcurrentMap<String, BlockingQueue<String>> queueTable = new ConcurrentHashMap<String, BlockingQueue<String>>();
	
	
	/**
	 * 
	 * Add a new robot to the client table
	 * 
	 * @param newRobot The name of the new robot
	 */
	public void add(String newRobot) {
		queueTable.put(newRobot, new LinkedBlockingQueue<String>());
	}
	
	/**
	 * 
	 * Get the queue for a specific robot
	 * 
	 * @param robotName The name of the robot in question
	 * @return THe queue for the robot in question
	 */
	public BlockingQueue<String> getQueue(String robotName) {
		return  queueTable.get(robotName);
	}
	
}