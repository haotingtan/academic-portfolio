//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 04: Access Control System - AccessControl class
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
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class represents a single terminal (computer), simulate a logging in & out system
 */
public class AccessControl {

  // An ArrayList of valid users
  private static ArrayList<User> users;

  // the current user who is using Access Control System
  // if there is one
  private User currentUser;

  // Default password given to new users or when
  // we reset a password of a specific user.
  private static final String DEFAULT_PASSWORD = "changeme";

  /**
   * This is a no-argument constructor that a new AccessControl object
   * if the Arraylist of users is null.
   */
  public AccessControl() {
    if (users == null) {
      users = new ArrayList<>() {};
      users.add(new User("admin", "root", true));

    }
    this.currentUser = null;
  }

  /**
   * This method find the username/password pair matches any user in the users ArrayList
   *
   * @param username given username that to be found
   * @param password given password that to be found
   * @return true if the username/password pair matches any user in the users ArrayList
   *         and false otherwise.
   */
  public static boolean isValidLogin(String username, String password) {
    for (int i = 0; i < users.size(); i++) {
      if (username.equals(users.get(i).getUsername())
               && users.get(i).isValidLogin(password)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method Logs out the current user
   */
  public void logout() {
    this.currentUser = null;
  }

  /**
   * This method changes the current password of the current user
   *
   * @param newPassword The new password that desired to be set to the current user
   */
  public void changePassword(String newPassword) {
    if (this.currentUser != null) {
      this.currentUser.setPassword(newPassword);
    }
  }

  /**
   * This method sets the current user to the user from the users list whose
   * username matches the string provided as input to the method
   *
   * @param username username to be set to current user
   */
  public void setCurrentUser(String username) {
    for (int i=0; i<users.size(); i++){
      if (users.get(i).getUsername().equals(username)) {
        this.currentUser = users.get(i);
      }
    }
  }

  /**
   * This method creates a new user with the default password and isAdmin==false
   * and add it to the users ArrayList
   *
   * @param username given new username that needed to be added into the user arraylist
   * @return true if the current user has Admin power and the new user was successfully added.
   *         Return false if the the current user is null or does not have Admin power
   * @throws IllegalArgumentException with a descriptive error message if  username is
   *                                  null or if its length is less than 5 ( < 5), or if
   *                                  a user with the same username is already in the list of user
   */
  public boolean addUser(String username) throws IllegalArgumentException {
    if (username == null || username.length() < 5 ) {
      throw new IllegalArgumentException("the username to "
          + "be added should NOT be null. Or/And the length "
          + "should NOT be less than 5");
    }
    for (int i=0; i<users.size(); i++) {
      if (username.equals(users.get(i).getUsername())) {
        throw new IllegalArgumentException("username is already"
            + " in the list of users");
      }
    }

    if (this.currentUser == null || !this.currentUser.getIsAdmin()) {
      return false;
    }

    User user = new User(username, DEFAULT_PASSWORD, false);
    users.add(user);
    return true;
  }

  /**
   * This method creates a new user, specify their admin status,
   * and add it to the list of users.
   *
   * @param username given new username that needed to be added into the user arraylist
   * @param isAdmin given the admin status that for this new user
   * @return true if the current user has Admin power and the new user was successfully added.
   *        Return false if the the current user is null or does not have Admin power
   * @throws IllegalArgumentException with a descriptive error message if username is
   *                                  null or if its length is less than 5 ( < 5), or if
   *                                  a user with the same username is already in the list of user
   */
  public boolean addUser(String username, boolean isAdmin) throws IllegalArgumentException {
    if (username == null || username.length() < 5 ) {
      throw new IllegalArgumentException("the username to "
          + "be added should NOT be null. And the length "
          + "should NOT be less than 5");
    }
    for (int i=0; i<users.size(); i++) {
      if (username.equals(users.get(i).getUsername())) {
        throw new IllegalArgumentException("username is already"
            + " in the list of users");
      }
    }

    if (this.currentUser == null || !this.currentUser.getIsAdmin()) {
      return false;
    }
    User user = new User(username, DEFAULT_PASSWORD, isAdmin);
    users.add(user);
    return true;
  }

  /**
   * This method removes a user given their unique username
   *
   * @param username username that the user needed to be removed
   * @return true if the current user has Admin powers and
   *         the user whose username is passed as input was successfully removed.
   *         Return false if the current user is null or does not have Admin power
   * @throws NoSuchElementException with a descriptive error message
   *                                if no match with username is found in the list of users
   */
  public boolean removeUser(String username) throws NoSuchElementException {
    if (this.currentUser == null || !this.currentUser.getIsAdmin()) {
      return false;
    }
    for (int i = 0; i < users.size(); i++) {
      if (username.equals(users.get(i).getUsername())) {
        users.remove(i);
        return true;
      }
    }
    // throws NoSuchElementException if no match found after the loop
    throw new NoSuchElementException("No match with username"
        + " is found in the list of users");
  }

  /**
   * This method gives a user admin power
   * @param username the username for the user that need give admin power
   * @return true if this operation terminates successfully. And
   *        false if the current user is null or does not have admin powers
   * @throws NoSuchElementException with a descriptive error message if no match
   *                                with username is found in the list of users
   */
  public boolean giveAdmin(String username) throws NoSuchElementException {
    if (this.currentUser == null || !this.currentUser.getIsAdmin()) {
      return false;
    }
    for (int i=0; i<users.size(); i++) {
      if (users.get(i).getUsername().equals(username)) {
        users.get(i).setIsAdmin(true);
        return true;
      }
    }
    throw new NoSuchElementException("No match with username"
        + " is found in the list of users");
  }

  /**
   * This method removes the admin power of a user given their username
   *
   * @param username the username for the user that need take away admin power
   * @return true if this operation terminates successfully.
   *         Return false if the current user is null or does not have admin powers
   * @throws NoSuchElementException with a descriptive error message if no
   *                                match with username is found in the list of users
   */
  public boolean takeAdmin(String username) throws NoSuchElementException {
    if (this.currentUser == null || !this.currentUser.getIsAdmin()) {
      return false;
    }
    for (int i=0; i<users.size(); i++) {
      if (users.get(i).getUsername().equals(username)) {
        users.get(i).setIsAdmin(false);
        return true;
      }
    }
    throw new NoSuchElementException("No match with username"
        + " is found in the list of users");
  }

  /**
   * Reset the password of a user given their username
   *
   * @param username the username for the user that need reset its password
   * @return true if this operation terminates successfully
   *         false if the current user is null or does not have admin powers
   * @throws NoSuchElementException with a descriptive error message if no match
   *                                with username is found in the list of users
   */
  public boolean resetPassword(String username) throws NoSuchElementException {
    if (this.currentUser == null || !this.currentUser.getIsAdmin()) {
      return false;
    }
    for (int i=0; i<users.size(); i++) {
      if (users.get(i).getUsername().equals(username)) {
        users.get(i).setPassword(DEFAULT_PASSWORD);
        return true;
      }
    }
    throw new NoSuchElementException("No match with username"
        + " is found in the list of users");
  }
}
