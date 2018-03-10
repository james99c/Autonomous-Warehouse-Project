import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTInfo;

public class CommInfo {
	
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	public CommInfo(DataInputStream newInputStream, DataOutputStream newOutputStream) {
		this.inputStream = newInputStream;
		this.outputStream = newOutputStream;
	}
	
	
	public DataInputStream getInputStream() {
		return this.inputStream;
	}
	
	public DataOutputStream getOutputStream() {
		return this.outputStream;
	}
	
	
}
