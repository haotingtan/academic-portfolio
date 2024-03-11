// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Email: htan47@wisc.edu
// Notes to Grader: None

import java.util.LinkedList;
import java.util.List;

/**
 * Path objects store a discovered path of locations and the overall cost, time, and distance
 * of the weighted directed edges along this path. Path objects can be copied and extended
 * to include new roads and locations using the extend constructor. In comparison to a
 * predecessor table which is sometimes used to implement Dijkstra's algorithm, this
 * eliminates the need for tracing paths backwards from the destination location to the
 * starting vertex at the end of the algorithm.
 */
public class Path implements Comparable<Path> {
  public Location start; // first vertex within path
  public double weight; // sumed weight of all edges in path
  public List<Road> roadSequence; // ordered sequence of data from roads in path
  public Location end; // last vertex within path

  /**
   * Creates a new path containing a single vertex.  Since this vertex is both
   * the start and end of the path, it's initial distance is zero.
   * @param start is the first vertex on this path
   */
  public Path(Location start) {
    this.start = start;
    this.weight = 0.0;
    this.roadSequence = new LinkedList<>();
    this.end = start;
  }

  /**
   * This extension constructor makes a copy of the path passed into it as an argument
   * without affecting the original path object (copyPath). The path is then extended
   * by the Road object extendBy.
   * @param copyPath is the path that is being copied
   * @param extendBy is the road the copied path is extended by
   * @param compType cost, time, or distance to be added
   */
  public Path(Path copyPath, Road extendBy, char compType) {
    this.start = copyPath.start;
    if (compType=='c') {
      this.weight = copyPath.weight + extendBy.cost;
    } else if (compType=='t') {
      this.weight = copyPath.weight + extendBy.time;
    } else {
      this.weight = copyPath.weight + extendBy.distance;
    }
    this.roadSequence = new LinkedList<>();
    for (int i = 0; i<copyPath.roadSequence.size(); i++) {
      this.roadSequence.add(copyPath.roadSequence.get(i));
    }
    this.roadSequence.add(extendBy);
    this.end = extendBy.target;
  }

  /**
   * Allows the natural ordering of paths to be increasing with path weight.
   * When path weight is equal, the string comparison of end location data is used to break ties.
   * @param other is the other path that is being compared to this one
   * @return -1 when this path has a smaller weight than the other,
   *         +1 when this path has a larger weight that the other,
   *         and the comparison of end location data in string form when these weights are tied
   */
  @Override
  public int compareTo(Path other) {
    if (this.weight - other.weight>0.0){
      return 1;
    } else if (this.weight - other.weight<0.0){
      return -1;
    }
    // when path weights are equal, break ties by comparing the string
    // representation of data in the end location of each path
    return this.end.locationName.compareTo(other.end.locationName);
  }
}
