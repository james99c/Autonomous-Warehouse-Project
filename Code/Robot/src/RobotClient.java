import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class RobotClient {
	
	public static void main(String[] args) {

		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("Successfully connected!");

		DataInputStream inputStream = connection.openDataInputStream();
		DataOutputStream outputStream = connection.openDataOutputStream();
		Random r = new Random();

		boolean run = true;
		int number = r.nextInt(10);
		while (run) {

			try {
				int input = inputStream.readInt();
				if (input != 0) {
					String inputAsString = Integer.toString(input);
					char[] instructions = inputAsString.toCharArray();
					for (int i = 0; i < instructions.length; i++) {
						System.out.println(instructions[i]);
					}
					
					outputStream.writeInt(number);
					outputStream.flush();
				}
				else {
					run = false;
				}
			}
			catch (IOException e) {
				System.out.println("Failed to talk to server");
				run = false;
			}
		}
	}

}
