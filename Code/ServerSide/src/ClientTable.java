import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTable {

	private ConcurrentMap<RobotConnector, BlockingQueue<Integer>> queueTable = new ConcurrentHashMap<RobotConnector, BlockingQueue<Integer>>();
	
	
	public void add(RobotConnector newRobot) {
		queueTable.put(newRobot, new LinkedBlockingQueue<Integer>());
	}
	
	public BlockingQueue<Integer> getQueue(RobotConnector robotName) {
		return  queueTable.get(robotName);
	}
	
}