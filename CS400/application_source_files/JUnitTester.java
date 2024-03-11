// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Email: htan47@wisc.edu
// Notes to Grader: None

/*** JUnit imports ***/
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*** JUnit imports end  ***/

import java.io.FileNotFoundException;
import java.util.List;

public class JUnitTester {

  protected NavigationBackEnd _instance = null;



  //BeforeEach annotation makes a method invocked automatically
  //before each test
  @BeforeEach
  public void createInstane() {
    try {
      _instance = new NavigationBackEnd();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // This tests if the application has all locations' addresses loaded in from file
  // when create an instance of it
  @Test
  public void testLookupAddress() {
    String[] locations = new String[]{ "Queens University:50 West 4th Street"
        , "L'Artusi:228 W 10th St" , "Queens Library:79-50 Bell Blvd" ,
        "Pizza Hut:22003 hillside Ave" , "Key Food:22046 Hillside Ave"
        , "Kohl's:6111 188th St" , "World Trade Center:285 Fulton St"
        , "Holiday Inn:99 Washington St" , "Pace University:1 Pace Plaza"
        , "Apple Store:767 Fifth Ave" , "Times Square:42 St" , "Macy's:151 W 34th St"
        , "Target:112 W 34th St" , "Vessel:20 hudson Yards" , "Thai Diner:186 Mott St"
        , "Mount Hospital:419 W 114th St" , "The Met:1000 Fifth Ave"
        , "The Home Depot:980 3rd Ave" , "Haidilao Hotpot:141 E 56th St"
        , "Jane Hair Salon:883 First Ave"};

    for (int i = 0; i < locations.length; i++) {
      String[] locationInfo = locations[i].split(":");
      String address = _instance.lookupLocationAddress(locationInfo[0]);
      assertEquals(locationInfo[1], address);
    }
  }


  // This tests if the application handles inserting roads with
  // invalid data
  @Test
  public void testInvalidInsertRoad() {
    // insert road with null source, target, or name
    {
      Exception exception = assertThrows(NullPointerException.class, () -> {
        _instance.insertRoad(null, null, null, 1,1,1);
      });
      String expectedMessage = "Cannot add road with null source, target or road name";
      String actualMessage = exception.getMessage();
      assertEquals(actualMessage, expectedMessage);
    }

    // insert road with negative cost, time, and distance
    {
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        _instance.insertRoad("Times Square", "Target", "roadName", -1,-1,-1);
      });
      String expectedMessage = "Cannot add road with invalid cost, time, or distance";
      String actualMessage = exception.getMessage();
      assertEquals(actualMessage, expectedMessage);
    }

    // insert road with duplicated names
    {
      boolean result = _instance.insertRoad("Holiday Inn", "Macy's", "Caffrey Ave", 1,1,1);
      assertEquals(false, result);
    }
  }

  // This tests if the application handles inserting locations with
  // invalid data
  @Test
  public void testInvalidInsertLocation() {
    // insert location with null
    {
      Exception exception = assertThrows(NullPointerException.class, () -> {
        _instance.insertLocation(null, null);
      });
      String expectedMessage = "Cannot add null location name or address";
      String actualMessage = exception.getMessage();
      assertEquals(actualMessage, expectedMessage);
    }

    // insert location that a location name already exists in the system
    {
      boolean result = _instance.insertLocation("Target", "Address");
      assertEquals(false, result);
    }
  }

  // this method test if the application have the correct lowest path return
  @Test
  public void testShortestPath() {
    // lowest cost path
    {
      List<Road> roadSequence = _instance.shortestPath("Queens University", "Queens Library", 'c');
      assertEquals(roadSequence.size(), 2);
      if (roadSequence.size()==2) {
        assertEquals(roadSequence.get(0).roadName, "Caffrey Ave");
        assertEquals(roadSequence.get(1).roadName, "Cedar Ln");
      }
    }
  }

  // This test for if application can successfully update a road's cost, time, and distance
  @Test
  public void testUpdateRoadInfo() {
    double newCost = 2.0;
    double newTime = 4.0;
    double newDistance = 2.3;
    boolean resultCost = _instance.updateRoadCost("Cedar Ln", newCost);
    boolean resultTime = _instance.updateRoadTime("Cedar Ln", newTime);
    boolean resultDistance = _instance.updateRoadDistance("Cedar Ln", newDistance);
    assertEquals(resultCost, true);
    assertEquals(resultTime, true);
    assertEquals(resultDistance, true);
    Road target = _instance.getRoad("Cedar Ln");
    assertEquals(target.cost, newCost);
    assertEquals(target.time, newTime);
    assertEquals(target.distance, newDistance);
  }
}
