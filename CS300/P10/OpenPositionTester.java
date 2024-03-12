//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Project 10 Open Position - OpenPositionTester class
// Course:   CS 300 Spring 2022
//
// Author:   Haoting Tan
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of Application,
 * ApplicationIterator, ApplicationQueue and OpenPosition classes in the assignment.
 */
public class OpenPositionTester {

  /**
   * This method tests and makes use of the Application constructor, getter methods, toString() and
   * compareTo() methods.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplication() {
    boolean result = true;
    // create an Application with valid input
    try {
      try {
        Application testApp = new Application("WeChat", "wechat@email.com", 60);
      } catch (Exception e) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e.getClass());
        e.printStackTrace();
        result = false;
      }

      // create an Application with invalid input:
      // blank name
      try {
        Application testApp = new Application("   ", "wechat@email.com", 60);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given a blank name");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // null name
      try {
        Application testApp = new Application(null, "wechat@email.com", 60);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given a null name");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // null email
      try {
        Application testApp = new Application("WeChat", null, 60);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given a null email address");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // blank email
      try {
        Application testApp = new Application("WeChat", "    ", 60);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given a blank email address");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // no @ email
      try {
        Application testApp = new Application("WeChat", "wechatemail.com", 60);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given a email address without '@'");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // too many @ email
      try {
        Application testApp = new Application("WeChat", "we@chat@email@.com", 60);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given a email address with too many '@'");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // invalid score
      try {
        Application testApp1 = new Application("WeChat", "wechat@email.com", -1);
        Application testApp2 = new Application("WeChat", "wechat@email.com", 101);
        System.out.println("Problem detected: the constructor of Application class does not throw "
            + "IllegalArgumentException as expected when given an invalid score");
        result = false;
      } catch (IllegalArgumentException e1) {
        // expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of Application class throws unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      Application testApp1 = new Application("WeChat", "wechat@email.com", 1);
      Application testApp100 = new Application("WeChat", "wechat@email.com", 100);
      Application testApp = new Application("WeChat", "wechat@email.com", 60);
      Application testApp2 = new Application("QQ", "QQ@email.com", 50);
      Application testApp3 = new Application("TikTok", "tiktok@email.com", 70);
      Application testApp4 = new Application("DingDing", "dingding@email.com", 20);
      Application testApp5 = new Application("Zenly", "zenly@email.com", 90);
      // verify getters
      if (!testApp.getName().equals("WeChat")) {
        System.out.println("Problem detected: the getName() method returns the "
            + "unexpected result.");
        result = false;
      }
      if (!testApp.getEmail().equals("wechat@email.com")) {
        System.out.println("Problem detected: the getEmail() method returns the "
            + "unexpected result.");
        result = false;
      }
      if (testApp.getScore() != 60 || testApp2.getScore() != 50 || testApp3.getScore() != 70
          || testApp4.getScore() != 20 || testApp5.getScore() != 90) {
        System.out.println("Problem detected: the getScore() method returns the "
            + "unexpected result.");
        result = false;
      }

      // verify compareTo
      if (testApp.compareTo(testApp2) <= 0) {
        System.out.println("Problem detected: the compareTo() method returns "
            + "the unexpected result.");
        result = false;
      }

      // verify toString
      if (!testApp.toString().equals("WeChat:wechat@email.com:60")) {
        System.out.println("Problem detected: the toString() methdo "
            + "returns the unexpected result.");
        result = false;
      }

      return result;

    } catch (Exception e) {
      System.out.println("Error: encounter exception when testing Application class: "
          + e.getClass());
      return false;
    }
  }

  /**
   * This method tests and makes use of the ApplicationIterator class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplicationIterator() {
    try {
      boolean result = true;
      // create an ApplicationQueue with capacity at least 3
      // and at least 3 Applications with different scores
      Application testApp1 = new Application("WeChat", "wechat@email.com", 60);
      Application testApp2 = new Application("QQ", "qq@email.com", 50);
      Application testApp3 = new Application("TikTok", "tiktok@email.com", 70);
      Application testApp4 = new Application("DingDing", "dingding@email.com", 20);
      Application testApp5 = new Application("Zenly", "zenly@email.com", 90);
      // add those Applications to the queue
      ApplicationQueue testQueue = new ApplicationQueue(5);
      testQueue.enqueue(testApp1);
      testQueue.enqueue(testApp2);
      testQueue.enqueue(testApp3);
      testQueue.enqueue(testApp4);
      testQueue.enqueue(testApp5);
      // verify that iterating through the queue gives you the applications in order of
      // INCREASING score
      Application[] expectedQueue = new Application[5];
      expectedQueue[0] = testApp4;
      expectedQueue[1] = testApp2;
      expectedQueue[2] = testApp1;
      expectedQueue[3] = testApp3;
      expectedQueue[4] = testApp5;
      int index = 0;
      try {
        for (Application current : testQueue) {
          if (current != expectedQueue[index++]) {
            System.out.println(
                "Problem detected: the ApplicationIterator class does not function as "
                    + "expected");
            result = false;
            break;
          }
        }
      } catch (Exception e) {
        System.out.println("Problem detected: the ApplicationIterator throws unexpected "
            + e.getClass());
        e.printStackTrace();
        result = false;
      }
      return result;

    } catch (Exception e) {
      System.out.println("Error: encounter exception when testing ApplicationIterator class: "
          + e.getClass());
      return false;
    }
  }

  /**
   * This method tests and makes use of the enqueue() and dequeue() methods in the ApplicationQueue
   * class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testEnqueueDequeue() {
    try {
      boolean result = true;
      // create an ApplicationQueue with capacity 3
      // and at least 4 Applications with different scores
      ApplicationQueue testQueue = new ApplicationQueue(3);
      Application testApp1 = new Application("WeChat", "wechat@email.com", 60);
      Application testApp2 = new Application("QQ", "qq@email.com", 50);
      Application testApp3 = new Application("TikTok", "tiktok@email.com", 70);
      Application testApp4 = new Application("DingDing", "dingding@email.com", 20);

      // enqueue an invalid value (null)
      try {
        testQueue.enqueue(null);
        System.out.println(
            "Problem detected: the enqueue() method does not throw expected NullPointerException");
        result = false;
      } catch (NullPointerException e1) {
        // Expected condition
      } catch (Exception e2) {
        System.out.println("Problem detected: the enqueue() method throws unexpected exception "
            + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // enqueue one valid application
      try {
        testQueue.enqueue(testApp1);
        if (testQueue.size() != 1) {
          System.out.println("Problem detected: the enqueue() method does not"
              + " function as expected");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: the enqueue() method throws unexpected exception "
            + e.getClass());
        e.printStackTrace();
        result = false;
      }

      // enqueue two more valid applications
      try {
        testQueue.enqueue(testApp2);
        testQueue.enqueue(testApp3);
        if (testQueue.size() != 3) {
          System.out.println("Problem detected: the enqueue() method does not "
              + "function as expected");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: the enqueue() method throws unexpected exception "
            + e.getClass());
        e.printStackTrace();
        result = false;
      }

      // enqueue one more application (exceeds capacity)
      try {
        testQueue.enqueue(testApp4);
        System.out.println(
            "Problem detected: the enqueue() method does not throw expected IllegalStateException");
        result = false;
      } catch (IllegalStateException e1) {
        // Expected condition
      } catch (Exception e2) {
        System.out.println("Problem detected: the enqueue() method throws unexpected exception "
            + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // dequeue one application (should be lowest score)
      try {
        if (testApp2 != testQueue.dequeue()) {
          System.out.println("Problem detected: the dequeue() method does not "
              + "function as expected");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: the dequeue() method throws unexpected exception "
            + e.getClass());
        e.printStackTrace();
        result = false;
      }
      // dequeue all applications
      try {
        if (testApp1 != testQueue.dequeue() || testApp3 != testQueue.dequeue() ||
            testQueue.size() != 0) {
          System.out.println("Problem detected: the dequeue() method does not "
              + "function as expected");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: the dequeue() method throws unexpected exception "
            + e.getClass());
        e.printStackTrace();
        result = false;
      }

      // dequeue from an empty queue
      try {
        testQueue.dequeue();
        System.out.println(
            "Problem detected: the dequeue() method does not throw expected"
                + " NoSuchElementException");
        result = false;
      } catch (NoSuchElementException e1) {
        // Expected condition
      } catch (Exception e2) {
        System.out.println("Problem detected: the dequeue() method throws unexpected "
            + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      return result;

    } catch (Exception e) {
      System.out.println("Error: encounter exception when testing enqueue() and dequeue() "
          + "methods in the ApplicationQueue: "
          + e.getClass());
      return false;
    }
  }

  /**
   * This method tests and makes use of the common methods (isEmpty(), size(), peek()) in the
   * ApplicationQueue class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testCommonMethods() {
    try {
      boolean result = true;
      // create an ApplicationQueue with 0 capacity (should fail)
      try {
        ApplicationQueue testQueue = new ApplicationQueue(0);
        System.out.println("Problem detected: the constructor of ApplicationQueue class does not "
            + "throw expected IllegalArgumentException when given a non-positive capacity");
        result = false;
      } catch (IllegalArgumentException e1) {
        // Expected Condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of ApplicationQueue class throws " + "unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }
      // create an ApplicationQueue with capacity 3
      // and at least 3 Applications with different scores
      ApplicationQueue testQueue = new ApplicationQueue(3);
      Application testApp1 = new Application("WeChat", "wechat@email.com", 60);
      Application testApp2 = new Application("QQ", "qq@email.com", 50);
      Application testApp3 = new Application("TikTok", "tiktok@email.com", 70);

      // verify the methods' behaviors on an empty queue
      try {
        if (testQueue.size() != 0) {
          System.out.println("Problem detected: size() method returns the unexpected result");
          result = false;
        }
        if (testQueue.isEmpty() != true) {
          System.out.println("Problem detected: isEmpty() method returns the unexpected result");
          result = false;
        }
        testQueue.peek();
        System.out.println(
            "Problem detected: peek() method does not throw expected NoSuchElementException");
        result = false;
      } catch (NoSuchElementException e1) {
        // Expected condition
      } catch (Exception e2) {
        System.out.println("Problem detected: unexpected " + e2.getClass() + " detected");
        e2.printStackTrace();
        result = false;
      }

      // add one Application and verify the methods' behaviors
      try {
        testQueue.enqueue(testApp1);
        if (testQueue.size() != 1) {
          System.out.println("Problem detected: size() method returns the unexpected result");
          result = false;
        }
        if (testQueue.isEmpty() != false) {
          System.out.println("Problem detected: isEmpty() method returns the unexpected result");
          result = false;
        }
        if (testQueue.peek() != testApp1) {
          System.out.println("Problem detected: peek() method returns the unexpected result");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: unexpected " + e.getClass() + " detected");
        e.printStackTrace();
        result = false;
      }
      // add the rest of the Applications and verify the methods' behaviors

      try {
        testQueue.enqueue(testApp2);
        testQueue.enqueue(testApp3);
        if (testQueue.size() != 3) {
          System.out.println("Problem detected: size() method returns the unexpected result");
          result = false;
        }
        if (testQueue.isEmpty() != false) {
          System.out.println("Problem detected: isEmpty() method returns the unexpected result");
          result = false;
        }
        if (testQueue.peek() != testApp2) {
          System.out.println("Problem detected: peek() method returns the unexpected result");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: unexpected " + e.getClass() + " detected");
        e.printStackTrace();
        result = false;
      }

      return result;
    } catch (Exception e) {
      System.out.println("Error: encounter exception when testing common methods (isEmpty(), "
          + "size(), peek()) in the ApplicationQueue class " + e.getClass());
      return false;
    }
  }

  /**
   * This method tests and makes use of OpenPosition class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testOpenPosition() {
    try {
      boolean result = true;
      // create an OpenPosition with 0 capacity (should fail)
      try {
        OpenPosition testOP = new OpenPosition("testOP", 0);
        System.out.println("Problem detected: the constructor of OpenPosition class does not throw "
            + "expected IllegalArgumentException");
        result = false;
      } catch (IllegalArgumentException e1) {
        // Expected condition
      } catch (Exception e2) {
        System.out.println(
            "Problem detected: the constructor of OpenPosition class throws " + "unexpected "
                + e2.getClass());
        e2.printStackTrace();
        result = false;
      }

      // create an OpenPosition with capacity 3
      // and at least 5 Applications with different scores
      OpenPosition testOP = new OpenPosition("testOP", 3);
      Application testApp1 = new Application("WeChat", "wechat@email.com", 60);
      Application testApp2 = new Application("QQ", "qq@email.com", 50);
      Application testApp3 = new Application("TikTok", "tiktok@email.com", 70);
      Application testApp4 = new Application("DingDing", "dingding@email.com", 20);
      Application testApp5 = new Application("Zenly", "zenly@email.com", 90);
      // verify that the 3 MIDDLE-scoring Applications can be added
      // don't use the highest and lowest scoring applications YET
      try {
        testOP.add(testApp3);
        testOP.add(testApp2);
        testOP.add(testApp1);
      } catch (Exception e) {
        System.out.println("Problem detected: the add() method throws unexpected " + e.getClass());
        e.printStackTrace();
        result = false;
      }
      // verify that getApplications returns the correct value for your input
      String expectedOutput = "QQ:qq@email.com:50" + "\n" + "WeChat:wechat@email.com:60"
          + "\n" + "TikTok:tiktok@email.com:70" + "\n";
      if (!testOP.getApplications().equals(expectedOutput)) {
        System.out.println(
            "Problem detected: the getApplications() method does not fuction as expected");
        result = false;
      }

      // verify that the result of getTotalScore is the sum of all 3 Application scores
      if (testOP.getTotalScore() != 180) {
        System.out.println(
            "Problem detected: the getTotalScore() method does not fuction as expected");
        result = false;
      }

      // verify that the lowest-scoring application is NOT added to the OpenPosition
      try {
        if (testOP.add(testApp4)) {
          System.out.println("Problem detected: the add() methdod does not function as expected");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem dected: the add() method throws unexpected " + e.getClass());
        e.printStackTrace();
        result = false;
      }
      // verify that the highest-scoring application IS added to the OpenPosition
      try {
        if (!testOP.add(testApp5)) {
          System.out.println("Problem detected: the add() methdod does not function as expected");
          result = false;
        }
      } catch (Exception e) {
        System.out.println("Problem dected: the add() method throws unexpected " + e.getClass());
        e.printStackTrace();
        result = false;
      }
      // verify that getApplications has changed correctly
      expectedOutput = "WeChat:wechat@email.com:60" + "\n" + "TikTok:tiktok@email.com:70"
          + "\n" + "Zenly:zenly@email.com:90" + "\n";
      if (!testOP.getApplications().equals(expectedOutput)) {
        System.out.println(
            "Problem detected: the getApplications() method does not fuction as expected");
        result = false;
      }

      // verify that the result of getTotalScore has changed correctly
      if (testOP.getTotalScore() != 220) {
        System.out.println(
            "Problem detected: the getTotalScore() method does not fuction as expected");
        result = false;
      }
      return result;
    } catch (Exception e) {
      System.out.println("Error: encounter exception when testing OpenPosition class: "
          + e.getClass());
      return false;
    }
  }

  /**
   * This method calls all the test methods defined and implemented in your OpenPositionTester
   * class.
   *
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {
    boolean result = true;
    boolean crtResult;

    crtResult = testApplication();
    result &= crtResult;
    System.out.println("run testApplication()-----------" + (crtResult ? "passed" : "failed"));

    crtResult = testApplicationIterator();
    result &= crtResult;
    System.out.println("run testApplicationIterator()---" + (crtResult ? "passed" : "failed"));

    crtResult = testEnqueueDequeue();
    result &= crtResult;
    System.out.println("run testEnqueueDequeue()--------" + (crtResult ? "passed" : "failed"));

    crtResult = testCommonMethods();
    result &= crtResult;
    System.out.println("run testCommonMethods()---------" + (crtResult ? "passed" : "failed"));

    crtResult = testOpenPosition();
    result &= crtResult;
    System.out.println("run testOpenPosition()----------" + (crtResult ? "passed" : "failed"));

    return result;
  }

  /**
   * Driver method defined in this OpenPositionTester class
   *
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    boolean crtResult = runAllTests();
    System.out.println("run all tests-------------------" + (crtResult ? "passed" : "failed"));
  }
}
