import Interfaces.RoutePlannerInterface;



public class RoutePlanner implements RoutePlannerInterface{
    private ArrayList<Integer> jobs;

    private ArrayList<Location> route;

    public RoutePlanner() {
        jobs = getJob();
    }

    public ArrayList<Location> getRoute() {
        return this.route;
    }

    public ArrayList<Integer> getJob() {
        // Take jobs from JobDecider
    }

    //public search
}
