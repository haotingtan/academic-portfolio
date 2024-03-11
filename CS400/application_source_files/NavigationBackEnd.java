// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Email: htan47@wisc.edu
// Notes to Grader: None

import java.util.Hashtable;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class NavigationBackEnd implements NavigationSystemADT {

  protected Hashtable<String, Road> roads; // holds graph roads, key=road name
  protected Hashtable<String, Location> locations; // holds graph locations, key=location name
  public NavigationBackEnd() throws FileNotFoundException {
    locations = new Hashtable<>();
    roads = new Hashtable<>();
    this.setupData();
  }

  /**
   * importing locations and roads information from external files
   * @throws FileNotFoundException when can't open the file with given name
   */
  public void setupData() throws FileNotFoundException {
    try {
      File fileName = new File("location_Information.txt");
      Scanner myReader = new Scanner(fileName);
      while (myReader.hasNextLine()) {
        String[] locationInfo = myReader.nextLine().trim().split(":");
        Location importLocation = new Location(locationInfo[0], locationInfo[1]);
        locations.put(locationInfo[0], importLocation);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("encountering problem when loading the data from "
          + "location_Information.txt file");
    }

    try {
      File fileName = new File("road_Information.txt");
      Scanner myReader = new Scanner(fileName);
      while (myReader.hasNextLine()) {
        String[] roadInfo = myReader.nextLine().trim().split(":");
        this.insertRoad(roadInfo[1], roadInfo[2], roadInfo[0], Double.valueOf(roadInfo[3]),
            Double.valueOf(roadInfo[4]), Double.valueOf(roadInfo[5]));
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("encountering problem when loading the data from "
          + "road_Information.txt file");
    }
  }

  /**
   * Insert a new location into the graph.
   *
   * @param locationName the location Name stored in the new location
   * @param locationAddress the location Address stored in the new location
   * @return true if the locationName can be inserted as a new vertex, false if it is
   *     already in the graph
   * @throws NullPointerException if locationName or locationAddress is null
   */
  @Override
  public boolean insertLocation(String locationName, String locationAddress)
      throws NullPointerException {
    if(locationName == null || locationAddress == null)
      throw new NullPointerException("Cannot add null location name or address");
    if(locations.containsKey(locationName)) return false; // duplicate names are not allowed
    locations.put(locationName, new Location(locationName, locationAddress));
    return true;
  }

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
  @Override
  public boolean removeLocation(String locationName) throws NullPointerException {
    if(locationName == null) throw new NullPointerException("Cannot remove null location");
    Location removeLocation = locations.get(locationName);
    for (int i=0; i<removeLocation.roadsLeaving.size(); i++) {
      roads.remove(removeLocation.roadsLeaving.get(i).roadName);
    }
    if(removeLocation == null) return false; // location not found within graph
    // search all locations for roads targeting removeVertex
    for(Location l : locations.values()) {
      Road removeRoad = null;
      for(Road r : l.roadsLeaving)
        if(r.target == removeLocation)
          removeRoad = r;
      // and remove any such edges that are found
      if(removeRoad != null) {
        l.roadsLeaving.remove(removeRoad);
        roads.remove(removeRoad);
      }
    }
    // finally remove the location and all roads contained within it
    return locations.remove(locationName) != null;
  }

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
  @Override
  public boolean insertRoad(String source, String target, String roadName, double cost,
      double time, double distance) throws IllegalArgumentException, NullPointerException {
    if(source == null || target == null || roadName == null)
      throw new NullPointerException("Cannot add road with null source, target or road name");
    Location sourceLocation = this.locations.get(source);
    Location targetLocation = this.locations.get(target);
    if(sourceLocation == null || targetLocation == null)
      throw new IllegalArgumentException("Cannot add road with locations that do not exist");
    if(cost < 0 || time < 0 || distance < 0)
      throw new IllegalArgumentException("Cannot add road with invalid cost, time, or distance");
    // handle cases where edge already exists between these locations
    if (roads.containsKey(roadName)) {
      return false;
    }
    // otherwise add new road to sourceLocation
    Road addRoad = new Road(targetLocation,roadName,cost,time,distance);
    sourceLocation.roadsLeaving.add(addRoad);
    this.roads.put(roadName, addRoad);
    return true;
  }

  /**
   * Remove a road from the graph.
   *
   * @param source the location name contained in the source location for the road
   * @param target the location name contained in the target location for the road
   * @return true if the road could be removed, false if it was not in the graph
   * @throws IllegalArgumentException if either source or target or both are not in the graph
   * @throws NullPointerException if either source or target or both are null
   */
  @Override
  public boolean removeRoad(String source, String target) {
    if(source == null || target == null)
      throw new NullPointerException("Cannot remove road with null source or target");
    Location sourceLocation = this.locations.get(source);
    Location targetLocation = this.locations.get(target);
    if(sourceLocation == null || targetLocation == null)
      throw new IllegalArgumentException("Cannot remove road with locations that do not exist");
    // find edge to remove
    Road removeRoad = null;
    for(Road r : sourceLocation.roadsLeaving)
      if(r.target == targetLocation)
        removeRoad = r;
    if(removeRoad != null) { // remove road that is successfully found
      roads.remove(removeRoad.roadName);
      sourceLocation.roadsLeaving.remove(removeRoad);
      return true;
    }
    return false; // otherwise return false to indicate failure to find
  }

  /**
   * Check if the graph contains a road with road name
   *
   * @param roadName name of the road to be fined
   * @return true if road name is stored in a road of the graph, false otherwise
   * @throws NullPointerException if roadName is null
   */
  @Override
  public boolean containsRoad(String roadName) {
    if(roadName == null) throw new NullPointerException("Cannot contain null road name");
    return roads.containsKey(roadName);
  }

  /**
   * Check if the graph contains a location with location name
   *
   * @param locationName name of the location to be fined
   * @return true if location name is stored in a location of the graph, false otherwise
   * @throws NullPointerException if locationName is null
   */
  @Override
  public boolean containsLocation(String locationName) {
    if(locationName == null) throw new NullPointerException("Cannot contain null location name");
    return locations.containsKey(locationName);
  }

  /**
   * Get the Road object by given road name
   *
   * @param roadName name of the road to be found
   * @return the road object if found; and null if not found
   * @throws NullPointerException if roadName is null
   */
  @Override
  public Road getRoad(String roadName) {
    if(roadName == null) throw new NullPointerException("Cannot contain null road name");
    if (roads.containsKey(roadName)) {
      return roads.get(roadName);
    } else {
      return null;
    }
  }

  /**
   * Get the Location object by given Location name
   *
   * @param locationName name of the Location to be found
   * @return the Location object if found; and null if not found
   * @throws NullPointerException if locationName is null
   */
  @Override
  public Location getLocation(String locationName) {
    if(locationName == null) throw new NullPointerException("Cannot find location with "
        + "null location name");
    if (locations.containsKey(locationName)) {
      return locations.get(locationName);
    } else {
      return null;
    }
  }

  /**
   * look up the address of a specific location name
   * @param locationName the name of the location
   * @throws NullPointerException if locationName is null
   * @return the address of the location, or empty string if not found the location
   */
  @Override
  public String lookupLocationAddress(String locationName) {
    if (locations.containsKey(locationName)) {
      return locations.get(locationName).locationAddress;
    } else {
      return "";
    }
  }

  /**
   * update the address of a specific location name
   * @param locationName the name of the location
   * @param newAddress the new Address to be changed
   * @return true if successfully update the address of this location; false otherwise
   * @throws NullPointerException if locationName or newAddress is null
   */
  @Override
  public boolean updateLocationAddress(String locationName, String newAddress) {
    if (locationName == null || newAddress == null) throw new NullPointerException("not accept "
        + "null data");
    if (locations.containsKey(locationName)) {
      this.getLocation(locationName).setAddress(newAddress);
      return true;
    } else {
      return false;
    }
  }

  /**
   * update the cost of a specific road name
   * @param roadName the name of the road
   * @param newCost the new cost of the road
   * @return true if successfully update the cost of this road; false otherwise
   * @throws NullPointerException if roadName is null
   * @throws IllegalArgumentException if newCost is smaller than 0
   */
  @Override
  public boolean updateRoadCost(String roadName, double newCost) {
    if (roadName== null) {
      throw new NullPointerException("Not accpet null data");
    }
    if (newCost < 0.0) {
      throw new IllegalArgumentException("can't have negative data");
    }
    if (roads.containsKey(roadName)) {
      roads.get(roadName).setCost(newCost);
      return true;
    } else {
      return false;
    }
  }

  /**
   * update the time of a specific road name
   * @param roadName the name of the road
   * @param newTime the new time of the road
   * @return true if successfully update the time of this road; false otherwise
   * @throws NullPointerException if roadName is null
   * @throws IllegalArgumentException if newTime is smaller than 0
   */
  @Override
  public boolean updateRoadTime(String roadName, double newTime) {
    if (roadName== null) {
      throw new NullPointerException("Not accpet null data");
    }
    if (newTime < 0.0) {
      throw new IllegalArgumentException("can't have negative data");
    }
    if (roads.containsKey(roadName)) {
      roads.get(roadName).setTime(newTime);
      return true;
    } else {
      return false;
    }
  }

  /**
   * update the time of a specific road name
   * @param roadName the name of the road
   * @param newDistance the new distance of the road
   * @return true if successfully update the time of this road; false otherwise
   * @throws NullPointerException if roadName is null
   * @throws IllegalArgumentException if newDistance is smaller than 0
   */
  @Override
  public boolean updateRoadDistance(String roadName, double newDistance) {
    if (roadName== null) {
      throw new NullPointerException("Not accpet null data");
    }
    if (newDistance < 0.0) {
      throw new IllegalArgumentException("can't have negative data");
    }
    if (roads.containsKey(roadName)) {
      roads.get(roadName).setDistance(newDistance);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Uses Dijkstra's shortest path algorithm to find and return the lowest cost, distance or
   * time path between two location in this graph: start and end. This path contains an ordered
   * list of the roads on this path
   *
   * @param start data item within first location in path
   * @param end data item within last location in path
   * @param compType type to be compared for the weight of the road: 'c' for cost, 'd' for distance
   *                't' fot time
   * @return the shortest path from start to end, as computed by Dijkstra's algorithm
   * @throws NoSuchElementException when no path from start to end can be found,
   *     including when no location containing start or end can be found
   */
  protected Path dijkstrasShortestPath(String start, String end, char compType)
      throws NoSuchElementException {
    // if the start or end is null, start or end doesn't have a corresponded location in hashtable,
    // then throw NoSuchElementException
    if (start == null || end == null || !this.locations.containsKey(start) ||
        !this.locations.containsKey(end) || this.locations.get(start)==null
        || this.locations.get(end)==null) {
      throw new NoSuchElementException("No path found");
    }

    // if the start is the end, it means find a path to itself
    if (start == end) {
      return new Path(this.locations.get(start));
    }

    PriorityQueue<Path> frontier = new PriorityQueue<>(); // stored the discovered path
    LinkedList<Location> visited = new LinkedList<>(); // store the locations are already visited
    Hashtable<String, Path> shortestPath = new Hashtable<>();

    Location current = this.locations.get(start);

    while (true) {
      // if the vertex is not on the "visited" LinkList, it means the edge for this vertex are not
      // discovered yet, then store those edges as a path into the frontier
      if (!visited.contains(current)) {
        for (int i = 0; i < current.roadsLeaving.size(); i++) {
          if (current.roadsLeaving.get(i).target != current) {
            Path newPath = new Path(current);
            if (compType == 'c') {
              newPath.weight = current.roadsLeaving.get(i).cost;
            } else if (compType == 'd') {
              newPath.weight = current.roadsLeaving.get(i).distance;
            } else if (compType == 't') {
              newPath.weight = current.roadsLeaving.get(i).time;
            }
            newPath.end = current.roadsLeaving.get(i).target;
            newPath.roadSequence.add(current.roadsLeaving.get(i));
            frontier.add(newPath);
          }
        }
        visited.add(current);
      }

      // the path being poll should have the lowest weight
      Path chosenPath = frontier.poll();
      // if there are no more path can be poll from the frontier, it means we have
      // finishing traverse each edge in the graph
      if (chosenPath == null) {
        break;
      }
      // if the path is going to the start location, we do not need this path actually
      // because we know the lowest cost of going to itself
      if (chosenPath.end.locationName.equals(start)) {
        continue;
      }
      // set the current location to the end of the edge
      current = chosenPath.end;
      // if the shortestPath hashtable contains the source location-path pair, it means we can
      // extend its path, and we can then get the cost from the start to the current vertex.
      if (shortestPath.containsKey(chosenPath.start.locationName)) {
        Road extendBy = new Road(chosenPath.end, chosenPath.roadSequence.get(0).roadName,
            chosenPath.roadSequence.get(0).cost, chosenPath.roadSequence.get(0).time,
            chosenPath.roadSequence.get(0).distance);
        chosenPath = new Path(shortestPath.get(chosenPath.start.locationName),
            extendBy, compType);
      }

      // if the shortestPath hashtable doesn't contain target vertex-path pair, it means
      // the hashtable not found the shortest path for this vertex yet, add path directly
      if (!shortestPath.containsKey(chosenPath.end.locationName)) {
        shortestPath.put(chosenPath.end.locationName, chosenPath);
      } else {
        // otherwise, needed to compare the path's distance in the hashtable to the new discovered
        // path, if the new one has shorter distance, replace it
        double weightOrig = shortestPath.get(chosenPath.end.locationName).weight;
        if (chosenPath.weight < weightOrig) {
          shortestPath.remove(chosenPath.end.locationName);
          shortestPath.put(chosenPath.end.locationName, chosenPath);
        }
      }
    }
    // if the visited node doesn't contain the end point data
    // throw NoSuchElementException
    if (!visited.contains(this.locations.get(end))) {
      throw new NoSuchElementException("No Path Found");
    }
    return shortestPath.get(end);
  }

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
  @Override
  public List<Road> shortestPath(String start, String end, char compType) {
    return dijkstrasShortestPath(start,end,compType).roadSequence;
  }

  /**
   * This method return a string of the list that locations on the graph
   * @return a string of list of locations
   */
  public String printLocations() {
    if (locations.size()==0) {
      return "";
    }
    String output = "";
    for (int i=0; i<26; i++) {
      output += "=";
      if (i==14) {
        output += "Location Name List";
      }
    }
    output += "\n";
    Set<String> locationNames = locations.keySet();
    for(String locationName: locationNames){
      output += locationName + "\n";
    }
    for (int i=0; i<44; i++) {
      output += "=";
    }
    return output;
  }
}
