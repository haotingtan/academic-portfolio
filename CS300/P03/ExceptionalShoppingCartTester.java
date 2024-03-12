//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 03 Exceptional Shopping Cart Tester
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This contains testing methods for the ExceptionalShoppingCart class.
 */
public class ExceptionalShoppingCartTester {

  /**
   * Checks whether ExceptionalShoppingCart.lookupProductByName() and
   * ExceptionalShoppingCart.lookupProductById() methods work as expected when
   * passing some invalid inputs.
   *
   * @return true when this test verifies a correct functionality,
   * and false otherwise
   */
  public static boolean testLookupMethods() {
    // define test scenarios

    // 1. Test lookupProductByName()
    // 1.1 The item to find is not in the market items list
    try {
      ExceptionalShoppingCart.lookupProductByName("NOT EXIST");
      System.out.println("lookupProductByName() does NOT throw exception "
          + "when passing a name of a product not found in the market.");
      return false;
    } catch (NoSuchElementException e) {
      // leave this line blank to make sure the rest of code inside
      // testLookupMethods() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Your lookupProductByName() throws to the "
          + "WRONG exception when passing a name of a product not found in the market.");
      return false;
    }

    // 1.2 The item to find is in the market items list,
    // BUT is NOT case-sensitive.
    try {
      ExceptionalShoppingCart.lookupProductByName("cErEal");
      System.out.println("Problem detected: lookupProductByName() does NOT throw exception "
          + "when passing a name of a product not case-sensitive but found in the market.");
      return false;
    } catch (NoSuchElementException e) {
      // leave this line blank to make sure the rest of code inside
      // testLookupMethods() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your lookupProductByName() throws the "
          + "WRONG exception when passing a name of a product not case-sensitive but "
          + "found in the market.");
      return false;
    }

    // 1.3 valid item name that is found in the market list
    try {
      String tomatoOutput = ExceptionalShoppingCart.lookupProductByName("Tomato");
      if (!tomatoOutput.equals("4688 Tomato $1.79")) {
        System.out.println("Problem detected: Your lookupProductByName() do NOT "
            + "return a expected string when passing the name of tomato as input");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your lookupProductByName() should NOT throw the "
          + " exception when passing a valid name of a product that is "
          + "found in the market.");
      return false;
    }

    // 2. Test lookupProductById()
    // 2.1 the input "id", is not a 4-digits integer
    try {
      ExceptionalShoppingCart.lookupProductById(32400);
      System.out.println("Problem detected: lookupProductById() does NOT throw exception "
          + "when passing an id of a product is not a 4-digits integer.");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testLookupMethods() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your lookupProductById() throws to the "
          + "WRONG exception when passing an id of a product is not a 4 digits integer.");
      return false;
    }

    // 2.2 the input "id", is a 4-digits NEGATIVE integer
    try {
      ExceptionalShoppingCart.lookupProductById(-4071);
      System.out.println("Problem detected: lookupProductById() does NOT throw exception "
          + "when passing an id of a product is a 4-digits NEGATIVE integer.");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testLookupMethods() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your lookupProductById() "
          + "throws to the WRONG exception when passing an id of a product is "
          + "a 4 digits NEGATIVE integer.");
      return false;
    }

    // 2.3 valid input
    try {
      String cucumberOutput =
          ExceptionalShoppingCart.lookupProductById(4232); // id for Cucumber
      if (!cucumberOutput.equals("4232 Cucumber $0.79")) {
        System.out.println("Problem detected: Your lookupProductById() do NOT "
            + "return a expected string when passing the id of Cucumber as input");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your lookupProductById() should NOT throw "
          + "exception when passing a id of a product that is "
          + "found in the market.");
      return false;
    }

    return true; // No bug detected.
    // The ExceptionalShoppingCart.lookupProductByName() &
    // ExceptionalShoppingCart.lookupProductById() passed this tester.
  }

  /**
   * Checks whether ExceptionalShoppingCart.addItemToMarketCatalog()
   * methods work as expected when passing some invalid inputs.
   *
   * @return true when this test verifies a correct functionality,
   * and false otherwise
   */
  public static boolean testAddItemToMarket() {
    // define test scenarios

    // 1. the name is empty
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("4071", "", "$12.1");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing an name of the product is empty.");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing an name of the product is empty.");
      return false;
    }

    // 2. the name is null
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("4071", null, "$12.1");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing an name of the product is null.");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing an name of the product is null.");
      return false;
    }

    // 3. The price does not contain a "$" sign
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("4071", "Iphone", "12.1");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing a price does not contain a \"$\" sign.");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing a price does not contain a \"&\" sign");
      return false;
    }

    // 4. The id is 4-digits negative int
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("-1071", "Coke", "$2.5");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing the id is 4-digits negative int.");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing the id is 4-digits negative int.");
      return false;
    }

    // 5. The id is 6-digits positive int
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("506071", "Coke", "$2.5");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing the id is 6-digits positive int");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing the id is 6-digits positive int");
      return false;
    }

    // 6. The id is a 4-digits character
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("OJFK", "Coke", "$2.5");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing the id is a 4-digits character");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing the id is a 4-digits character");
      return false;
    }

    // 7. The price is not numeric
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("5555", "Coke", "$what");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing the price is not numeric");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing the price is not numeric");
      return false;
    }

    // 8. The price is negative
    try {
      ExceptionalShoppingCart.addItemToMarketCatalog("8754", "Coke", "$-12.5");
      System.out.println("Problem detected: addItemToMarketCatalog() does NOT throw exception "
          + "when passing the price is negative");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testAddItemToMarket() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your addItemToMarketCatalog() "
          + "throws to the WRONG exception when passing the price is negative");
      return false;
    }

    return true; // No bug detected.
    // The ExceptionalShoppingCart.addItemToMarketCatalog() passed this tester.
  }

  /**
   * Checks whether ExceptionalShoppingCart.saveCartSummary()
   * methods work as expected when passing some invalid inputs.
   *
   * @return true when this test verifies a correct functionality,
   * and false otherwise
   */
  public static boolean testSaveCartSummary() {
    File file = null;

    // 1. test when the size of the cart is less than 0.
    try {
      file = new File("testSaveCartSummary1.txt");
      String[] cart = new String[] {"Apple", "Banana", "Milk"};
      ExceptionalShoppingCart.saveCartSummary(cart,-1, file);
      System.out.println("Problem detected: saveCartSummary() does NOT throw exception "
          + "when the size is less than 0");
      return false;
    } catch (IllegalArgumentException e) {
      // leave this line blank to make sure the rest of code inside
      // testSaveCartSummary() can run when catching the expected exception
    } catch (Exception e) {
      System.out.println("Problem detected: Your saveCartSummary() "
          + "throws to the WRONG exception when the size is less than 0");
      return false;
    }

    // 2. test saveCartSummary() works well will no exception
    try {
      file = new File("testSaveCartSummary2.txt");
      String[] cart = new String[] {"Apple", "Banana", "Milk"};
      ExceptionalShoppingCart.saveCartSummary(cart, 3, file);
    } catch (Exception e) {
      System.out.println("Problem detected: Your saveCartSummary() "
          + "Should NOT throw any exceptions when the inputs have no errors.");
      return false;
    }
    try {
      Scanner scnr = new Scanner(file);
      String actualContents = "";
      while (scnr.hasNextLine()) {
        actualContents = actualContents + scnr.nextLine() + "\n";
      }
      if (!actualContents.trim().contains("( 1 ) Apple")
          || !actualContents.trim().contains("( 1 ) Banana")
          || !actualContents.trim().contains("( 1 ) Milk")) {
        System.out.println("Problem detected: Your saveCartSummary() "
            + "do NOT save the cart summary correctly in the file");
        return false;
      }
      scnr.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return true; // No bug detected.
    // ExceptionalShoppingCart.saveCartSummary() passes the test
  }

  /**
   * Checks whether ExceptionalShoppingCart.loadCartSummary()
   * methods work as expected when passing some invalid inputs.
   *
   * @return true when this test verifies a correct functionality,
   * and false otherwise
   */
  public static boolean testLoadCartSummary() {
    File file = null;
    PrintWriter write = null;

    // 1. size is equals to 0, cart doesn't reach capacity
    {
      try {
        file = new File("testLoadCartSummary_1.txt");
        write = new PrintWriter(file);
        write.print("( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( Two ) Banana\n(  2 ) Avocado");
        write.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      String[] cart = new String[10];
      int cartSize;
      try {
        cartSize = ExceptionalShoppingCart.loadCartSummary(file, cart, 0);
      } catch (Exception e) {
        System.out.println("Problem detected: Your loadCartSummary() "
            + "Should NOT throw any exceptions unless size is less than 0 "
            + "or cart reaches capacity.");
        return false;
      }
      if (cartSize != 3) {
        System.out.println("Problem detected: Your loadCartSummary() method fail to return "
            + "the expected cart size when passing a file contents with the following:\n"
            + "( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( Two ) Banana\n(  2 ) Avocado");
        return false;
      }
      if (ExceptionalShoppingCart.nbOccurrences("Apple", cart, 3) != 2
          || ExceptionalShoppingCart.nbOccurrences("Milk", cart, 3) != 1) {
        System.out.println("Problem detected: Your loadCartSummary() method fail to return "
            + "the expected cart contents when passing a file contents with the following:\n"
            + "( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( Two ) Banana\n(  2 ) Avocado");
        return false;
      }
    }

    // 2. size is equals to 0, cart DOES reach capacity
    {
      try {
        file = new File("testLoadCartSummary_2.txt");
        write = new PrintWriter(file);
        write.print("( 6 ) Apple \n( 2 ) Milk\n( 1 )    \n( Two ) Banana\n( 5 ) Avocado");
        write.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      String[] cart = new String[10];
      try {
        ExceptionalShoppingCart.loadCartSummary(file, cart, 0);
        System.out.println("Problem detected: Your loadCartSummary() method fail to throw "
            + "the exception when passing a file contents with the following:\n"
            + "( 6 ) Apple \n( 2 ) Milk\n( 1 )    \n( Two ) Banana\n( 5 ) Avocado\n"
            + "and with 0 items in the cart");
        return false;
      } catch (IllegalStateException e1) {
        // leave this line blank to make sure the rest of code inside
        // testLoadCartSummary() can run when catching the expected exception
      } catch (Exception e2) {
        System.out.println("Problem detected: Your loadCartSummary() method throws "
            + "the WRONG exception when passing a file contents with the following:\n"
            + "( 6 ) Apple \n( 2 ) Milk\n( 1 )    \n( Two ) Banana\n( 5 ) Avocado\n"
            + "and with 0 items in the cart");
        return false;
      }
    }

    // 3. size is equals to 3, cart will reach capacity AFTER adding the item in the file
    {
      try {
        file = new File("testLoadCartSummary_3.txt");
        write = new PrintWriter(file);
        write.print("( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( Two ) Banana\n(  2 ) Avocado");
        write.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      String[] cart = new String[] {"Onion", "Milk", "Eggs", null, null, null};
      int cartSize;
      try {
        cartSize = ExceptionalShoppingCart.loadCartSummary(file, cart, 3);
      } catch (Exception e) {
        System.out.println("Problem detected: Your loadCartSummary() "
            + "Should NOT throw any exceptions unless size is less than 0 "
            + "or cart reaches capacity.");
        return false;
      }
      if (cartSize != cart.length) {
        System.out.println("Problem detected: Your loadCartSummary() method fail to return "
            + "the expected cart size when passing a file contents with the following:\n"
            + "( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( Two ) Banana\n(  2 ) Avocado\n "
            + "and with 3 items in the cart");
        return false;
      }
      if (ExceptionalShoppingCart.nbOccurrences("Apple", cart, 6) != 2
          || ExceptionalShoppingCart.nbOccurrences("Milk", cart, 6) != 2
          || ExceptionalShoppingCart.nbOccurrences("Eggs", cart, 6) != 1
          || ExceptionalShoppingCart.nbOccurrences("Onion", cart, 6) != 1) {
        System.out.println("Problem detected: Your loadCartSummary() method fail to return "
            + "the expected cart contents when passing a file contents with the following:\n"
            + "( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( Two ) Banana\n(  2 ) Avocado\n"
            + "and with 3 items in the cart");
        return false;
      }
    }

    // 4. size is equals to 5, cart reaches capacity
    // BEFORE finishing adding the item in the file
    {
      try {
        file = new File("testLoadCartSummary_3.txt");
        write = new PrintWriter(file);
        write.print("( 2 ) Apple \n( 1 ) Milk\n( 1 )    \n( 2 ) Banana\n(  2 ) Avocado");
        write.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      String[] cart = new String[] {"Onion", "Milk", "Eggs",
          "Pizza", "Patato", null, null, null};
      try {
        ExceptionalShoppingCart.loadCartSummary(file, cart, 5);
        System.out.println("Problem detected: Your loadCartSummary() method fail to throw "
            + "the exception when trying to load items to the cart that the cart "
            + "will reach the capacity before finishing adding items.");
        return false;
      } catch (IllegalStateException e1) {
        // leave this line blank to make sure the rest of code inside
        // testLoadCartSummary() can run when catching the expected exception
      } catch (Exception e2) {
        System.out.println("Problem detected: Your loadCartSummary() "
            + "throws the WRONG exception when trying to load items to the cart that the cart "
            + "will reach the capacity before finishing adding items.");
        return false;
      }
    }

    return true; // no BUGs detected.
    // ExceptionalShoppingCart.loadCartSummary() method passes
  }

  /**
   * This tester runs all the tester methods defined in this tester class.
   * For instance, if this tester class defines three tester methods
   * named test1(), test2() and test3(), it will return test1() && test2() && test3()
   * <p>
   * @return false if any of the tester methods fails, and true if all
   *       the tests are passed.
   */
  public static boolean runAllTests() {
    boolean testResult = true;
    if (!testLookupMethods()) {
      testResult = false;
    }
    if (!testAddItemToMarket()) {
      testResult = false;
    }
    if (!testSaveCartSummary()) {
      testResult = false;
    }
    if (!testLoadCartSummary()) {
      testResult = false;
    }

    return testResult; // No BUGs detected. all tests pass.
  }

  /**
   * Main method runs the ExceptionalShoppingCart tests.
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.print("ExceptionalShoppingCart class methods' test result: " + runAllTests());
  }
}
