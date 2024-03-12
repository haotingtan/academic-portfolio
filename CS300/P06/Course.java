//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 6: Exam Scheduler - Course class
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
 * This is a class for course-related information
 */
public class Course {

  // the name of the course
  private String name;

  // the number of students enrolled in the course
  private int numStudents;

  /**
   * This is a constructor initializes the data fields to the values of the arguments.
   *
   * @param name name of the course to be set
   * @param numStudents number of the student in this course
   * @throws IllegalArgumentException with a descriptive error message if the
   *          provided numStudents is negative (<0)
   */
  public Course(String name, int numStudents) throws IllegalArgumentException {
    if (numStudents < 0) {
      throw new IllegalArgumentException("The number of students can NOT be negative");
    }
    this.name = name;
    this.numStudents = numStudents;
  }

  /**
   * This is a getter method that returns the name of this course
   *
   * @return the name of this course
   */
  public String getName() {
    return this.name;
  }

  /**
   * This is a getter method that returns the number of students enrolled in this course
   *
   * @return the number of students enrolled in this course
   */
  public int getNumStudents() {
    return this.numStudents;
  }
}
