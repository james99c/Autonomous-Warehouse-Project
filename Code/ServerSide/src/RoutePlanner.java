import Interfaces.RoutePlannerInterface;



public class RoutePlanner implements RoutePlannerInterface{
    private ArrayList<Integer> jobs;

    private ArrayList<Location> route;

    public RoutePlanner() {
        jobs = getJob();
    }

    public ArrayList<Location> getRoute(Location start, Location goal) {
        return this.route;
    }

    public ArrayList<Integer> getJob() {
        // Take jobs from JobDecider
    }

    public ArrayList<Integer[]> search(currentLocation, previousLocation, currentCost, goalLocation, currentPath, inputVariable){
        
    }
}
