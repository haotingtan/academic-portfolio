//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P09: Art Gallery - ArtGalleryTester class
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
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * This class checks the correctness of the implementation of the methods defined in the class
 * ArtworkGallery.
 *
 * @author Haoting Tan
 */
public class ArtGalleryTester {

  /**
   * Checks the correctness of the implementation of both compareTo() and equals() methods defined
   * in the Artwork class.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testArtworkCompareToEquals() {
    try {
      // 1. All the elements are the same
      {
        Artwork testArtWork1 = new Artwork("NewDesign", 1800, 19000);
        Artwork testArtWork2 = new Artwork("NewDesign", 1800, 19000);
        if (testArtWork1.compareTo(testArtWork2) != 0 || !testArtWork1.equals(testArtWork2)) {
          System.out.println("Problem detected: Your Artwork class return the "
              + "unexpected result(Case 1)");
          return false;
        }
      }

      // 2. Compare with different price
      {
        Artwork testArtWork1 = new Artwork("HALILUYA", 1800, 80000);
        Artwork testArtWork2 = new Artwork("HALILUYA", 1800, 19000);
        if (testArtWork1.compareTo(testArtWork2) <= 0 || !testArtWork1.equals(testArtWork2)) {
          System.out.println("Problem detected: Your Artwork class return the "
              + "unexpected result(Case 2)");
          return false;
        }
      }

      // 3. Compare with different price and year
      {
        Artwork testArtWork1 = new Artwork("DIFF", 2000, 78998);
        Artwork testArtWork2 = new Artwork("DIFF", 1800, 19000);
        if (testArtWork1.compareTo(testArtWork2) <= 0 || testArtWork1.equals(testArtWork2)) {
          System.out.println("Problem detected: Your Artwork class return the "
              + "unexpected result(Case 3)");
          return false;
        }
      }

      // 4. Compare with different name with same year and cost
      {
        Artwork testArtWork1 = new Artwork("SAMEname", 1800, 80000);
        Artwork testArtWork2 = new Artwork("samename", 1800, 80000);
        if (testArtWork1.compareTo(testArtWork2) >= 0 || testArtWork1.equals(testArtWork2)) {
          System.out.println("Problem detected: Your Artwork class return the "
              + "unexpected result(Case 4)");
          return false;
        }
      }

      // 5. using equals with an object that is not an instance of Artwork
      {
        Artwork testArtWork1 = new Artwork("SAMEname", 1800, 80000);
        Integer wrongObject = new Integer(10);
        if (testArtWork1.equals(wrongObject)) {
          System.out.println("Problem detected: Your Artwork class return the "
              + "unexpected result(Case 5)");
          return false;
        }
      }

      // 6. Compare with same name, higher year, lower cost
      {
        Artwork testArtWork1 = new Artwork("What", 2300, 2000);
        Artwork testArtWork2 = new Artwork("what", 1800, 80000);
        if (testArtWork1.compareTo(testArtWork2) <= 0 || testArtWork1.equals(testArtWork2)) {
          System.out.println("Problem detected: Your Artwork class return the "
              + "unexpected result(Case 6)");
          return false;
        }
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your Artwork class throws unexpected exception "
          + e.getClass());
    }

    return true; // No BUGs detected in compareTo() and equals()
  }

  /**
   * Checks the correctness of the implementation of both addArtwork() and toString() methods
   * implemented in the ArtworkGallery class. This unit test considers at least the following
   * scenarios. (1) Create a new empty ArtworkGallery, and check that its size is 0, it is empty,
   * and that its string representation is an empty string "". (2) try adding one artwork and then
   * check that the addArtwork() method call returns true, the tree is not empty, its size is 1, and
   * the .toString() called on the tree returns the expected output. (3) Try adding another artwork
   * which is smaller that the artwork at the root, (4) Try adding a third artwork which is greater
   * than the one at the root, (5) Try adding at least two further artwork such that one must be
   * added at the left subtree, and the other at the right subtree. For all the above scenarios, and
   * more, double check each time that size() method returns the expected value, the add method call
   * returns true, and that the .toString() method returns the expected string representation of the
   * contents of the binary search tree in an increasing order from the smallest to the greatest
   * artwork with respect to year, cost, and then name. (6) Try adding an artwork already stored in
   * the tree. Make sure that the addArtwork() method call returned false, and that the size of the
   * tree did not change.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddArtworkToStringSize() {
    // 1. pass the newArtwork is null and throws NullPointerException
    {
      try {
        Artwork testArtwork = null;
        ArtGallery testArtGallery = new ArtGallery();
        testArtGallery.addArtwork(testArtwork);
        System.out.println("Problem detected: Your addArtwork does NOT throw exception "
            + "when newArtwork is null(case 1)");
        return false;
      } catch (NullPointerException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your addArtwork throws the wrong exception "
            + "when new Artwork is null(case 1)");
        return false;
      }
    }

    try {
      ArtGallery testArtGallery = new ArtGallery();

      // 2. check that its size is 0, it is empty, and that its string
      // representation is an empty string ""
      if (!testArtGallery.toString().equals("")) {
        System.out.println("Problem detected: Your toString method "
            + "return unexpected result when size is 0(case 2)");
        return false;
      }

      // 3. adding one artwork and then check that the addArtwork() and toString()
      // method return expected result
      Artwork testArtwork1 = new Artwork("firstOne", 2022, 1.11);
      if (!testArtGallery.addArtwork(testArtwork1)) {
        System.out.println("Problem detected: Your addArtwork() return unexpected result "
            + "when adding an artwork to an empty artGallery(case 3:1)");
        return false;
      }
      if (!testArtGallery.toString().trim().equals("[(Name: firstOne) "
          + "(Year: 2022) (Cost: $1.11)]")) {
        System.out.println("Your toString():\n" + testArtGallery.toString());
        System.out.println("Problem detected: Your toString() return unexpected result "
            + "when adding an artwork to an empty artGallery(case 3:2)");
        return false;
      }
      if (testArtGallery.isEmpty() || testArtGallery.size() != 1) {
        System.out.println("Problem detected: Your artGallery has wrong size "
            + "when adding an artwork to an empty artGallery(case 3:3)");
        return false;
      }

      // 4. adding another artwork which is smaller that the artwork at the root
      Artwork testArtwork2 = new Artwork("SecondOne", 2000, 3000);
      if (!testArtGallery.addArtwork(testArtwork2)) {
        System.out.println("Problem detected: Your addArtwork() return unexpected result "
            + "when adding an artwork to an artGallery with one artwork in it(case 4:1)");
        return false;
      }
      String actualCase4 = testArtGallery.toString();
      if (!actualCase4.trim().equals("[(Name: SecondOne) (Year: 2000) (Cost: $3000.0)]\n"
          + "[(Name: firstOne) (Year: 2022) (Cost: $1.11)]")) {
        System.out.println("Your toString():\n" + testArtGallery.toString());
        System.out.println("Problem detected: Your toString() return unexpected result "
            + "when adding an artwork to an artGallery with one artwork in it(case 4:2)");
        return false;
      }
      if (testArtGallery.isEmpty() || testArtGallery.size() != 2) {
        System.out.println("Problem detected: Your artGallery has wrong size "
            + "when adding an artwork to an artGallery with one artwork in it(case 4:3)");
        return false;
      }

      // 5. adding a third artwork which is greater than the one at the root
      Artwork testArtwork3 = new Artwork("ThirdOne", 2042, 2000);
      if (!testArtGallery.addArtwork(testArtwork3)) {
        System.out.println("Problem detected: Your addArtwork() return unexpected result "
            + "when adding an artwork to an artGallery with one artwork in it(case 5:1)");
        return false;
      }
      String actualCase5 = testArtGallery.toString();
      if (!actualCase5.trim().equals("[(Name: SecondOne) (Year: 2000) (Cost: $3000.0)]\n"
          + "[(Name: firstOne) (Year: 2022) (Cost: $1.11)]\n"
          + "[(Name: ThirdOne) (Year: 2042) (Cost: $2000.0)]")) {
        System.out.println("Your toString():\n" + testArtGallery.toString());
        System.out.println("Problem detected: Your toString() return unexpected result "
            + "when adding an artwork to an artGallery with one artwork in it(case 5:2)");
        return false;
      }
      if (testArtGallery.isEmpty() || testArtGallery.size() != 3) {
        System.out.println("Problem detected: Your artGallery has wrong size "
            + "when adding an artwork to an artGallery with one artwork in it(case 5:3)");
        return false;
      }

      // 6. adding six further artworks such that
      // 4 added at the left subtree, and the other 2 at the right subtree.
      Artwork testArtwork4 = new Artwork("FourthOne", 1900, 2000);
      Artwork testArtwork5 = new Artwork("FifthOne", 1900, 2000);
      Artwork testArtwork6 = new Artwork("SixthOne", 1800, 2000);
      Artwork testArtwork7 = new Artwork("SeventhOne", 1850, 20000);
      Artwork testArtwork8 = new Artwork("EighthOne", 2022, 1000);
      Artwork testArtwork9 = new Artwork("NinthOne", 2032, 1500);
      if (!testArtGallery.addArtwork(testArtwork4)
          || !testArtGallery.addArtwork(testArtwork5)
          || !testArtGallery.addArtwork(testArtwork6)
          || !testArtGallery.addArtwork(testArtwork7)
          || !testArtGallery.addArtwork(testArtwork8)
          || !testArtGallery.addArtwork(testArtwork9)) {
        System.out.println("Problem detected: Your addArtwork() return unexpected result "
            + "when adding an valid artwork to an artGallery(case 6:1)");
        return false;
      }
      String actualCase6 = testArtGallery.toString();
      if (!actualCase6.trim().equals("[(Name: SixthOne) (Year: 1800) (Cost: $2000.0)]\n" +
          "[(Name: SeventhOne) (Year: 1850) (Cost: $20000.0)]\n" +
          "[(Name: FifthOne) (Year: 1900) (Cost: $2000.0)]\n" +
          "[(Name: FourthOne) (Year: 1900) (Cost: $2000.0)]\n" +
          "[(Name: SecondOne) (Year: 2000) (Cost: $3000.0)]\n" +
          "[(Name: firstOne) (Year: 2022) (Cost: $1.11)]\n" +
          "[(Name: EighthOne) (Year: 2022) (Cost: $1000.0)]\n" +
          "[(Name: NinthOne) (Year: 2032) (Cost: $1500.0)]\n" +
          "[(Name: ThirdOne) (Year: 2042) (Cost: $2000.0)]" )) {
        System.out.println("Your toString():\n" + testArtGallery.toString());
        System.out.println("Problem detected: Your toString() return unexpected result "
            + "when adding more than one artworks to an artGallery with "
            + "one artwork in it(case 6:2)");
        return false;
      }
      if (testArtGallery.isEmpty() || testArtGallery.size() != 9) {
        System.out.println("Problem detected: Your artGallery has wrong size "
            + "when adding more than one artworks to an artGallery (case 6:3)");
        return false;
      }

      // 7. adding an artwork already stored in the tree.
      Artwork testArtwork10 = new Artwork("firstOne", 2022, 1.11);
      Artwork testArtwork11 = new Artwork("SeventhOne", 1850, 20000);
      if (testArtGallery.addArtwork(testArtwork10) || testArtGallery.addArtwork(testArtwork11)) {
        System.out.println("Problem detected: Your addArtwork() return unexpected result "
            + "when adding an repeated artwork to an artGallery(case 7:1)");
        return false;
      }
      String actualCase7 = testArtGallery.toString();
      if (!actualCase7.trim().equals("[(Name: SixthOne) (Year: 1800) (Cost: $2000.0)]\n" +
          "[(Name: SeventhOne) (Year: 1850) (Cost: $20000.0)]\n" +
          "[(Name: FifthOne) (Year: 1900) (Cost: $2000.0)]\n" +
          "[(Name: FourthOne) (Year: 1900) (Cost: $2000.0)]\n" +
          "[(Name: SecondOne) (Year: 2000) (Cost: $3000.0)]\n" +
          "[(Name: firstOne) (Year: 2022) (Cost: $1.11)]\n" +
          "[(Name: EighthOne) (Year: 2022) (Cost: $1000.0)]\n" +
          "[(Name: NinthOne) (Year: 2032) (Cost: $1500.0)]\n" +
          "[(Name: ThirdOne) (Year: 2042) (Cost: $2000.0)]" )) {
        System.out.println("Your toString():\n" + testArtGallery.toString());
        System.out.println("Problem detected: Your toString() return unexpected result "
            + "when adding an artwork that already exists to an artGallery with "
            + "one artwork in it(case 7:2)");
        return false;
      }
      if (testArtGallery.isEmpty() || testArtGallery.size() != 9) {
        System.out.println("Problem detected: Your artGallery has wrong size "
            + "when adding an artworks that already exists to an artGallery (case 7:3)");
        return false;
      }

    } catch (Exception e) {
      System.out.println("Problem detected: Your addArtwork or toString "
          + "method throws unexpected exception when testing: " + e.getClass());
      return false;
    }

    return true; // No BUGs detected in addArtwork() and toString()
  }

  /**
   * This method checks mainly for the correctness of the ArtworkGallery.lookup() method. It must
   * consider at least the following test scenarios. (1) Create a new ArtworkGallery. Then, check
   * that calling the lookup() method on an empty ArtworkGallery returns false. (2) Consider a
   * ArtworkGallery of height 3 which lookup at least 5 artwork. Then, try to call lookup() method
   * to search for the artwork having a match at the root of the tree. (3) Then, search for an
   * artwork at the right and left subtrees at different levels considering successful and
   * unsuccessful search operations. Make sure that the lookup() method returns the expected output
   * for every method call.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testLookup() {
    try {
      ArtGallery testArtGallery = new ArtGallery();

      // 1. calling the lookup() method on an empty ArtworkGallery
      if (testArtGallery.lookup("ShouldBeNothing", 1500, 0.1)) {
        System.out.println("Problem detected: Your lookup method return unexpected"
            + " result when searching an empty ArtworkGallery(Case 1)");
        return false;
      }

      Artwork testArtwork1 = new Artwork("firstOne", 2022, 1.11);
      Artwork testArtwork2 = new Artwork("SecondOne", 2000, 3000);
      Artwork testArtwork3 = new Artwork("ThirdOne", 2042, 2000);
      Artwork testArtwork4 = new Artwork("FourthOne", 1900, 2000);
      Artwork testArtwork5 = new Artwork("FifthOne", 1900, 2000);
      Artwork testArtwork6 = new Artwork("SixthOne", 1800, 2000);
      Artwork testArtwork7 = new Artwork("SeventhOne", 1850, 20000);
      Artwork testArtwork8 = new Artwork("EighthOne", 2022, 1000);
      Artwork testArtwork9 = new Artwork("NinthOne", 2032, 1500);
      if (!testArtGallery.addArtwork(testArtwork1) ||
          !testArtGallery.addArtwork(testArtwork2) ||
          !testArtGallery.addArtwork(testArtwork3) ||
          !testArtGallery.addArtwork(testArtwork4) ||
          !testArtGallery.addArtwork(testArtwork5) ||
          !testArtGallery.addArtwork(testArtwork6) ||
          !testArtGallery.addArtwork(testArtwork7) ||
          !testArtGallery.addArtwork(testArtwork8) ||
          !testArtGallery.addArtwork(testArtwork9)) {
        System.out.println("Some problem with your addArtwork method, check this method first");
        return false;
      }

      // 2. call lookup() method to search for the artwork having a match at the root of the tree
      Artwork search1 = new Artwork("firstOne", 2022, 1.11);
      if (!testArtGallery.lookup(search1.getName(), search1.getYear(), search1.getCost())
          || testArtGallery.size() != 9) {
        System.out.println("Problem detected: Your lookup() method return unexpected result "
            + "when searching an artwork that is the root of the subtree(Case 2)");
        return false;
      }

      // 3. search for an artwork at the right and left subtrees at different levels
      // considering successful and unsuccessful search operations.
      Artwork search2 = new Artwork("SeventhOne", 1850, 20000);
      Artwork search3 = new Artwork("NinthOne", 2022, 1590);
      Artwork search4 = new Artwork("NINTHOne", 2022, 1500);
      if (!testArtGallery.lookup(search2.getName(), search2.getYear(), search2.getCost()) ||
          testArtGallery.lookup(search3.getName(), search3.getYear(), search3.getCost()) ||
          testArtGallery.lookup(search4.getName(), search4.getYear(), search4.getCost())
          || testArtGallery.size() != 9) {
        System.out.println("Problem detected: Your lookup() method return unexpected result "
            + "(case 3)");
        return false;
      }

    } catch (Exception e) {
      System.out.println("Problem detected: Your ArtworkGallery.lookup() "
          + "method throws unexpected exception when testing: " + e.getClass());
      return false;
    }

    return true; // No BUGs detected in lookup() method
  }

  /**
   * Checks for the correctness of ArtworkGallery.height() method. This test must consider several
   * scenarios such as, (1) ensures that the height of an empty artwork tree is zero. (2) ensures
   * that the height of a tree which consists of only one node is 1. (3) ensures that the height of
   * a ArtworkGallery with the following structure for instance, is 4.
   *               (*)
   *              /  \
   *            (*)  (*)
   *             \   / \
   *            (*) (*) (*)
   *                    /
   *                   (*)
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testHeight() {
    try {
      ArtGallery testArtGallery = new ArtGallery();

      // 1. ensures that the height of an empty artwork tree is zero
      if (testArtGallery.height() != 0) {
        System.out.println("Problem detected: Your height() method return "
            + "unexpected result with an empty artwork tree(Case 1)");
        return false;
      }

      // 2. ensures that the height of a tree which consists of only one node is 1
      Artwork testArtwork1 = new Artwork("Guernica, Picasso", 1937, 3000);
      if (!testArtGallery.addArtwork(testArtwork1) || testArtGallery.size() != 1) {
        System.out.println("Some problem with your addArtwork method, check this method first");
        return false;
      }
      if (testArtGallery.height() != 1) {
        System.out.println("Problem detected: Your height() method return "
            + "unexpected result with an artwork tree with only a root(Case 2)");
        return false;
      }

      // 3. ensures that the height of a ArtworkGallery with the following structure
      // for instance, is 4.
      //                (*)
      //               /  \
      //             (*)  (*)
      //              \   / \
      //             (*) (*) (*)
      //                     /
      //                    (*)
      Artwork testArtwork2 = new Artwork("Mona Lisa, DaVinci", 1871, 1000.0);
      Artwork testArtwork3 = new Artwork("Whistler, Abbott", 1932, 5000);
      Artwork testArtwork4 = new Artwork("NightHawks, Hopper", 1942, 4000);
      Artwork testArtwork5 = new Artwork("Guernica, Pi", 1939, 3000);
      Artwork testArtwork6 = new Artwork("Amazone, Tsalapatanis", 2021, 6080);
      Artwork testArtwork7 = new Artwork("I just need One more", 2020, 9000);
      if (!testArtGallery.addArtwork(testArtwork2) ||
          !testArtGallery.addArtwork(testArtwork3) ||
          !testArtGallery.addArtwork(testArtwork4) ||
          !testArtGallery.addArtwork(testArtwork5) ||
          !testArtGallery.addArtwork(testArtwork6) ||
          !testArtGallery.addArtwork(testArtwork7) || testArtGallery.size() != 7) {
        System.out.println("Some problem with your addArtwork method, check this method first");
        return false;
      }
      if (testArtGallery.height() != 4) {
        System.out.println("Problem detected: Your height() method return unexpected result "
            + "when the height of tree is supposed to be 4(Case 3)");
        System.out.println("Your return height(): " + testArtGallery.height());
        return false;
      }

    } catch (Exception e) {
      System.out.println("Problem detected: Your ArtworkGallery.height() "
          + "method throws unexpected exception when testing: " + e.getClass());
      return false;
    }

    return true; // No BUGs detected in ArtworkGallery.height() method
  }

  /**
   * Checks for the correctness of ArtworkGallery.getBestArtwork() method.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetBestArtwork() {
    try {
      ArtGallery testArtGallery = new ArtGallery();

      // 1. return null if this tree is empty
      if (testArtGallery.getBestArtwork() != null) {
        System.out.println("Problem detected: Your getBestArtwork() method return "
            + "unexpected result when the tree is empty(Case 1)");
        return false;
      }

      // 2. check when the tree only contains a root
      Artwork testArtwork1 = new Artwork("Guernica, Picasso", 1937, 3000);
      testArtGallery.addArtwork(testArtwork1);
      if (testArtGallery.getBestArtwork() != testArtwork1 || testArtGallery.size() != 1) {
        System.out.println("Problem detected: Your getBestArtwork() method return unexpected "
            + "result when the tree contains only a root(Case 2)");
        return false;
      }

      // 3. check when the tree has multiple nodes but the root is the largest one
      Artwork testArtwork2 = new Artwork("Mona Lisa, DaVinci", 1503, 1000.0);
      Artwork testArtwork3 = new Artwork("Whistler, Abbott", 1871, 5000);
      Artwork testArtwork4 = new Artwork("NightHawks, Hopper", 1889, 2000);
      testArtGallery.addArtwork(testArtwork2);
      testArtGallery.addArtwork(testArtwork3);
      testArtGallery.addArtwork(testArtwork4);
      if (testArtGallery.getBestArtwork() != testArtwork1 || testArtGallery.size() != 4) {
        System.out.println("Problem detected: Your getBestArtwork() method return "
            + "unexpected result when the tree has multiple nodes but the root is the "
            + "biggest one(Case 3)");
        return false;
      }

      // 4. check when the tree has multiple nodes and the rightest one has left child
      Artwork testArtwork5 = new Artwork("Amazone, Tsalapatanis", 2021, 6080);
      Artwork testArtwork6 = new Artwork("I am the createst", 1940, 6000);
      testArtGallery.addArtwork(testArtwork5);
      testArtGallery.addArtwork(testArtwork6);
      if (testArtGallery.getBestArtwork() != testArtwork5 || testArtGallery.size() != 6) {
        System.out.println("Problem detected: Your getBestArtwork() method return "
            + "unexpected result(Case 4)");
        return false;
      }

      // 5. check when the tree has multiple nodes and the rightest one doesn't have any children
      Artwork testArtwork7 = new Artwork("I am the biggest", 2022, 60000);
      testArtGallery.addArtwork(testArtwork7);
      if (testArtGallery.getBestArtwork() != testArtwork7 || testArtGallery.size() != 7) {
        System.out.println("Problem detected: Your getBestArtwork() method return "
            + "unexpected result(Case 5)");
        return false;
      }

    } catch (Exception e) {
      System.out.println("Problem detected: Your ArtworkGallery.getBestArtwork() "
          + "method throws unexpected exception when testing: " + e.getClass());
      return false;
    }

    return true; // No BUGs detected in getBestArtwork() method
  }


  /**
   * Checks for the correctness of ArtworkGallery.lookupAll() method. This test must consider at
   * least 3 test scenarios. (1) Ensures that the ArtworkGallery.lookupAll() method returns an
   * empty arraylist when called on an empty tree. (2) Ensures that the
   * ArtworkGallery.lookupAll() method returns an array list which contains all the artwork satisfying
   * the search criteria of year and cost, when called on a non empty artwork tree with one match,
   * and two matches and more. Vary your search criteria such that the lookupAll() method must check
   * in left and right subtrees. (3) Ensures that the ArtworkGallery.lookupAll() method returns an
   * empty arraylist when called on a non-empty artwork tree with no search results found.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testLookupAll() {
    try {
      ArtGallery testArtGallery = new ArtGallery();

      // 1. returns an empty arraylist when called on an empty tree.
      {
        ArrayList<Artwork> expectedArtworks = new ArrayList<>();
        if (!testArtGallery.lookupAll(1999, 1999).equals(expectedArtworks)) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result when the tree is empty(Case 1)");
          return false;
        }
      }

      Artwork testArtwork1 = new Artwork("Guernica, Picasso", 1937, 3000);
      testArtGallery.addArtwork(testArtwork1);

      // 2. check if lookupAll() return expected artwork when only contains a root
      {
        ArrayList<Artwork> expectedArtworks = new ArrayList<>();
        expectedArtworks.add(testArtwork1);
        if (!testArtGallery.lookupAll(1937, 3000).equals(expectedArtworks)) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result(Case 2)");
          return false;
        }
      }

      Artwork testArtwork2 = new Artwork("Mona Lisa, DaVinci", 1937, 1000.0);
      Artwork testArtwork3 = new Artwork("Whistler, Abbott", 1937, 2540);
      Artwork testArtwork4 = new Artwork("NightHawks, Hopper", 1937, 4000);
      Artwork testArtwork5 = new Artwork("Guernica, Pi", 1937, 9000);
      Artwork testArtwork6 = new Artwork("Amazone, Tsalapatanis", 2021, 6080);
      Artwork testArtwork7 = new Artwork("I just need One more", 2020, 9000);
      testArtGallery.addArtwork(testArtwork1);
      testArtGallery.addArtwork(testArtwork2);
      testArtGallery.addArtwork(testArtwork3);
      testArtGallery.addArtwork(testArtwork4);
      testArtGallery.addArtwork(testArtwork5);
      testArtGallery.addArtwork(testArtwork6);
      testArtGallery.addArtwork(testArtwork7);

      // 3. check if lookupAll() return expected artworks exactly one artwork satisfies
      {
        ArrayList<Artwork> expectedArtworks = new ArrayList<>();
        expectedArtworks.add(testArtwork2);
        if (!testArtGallery.lookupAll(1937, 1500).equals(expectedArtworks)) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result(Case 3)");
          return false;
        }
      }

      // 4. check if lookupAll() return expected artworks two artwork satisfy
      {
        ArrayList<Artwork> actualArtworks = testArtGallery.lookupAll(1937, 2550);
        if (!actualArtworks.contains(testArtwork2) || !actualArtworks.contains(testArtwork3)
            || actualArtworks.size() != 2) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result(Case 4)");
          return false;
        }
      }

      // 5. check if lookupAll() return expected artworks three artwork satisfy
      {
        ArrayList<Artwork> actualArtworks = testArtGallery.lookupAll(1937, 3002);
        if (!actualArtworks.contains(testArtwork1) || !actualArtworks.contains(testArtwork2)
            || !actualArtworks.contains(testArtwork3) || actualArtworks.size() != 3) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result(Case 5)");
          return false;
        }
      }

      // 6. check if lookupAll() return expected artworks five artwork satisfy
      {
        ArrayList<Artwork> actualArtworks = testArtGallery.lookupAll(1937, 9000);
        if (!actualArtworks.contains(testArtwork1) || !actualArtworks.contains(testArtwork2)
            || !actualArtworks.contains(testArtwork3) || !actualArtworks.contains(testArtwork4)
            || !actualArtworks.contains(testArtwork5)|| actualArtworks.size() != 5) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result(Case 6)");
          return false;
        }
      }

      // 7. returns an empty arraylist when called on a non-empty artwork tree with no
      // search results found
      {
        ArrayList<Artwork> actualArtworks = testArtGallery.lookupAll(2020, 8999);
        if (!actualArtworks.equals(new ArrayList<Artwork>())) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result when no match found (Case 7:1)");
          return false;
        }
      }
      {
        ArrayList<Artwork> actualArtworks = testArtGallery.lookupAll(2022, 9000);
        if (!actualArtworks.equals(new ArrayList<Artwork>())) {
          System.out.println("Problem detected: Your lookupAll() method return unexpected "
              + "result when no match found (Case 7:2)");
          return false;
        }
      }

    } catch (Exception e) {
      System.out.println("Problem detected: Your ArtworkGallery.lookupAll() "
          + "method throws unexpected exception when testing: " + e.getClass());
      return false;
    }

    return true; // No BUGs detected in lookupAll() method.
  }

  /**
   * Checks for the correctness of ArtworkGallery.buyArtwork() method. This test must consider at
   * least 3 test scenarios. (1) Buying artwork that is at leaf node (2) Buying artwork at non-leaf
   * node (3) ensures that the ArtworkGallery.buyArtwork() method throws a NoSuchElementException
   * when called on an artwork that is not present in the BST
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testBuyArtwork() {
    try {
      ArtGallery testArtGallery = new ArtGallery();
      Artwork testArtwork1 = new Artwork("Guernica, Picasso", 1937, 3000);
      Artwork testArtwork2 = new Artwork("Mona Lisa, DaVinci", 1871, 1000.0);
      Artwork testArtwork3 = new Artwork("Whistler, Abbott", 1932, 5000);
      Artwork testArtwork4 = new Artwork("NightHawks, Hopper", 1942, 4000);
      Artwork testArtwork5 = new Artwork("Guernica, Pi", 1939, 3000);
      Artwork testArtwork6 = new Artwork("Amazone, Tsalapatanis", 2021, 6080);
      Artwork testArtwork7 = new Artwork("I just need One more", 2020, 9000);
      testArtGallery.addArtwork(testArtwork1);
      testArtGallery.addArtwork(testArtwork2);
      testArtGallery.addArtwork(testArtwork3);
      testArtGallery.addArtwork(testArtwork4);
      testArtGallery.addArtwork(testArtwork5);
      testArtGallery.addArtwork(testArtwork6);
      testArtGallery.addArtwork(testArtwork7);

      String originalCopy = "[(Name: Mona Lisa, DaVinci) (Year: 1871) (Cost: $1000.0)]\n" +
          "[(Name: Whistler, Abbott) (Year: 1932) (Cost: $5000.0)]\n" +
          "[(Name: Guernica, Picasso) (Year: 1937) (Cost: $3000.0)]\n" +
          "[(Name: Guernica, Pi) (Year: 1939) (Cost: $3000.0)]\n" +
          "[(Name: NightHawks, Hopper) (Year: 1942) (Cost: $4000.0)]\n" +
          "[(Name: I just need One more) (Year: 2020) (Cost: $9000.0)]\n" +
          "[(Name: Amazone, Tsalapatanis) (Year: 2021) (Cost: $6080.0)]\n";

      // 1. Buying artwork at non-leaf node
      testArtGallery.buyArtwork("NightHawks, Hopper", 1942, 4000);
      String expected1 = "[(Name: Mona Lisa, DaVinci) (Year: 1871) (Cost: $1000.0)]\n" +
          "[(Name: Whistler, Abbott) (Year: 1932) (Cost: $5000.0)]\n" +
          "[(Name: Guernica, Picasso) (Year: 1937) (Cost: $3000.0)]\n" +
          "[(Name: Guernica, Pi) (Year: 1939) (Cost: $3000.0)]\n" +
          "[(Name: I just need One more) (Year: 2020) (Cost: $9000.0)]\n" +
          "[(Name: Amazone, Tsalapatanis) (Year: 2021) (Cost: $6080.0)]";
      if (!testArtGallery.toString().trim().equals(expected1) || testArtGallery.size() != 6) {
        System.out.println("Problem detected: Your buyArtwork method return "
            + "the unexpected result(Case 1)");
        System.out.println("Your output: \n" + testArtGallery.toString());
        return false;
      }

      // 2. Buying artwork that is at leaf node
      testArtGallery.buyArtwork("Amazone, Tsalapatanis", 2021, 6080);
      String expected2 = "[(Name: Mona Lisa, DaVinci) (Year: 1871) (Cost: $1000.0)]\n" +
          "[(Name: Whistler, Abbott) (Year: 1932) (Cost: $5000.0)]\n" +
          "[(Name: Guernica, Picasso) (Year: 1937) (Cost: $3000.0)]\n" +
          "[(Name: Guernica, Pi) (Year: 1939) (Cost: $3000.0)]\n" +
          "[(Name: I just need One more) (Year: 2020) (Cost: $9000.0)]";
      if (!testArtGallery.toString().trim().equals(expected2) || testArtGallery.size() != 5) {
        System.out.println("Problem detected: Your buyArtwork method return "
            + "the unexpected result(Case 2)");
        System.out.println("Your output: \n" + testArtGallery.toString());
        return false;
      }

      // 3. Buying artwork that is the root of the tree
      testArtGallery.buyArtwork("Guernica, Picasso", 1937, 3000);
      String expected3 = "[(Name: Mona Lisa, DaVinci) (Year: 1871) (Cost: $1000.0)]\n" +
          "[(Name: Whistler, Abbott) (Year: 1932) (Cost: $5000.0)]\n" +
          "[(Name: Guernica, Pi) (Year: 1939) (Cost: $3000.0)]\n" +
          "[(Name: I just need One more) (Year: 2020) (Cost: $9000.0)]";
      if (!testArtGallery.toString().trim().equals(expected3) || testArtGallery.size() != 4) {
        System.out.println("Problem detected: Your buyArtwork method return "
            + "the unexpected result(Case 3)");
        System.out.println("Your output: \n" + testArtGallery.toString());
        return false;
      }

      // 4. throws a NoSuchElementException when called on an artwork
      // that is not present in the BST
      try {
        testArtGallery.buyArtwork("nonexist", 1939, 3000);
        if (testArtGallery.size() != 4) {
          System.out.println("Problem detected: Your buyArtwork return "
              + "unexpected size when no match found(Case 4)");
          return false;
        }
        System.out.println("Problem detected: Your buyArtwork method does NOT throw "
            + "exception when an artwork that is not present in the BST(Case 4)");
        return false;
      } catch (NoSuchElementException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your buyArtwork method throws the wrong "
            + "exception when an artwork that is not present in the BST(Case 4)");
        return false;
      }

      // 5. buy an artwork which successor has a right child
      Artwork testArtwork8 = new Artwork("EgithOne", 1940, 4000);
      Artwork testArtwork9 = new Artwork("NInthOne hey", 2000, 4560);
      Artwork testArtwork10 = new Artwork("Wonderfunful", 1960, 4560);
      Artwork testArtwork11 = new Artwork("onemore", 2010, 7645);
      testArtGallery.addArtwork(testArtwork8);
      testArtGallery.addArtwork(testArtwork9);
      testArtGallery.addArtwork(testArtwork10);
      testArtGallery.addArtwork(testArtwork11);
      testArtGallery.buyArtwork("Guernica, Pi", 1939, 3000);
      String expected5 = "[(Name: Mona Lisa, DaVinci) (Year: 1871) (Cost: $1000.0)]\n" +
          "[(Name: Whistler, Abbott) (Year: 1932) (Cost: $5000.0)]\n" +
          "[(Name: EgithOne) (Year: 1940) (Cost: $4000.0)]\n" +
          "[(Name: Wonderfunful) (Year: 1960) (Cost: $4560.0)]\n" +
          "[(Name: NInthOne hey) (Year: 2000) (Cost: $4560.0)]\n" +
          "[(Name: onemore) (Year: 2010) (Cost: $7645.0)]\n" +
          "[(Name: I just need One more) (Year: 2020) (Cost: $9000.0)]";
      if (!testArtGallery.toString().trim().equals(expected5) ||testArtGallery.size() != 7) {
        System.out.println("Problem detected: Your buyArtwork method return "
            + "the unexpected result(Case 5)");
        System.out.println("Your output: \n" + testArtGallery.toString());
        return false;
      }

    } catch (Exception e) {
      System.out.println("Problem detected: Your ArtworkGallery.buyArtwork() "
          + "method throws unexpected exception when testing: " + e.getClass());
      e.printStackTrace();
      return false;
    }

    return true; // No BUGs detected in buyArtwork()
  }

  /**
   * Returns false if any of the tester methods defined in this tester class fails.
   *
   * @return false if any of the tester methods defined in this tester class fails, and true if all
   *         tests pass
   */
  public static boolean runAllTests() {
    boolean result = true;
    if (!testArtworkCompareToEquals()) {
      System.out.println("testArtworkCompareToEquals(): false");
      result = false;
    }
    if (!testAddArtworkToStringSize()) {
      System.out.println("testAddArtworkToStringSize(): false");
      result = false;
    }
    if (!testLookup()) {
      System.out.println("testLookup(): false");
      result = false;
    }
    if (!testHeight()) {
      System.out.println("testHeight(): false");
      result = false;
    }
    if (!testGetBestArtwork()) {
      System.out.println("testGetBestArtwork(): false");
      result = false;
    }
    if (!testLookupAll()) {
      System.out.println("testLookupAll(): false");
      result = false;
    }
    if (!testBuyArtwork()) {
      System.out.println("testBuyArtwork(): false");
      result = false;
    }

    return result;
  }

  /**
   * Calls the test methods
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("runAllTests(): " + runAllTests());
  }
}
