import lejos.nxt.LCD

public class Display {
	String jobID;
	String status;
	int weight;
	public static void main(String args[]) {
		LCD.drawString(jobID, 0, 5);
		LCD.drawString(status, 2, 3)
		LCD.drawInt(weight, 4, 7);
		LCD.drawnString("< pick  cancel >", 6, 0);
	}
}
