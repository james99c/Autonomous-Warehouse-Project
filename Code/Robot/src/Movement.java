import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class Movement {

	private DifferentialPilot m_pilot;

	//The left sensor 
	private final LightSensor leftSensor;
	//The right sensor 
	private final LightSensor rightSensor;
	//The middle sensor 
	private final LightSensor middleSensor;
	// The maximum of the first 3 left sensor readings 
	private int leftCalibration = 0;
	// The maximum of the first 3 right sensor readings
	private int rightCalibrate = 0;
	// The maximum of the first 3 middle sensor readings
	private int middleCalibration = 0;
	//The current left sensor value
	int leftSensLightVal;
	//The current right sensor value
	int middleSensLightVal;
	//The current middle sensor value
	int rightSensLightVal;
	Rate r = new Rate(20);

	/**
	 * Creates a Movement object and sets the robot speed
	 * 
	 * @param _config The configuration information for the robot
	 * @param port1 The left sensor
	 * @param port2 The right sensor
	 * @param port3 The middle sensor
	 */
	public Movement(WheeledRobotConfiguration _config, LightSensor port1, LightSensor port2, LightSensor port3) {
		this.leftSensor = port1;
		this.rightSensor = port2;
		this.middleSensor = port3;
		m_pilot = new WheeledRobotSystem(_config).getPilot();
		m_pilot.setTravelSpeed(m_pilot.getMaxTravelSpeed()/2);
	}

	/**
	 * Sets the leftCalibration, rightCalibration, middleCalibration for the black line
	 */
	public void calibrate() {
		int leftReading = 0;
		int middleReading = 0;
		int rightReading = 0;
		System.out.println("Calibrate sensors 3 times");
		for (int j = 0; j < 3; j++) {
			Button.waitForAnyPress();
			System.out.println("Calibrate n*" + (j + 1));
			leftReading = leftSensor.getLightValue() + 2;
			rightReading = rightSensor.getLightValue() + 2;
			middleReading = middleSensor.getLightValue() + 2;
			leftCalibration = Math.max(leftReading, leftCalibration);
			middleCalibration = Math.max(middleReading, middleCalibration);
			rightCalibrate = Math.max(rightReading, rightCalibrate);

		}
		System.out.println("Calibrated!");
	}

	 
	public char moveForward() {
		m_pilot.forward();
		r.sleep();
		while (true) {
			// gets the sensor readings
			leftSensLightVal = leftSensor.getLightValue();
			middleSensLightVal = middleSensor.getLightValue();
			rightSensLightVal = rightSensor.getLightValue();
			
			// if the left sensor sees the black line it rotates 10 degrees to the left
			if (leftSensLightVal <= leftCalibration && middleSensLightVal > middleCalibration
					&& rightSensLightVal > rightCalibrate) {
				m_pilot.steer(200, 10);
				m_pilot.forward();
				continue;
			}
			
			// if the right sensor sees the black line it rotates 10 degrees to the right
			if (leftSensLightVal > leftCalibration && middleSensLightVal > middleCalibration
					&& rightSensLightVal <= rightCalibrate) {
				m_pilot.steer(200, -10);
				m_pilot.forward();
				continue;
			}
			
			// Moves forward until all the sensor see the black line
			if ((leftSensLightVal <= leftCalibration && middleSensLightVal <= middleCalibration
					&& rightSensLightVal <= rightCalibrate)) {
				m_pilot.stop();
				break;
			}

		}
		// Distance between sensors and wheels
		m_pilot.travel(0.065);
		return '0';
	}

	//randomly rotates twice to the left or to the right
	public char goBack() {
		int random = (int) (Math.random() * 2 + 1);

		if (random == 1) {
			turnLeft();
			turnLeft();
		} else {
			turnRight();
			turnRight();
		}
		return '3';
	}

	// rotates 60 degrees to the left and continues until it see the black line
	public char turnLeft() {
		m_pilot.steer(200, 60);
		middleSensLightVal = middleSensor.getLightValue();

		while (middleSensLightVal > middleCalibration) {
			m_pilot.steer(200);
			middleSensLightVal = middleSensor.getLightValue();

		}
		m_pilot.stop();
		return '1';
	}


	// rotates 60 degrees to the right and continues until it see the black line
	public char turnRight() {
		m_pilot.steer(200, -60);
		middleSensLightVal = middleSensor.getLightValue();

		while (middleSensLightVal > middleCalibration) {
			m_pilot.steer(-200);
			middleSensLightVal = middleSensor.getLightValue();
		}
		m_pilot.stop();
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
				letter = moveForward();
				routeExecuted += letter;
				break;
			case '1':
				letter = turnLeft();
				routeExecuted += letter;
				break;
			case '2':
				letter = turnRight();
				routeExecuted += letter;
				break;
			case '3':
				letter = goBack();
				routeExecuted += letter;
				break;
			default:
				break;
			}
		}
		
		return routeExecuted;
		
	}

}
