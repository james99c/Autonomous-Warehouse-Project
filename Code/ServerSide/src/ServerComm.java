import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

//import com.sun.deploy.util.SessionState.Client;

import lejos.pc.comm.NXTInfo;

public class ServerComm extends Thread {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	CommInfo robotInfo;
	private ClientTable clientTable;

	public ServerComm(CommInfo newCommInfo, ClientTable clientTable) {
		this.robotInfo = newCommInfo;
		this.inputStream = newCommInfo.getInputStream();
		this.outputStream = newCommInfo.getOutputStream();
		this.clientTable = clientTable;
		
	}

	public void run() {
		System.out.println("Server comm is active");
		try {
			while (true) {
				
					int length = inputStream.readInt();
					byte[] array = new byte[length];
					inputStream.read(array);
					String answer = new String(array);
					//System.out.println("The route is: " + route);
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
