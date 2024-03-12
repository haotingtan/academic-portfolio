//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 6: Exam Scheduler - Room class
// Course:   CS 300 Spring 2022
//
// Author:   Haoting Tan
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class is a container for room-related information
 */
public class Room {

  // the building and room number
  private String location;

  // the maximum number of people who can be in the room at a time
  private int capacity;

  /**
   * This is a constructor initializes the data fields to the values of the arguments.
   *
   * @param location location of the room to be set
   * @param capacity the maximum number of people who can be in the room at a time
   * @throws IllegalArgumentException with a descriptive error message if the
   *          provided capacity is negative (<0)
   */
  public Room(String location, int capacity) throws IllegalArgumentException {
    if (capacity < 0) {
      throw new IllegalArgumentException("The maximum number of people can NOT be negative");
    }
    this.location = location;
    this.capacity = capacity;
  }

  /**
   * This is a getter method that returns the location of this room
   *
   * @return the location of this room
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * This is a getter method that returns the capacity of this room
   *
   * @return the capacity of this room
   */
  public int getCapacity() {
    return this.capacity;
  }

  /**
   * returns a NEW Room object with the same location as this one, but
   * with a capacity that reduce the capacity with the given amount passing by the argument.
   *
   * @param reduceAmount the amount of capacity that needed to be reduced
   * @return a new room object with same location as this one, reduce the capacity by given amount
   * @throws IllegalArgumentException with a descriptive error message if the amount to reduce
   *         is greater than the given Room’s capacity
   */
  public Room reduceCapacity(int reduceAmount) throws IllegalArgumentException {
    if (reduceAmount > getCapacity()) {
      throw new IllegalArgumentException("the amount to reduce is "
          + "greater than the original room's capacity");
    }
    Room newRoom = new Room(this.getLocation(), this.getCapacity()-reduceAmount);
    return newRoom;
  }
}
