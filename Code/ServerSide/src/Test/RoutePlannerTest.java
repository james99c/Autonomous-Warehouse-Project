package Test;

import RoutePlanner;
import DataObjects.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoutePlannerTest {
    RoutePlanner planner = new RoutePlanner();
    Location start;
    Location end;

    @Test
    public void test1() {
        start = new Location(0, 0, true);
        end = new Location(2, 3, true);

        ArrayList<Location> desired;
        ArrayList<Location> result = planner.getRoute();
        Assertions.assertEquals(desired, result);
    }

  



}
