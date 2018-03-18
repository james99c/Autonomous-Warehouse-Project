package rp.Interfaces;

public interface LocationInterface {

	public int getX();
	public int getY();
	public int[] getCoordinates();
	public boolean available();
	public void setAvailability(Boolean availability);
}
