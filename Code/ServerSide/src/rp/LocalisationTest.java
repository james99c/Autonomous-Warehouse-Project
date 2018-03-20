package rp;

import rp.DataObjects.*;
import rp.DataObjects.Map;

import java.util.*;
import org.junit.*;

public class LocalisationTest {
  @Test
  public void testOfGetDistancesAtGridPoint() {
    ArrayList<Location> blocks = new ArrayList<>();
    ArrayList<GridPoint> gridPointList = new ArrayList<>();

    // generates the map as laid out in robot labs
    for (int i = 1; i < 11; i += 3) {
      for (int j = 1; j < 6; j++) {
        blocks.add(new Location(i, j));
      }
    }

    Map map = new Map(7, 11, blocks);
    Localisation local = new Localisation(map);

    for (int i = 0; i < map.getWidth(); i++) {
      for (int j = 0; j < map.getHeight(); j++) {
        GridPoint gp = map.getGridPoint(i, j);
        if (gp.isAvailable(-1.0f))
          gridPointList.add(gp);
      }
    }


    Integer[] dFromGridPoint = local.getDistancesAtGridPoint(map.getGridPoint(2, 4));
    ArrayList<Integer> listOfD = new ArrayList<>();
    for (int i = 0; i < 4; i++)
      listOfD.add(dFromGridPoint[i]);
    //System.out.println(gridPointList);
    ArrayList<GridPoint> match = local.getMatchingGridPoints(listOfD, gridPointList);
    //System.out.println(dFromGridPoint);
    //for (int i = 0; i < 4; i++) {
    //	System.out.print(dFromGridPoint[i] + ",");
    //}
    System.out.println(match);

  }
}
