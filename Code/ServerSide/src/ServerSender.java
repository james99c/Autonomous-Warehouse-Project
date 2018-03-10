import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSender implements Runnable {
	
	private DataInputStream input;
	private DataOutputStream output;
	
	ServerSender(DataInputStream newInputStream, DataOutputStream newOutputStream) {
		this.input = newInputStream;
		this.output = newOutputStream;
	}

	@Override
	public void run() {
		try {
			while (true) {
				output.writeBytes("hello");
				output.flush();
				
				byte[] fromClient = input.readAllBytes();
				String clientResponse = new String(fromClient, "UTF-8");
				//System.out.println(robot.name + " says " + clientResponse);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
