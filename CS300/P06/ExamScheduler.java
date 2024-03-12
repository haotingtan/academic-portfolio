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
 *  This class has a collection of static recursive
 *  utility methods to help create the Schedule
 */
public class ExamScheduler {

  /**
   * This method finds a valid Schedule for the given set of rooms and courses
   *
   * @param rooms an array contains Room objects
   * @param courses an array contains Course objects
   * @return a valid Schedule for the given set of rooms and courses
   * @throws IllegalStateException with descriptive message if no such schedule exists
   */
  public static Schedule findSchedule(Room[] rooms, Course[] courses)
      throws IllegalStateException {
    try {
      Schedule schedule = new Schedule(rooms, courses);
      return findScheduleHelper(schedule, 0);
    } catch (IllegalStateException e) {
      throw new IllegalStateException("no such schedule exists");
    }
  }

  /**
   * recursive method that assigns all unassigned courses in a
   * Schedule beginning from the index provided as an argument
   *
   * @param schedule provided schedule
   * @param courseIndex given the index of courses' array
   * @return the schedule will valid assignment to each course
   * @throws IllegalStateException with descriptive message if the schedule is invalid
   */
  private static Schedule findScheduleHelper(Schedule schedule, int courseIndex)
      throws IllegalStateException {
    // If the provided index is equal to the number of courses, check to see if the Schedule is
    // complete. If so, return the schedule; otherwise throw an IllegalStateException indicating
    // that this Schedule is invalid.
    if (courseIndex == schedule.getNumCourses()) {
      if (schedule.isComplete()) {
        return schedule;
      } else {
        throw new IllegalStateException(
            "These courses cannot arranged to these classroom properly");
      }
    }

    // If the provided index corresponds to a course that has already been assigned to a room,
    // recursively assign the courses at the following indexes and return the resulting schedule.
    if (schedule.isAssigned(courseIndex)) {
      return findScheduleHelper(schedule, courseIndex + 1);
    } else {

      // If the provided index corresponds to a course that has NOT already been assigned to a
      // room, iteratively try to assign it to each possible valid Room and recursively assign the
      // courses at the following indexes. If this course cannot be assigned to that room, try the
      // next one; return the resulting schedule.
      Schedule newSchedule;
      for (int i = 0; i < schedule.getNumRooms(); ++i) {
        try {
          newSchedule = schedule.assignCourse(courseIndex, i);
          return findScheduleHelper(newSchedule, courseIndex + 1);
        } catch (IllegalArgumentException e1) {
          continue;
        } catch (IndexOutOfBoundsException e2) {
          System.out.println(e2.getMessage());
          break;
        } catch (IllegalStateException e3) {
          continue;
        }
      }
    }
    throw new IllegalStateException("These courses cannot arranged to these classroom properly");
  }


  public static ArrayList<Schedule> findAllSchedules(Room[] rooms, Course[] courses) {
    return findAllSchedulesHelper(new Schedule(rooms, courses), 0);
  }

  private static ArrayList<Schedule> findAllSchedulesHelper(Schedule schedule, int courseIndex) {
    ArrayList<Schedule> schedulesResult = new ArrayList<Schedule>();

    // If the provided index is equal to the number of courses, check to see if the Schedule is
    // complete. If so, add it to an ArrayList of possible schedules and return the ArrayList.
    if (courseIndex == schedule.getNumCourses()) {
      if (schedule.isComplete()) {
        schedulesResult.add(schedule);
        return schedulesResult;
      }
    }

    // If the provided index corresponds to a course that has already been assigned to a room,
    // recursively add all possible valid schedules from the following indexes to an ArrayList of
    // Schedules and return this ArrayList.
    if (schedule.isAssigned(courseIndex)) {
      return findAllSchedulesHelper(schedule, courseIndex + 1);
    } else {

      // If the provided index corresponds to a course that has NOT already been assigned to a
      // room, iteratively try to assign it to each possible valid Room and recursively add all
      // possible valid schedules from the following indexes to an ArrayList of Schedules and
      // return this ArrayList.
      for (int i = 0; i < schedule.getNumRooms(); ++i) {
        try {
          Schedule newSchedule = schedule.assignCourse(courseIndex, i);
          ArrayList<Schedule> derivedSchedules =
              findAllSchedulesHelper(newSchedule, courseIndex + 1);
          for (int j = 0; j < derivedSchedules.size(); ++j) {
            schedulesResult.add(derivedSchedules.get(j));
          }
        } catch (IllegalArgumentException e1) {
          continue;
        } catch (IndexOutOfBoundsException e2) {
          System.out.println(e2.getMessage());
          break;
        }
      }
    }
    return schedulesResult;
  }
}
