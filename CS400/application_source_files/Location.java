// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Email: htan47@wisc.edu
// Notes to Grader: None

import java.util.LinkedList;

/**
 * location objects group a data field with an adjacency list of weighted
 * directed roads that lead away from them.
 */
public class Location {
  public String locationName; // locationName will serve as the key of the hashtable
  public String locationAddress;
  public LinkedList<Road> roadsLeaving;

  /**
   * initilize location object with given name and address
   *
   * @param locationName location name
   * @param locationAddress location address
   */
  public Location(String locationName, String locationAddress) {
    this.locationName = locationName;
    this.locationAddress = locationAddress;
    this.roadsLeaving = new LinkedList<>();
  }

  /**
   * give this location new address
   * @param newAddress the new address to be set
   */
  public void setAddress(String newAddress) {
    this.locationAddress = newAddress;
  }
}
