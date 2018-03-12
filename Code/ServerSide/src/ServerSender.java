import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ServerSender extends Thread {
	
	private DataInputStream input;
	private DataOutputStream output;
	private String robotName;
	private BlockingQueue<String> clientQueue;
	
	ServerSender(CommInfo newRobotCommInfo, BlockingQueue<String> test) {
		this.input = newRobotCommInfo.getInputStream();
		this.output = newRobotCommInfo.getOutputStream();
		this.robotName = newRobotCommInfo.getRobotName();
		this.clientQueue = test;
	}

	public void run() {
		try {
			while (true) {
					String route = clientQueue.take();
					output.writeInt(route.length());
					output.writeBytes(route);
					output.flush();
					System.out.println("We're writing ints! ---------- " + route);
			}
		}
		catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}
