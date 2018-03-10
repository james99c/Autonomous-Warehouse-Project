import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import lejos.pc.comm.NXTInfo;

public class ServerComm extends Thread {

	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private BlockingQueue<Integer> list;

	public ServerComm(CommInfo newCommInfo, BlockingQueue<Integer> newList) {
		this.inputStream = newCommInfo.getInputStream();
		this.outputStream = newCommInfo.getOutputStream();
		this.list = newList;
	}

	public void run() {
		try {
			while (true) {
				// 0 = forward, 1 = left, 2 = right
				outputStream.writeInt(02010);
				outputStream.flush();

				int answer = inputStream.readInt();
				System.out.println(" returned " + answer);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
