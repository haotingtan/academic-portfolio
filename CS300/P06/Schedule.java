//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 6: Exam Scheduler - Schedule class
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
 * This class is about an object to maintain the current draft schedule
 */
public class Schedule {

  // an array of the Room objects available for exams
  private Room[] rooms;

  // an array of the Course objects which require exam rooms
  private Course[] courses;

  // an array where the integer at index N is the index of the
  // room that course[N] has been assigned to
  private int[] assignments;

  /**
   * This is a constructor initializes the data fields to the values of the arguments,
   * and creates an assignments array of the correct length where all values are -1,
   * indicating that the corresponding course has not yet been assigned a room.
   *
   * @param rooms an array of the Room objects to be set
   * @param courses an array of the Course objects to be set
   */
  public Schedule(Room[] rooms, Course[] courses) {
    this.rooms = rooms;
    this.courses = courses;
    this.assignments = new int[this.courses.length];
    for (int i=0; i<this.assignments.length; i++) {
      this.assignments[i] = -1;
    }
  }

  /**
   * This is a private constructor Initializes the rooms and courses arrays to the
   * provided values and assignments to the provided assignments array.
   *
   * @param rooms an array of the Room objects to be set
   * @param courses an array of the Course objects to be set
   * @param assignments an array where the integer at index N is the index of the room that
   *                    course[N] has been assigned to
   */
  private Schedule(Room[] rooms, Course[] courses, int[] assignments) {
    this.rooms = rooms;
    this.courses = courses;
    this.assignments = assignments;
  }

  /**
   * This is a getter method that returns the number of rooms available in this schedule
   *
   * @return the number of rooms available in this schedule
   */
  public int getNumRooms() {
    return this.rooms.length;
  }

  /**
   * This method returns the Room object at the given index in the rooms array
   *
   * @param index the index that given to find that room at this index
   * @return Room object at the given index in the rooms array
   * @throws IndexOutOfBoundsException with descriptive message if the given index is invalid
   */
  public Room getRoom(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= getNumRooms()) {
      throw new IndexOutOfBoundsException("the given index is invalid");
    }
    return this.rooms[index];
  }

  /**
   * This is a getter method that returns the number of courses available in this schedule
   *
   * @return the number of courses available in this schedule
   */
  public int getNumCourses() {
    return this.courses.length;
  }

  /**
   * This method returns the Course object at the given index in the courses array
   *
   * @param index the index that given to find that course at this index
   * @return Course object at the given index in the courses array
   * @throws IndexOutOfBoundsException with descriptive message if the given index is invalid
   */
  public Course getCourse(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= this.courses.length) {
      throw new IndexOutOfBoundsException("the given index is invalid");
    }
    return this.courses[index];
  }

  /**
   * This method checks if the course at given index has been assigned or not
   *
   * @param index the index that given to find that course's assignment state at this index
   * @return true if and only if the course at the given index has been assigned a room;
   *         false otherwise.
   */
  public boolean isAssigned(int index) {
    if (this.assignments[index] == -1) {
      return false;
    }
    return true;
  }

  /**
   * This method returns the Room object assigned to the course at the given index
   *
   * @param courseIndex the given course index
   * @return the Room object assigned to the course at the given index
   * @throws IllegalArgumentException with descriptive message if the course
   *                                  has not been assigned a room
   * @throws IndexOutOfBoundsException with descriptive message if the given
   *                                   course index is invalid
   */
  public Room getAssignment(int courseIndex)
      throws IllegalArgumentException, IndexOutOfBoundsException{
    if (courseIndex < 0 || courseIndex >= getNumCourses()) {
      throw new IndexOutOfBoundsException("the given index is invalid");
    }
    if (!isAssigned(courseIndex)) {
      throw new IllegalArgumentException("the course at the given index "
          + "has NOT been assigned a room");
    }
    return this.rooms[this.assignments[courseIndex]];
  }

  /**
   * This method checks if every course in this schedule has been assigned to a room
   *
   * @return true if and only if all courses have been assigned to rooms; false otherwise.
   */
  public boolean isComplete() {
    for (int i = 0; i < this.assignments.length; i++) {
      if (this.assignments[i] == -1) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method returns a new Schedule object with assigning the course at the given index
   * to the room at the given index.
   *
   * @param courseIndex the given course index
   * @param roomIndex the given room index
   * @return a new Schedule object with assigning the course at the given index
   *         to the room at the given index.
   * @throws IndexOutOfBoundsException with descriptive message if the given
   *                                   course or room index is invalid
   * @throws IllegalArgumentException with descriptive message if the given course has already
   *                                   been assigned a room, or if the room does not have
   *                                   sufficient capacity.
   */
  public Schedule assignCourse(int courseIndex, int roomIndex)
      throws IndexOutOfBoundsException, IllegalArgumentException{
    if (courseIndex < 0 || courseIndex >= getNumCourses()
        || roomIndex < 0 || roomIndex >= getNumRooms()) {
      throw new IndexOutOfBoundsException("the given course or room index is invalid");
    }
    if (isAssigned(courseIndex)
        || getRoom(roomIndex).getCapacity() < getCourse(courseIndex).getNumStudents()) {
      throw new IllegalArgumentException("the given course has already been "
          + "assigned a room, or if the room does not have sufficient capacity");
    }

    Room[] deepCopyRooms = new Room[this.rooms.length];
    for (int i=0; i<this.rooms.length; i++) {
      deepCopyRooms[i] = this.getRoom(i);
    }
    int[] deepCopyAssignments = new int[this.assignments.length];
    for (int i=0; i<this.assignments.length; i++) {
      deepCopyAssignments[i] = this.assignments[i];
    }

    deepCopyAssignments[courseIndex] = roomIndex;
    int reduceAmount = this.courses[courseIndex].getNumStudents();
    deepCopyRooms[roomIndex] = deepCopyRooms[roomIndex].reduceCapacity(reduceAmount);

    Schedule newSchedule = new Schedule(deepCopyRooms, this.courses, deepCopyAssignments);
    return newSchedule;
  }

  /**
   * This method create a String representation that shows every course's
   * assignment state in this Schedule
   *
   * @return a String representation shows this Schedule's assignment for every course
   */
  @Override
  public String toString() {
    String result = "{";
    for (int i = 0; i < this.assignments.length; ++i) {
      result += (courses[i].getName() + ": "
          + (assignments[i] != -1 ? this.rooms[assignments[i]].getLocation() : "Unassigned")
          + (i != this.assignments.length - 1 ? ", " : ""));
    }
    result += "}";
    return result;
  }
}
