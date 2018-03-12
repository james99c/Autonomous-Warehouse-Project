import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.debug.DebugMonitor;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;

public class Client {
	
	private static Movement robot;
	
	public static void main(String[] args) {
		
		initialiseSensors();
		
		System.out.println("Press any button to request bluetooth");
		Button.waitForAnyPress();

		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("Successfully connected!");

		DataInputStream inputStream = connection.openDataInputStream();
		DataOutputStream outputStream = connection.openDataOutputStream();

		boolean run = true;
		while (run) {

			try {
				int length = inputStream.readInt();
				byte[] array = new byte[length];
				inputStream.read(array);
				String route = new String(array);
				if (route == null || route.equals("")) {
				}
				else {
					System.out.println("Route: " + route);
					String routeExecuted = robot.executeRoute(route);
					System.out.println("Route finished");
					outputStream.writeInt(routeExecuted.length());
					outputStream.writeBytes(routeExecuted);
					outputStream.flush();
				}
			}
			catch (IOException e) {
				System.out.println("Failed to talk to server");
				run = false;
			}
		}
	}
	
	
	public static void initialiseSensors() {
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		LightSensor middleLightSensor = new LightSensor(SensorPort.S3);
		robot = new Movement(RobotConfigs.EXPRESS_BOT, leftLightSensor, rightLightSensor, middleLightSensor);
		robot.calibrate();
	}

}
