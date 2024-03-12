//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 1 Shopping Cart
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
 * This class models a user shopping situation.
 */
public class ShoppingCart {
  private static final double TAX_RATE = 0.05; // sales tax

  // MarketItems: a perfect-size two-dimensional array that stores the list of
  // available items in a given market
  // MarketItems[i][0] refers to a String representation of the item identifiers
  // MarketItems[i][1] refers the item name. Item names are also unique
  // MarketItems[i][2] a String representation of the unit price of the item in dollars
  private static String[][] marketItems =
          new String[][] {{"4390", "Apple", "$1.59"}, {"4046", "Avocado", "$0.59"},
                  {"4011", "Banana", "$0.49"}, {"4500", "Beef", "$3.79"},
                  {"4033", "Blueberry", "$6.89"}, {"4129", "Broccoli", "$1.79"},
                  {"4131", "Butter", "$4.59"}, {"4017", "Carrot", "$1.19"},
                  {"3240", "Cereal", "$3.69"}, {"3560", "Cheese", "$3.49"},
                  {"3294", "Chicken", "$5.09"}, {"4071", "Chocolate", "$3.19"},
                  {"4363", "Cookie", "$9.5"}, {"4232", "Cucumber", "$0.79"},
                  {"3033", "Eggs", "$3.09"}, {"4770", "Grape", "$2.29"},
                  {"3553", "Ice Cream", "$5.39"}, {"3117", "Milk", "$2.09"},
                  {"3437", "Mushroom", "$1.79"}, {"4663", "Onion", "$0.79"},
                  {"4030", "Pepper", "$1.99"}, {"3890", "Pizza", "$11.5"},
                  {"4139", "Potato", "$0.69"}, {"3044", "Spinach", "$3.09"},
                  {"4688", "Tomato", "$1.79"}, null, null, null, null};

  /**
   * Returns a string representation of the item's ID, name, and price,
   * whose name is provided as input if a match was found.
   * <p>
   * @param name name of the product to search
   * @return a string representation of the item's information
   *         return "No match found" if given name can't be found in the market items
   */
  public static String lookupProductByName(String name) {
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] == null) {
        break;
      }
      if (name.equals(marketItems[i][1])) {
        String itemsDescription = marketItems[i][0] + " " +
                marketItems[i][1] + " " + marketItems[i][2];
        return itemsDescription;
      }
    }
    return "No match found";
  }

  /**
   * Returns a string representation of the item's ID, name, and price,
   * whose ID is provided as input if a match was found.
   * <p>
   * @param id ID of the product to search
   * @return a string representation of the item's information
   *         return "No match found" if given name can't be found in the market items
   */
  public static String lookupProductById(int id) {
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] == null) {
        break;
      }
      if (id == Integer.parseInt(marketItems[i][0])) {
        String itemsDescription = marketItems[i][0] + " "
            + marketItems[i][1] + " " + marketItems[i][2];
        return itemsDescription;
      }
    }
    return "No match found";
  }

  /**
   * Returns the price in dollars of a market item given its name.
   *
   * @param name name of the product to check its price
   * @return the price of the market item being searched
   *         return -1.0 if no match was found in the market catalog
   */
  public static double getProductPrice(String name) {
    for (int i = 0; i < marketItems.length; i++) {
      if ( marketItems[i] != null && name.equals(marketItems[i][1]) ) {
        double itemPrice = Double.valueOf(marketItems[i][2].substring(1)); // substring(1) here to
        // ignore the "$" symbol
        return itemPrice;
      }
    }
    return -1.0;
  }

  /**
   * Returns a deep copy of the marketItems array
   *
   * @return a deep copy of the marketItems array
   */
  public static String[][] getCopyOfMarketItems() {
    String[][] copyOfItems = new String[marketItems.length][];
    for (int i = 0; i < marketItems.length; i++) {
      if (marketItems[i] == null) {
        copyOfItems[i] = null;
      } else {
        copyOfItems[i] = new String[marketItems[i].length];
        for (int j = 0; j < marketItems[i].length; j++) {
          copyOfItems[i][j] = marketItems[i][j];
        }
      }
    }
    return copyOfItems;
  }

  /**
   * Adding item to a given cart at the end of the items in the cart.
   * If the cart is already full (meaning its size equals its length),
   * the item will NOT be added to the cart.
   * <p>
   *
   * @param item the name of the product to be added to the cart
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the size of the oversize array cart after trying to add item to the cart.
   *          returns the same of size without making any change to the contents
   *          of the array if it is full.
   */
  public static int addItemToCart(String item, String[] cart, int size) {
    if (size == cart.length) {
      return size;
    } else {
      for (int i = 0; i < cart.length; i++) {
        if (cart[i] == null) {
          cart[i] = item;
          break;
        }
      }
    }
    return size + 1;
  }

  /**
   * Returns the number of occurrences of a given item within a cart. This
   * method must not make any changes to the contents of the cart.
   * <p>
   * @param item the name of the item to search
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the number of occurrences of item (exact match) within the oversize
   *         array cart. Zero or more occurrences of item can be present in the cart.
   */
  public static int nbOccurrences(String item, String[] cart, int size) {
    int itemNum = 0; // use this variable to keep counting the occurrences
    for (int i = 0; i < size; i++) {
      if (item.equals(cart[i])) {
        itemNum = itemNum + 1; //the number of occurrences increased by 1 each time it is found
        // on the cart
      }
    }
    return itemNum;
  }

  /**
   * Checks whether a cart contains at least one occurrence of a given item.
   * This method must not make any changes to the contents of the cart.
   *
   * @param item the name of the item to search
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return true if there is a match (exact match) of item within the provided cart,
   *       and false otherwise.
   */
  public static boolean contains(String item, String[] cart, int size) {
    for (int i = 0; i < size; i++) {
      if (item.equals(cart[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method returns the total value in dollars of the cart. All
   * products in the market are taxable (subject to TAX_RATE).
   * <p>
   *
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the total value in dollars of the cart accounting taxes.
   */
  public static double checkout(String[] cart, int size) {
    double totalNoTax = 0;
    for (int i = 0; i < size; i++) {
      totalNoTax = totalNoTax + getProductPrice(cart[i]);
    }
    double finalTotal = totalNoTax * (1 + TAX_RATE); //Get the total cost with tax
    return finalTotal;
  }

  /**
   * Removes one occurrence of item from a given cart. If no match with item
   * was found in the cart, the method returns the same value of input size
   * without making any change to the contents of the array.
   *
   * @param item the name of the item to remove
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the size of the oversize array cart after trying to remove item
   * from the cart.
   */
  public static int removeItem(String[] cart, String item, int size) {
    for (int i = 0; i < size; i++) {
      if (item.equals(cart[i])) {
        if (i == (cart.length - 1)) {
          cart[i] = null; // replace the item with null if it is at the last position of the cart
        } else {
          while (cart[i] != null) {
            cart[i] = cart[i + 1]; // shift the items in the cart to one position left until
            // get to the null position
            i++;
          }
        }
        return size - 1;
      }
    }
    return size;
  }

  /**
   * Removes all items from a given cart. The array cart must be empty (contains
   * only null references) after this method returns.
   * <p>
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return the size of the cart after removing all its items.
   */
  public static int emptyCart(String[] cart, int size) {
    for (int i = 0; i < size; i++) {
      cart[i] = null;
    }
    return 0;
  }

  /**
   * Returns a string representation of the summary of the contents of a given cart.
   * The format of the returned string contains a set of lines where each line contains
   * the number of occurrences of a given item, between parentheses, followed by
   * one space followed by the name of a unique item in the cart.
   *
   * For example :
   * (#occurrences) name1
   * (#occurrences) name2
   * etc.
   * <p>
   * @param cart an array of strings which contains the names of items in the cart
   * @param size the number of items in the cart
   * @return a string representation of the summary of the contents of the cart
   */
  public static String getCartSummary(String[] cart, int size) {
    String cartSummary = "";
    int itemCount = 0;
    String[][] deepCopy = getCopyOfMarketItems(); // using deep copy in this method instead of
    // original market items list to avoid potential damage or change to the original list
    int i = 0;
    while (deepCopy[i] != null) {
      if (contains(deepCopy[i][1], cart, size)) {
        cartSummary = cartSummary + "(" + nbOccurrences(deepCopy[i][1], cart, size) + ") " +
            deepCopy[i][1];
        itemCount = itemCount + nbOccurrences(deepCopy[i][1], cart, size);
        if (itemCount == size) {
          break; // can exit the loop advanced once the number of items were found is equal to the
          // cart items' size. Moreover, it can prevent to switch to new line after the last item's
          // statement
        }
        cartSummary = cartSummary + "\n";
      }
      i++;
    }
    return cartSummary;
  }
}
