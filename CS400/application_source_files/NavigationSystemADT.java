// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Notes to Grader: None

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This ADT represents a directed graph data structure with positive edge weights.
 */
public interface NavigationSystemADT {

  /**
   * importing locations and roads information from external files
   * @throws FileNotFoundException when can't open the file with given name
   */
  public void setupData() throws FileNotFoundException;

  /**
   * Insert a new location into the graph.
   *
   * @param locationName the location Name stored in the new location
   * @param locationAddress the location Address stored in the new location
   * @return true if the locationName can be inserted as a new vertex, false if it is
   *     already in the graph
   * @throws NullPointerException if locationName or locationAddress is null
   */
  public boolean insertLocation(String locationName, String locationAddress);

  /**
   * Remove a location from the graph.
   * Also removes all roads adjacent to the location from the graph (all roads
   * that have this location as a source or a destination location).
   *
   * @param locationName the locationName item stored in the vertex to remove
   * @return true if a location with *locationName* has been removed,
   *         false if it was not in the graph
   * @throws NullPointerException if locationName is null
   */
  public boolean removeLocation(String locationName);

  /**
   * Insert a new directed road with a positive cost, time, and distance into the graph.
   *
   * @param source the location name contained in the source location for the road
   * @param target the location name contained in the target location for the road
   * @param cost the cost of the road (has to be a non-negative double)
   * @param time the time for passing the road (has to be a positive double)
   * @param distance the distance of the road (has to be a positive double)
   * @return true if the road could be inserted, false
   *     if the road with the same name was already in the graph
   * @throws IllegalArgumentException if either source or target or both are not in the graph,
   *     or if its weight is < 0
   * @throws NullPointerException if either source or target or both are null
   */
  public boolean insertRoad(String source, String target, String roadName, double cost,
      double time, double distance);

  /**
   * Remove a road from the graph.
   *
   * @param source the location name contained in the source location for the road
   * @param target the location name contained in the target location for the road
   * @return true if the road could be removed, false if it was not in the graph
   * @throws IllegalArgumentException if either source or target or both are not in the graph
   * @throws NullPointerException if either source or target or both are null
   */
  public boolean removeRoad(String source, String target);

  /**
   * Check if the graph contains a road with road name
   *
   * @param roadName name of the road to be fined
   * @return true if road name is stored in a road of the graph, false otherwise
   * @throws NullPointerException if roadName is null
   */
  public boolean containsRoad(String roadName);

  /**
   * Check if the graph contains a location with location name
   *
   * @param locationName name of the location to be fined
   * @return true if location name is stored in a location of the graph, false otherwise
   * @throws NullPointerException if locationName is null
   */
  public boolean containsLocation(String locationName);

  /**
   * Get the Road object by given road name
   *
   * @param roadName name of the road to be found
   * @return the road object if found; and null if not found
   * @throws NullPointerException if roadName is null
   */
  public Road getRoad(String roadName);

  /**
   * Get the Location object by given Location name
   *
   * @param locationName name of the Location to be found
   * @return the Location object if found; and null if not found
   * @throws NullPointerException if locationName is null
   */
  public Location getLocation(String locationName);

  /**
   * look up the address of a specific location name
   * @param locationName the name of the location
   * @throws NullPointerException if locationName is null
   * @return the address of the location, or empty string if not found the location
   */
  public String lookupLocationAddress(String locationName);

  /**
   * update the address of a specific location name
   * @param locationName the name of the location
   * @return true if successfully update the address of this location; false otherwise
   * @throws NullPointerException if locationName or newAddress is null
   */
  public boolean updateLocationAddress(String locationName, String newAddress);

  /**
   * update the cost of a specific road name
   * @param roadName the name of the road
   * @param newCost the new cost of the road
   * @return true if successfully update the cost of this road; false otherwise
   * @throws NullPointerException if roadName is null
   * @throws IllegalArgumentException if newCost is smaller than 0
   */
  public boolean updateRoadCost(String roadName, double newCost);

  /**
   * update the time of a specific road name
   * @param roadName the name of the road
   * @param newTime the new time of the road
   * @return true if successfully update the time of this road; false otherwise
   * @throws NullPointerException if roadName is null
   * @throws IllegalArgumentException if newTime is smaller than 0
   */
  public boolean updateRoadTime(String roadName, double newTime);

  /**
   * update the time of a specific road name
   * @param roadName the name of the road
   * @param newDistance the new distance of the road
   * @return true if successfully update the time of this road; false otherwise
   * @throws NullPointerException if roadName is null
   * @throws IllegalArgumentException if newDistance is smaller than 0
   */
  public boolean updateRoadDistance(String roadName, double newDistance);

  /**
   * Returns the shortest path between start and end.
   * Uses Dijkstra's shortest path algorithm to find the shortest path.
   *
   * @param start data item within first location in path
   * @param end data item within last location in path
   * @param compType type to be compared for the weight of the road: 'c' for cost, 'd' for distance
   *                't' fot time
   * @return list of roads in order on the shortest path between locations
   * @throws NoSuchElementException when no path from start to end can be found
   *     including when no vertex containing start or end can be found
   */
  public List<Road> shortestPath(String start, String end, char compType);
}
