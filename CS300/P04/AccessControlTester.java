//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 04: Access Control System - Tester
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
import java.util.NoSuchElementException;

/**
 * This contains testing methods for the User class and AccessControl class.
 */
public class AccessControlTester {

  /**
   * check for the correctness of the constructor, and all the accessor
   * and mutator methods defined in the User class.
   *
   * @return true when this test verifies a correct functionality,
   * and false otherwise
   */
  public static boolean testUserConstructorAndMethods() {
    User testUser = new User("newUser", "Abc123", false);

    // test if the constructor set the correct name and getUsername() method works
    if (!testUser.getUsername().equals("newUser")) {
      System.out.println("Problem detected: your User class has problem "
          + "with the constructor or the getUsername() method");
      return false;
    }

    // test if the constructor set the correct admin status and getIsAdmin() method works
    if (testUser.getIsAdmin() != false) {
      System.out.println("Problem detected: your User class has problem "
          + "with the constructor or the getIsAdmin() method");
      return false;
    }

    // test isValidLogin() return true if the input string match the password
    if (testUser.isValidLogin("Abc123") != true) {
      System.out.println("Problem detected: your User class has problem "
          + "with the isValidLogin() method when the input string match the password");
      return false;
    }

    // test isValidLogin() return false if the input string mis-match the password
    if (testUser.isValidLogin("abc123") != false) {
      System.out.println("Problem detected: your User class has problem "
          + "with the isValidLogin() method when the input string mis-match the password");
      return false;
    }

    // test setIsAdmin() works if changing the current admin status
    testUser.setIsAdmin(true);
    if (testUser.getIsAdmin() != true){
      System.out.println("Problem detected: your User class has problem "
          + "with the setIsAdmin() method when changing the current admin status");
      return false;
    }

    // test setIsAdmin() works if giving the same admin status with the
    // current admin status
    testUser.setIsAdmin(true);
    if (testUser.getIsAdmin() != true){
      System.out.println("Problem detected: your User class has problem "
          + "with the setIsAdmin() method when giving the same admin status with the "
          + "current admin status");
      return false;
    }

    // test setPassword() works
    testUser.setPassword("newPassword");
    if (!testUser.isValidLogin("newPassword")) {
      System.out.println("Problem detexted: you User class has problem with the setPassword() "
          + "method, this method does not change the user's password");
      return false;
    }

    return true; // No BUGs detected. the Constructor, and all other methods
    // defined in the User class pass this tester.
  }

  /**
   * This tester checks the correctness of AccessControl.isValidLogin() method when
   * called with incorrect username or not matching (username, password) pair
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testAccessControlIsValidLoginNotValidUser() {
    AccessControl firstControl = new AccessControl();

    // 1. test with incorrect username
    if (AccessControl.isValidLogin("Admin", "root")) {
      System.out.println("Problem detected: Your AccessControl.isValidLogin() "
          + "return true when passing incorrect username(Not case-sensitive)");
      return false;
    }

    // 2. test with not matching (username, password) pair
    if (AccessControl.isValidLogin("admin", "wrongPassword")) {
      System.out.println("Problem detected: Your AccessControl.isValidLogin() "
          + "return true when the (username, password) pair not matching.");
      return false;
    }

    return true; // No BUGs detected. AccessControl.isValidLogin() passes this tester
  }

  /**
   * This tester creates a new AccessControl object and does not log in an admin.
   * This test must fail if addUser(String username) does not return false or
   * if a user was added to the list of user after the method returns.
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testAddUserWithNoAdminPowers() {
    AccessControl secondControl = new AccessControl();
    secondControl.setCurrentUser("admin");
    secondControl.addUser("noAdminPower");
    secondControl.setCurrentUser("noAdminPower");

    // 1. test addUser(String name) return value expected
    try {
      boolean result = secondControl.addUser("newUser");
      if (result == true) {
        System.out.println("Problem detected: Your AccessControl.addUser(String username) "
            + "returns true when adding user but not logging in as admin");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "throws unexpected exception when adding user but not logging in as admin");
      return false;
    }

    // 2. test addUser(String username, boolean isAdmin) return value expected
    try {
      boolean result = secondControl.addUser("anotherNewUser", true);
      if (result == true) {
        System.out.println("Problem detected: "
            + "Your AccessControl.addUser(String username, boolean isAdmin) "
            + "returns true when adding user but not logging in as admin");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: "
          + "Your AccessControl.addUser(String username, boolean isAdmin) "
          + "throws unexpected exception when adding user but not logging in as admin");
      return false;
    }

    secondControl.setCurrentUser("admin");

    // 3. test addUser(String name) add user without checking admin power
    try {
      secondControl.addUser("newUser");
    } catch (IllegalArgumentException e1) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "adds new user when adding user but not logging in as admin");
      return false;
    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "throws unexpected exception");
      return false;
    }

    // 4. test addUser(String username, boolean isAdmin) add user without checking admin power
    try {
      secondControl.addUser("anotherNewUser", true);
    } catch (IllegalArgumentException e1) {
      System.out.println("Problem detected: "
          + "AccessControl.addUser(String username, boolean isAdmin) "
          + "adds new user when adding user but not logging in as admin");
      return false;
    } catch (Exception e2) {
      System.out.println("Problem detected: Your "
          + "AccessControl.addUser(String username, boolean isAdmin) "
          + "throws unexpected exception");
      return false;
    }

    secondControl.logout();
    return true; // No BUGs detected.
    // AccessControl.addUser(string username) passes this tester
  }

  /**
   * This tester checks the correctness of addUser and removeUser methods when the
   * current user has admin powers
   *
   * @return true when this test verifies a correct functionality,
   *         and false otherwise
   */
  public static boolean testAddRemoveUserWithAdminPowers() {
    AccessControl thirdControl = new AccessControl();
    thirdControl.setCurrentUser("admin");

    // 1. test addUser(String username) return true
    try {
      boolean result = thirdControl.addUser("user1");
      if (!result) {
        System.out.println("Problem detected: Your AccessControl.addUser(String username) "
            + "returns false when adding user AND logging in as admin");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "throws unexpected exception when adding user AND logging in as admin");
      return false;
    }

    // 2. test addUser(String username, boolean isAdmin) return value expected
    try {
      boolean result = thirdControl.addUser("user2", false);
      if (!result) {
        System.out.println("Problem detected: "
            + "Your AccessControl.addUser(String username, boolean isAdmin) "
            + "returns false when adding user AND logging in as admin");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: "
          + "Your AccessControl.addUser(String username, boolean isAdmin) "
          + "throws unexpected exception when adding user AND logging in as admin");
      return false;
    }

    // 3. test addUser(String name) have added user AND
    // check if it can detect duplicated username
    try {
      thirdControl.addUser("user1");
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "can NOT detect duplicated usernames when adding new user.");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "throws unexpected exception when checking if there are duplicated usernames");
      return false;
    }

    // 4. test addUser(String username, boolean isAdmin) have added user AND
    // check if it can detect duplicated username
    try {
      thirdControl.addUser("user2", false);
      System.out.println("Problem detected: Your addUser(String username, boolean isAdmin) "
          + "can NOT detect duplicated usernames when adding new user.");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your addUser(String username, boolean isAdmin) "
          + "throws unexpected exception when checking if there are duplicated usernames");
      return false;
    }

    // 5. Passing null as the username of a new user
    try {
      thirdControl.addUser(null);
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "does not throw exception when the username to added is null");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "throws unexpected exception when the username to added is null");
      return false;
    }
    try {
      thirdControl.addUser(null, true);
      System.out.println("Problem detected: Your addUser(String username, boolean isAdmin) "
          + "does not throw exception when the username to added is null");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your addUser(String username, boolean isAdmin) "
          + "throws unexpected exception when the username to added is null");
      return false;
    }

    // 6. Passing a username that the length is smaller than 5
    try {
      thirdControl.addUser("null");
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "does not throw exception when the username's length is less than 5");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.addUser(String username) "
          + "throws unexpected exception when the username's length is less than 5");
      return false;
    }
    try {
      thirdControl.addUser("what", true);
      System.out.println("Problem detected: Your addUser(String username, boolean isAdmin) "
          + "does not throw exception when the username's length is less than 5");
      return false;
    } catch (IllegalArgumentException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your addUser(String username, boolean isAdmin) "
          + "throws unexpected exception when the username's length is less than 5");
      return false;
    }

    // 7. test AccessControl.removeUser(String username) throws expected exception
    // if the username is not in the users arraylist
    try {
      thirdControl.removeUser("NotExist");
      System.out.println("Problem detected: Your AccessControl.removeUser method "
          + "does not throw exception when the username of the user to be removed "
          + "is not in the users arraylist");
      return false;
    } catch (NoSuchElementException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.removeUser method "
          + "throws WRONG exception when the username of the user to be removed "
          + "is not in the users arraylist");
      return false;
    }

    // 8. test AccessControl.removeUser(String username) return false if the current user
    // does not have admin power
    thirdControl.setCurrentUser("user2");
    try {
      boolean result = thirdControl.removeUser("user1");
      if (result == true) {
        System.out.println("Problem detected: Your AccessControl.removeUser method "
            + "does not throw exception when the current user does NOT have admin power");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your AccessControl.removeUser method "
          + "throws unexpected exception when the current user"
          + " does NOT have admin power");
      return false;
    }

    // 9. test AccessControl.removeUser(String username) removes the user if the current user
    // does not have admin power
    thirdControl.setCurrentUser("admin");
    try {
      thirdControl.removeUser("user1");
    } catch (NoSuchElementException e1) {
      System.out.println("Problem detected: Your AccessControl.removeUser removes user "
          + "when the current user does NOT have admin power");
      return false;
    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.removeUser method "
          + "throws unexpected exception when removes user with current user"
          + " does NOT have admin power");
      return false;
    }

    // 9. test AccessControl.removeUser(String username) removes the user if the current user
    // does have admin power
    try {
      thirdControl.removeUser("user1");
      System.out.println("Problem detected: Your AccessControl.removeUser can NOT removes user "
          + "when the current user have admin power, OR it does not throws exception "
          + "if the username is not in the arraylist.");
      return false;
    } catch (NoSuchElementException e1) {

    } catch (Exception e2) {
      System.out.println("Problem detected: Your AccessControl.removeUser method throws "
          + "the unexpected exception removes same username twice when the "
          + "current user does have admin power ");
      return false;
    }

    return true; // No BUGs detected. AccessControl.addUser(String username),
    // AccessControl.addUser(String username, boolean isAdmin), and
    // AccessControl.removeUser(String username) pass this tester
  }

  /**
   * This tester runs all the tester methods defined in this tester class.
   * <p>
   * @return false if any of the tester methods fails, and true if all
   *       the tests are passed.
   */
  public static boolean runAllTests() {
    boolean testResult = true;
    if (!testUserConstructorAndMethods()) {
      testResult = false;
    }
    if (!testAccessControlIsValidLoginNotValidUser()) {
      testResult = false;
    }
    if (!testAddUserWithNoAdminPowers()) {
      testResult = false;
    }
    if (!testAddRemoveUserWithAdminPowers()) {
      testResult = false;
    }
    return testResult;
  }

  /**
   * Main method runs the AccessControl and User class tests.
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("AccessControl class & User class test result: " + runAllTests());
  }
}
