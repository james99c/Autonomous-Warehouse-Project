import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

/**
 * 
 * Specific movements of a robot
 * 
 * @author Anthony
 *
 */
public class Movement {

	private DifferentialPilot m_pilot;

	private final LightSensor leftSensor;
	private final LightSensor rightSensor;
	private final LightSensor middleSensor;
	private int leftCalibration = 0;
	private int rightCalibration = 0;
	private int middleCalibration = 0;
	int leftSensLightVal;
	int middleSensLightVal;
	int rightSensLightVal;
	Rate r = new Rate(20);
	
	/**
	 * 
	 * Constructor for new movement
	 * 
	 * @param _config The robot's configuration
	 * @param port1 The type of sensor at port 1
	 * @param port2 The type of sensor at port 2
	 * @param port3 The type of sensor at port 3
	 */
	public Movement(WheeledRobotConfiguration _config, LightSensor port1, LightSensor port2, LightSensor port3) {
		this.leftSensor = port1;
		this.rightSensor = port2;
		this.middleSensor = port3;
		m_pilot = new WheeledRobotSystem(_config).getPilot();
		m_pilot.setTravelSpeed(0.1);
	}
	

	public void calibrate() {
		int leftReading = 0;
		int middleReading = 0;
		int rightReading = 0;
		System.out.println("Calibrate sensors 3 times");
		
		for (int j = 0; j < 3; j++) {
			Button.waitForAnyPress();
			System.out.println(j + 1);
			leftReading = leftSensor.getLightValue() + 2;
			rightReading = rightSensor.getLightValue() + 2;
			middleReading = middleSensor.getLightValue() + 2;
			leftCalibration = Math.max(leftReading, leftCalibration);
			middleCalibration = Math.max(middleReading, middleCalibration);
			rightCalibration = Math.max(rightReading, rightCalibration);
		}
		
		System.out.print(leftCalibration + ", " + middleCalibration + ", " + rightCalibration + "\nCalibration complete");
	}
	

	public char moveForward() {
		m_pilot.forward();
		r.sleep();
		while (true) {
			leftSensLightVal = leftSensor.getLightValue();
			middleSensLightVal = middleSensor.getLightValue();
			rightSensLightVal = rightSensor.getLightValue();
			if ((leftSensLightVal <= leftCalibration && middleSensLightVal <= middleCalibration && rightSensLightVal <= rightCalibration)) {
				m_pilot.stop();
				break;
			}
		}
		m_pilot.travel(0.065);
		return '0';
	}
	

	public char turnLeft() {
		m_pilot.steer(200, 60);
		middleSensLightVal = middleSensor.getLightValue();

		while (middleSensLightVal > middleCalibration) {
			m_pilot.steer(200);
			middleSensLightVal = middleSensor.getLightValue();

		}
		m_pilot.stop();
		m_pilot.steer(200, 6);
		return '1';
	}
	

	public char turnRight() {
		m_pilot.steer(200, -60);
		middleSensLightVal = middleSensor.getLightValue();

		while (middleSensLightVal > middleCalibration) {
			m_pilot.steer(-200);
			middleSensLightVal = middleSensor.getLightValue();
		}

		m_pilot.steer(200, -5);
		return '2';
	}
	

	public String executeRoute(String route) {
		char[] instructions = route.toCharArray();
		
		for (int i = 0; i < instructions.length; i++) {
			//System.out.println(instructions[i]);
		}
		String routeExecuted = "";
		for (int i = 0; i < instructions.length; i++) {
			char instruction = instructions[i];
			char letter;
			switch (instruction) {
			case '0':
				System.out.println("forward");
				letter = moveForward();
				routeExecuted += letter;
				break;
			case '1':
				System.out.println("left");
				letter = turnLeft();
				routeExecuted += letter;
				break;
			case '2':
				System.out.println("right");
				letter = turnRight();
				routeExecuted += letter;
				break;
			default:
				System.out.println("nothing");
				break;
			}
		}
		
		return routeExecuted;
		
	}

}
