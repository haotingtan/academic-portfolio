//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 04: Access Control System - User class
// Course:   CS 300 Spring 2022
//
// Author:   Haoting Tan
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// NONE
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This is a class about some properties of a user.
 */
public class User {

  private final String USERNAME; // The name of the user
  private String password; // The password of the user
  private boolean isAdmin; // Whether or not the user has Admin powers

  /**
   * This is a constructor that creates a new user with the
   * given username, password, and admin status
   *
   * @param username the username of the new user
   * @param password the password of the new user
   * @param isAdmin the admin status of the new user
   */
  public User(String username, String password, boolean isAdmin) {
    this.USERNAME = username;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  /**
   * This method reports whether the password is correct for this particular user
   *
   * @param password the given password string to be tested if
   *                 it matches the user's actual correct password
   * @reture true when the given password string matches the user's
   *         correct password, and false otherwise
   */
  public boolean isValidLogin(String password) {
    if (password.equals(this.password)) {
      return true;
    }
    return false;
  }

  /**
   * This is an accessor that access the name of the user.
   *
   * @return the name of the user
   */
  public String getUsername() {
    return this.USERNAME;
  }

  /**
   * This is an accessor that access the user's admin status,
   * return true if the user is an admin, and false otherwise
   *
   * @return return true if the user is an admin, and false otherwise.
   */
  public boolean getIsAdmin() {
    return this.isAdmin;
  }

  /**
   * This is a mutator that set the user with a new given password
   *
   * @param password the new password that set to the user
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * This is a mutator that set the user with a new given admin status
   *
   * @param isAdmin the new admin status that set to the user
   */
  public void setIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
}
