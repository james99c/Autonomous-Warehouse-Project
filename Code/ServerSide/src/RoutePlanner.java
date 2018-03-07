import Interfaces.RoutePlannerInterface;



public class RoutePlanner implements RoutePlannerInterface{
    private ArrayList<Integer> jobs;

    public RoutePlanner() {
        jobs = getJob();
    }

    public ArrayList<Integer> getJob() {
        // Take jobs from JobDecider
    }
}
