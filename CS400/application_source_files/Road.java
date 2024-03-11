// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Email: htan47@wisc.edu
// Notes to Grader: None

/**
 * Road objects are stored within their source location, and group together
 * their target destination location, along with a double weight, double
 * time, double distance.
 */
public class Road {
  public Location target;
  public String roadName;
  public double cost; // in dollars
  public double time; // in form of minutes
  public double distance; // in kilometers

  /**
   * initilize a new directed road with a positive cost, time, and distance into the graph.
   *
   * @param target the location name contained in the target location for the road
   * @param cost the cost of the road (has to be a non-negative double)
   * @param time the time for passing the road (has to be a positive double)
   * @param distance the distance of the road (has to be a positive double)
   */
  public Road(Location target, String roadName, double cost, double time, double distance) {
    this.target = target;
    this.roadName = roadName;
    this.cost = cost;
    this.time = time;
    this.distance = distance;
  }

  /**
   * changed the road's cost
   * @param newCost new cost to changed
   */
  public void setCost(double newCost) {
    this.cost = newCost;
  }

  /**
   * changed the road's time
   * @param newTime new time to changed
   */
  public void setTime(double newTime) {
    this.time = newTime;
  }

  /**
   * changed the road's distance
   * @param newDistance new distance to changed
   */
  public void setDistance(double newDistance) {
    this.distance = newDistance;
  }
}
