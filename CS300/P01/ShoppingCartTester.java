//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 1 Shopping Cart Tester
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
import java.util.Arrays;

/**
 * This contains testing methods for the ShoppingCart class.
 */
public class ShoppingCartTester {

  /**
   * Checks whether ShoppingCart.lookupProductByName() and
   * ShoppingCart.lookupProductById() methods work as expected.
   *
   * @return true when this test verifies a correct functionality,
   *        and false otherwise
   */
  public static boolean testLookupMethods() {
    // define test scenarios.

    // 1. The item to find is at index 0 of the marketItems array
    {
      String expectedOutput = "4390 Apple $1.59";
      if (!ShoppingCart.lookupProductByName("Apple").equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductByName() method "
                + "failed to return the expected output when passed Apple as input");
        return false;
      }
      if (!ShoppingCart.lookupProductById(4390).equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductById() method "
                + "failed to return the expected output when passed the id "
                + "of Apple as input");
        return false;
      }
    }

    // 2. The item to find is at the last non-null position of the marketItems array
    {
      String expectedOutput = "4688 Tomato $1.79";
      if (!ShoppingCart.lookupProductByName("Tomato").equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductByName() method "
                + "failed to return the expected output when passed Tomato as input");
        return false;
      }
      if (!ShoppingCart.lookupProductById(4688).equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductById() method "
                + "failed to return the expected output when passed the id "
                + "of Tomato as input");
        return false;
      }
    }

    // 3. The item to find is at an arbitrary position of the middle of the marketItems array
    {
      String expectedOutput = "3033 Eggs $3.09";
      if (!ShoppingCart.lookupProductByName("Eggs").equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductByName() method "
                + "failed to return the expected output when passed Eggs as input");
        return false;
      }
      if (!ShoppingCart.lookupProductById(3033).equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductById() method "
                + "failed to return the expected output when passed the id "
                + "of Eggs as input");
        return false;
      }
    }

    // 4. The item to find is not found in the market
    {
      String expectedOutput = "No match found";
      if (!ShoppingCart.lookupProductByName("NOT FOUND").equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductByName() method "
                + "failed to return the expected output when passed the name of "
                + "a product not found in the market.");
        return false;
      }
      if (!ShoppingCart.lookupProductById(2000).equals(expectedOutput)) {
        System.out.println("Problem detected: Your lookupProductById() method "
                + "failed to return the expected output when passed the identifier"
                + "of a product not found in the market.");
        return false;
      }
    }

    return true; // NO BUGS detected in ShoppingCart.lookupProductByName()
    // and ShoppingCart.lookupProductById method
  }

  /**
   * Checks the correctness of ShoppingCart.getProductPrice() method
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetProductPrice() {
    // define test scenarios

    // 1. get the price of at index 0 of the marketItems array
    {
      double expectedPrice = 1.59; // price of the Apple in the market
      if (Math.abs(ShoppingCart.getProductPrice("Apple") - expectedPrice) > 0.001) {
        System.out.println("Problem detected: Your getProductPrice() method "
            + "failed to return the expected output when passed Apple as input");
        return false;
      }
    }

    // 2. get the price of the item at an arbitrary position of the
    // middle of the marketItems array
    {
      double expectedPrice = 3.69; // price of the Cereal in the market
      if (Math.abs(ShoppingCart.getProductPrice("Cereal") - expectedPrice) > 0.001) {
        System.out.println("Problem detected: Your getProductPrice() method "
            + "failed to return the expected output when passed Cereal as input");
        return false;
      }
    }

    // 3. get the price of the item at the last non-null position of the marketItems array
    {
      double expectedPrice = 1.79; // price of the product Tomato in the market
      if (Math.abs(ShoppingCart.getProductPrice("Tomato") - expectedPrice) > 0.001) {
        System.out.println("Problem detected: Your getProductPrice() method "
            + "failed to return the expected output when passed Tomato as input");
        return false;
      }
    }

    // 4. return -1.0 when the item is not in the market catalog
    {
      double expectedPrice = -1.0;
      if (Math.abs(ShoppingCart.getProductPrice("NOT FOUND") - expectedPrice) > 0.001) {
        System.out.println("Problem detected: Your getProductPrice() method "
            + "failed to return the expected output when passed the name of "
            + "a product not found in the market.");
        return false;
      }
    }

    return true; // No bug detected. The ShoppingCart.getProductPrice() passed this tester.
  }

  /**
   * This tester method checks the correctness of addItemToCart,
   * contains, and nbOccurrences methods defined in the ShoppingCart class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddItemToCartContainsNbOccurrences() {
    // define test scenarios

    // Section 1 : Test ShoppingCart.addItemToCart()
    // 1.1: Adding an item to an empty cart
    {
      int expectedOutput = 1; // the size of the cart at this scenarios
      String[] cart = new String[10];
      String[] expectedCart =
          new String[] {"Banana", null, null, null, null, null, null, null, null, null};
      if (ShoppingCart.addItemToCart("Banana", cart, 0) != expectedOutput ||
          !Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your addItemToCart() method "
            + "failed to return the expected OUTPUT when adding an item to an empty cart");
        return false;
      }
    }

    // 1.2: Adding an item to a full cart
    {
      int expectedOutput = 10; // the size of the cart at this scenarios
      String[] cart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple",
              "Pizza", "Butter", "Chicken", "Grape", "Apple"};
      String[] expectedCart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple",
          "Pizza", "Butter", "Chicken", "Grape", "Apple"}; //expected array should be the same
      //to the original array
      if (ShoppingCart.addItemToCart("Apple", cart, 10) != expectedOutput ||
          !Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your addItemToCart() method "
            + "failed to return the expected OUTPUT when adding an item to a full cart");
        return false;
      }
    }

    // 1.3: Adding an item to non-full & non-empty cart
    {
      int expectedOutput = 6; // the size of the cart at this scenarios
      String[] cart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple",
              null, null, null, null, null};
      String[] expectedCart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple", "Cookie",
              null, null, null, null};
      if (ShoppingCart.addItemToCart("Cookie", cart, 5) != expectedOutput ||
          !Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your addItemToCart() method "
            + "failed to return the expected OUTPUT when adding an item to a non-full cart");
        return false;
      }
    }

    // Section 2 : Test ShoppingCart.nbOccurrences
    // 2.1: search an item that does not occur in the cart
    {
      int expectedOutput = 0;
      String[] cart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple",
              null, null, null, null, null};
      if (ShoppingCart.nbOccurrences("Cookie", cart, 5) != expectedOutput) {
        System.out.println("Problem detected: Your nbOccurrences() method "
            + "failed to return the expected output when checking the number of occurrences "
            + "for an item that does not occur in the cart.");
        return false;
      }
    }

    // 2.2: search an item that occurs exactly one time in the cart
    {
      int expectedOutput = 1;
      String[] cart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple",
              null, null, null, null, null};
      if (ShoppingCart.nbOccurrences("Eggs", cart, 5) != expectedOutput) {
        System.out.println("Problem detected: Your nbOccurrences() method failed to return "
            + "the expected output when checking the number of occurrences for an item "
            + "that exactly occur one time in the cart.");
        return false;
      }
    }

    // 2.3: search an item that occurs multiple times in the cart
    {
      int expectedOutput = 3;
      String[] cart =
          new String[] {"Banana", "Onion", "Banana", "Eggs", "Banana",
              null, null, null, null, null};
      if (ShoppingCart.nbOccurrences("Banana", cart, 5) != expectedOutput) {
        System.out.println("Problem detected: Your nbOccurrences() method failed to return "
            + "the expected output when checking the number of occurrences for an item "
            + "that occurs multiple times in the cart.");
        return false;
      }
    }

    // Section 3 : Test ShoppingCart.contains()
    // 3.1: search an item that does not occur in the cart
    {
      boolean expectedOutput = false;
      String[] cart =
          new String[] {"Banana", "Onion", "Tomato", "Eggs", "Apple",
              null, null, null, null, null};
      if (ShoppingCart.contains("Cookie", cart, 5) != expectedOutput) {
        System.out.println("Problem detected: Your contains() method "
            + "failed to return the expected output "
            + "when checking an item that does not occur in the cart.");
        return false;
      }
    }

    // 3.2: search an item that occurs one time in the cart
    {
      boolean expectedOutput = true;
      String[] cart =
          new String[] {"Banana", "Onion", "Avocado", "Eggs", "Chocolate",
              null, null, null, null, null};
      if (ShoppingCart.contains("Avocado", cart, 5) != expectedOutput) {
        System.out.println("Problem detected: Your contains() method "
            + "failed to return the expected output "
            + "when checking an item that occurs in the cart.");
        return false;
      }
    }

    // 3.3: search an item that occurs many times in the cart
    {
      boolean expectedOutput = true;
      String[] cart =
          new String[] {"Avocado", "Onion", "Avocado", "Eggs", "Avocado",
              null, null, null, null, null};
      if (ShoppingCart.contains("Avocado", cart, 5) != expectedOutput) {
        System.out.println("Problem detected: Your contains() method "
            + "failed to return the expected output "
            + "when checking an item that occurs in the cart.");
        return false;
      }
    }

    return true; // No bug detected.
    // The ShoppingCart.addItemToCart(), ShoppingCart.nbOccurrences(),
    // ShoppingCart.contains() passed this tester.
  }

  /**
   * This tester method checks the correctness of removeItem() method
   * defined in the ShoppingCart class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRemoveItem() {
    // define test scenarios

    // 1. remove an item stored at index 0 of a non-empty cart
    {
      int expectedOutput = 5;
      String[] cart =
          new String[] {"Carrot", "Blueberry", "Avocado", "Eggs", "Chocolate", "Cucumber",
              null, null, null, null};
      if (ShoppingCart.removeItem(cart, "Carrot", 6) != expectedOutput) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected OUTPUT when removing an item stored at index 0 of a non-empty cart");
        return false;
      }
      if (ShoppingCart.nbOccurrences("Blueberry", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Avocado", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Eggs", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Chocolate", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Cucumber", cart, 5) != 1) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected CART when removing an item stored at index 0 of a non-empty cart");
        return false;
      }
    }

    // 2. remove an item whose first occurrence is stored at
    // index size-1 of a non-empty cart
    {
      int expectedOutput = 5;
      String[] cart =
          new String[] {"Carrot", "Blueberry", "Avocado", "Eggs", "Chocolate", "Cucumber", null,
              null, null, null};
      if (ShoppingCart.removeItem(cart, "Cucumber", 6) != expectedOutput) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected OUTPUT when removing an item whose first occurrence is stored at "
            + "index size-1 of a non-empty cart");
        return false;
      }
      if (ShoppingCart.nbOccurrences("Carrot", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Blueberry", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Avocado", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Eggs", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Chocolate", cart, 5) != 1) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected CART when removing an item whose first occurrence is stored at "
            + "index size-1 of a non-empty cart");
        return false;
      }
    }

    // 3. removing an item whose first occurrence is stored at an arbitrary
    // position within a non-empty array cart
    {
      int expectedOutput = 5;
      String[] cart =
          new String[] {"Carrot", "Blueberry", "Avocado", "Eggs", "Avocado", "Cucumber", null,
              null, null, null};
      if (ShoppingCart.removeItem(cart, "Avocado", 6) != expectedOutput) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected OUTPUT when removing an item whose first occurrence is "
            + "stored at an arbitrary position within a non-empty array cart");
        return false;
      }
      if (ShoppingCart.nbOccurrences("Carrot", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Blueberry", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Eggs", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Avocado", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Cucumber", cart, 5) != 1) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected CART when removing an item whose first occurrence is "
            + "stored at an arbitrary position within a non-empty array cart");
        return false;
      }
    }

    // 4. remove an item from an empty cart
    {
      int expectedOutput = 0;
      String[] cart = new String[10];
      String[] expectedCart = new String[10];
      if (ShoppingCart.removeItem(cart, "Avocado", 0) != expectedOutput ||
          !Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your removeItem() method failed to return "
            + "the expected OUTPUT when removing an item from an empty cart");
        return false;
      }
    }

    // 5. remove a non-existing item from the cart.
    {
      int expectedOutput = 5;
      String[] cart =
          new String[] {"Carrot", "Blueberry", "Avocado", "Eggs", "Chocolate", null, null, null,
              null, null};
      String[] expectedCart =
          new String[] {"Carrot", "Blueberry", "Avocado", "Eggs", "Chocolate", null, null, null,
              null, null};
      if (ShoppingCart.removeItem(cart, "Cereal", 5) != expectedOutput ||
          !Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your removeItem() method failed to return the "
            + "expected OUTPUT when removing a non-existing item from the cart.");
        return false;
      }
    }

    // 6. remove an item that occurs more than two times in the cart
    {
      int expectedOutput = 5;
      String[] cart =
          new String[] {"Carrot", "Blueberry", "Avocado", "Blueberry", "Chocolate", "Blueberry",
              null, null, null, null};
      String[] expectedCart =
          new String[] {"Carrot", "Avocado", "Blueberry", "Chocolate", "Blueberry",
              null, null, null, null, null};
      if (ShoppingCart.removeItem(cart, "Blueberry", 6) != expectedOutput) {
        System.out.println("Problem detected: Your removeItem() method "
            + "failed to return the expected OUTPUT "
            + "when removing an item that occurs more than one time in the cart.");
        return false;
      }
      if (ShoppingCart.nbOccurrences("Blueberry", cart, 5) != 2 ||
          ShoppingCart.nbOccurrences("Carrot", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Avocado", cart, 5) != 1 ||
          ShoppingCart.nbOccurrences("Chocolate", cart, 5) != 1) {
        System.out.println("Problem detected: Your removeItem() method "
            + "failed to return the expected CART "
            + "when removing an item that occurs more than one time in the cart.");
        return false;
      }
    }

    return true; // No bug detected. The ShoppingCart.removeItem() passed this tester.
  }

  /**
   * This tester method checks the correctness of checkout() and
   * getCartSummary() methods defined in the ShoppingCart class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testCheckoutGetCartSummary() {
    // define test scenarios

    // 1. get cart summary and checkout with an empty cart.
    {
      String[] cart = new String[10];
      String expectedSummary = "";
      String[] expectedCart = new String[10];
      if (!ShoppingCart.getCartSummary(cart, 0).equals(expectedSummary)) {
        System.out.println("Problem detected: Your getCartSummary() method "
            + "failed to return the expected OUTPUT with an empty cart");
        return false;
      }
      if (!Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your getCartSummary() method "
            + "has changed the shopping cart with an empty cart");
        return false;
      }
      double expectedPrice = 0.0;
      if (ShoppingCart.checkout(cart, 0) != expectedPrice) {
        System.out.println("Problem detected: Your checkout() method "
            + "failed to return the expected output with an empty cart");
        return false;
      }
    }

    // 2. get cart summary and checkout with a cart
    // which contains non-duplicate items
    {
      String[] cart = new String[] {"Milk", "Apple", "Banana", "Pizza", null, null};
      String[] expectedCart = new String[] {"Milk", "Apple", "Banana", "Pizza", null, null};
      String actual = ShoppingCart.getCartSummary(cart, 4);
      if (!actual.contains("(1) Milk") || !actual.contains("(1) Apple") || !actual.contains(
          "(1) Banana") || !actual.contains("(1) Pizza")) {
        System.out.println("Problem detected: Your getCartSummary() method "
            + "failed to return the expected OUTPUT "
            + "with a cart which contains non-duplicate items");
        return false;
      }
      if (!Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your getCartSummary() method "
            + "has changed the shopping cart with a cart which contains non-duplicate items");
        return false;
      }
      double expectedPrice = 16.4535;
      if (Math.abs(ShoppingCart.checkout(cart, 4) - expectedPrice) > 0.001) {
        System.out.println("Problem detected: Your checkout() method "
            + "failed to return the expected output "
            + "with a cart which contains non-duplicate items");
        return false;
      }
    }

    // 3. get cart summary and checkout with a cart which contains items
    // with multiple occurrences at different positions of the oversized array.
    {
      String[] cart = new String[] {"Milk", "Apple", "Banana",
          "Pizza", "Milk", "Blueberry", "Milk", null, null};
      String[] expectedCart = new String[] {"Milk", "Apple", "Banana",
          "Pizza", "Milk", "Blueberry", "Milk", null, null};
      String actual = ShoppingCart.getCartSummary(cart, 7);
      if (!actual.contains("(3) Milk") || !actual.contains("(1) Apple") || !actual.contains(
          "(1) Banana") || !actual.contains("(1) Pizza") || !actual.contains("(1) Blueberry")) {
        System.out.println("Problem detected: Your getCartSummary() method "
            + "failed to return the expected output with a cart which contains items"
            + "with multiple occurrences at different positions of the oversized array.");
        return false;
      }
      if (!Arrays.equals(cart, expectedCart)) {
        System.out.println("Problem detected: Your getCartSummary() method "
            + "has changed the shopping cart with a cart which contains items with "
            + "multiple occurrences at different positions of the oversized array.");
        return false;
      }
      double expectedPrice = 28.077;
      if (Math.abs(ShoppingCart.checkout(cart, 7) - expectedPrice) > 0.001) {
        System.out.println("Problem detected: Your checkout() method "
            + "failed to return the expected output with a cart which contains items"
            + "with multiple occurrences at different positions of the oversized array.");
        return false;
      }
    }

    return true; // No bug detected.
    // The ShoppingCart.checkout() and ShoppingCart.getCartSummary() passed this tester.
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
    if (!testGetProductPrice()) {
      testResult = false;
    }
    if (!testAddItemToCartContainsNbOccurrences()) {
      testResult = false;
    }
    if (!testRemoveItem()) {
      testResult = false;
    }
    if (!testCheckoutGetCartSummary()) {
      testResult = false;
    }
    return testResult;
  }

  /**
   * Main method runs the ShoppingCart tests.
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.print("ShoppingCart class methods' test result: " + runAllTests());
  }
}
