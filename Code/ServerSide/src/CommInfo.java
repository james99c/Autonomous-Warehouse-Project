import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTInfo;

public class CommInfo {
	
	String robotName;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	public CommInfo(String newRobotName, DataInputStream newInputStream, DataOutputStream newOutputStream) {
		this.robotName = newRobotName;
		this.inputStream = newInputStream;
		this.outputStream = newOutputStream;
	}
	
	
	public String getRobotName() {
		return this.robotName;
	}
	
	
	public DataInputStream getInputStream() {
		return this.inputStream;
	}
	
	public DataOutputStream getOutputStream() {
		return this.outputStream;
	}
	
	
}
