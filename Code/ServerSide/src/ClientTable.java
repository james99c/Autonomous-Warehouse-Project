import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTable {

	private ConcurrentMap<String, BlockingQueue<String>> queueTable = new ConcurrentHashMap<String, BlockingQueue<String>>();
	
	
	public void add(String newRobot) {
		queueTable.put(newRobot, new LinkedBlockingQueue<String>());
	}
	
	public BlockingQueue<String> getQueue(String robotName) {
		return  queueTable.get(robotName);
	}
	
}