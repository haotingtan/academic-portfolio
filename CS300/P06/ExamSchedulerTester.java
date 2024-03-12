//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 6: Exam Scheduler - ExamScheduler class
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
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * This contains testing methods for the Course, Room, Schedule, and ExamScheduler classes.
 */
public class ExamSchedulerTester {

  /**
   * This method verifies that the constructor and methods in Course.java work properly
   * and any relevant exceptions are thrown.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testCourse() {
    // 1. Checks if the constructor throws IllegalArgumentException if numStudents <0
    {
      try {
        Course testCourse = new Course("CS 300", -20);
        System.out.println("Problem detected: Your Course class' constructor "
            + "does NOT throw exception when passing numStudents <0.");
        return false;
      } catch (IllegalArgumentException e1) {

      } catch (Exception e2) {
        System.out.println("Problem detected: Your Course class' constructor "
            + "throws wrong exception when passing numStudents <0.");
        return false;
      }
    }

    // 2. Checks if the constructor works if numStudents = 0
    {
      try {
        Course testCourse = new Course("CS 300", 0);
      } catch (Exception e) {
        System.out.println("Problem detected: Your Course class' constructor "
            + "throws unexpected exception when passing numStudents =0.");
        return false;
      }
    }

    // 3. Check if the constructor correctly setup the data field and the correctness of
    // getName() and getNumStudents() method.
    {
      Course testCourse;
      try {
        testCourse = new Course("CS 300", 200);
      } catch (Exception e) {
        System.out.println("Problem detected: Your Course class' constructor "
            + "throws unexpected exception when passing valid numStudents.");
        return false;
      }
      String expectedName = "CS 300";
      int expectedNumStudents = 200;
      if (!testCourse.getName().equals(expectedName)) {
        System.out.println("Problem detected: check Course class' constructor or "
            + "getName() method, the result course name does NOT match the expected name");
        return false;
      }
      if (testCourse.getNumStudents() != expectedNumStudents) {
        System.out.println("Problem detected: check Course class' constructor or "
            + "getNumStudents() method, the result course number of students does "
            + "NOT match the expected number");
        return false;
      }
    }

    return true; // No bugs detected in Course class. Course.java passes this tester.
  }

  /**
   * This method verifies that the constructor and methods in Room.java work properly
   * and any relevant exceptions are thrown.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testRoom() {
    // 1. Checks if the constructor throws IllegalArgumentException if capacity <0
    {
      try {
        Room testRoom = new Room("CHAD 1111", -20);
        System.out.println("Problem detected: Your Room class' constructor "
            + "does NOT throw exception when passing capacity <0.");
        return false;
      } catch (IllegalArgumentException e1) {

      } catch (Exception e2) {
        System.out.println("Problem detected: Your Room class' constructor "
            + "throws wrong exception when passing capacity <0.");
        return false;
      }
    }

    // 2. Checks if the constructor works if capacity = 0
    {
      try {
        Room testRoom = new Room("HUM 3650", 0);
      } catch (Exception e) {
        System.out.println("Problem detected: Your Room class' constructor "
            + "throws unexpected exception when passing capacity =0.");
        return false;
      }
    }

    // 3. Check if the constructor correctly setup the data field and the correctness of
    // getLocation() and getCapacity() method.
    Room testRoom;
    try {
      testRoom = new Room("CS 1650", 100);
    } catch (Exception e) {
      System.out.println("Problem detected: Your Room class' constructor "
          + "throws unexpected exception when passing valid capacity.");
      return false;
    }
    {
      String expectedLocation = "CS 1650";
      int expectedCapacity = 100;
      if (!testRoom.getLocation().equals(expectedLocation)) {
        System.out.println("Problem detected: check Room class' constructor or "
            + "getLocation() method, the result location name does NOT match the expected name");
        return false;
      }
      if (testRoom.getCapacity() != expectedCapacity) {
        System.out.println("Problem detected: check Room class' constructor or "
            + "getCapacity() method, the result capacity does "
            + "NOT match the expected number");
        return false;
      }
    }

    // 4. Checks if the reduceCapacity() method throws IllegalArgumentException when the
    // argument is greater than the given Room's capacity
    try {
      testRoom.reduceCapacity(200);
      System.out.println("Problem detected: Your Room class' reduceCapacity() method "
          + "does NOT throw exception when passing reduce amount > capacity.");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your Room class' reduceCapacity() method "
          + "throws the WRONG exception when passing reduce amount > capacity.");
      return false;
    }

    // 5. Checks if the reduceCapacity() method works when passing valid argument
    try {
      Room testNewRoom1 = testRoom.reduceCapacity(100); // argument = capacity
      Room testNewRoom2 = testRoom.reduceCapacity(50); // argument < capacity
      if (testNewRoom1.getCapacity() != (100-100) || testNewRoom2.getCapacity() != (100-50)) {
        System.out.println("Problem detected: Your Room class' reduceCapacity() method "
            + "fails to return the new Room object with correct capacity");
        return false;
      }
    }catch (Exception e) {
      System.out.println("Problem detected: Your Room class' reduceCapacity() method "
          + "throws the unexpected exception when passing valid argument.");
      return false;
    }

    return true; // No bugs detected in Course class. Course.java passes this tester.
  }

  /**
   * This method verifies that the constructor and accessors in Schedule.java work properly
   * and any relevant exceptions are thrown.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testScheduleAccessors() {

    // 1. checks if the constructor setup data fields correctly and correctness of
    // getNumRooms() and getNumCourses() method
    Room room1 = new Room("HUM 3650", 300);
    Room room2 = new Room("Van Vleck B220", 150);
    Room room3 = new Room("CS 1530", 50);
    Room[] testRooms = new Room[] {room1, room2, room3};
    Course course1 = new Course("MATH 222", 120);
    Course course2 = new Course("PHY 109", 50);
    Course course3 = new Course("GEOG 100", 20);
    Course course4 = new Course("ECON 101", 150);
    Course[] testCourses = new Course[] {course1, course2, course3, course4};

    Schedule testSchedule = new Schedule(testRooms, testCourses);
    if (testSchedule.getNumRooms() != 3 || testSchedule.getNumCourses() != 4) {
      System.out.println("Problem detected: Your Schedule class' constructor, "
          + "getNumRooms(), or getNumCourses() method works improperly, the number "
          + "of rooms or the number of courses does Not match expected");
      return false;
    }

    // 2. checks getRoom() and getCourse() method return expected object when passing valid index
    try {
      if (!testSchedule.getRoom(2).equals(room3)
          || !testSchedule.getCourse(2).equals(course3)) {
        System.out.println("Problem detected: Your Schedule class' getRoom() or getCourse() "
            + "method fails to return the expected object");
        return false;
      }
      testSchedule.getRoom(0);
      testSchedule.getCourse(0);
    } catch (Exception e) {
      System.out.println("Problem detected: Your Schedule class' getRoom() or getCourse() "
          + "method throws unexpected exception when passing valid index");
      return false;
    }

    // 3. checks getRoom() and getCourse() method throws IndexOutOfBoundsException
    // when passing invalid index
    try {
      testSchedule.getRoom(-5);
      System.out.println("Problem detected: Your Schedule class' getRoom() "
          + "method does NOT throw exception when passing invalid index");
      return false;
    } catch (IndexOutOfBoundsException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your Schedule class' getRoom() "
          + "method throws WRONG exception when passing invalid index");
      return false;
    }
    try {
      testSchedule.getCourse(-1);
      System.out.println("Problem detected: Your Schedule class' getCourse() "
          + "method does NOT throw exception when passing invalid index");
      return false;
    } catch (IndexOutOfBoundsException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your Schedule class' getCourse() "
          + "method throws WRONG exception when passing invalid index");
      return false;
    }

    // 4. checks the correctness of the assignment array and correctness of isAssigned()
    int count = 0;
    for (int i = 0; i < testCourses.length; i++) {
      if (testSchedule.isAssigned(i)) {
        System.out.println("Problem detected: Your Schedule class "
            + "should set all assignments' content to -1 at the beginning");
        return false;
      }
      count++;
    }
    if (count != testSchedule.getNumCourses()) {
      System.out.println("Problem detected: Your Schedule class' assignment array length"
          + "should be equal to the courses array length");
      return false;
    }

    // 5. Checks if getAssignment() throws IndexOutOfBoundsException when passing invalid index
    try {
      testSchedule.getAssignment(-1);
      testSchedule.getAssignment(5);
      System.out.println("Problem detected: Your Schedule class' getAssignment() "
          + "does NOT throws exception when passing invalid index");
      return false;
    } catch (IndexOutOfBoundsException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your Schedule class' getAssignment() "
          + "throws the wrong exception when passing invalid index");
      return false;
    }

    // 6. Checks if getAssignment() throws IllegalArgumentException when the course at the
    // given index has not been assigned
    try {
      for (int i=0; i<testSchedule.getNumCourses(); i++) {
        testSchedule.getAssignment(i);
      }
      System.out.println("Problem detected: Your Schedule class' getAssignment() "
          + "does NOT throw exception when the course at the given has not been "
          + "assigned to a room");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your Schedule class' getAssignment() "
          + "throws the wrong exception when the course at the given has not been "
          + "assigned to a room");
      return false;
    }

    // 7. Checks if the isComplete() return false if no assignment to any courses
    if (testSchedule.isComplete()) {
      System.out.println("Problem detected: Your Schedule class' isComplete() "
          + "returns true when no assignment to any courses");
      return false;
    }

    // 8. checks the correctness of toString()
    if (!testSchedule.toString().equals("{MATH 222: Unassigned, PHY 109: "
        + "Unassigned, GEOG 100: Unassigned, ECON 101: Unassigned}")) {
      System.out.println("Problem detected: Your Schedule class' toString() "
          + "returns the unexpected string when no assignment to any courses");
      return false;
    }

    return true; // no bugs detected in Schedule.java constructor and accessors
  }

  /**
   * This method verifies that the AssignCourse() in Schedule.java work properly
   * and any relevant exceptions are thrown.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testAssignCourse() {
    Room room1 = new Room("HUM 3650", 300);
    Room room2 = new Room("Van Vleck B220", 150);
    Room room3 = new Room("CS 1530", 50);
    Room[] testRooms = new Room[] {room1, room2, room3};
    Course course1 = new Course("MATH 222", 120);
    Course course2 = new Course("PHY 109", 50);
    Course course3 = new Course("GEOG 100", 20);
    Course course4 = new Course("ECON 101", 150);
    Course[] testCourses = new Course[] {course1, course2, course3, course4};

    Schedule testSchedule = new Schedule(testRooms, testCourses);

    // 1. Checks if assignCourse() throws IndexOutOfBoundsException if provided index is invalid
    try {
      testSchedule.assignCourse(-5, -5);
      testSchedule.assignCourse(0,-5);
      testSchedule.assignCourse(-5,0);
      testSchedule.assignCourse(1,4);
      testSchedule.assignCourse(3,1);
      System.out.println("Problem detected: Your Schedule class' assignCourse() "
          + "does NOT throws exception when passing invalid index");
      return false;
    } catch (IndexOutOfBoundsException e1) {

    } catch (Exception e) {
      System.out.println("Problem detected: Your Schedule class' assignCourse() "
          + "throws the wrong exception when passing invalid index");
      return false;
    }

    // 2. Checks if assignCourse() throws IllegalArgumentException() properly
    try {
      testSchedule.assignCourse(0,2);
      System.out.println("Problem detected: Your Schedule class' assignCourse() "
          + "does NOT throws exception when the course's number of student excess "
          + "the room's capacity");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e) {
      System.out.println("Problem detected: Your Schedule class' assignCourse() "
          + "throws WRONG exception when the course's number of student excess "
          + "the room's capacity");
      return false;
    }

    // 3. checks if assignCourse() can assign courses properly
    try {
      Schedule testSchedule1 = testSchedule.assignCourse(0,0);
      Schedule testSchedule2 = testSchedule1.assignCourse(1,2);
      for (int i=0; i<testSchedule.getNumRooms(); i++) {
        if (!testSchedule.getRoom(i).equals(testRooms[i])) {
          System.out.println("Problem detected: Your Schedule class' assignCourse() "
              + "modifies the Room object(s) in the Room array");
          return false;
        }
      }
      for (int i=0; i<testSchedule.getNumCourses(); i++) {
        if (!testSchedule.getCourse(i).equals(testCourses[i])) {
          System.out.println("Problem detected: Your Schedule class' assignCourse() "
              + "modifies the Course object(s) in the Course array");
          return false;
        }
      }
      for (int i=0; i<testSchedule.getNumCourses(); i++) {
        if (testSchedule.isAssigned(i)) {
          System.out.println("Problem detected: Your Schedule class' assignCourse() "
              + "modifies the assignment array");
          return false;
        }
      }
      if (!testSchedule1.getAssignment(0).getLocation().equals("HUM 3650")
          || testSchedule1.getAssignment(0).getCapacity() != 180) {
        System.out.println("Problem detected: Your Schedule class' assignCourse() "
            + "does NOT return a new schedule with correct assignment");
        return false;
      }
      for (int i=1; i<testSchedule1.getNumCourses(); i++) {
        if (testSchedule1.isComplete()) {
          System.out.println("Problem detected: Your Schedule class' assignCourse() "
              + "returns a new schedule with wrong assignment");
          return false;
        }
      }
      if (!testSchedule2.getAssignment(0).getLocation().equals("HUM 3650")
          || testSchedule2.getAssignment(0).getCapacity() != 180 ||
          !testSchedule2.getAssignment(1).getLocation().equals("CS 1530")
          || testSchedule2.getAssignment(1).getCapacity() != 0) {
        System.out.println("Problem detected: Your Schedule class' assignCourse() "
            + "does NOT return a new schedule with correct assignment");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your Schedule class' assignCourse() "
          + "throws unexpected exception when properly running the method");
      return false;
    }

    return true; // no bugs detected; Schedule.assignCourse() passes this test
  }

  /**
   * This method verifies that the findSchedule() in ExamScheduler.java work properly
   * and any relevant exceptions are thrown.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testFindAllSchedules() {
    // 1. Checks if findSchedule() throws IllegalStateException if no such schedule exists
    {
      Room room1 = new Room("ROOM1", 49);
      Room room2 = new Room("ROOM2", 100);
      Room[] testRooms = new Room[] {room1, room2};
      Course course1 = new Course("MATH 222", 50);
      Course course2 = new Course("PHY 109", 110);
      Course[] testCourses = new Course[] {course1, course2};
      try {
        ExamScheduler.findSchedule(testRooms, testCourses);
        System.out.println("Problem detected: Your ExamScheduler class' "
            + "findSchedule() does NOT throw exception when no such schedule exists");
        return false;
      } catch (IllegalStateException e1) {

      } catch (Exception e2) {
        System.out.println("Problem detected: Your ExamScheduler class' "
            + "findSchedule() throws the WRONG exception when no such schedule exists");
        return false;
      }
    }

    // 2. Checks if findSchedule() works properly to create a valid schedule
    // if every input is valid
    {
      Room room1 = new Room("ROOM1", 100);
      Room room2 = new Room("ROOM2", 150);
      Room room3 = new Room("ROOM3", 75);
      Room[] testRooms = new Room[] {room1, room2, room3};
      Course course1 = new Course("MATH 222", 50);
      Course course2 = new Course("PHY 109", 110);
      Course course3 = new Course("GEOG 100", 75);
      Course[] testCourses = new Course[] {course1, course2, course3};

      try {
        Schedule newSchedule = ExamScheduler.findSchedule(testRooms, testCourses);

        for (int i = 0; i < newSchedule.getNumCourses(); i++) {
          if (!newSchedule.isAssigned(i)) {
            System.out.println("Problem detected: Your ExamScheduler class' findSchedule() "
                + "showing the schedule that not every course has been assigned although it "
                + "should");
            return false;
          }
        }

        if (!newSchedule.isComplete()) {
          System.out.println("Problem detected: Your ExamScheduler class' findSchedule() "
              + "showing the schedule is incomplete although it is actually completed");
          return false;
        }

        if (newSchedule.getRoom(0).getCapacity() != 100 - 50
            || newSchedule.getRoom(1).getCapacity() != 150 - 110
            || newSchedule.getRoom(2).getCapacity() != 75 - 75) {
          System.out.println("Problem detected: Your ExamScheduler class' findSchedule() "
              + "showing the schedule does not have correct corresponding room's capacity");
          return false;
        }

        String expected = "{MATH 222: ROOM1, PHY 109: ROOM2, GEOG 100: ROOM3}";
        if (!newSchedule.toString().equals(expected)) {
          System.out.println("Problem detected: Your ExamScheduler class' findSchedule() "
              + "showing the schedule's String does not match the expected string");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your ExamScheduler class' findSchedule() "
            + "throws unexpected exception when properly running the method");
        return false;
      }
    }

    return true; // no bugs detected in findSchedule(), findSchedule() method passes this test
  }

  /**
   * This method verifies that the findSchedule() in ExamScheduler.java work properly
   * and any relevant exceptions are thrown.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testFindSchedule() {
    // 1. Checks if findAllSchedules() return null arrayList if none of the schedule can be
    // created
    {
      Room room1 = new Room("ROOM1", 49);
      Room room2 = new Room("ROOM2", 110);
      Room[] testRooms = new Room[] {room1, room2};
      Course course1 = new Course("MATH 222", 50);
      Course course2 = new Course("PHY 109", 110);
      Course[] testCourses = new Course[] {course1, course2};
      ArrayList<Schedule> schedules = ExamScheduler.findAllSchedules(testRooms, testCourses);
      if (schedules.size() != 0) {
        System.out.println("Problem detected: Your ExamScheduler class' "
            + "findSchedule() does NOT return null arrayList if non of the "
            + "schedule can be created");
        return false;
      }
    }

    // 2. Checks if findAllSchedules() works properly to create valid schedules
    // if every input is valid
    {
      Room room1 = new Room("ROOM1", 100);
      Room room2 = new Room("ROOM2", 150);
      Room room3 = new Room("ROOM3", 75);
      Room[] testRooms = new Room[] {room1, room2, room3};
      Course course1 = new Course("MATH 222", 50);
      Course course2 = new Course("PHY 109", 110);
      Course course3 = new Course("GEOG 100", 75);
      Course[] testCourses = new Course[] {course1, course2, course3};

      ArrayList<Schedule> schedules = ExamScheduler.findAllSchedules(testRooms, testCourses);
      if (schedules.size() != 2) {
        System.out.println("Problem detected: Your ExamScheduler class' "
            + "findAllSchedules() does NOT have expected arrayList length "
            + "when having valid input");
        return false;
      }

      Schedule possibleSchedule1 = new Schedule(testRooms, testCourses);
      possibleSchedule1.assignCourse(0,0);
      possibleSchedule1.assignCourse(1,1);
      possibleSchedule1.assignCourse(2,2);
      Schedule possibleSchedule2 = new Schedule(testRooms, testCourses);
      possibleSchedule2.assignCourse(0,2);
      possibleSchedule2.assignCourse(1,1);
      possibleSchedule2.assignCourse(2,0);
      boolean resultSchedule = false;
      if (schedules.get(0).toString().equals(possibleSchedule1.toString())
          && schedules.get(1).toString().equals(possibleSchedule2.toString())) {
        resultSchedule = true;
      }
      if (schedules.get(0).toString().equals(possibleSchedule2.toString())
          && schedules.get(1).toString().equals(possibleSchedule1.toString())) {
        resultSchedule = true;
      }
      if (!resultSchedule) {
        System.out.println("Problem detected: Your ExamScheduler class' "
            + "findAllSchedules() does NOT return expected Schedules contained "
            + "in the ArrayList");
        return false;
      }
    }
    return true; // no bugs detected. findAllSchedules() passes this test.
  }

  /**
   * Main method to run all the tester
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("testCourse: " + testCourse());
    System.out.println("testRoom: " + testRoom());
    System.out.println("testScheduleAccessors: " + testScheduleAccessors());
    System.out.println("testAssignCourse: " + testAssignCourse());
    System.out.println("testFindAllSchedules: " + testFindAllSchedules());
    System.out.println("testFindSchedule: " + testFindSchedule());
  }
}
