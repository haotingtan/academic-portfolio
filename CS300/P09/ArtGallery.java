//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P09: Art Gallery - ArtGallery class
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
import java.util.NoSuchElementException;

/**
 * A binary search tree version of the Artwork Gallery, where all artwork pieces are sorted based
 * on increasing order with respect to the result of Artwork.compareTo() method. Artwork should be
 * compared first based on year, then by the lexical order of name
 *
 * @author Haoting Tan
 */
public class ArtGallery {

  private BSTNode<Artwork> root; // root node of the artwork catalog BST
  private int size; // size of the artwork catalog tree

  /**
   * Checks whether this binary search tree (BST) is empty
   *
   * @return true if this ArtworkGallery is empty, false otherwise
   */
  public boolean isEmpty() {
    if (this.root == null || this.size == 0) {
      return true;
    }
    return false;
  }

  /**
   * Returns the number of artwork pieces stored in this BST.
   *
   * @return the size of this ArtworkGallery
   */
  public int size() {
    return this.size;
  }

  /**
   * Checks whether this ArtworkGallery contains an Artwork given its name, year, and cost.
   *
   * @param name name of the Artwork to search
   * @param year year of creation of the Artwork to search
   * @param cost cost of the Artwork to search
   * @return true if there is a match with this Artwork in this BST, and false otherwise
   */
  public boolean lookup(String name, int year, double cost) {
    Artwork searchArtwork = new Artwork(name, year, cost);
    return lookupHelper(searchArtwork, root);
  }

  /**
   * Recursive helper method to search whether there is a match with a given Artwork in the subtree
   * rooted at current
   *
   * @param target  a reference to a Artwork we are searching for a match in the BST rooted at
   *                current.
   * @param current "root" of the subtree we are checking whether it contains a match to target.
   * @return true if match found and false otherwise
   */
  protected static boolean lookupHelper(Artwork target, BSTNode<Artwork> current) {
    if (current == null || target == null) {
      return false;
    }
    if (current.getData().compareTo(target) == 0) {
      return true;
    } else {
      if (current.getData().compareTo(target) > 0) {
        return lookupHelper(target, current.getLeft());
      } else {
        return lookupHelper(target, current.getRight());
      }
    }
  }

  /**
   * Adds a new artwork piece to this ArtworkGallery
   *
   * @param newArtwork a new Artwork to add to this BST (gallery of artworks).
   * @return true if the newArtwork was successfully added to this gallery, and returns false if
   *         there is a match with this Artwork already stored in gallery.
   * @throws NullPointerException if newArtwork is null
   */
  public boolean addArtwork(Artwork newArtwork) throws NullPointerException {
    if (newArtwork == null) {
      throw new NullPointerException("The newArtwork is null");
    }
    if (isEmpty()) {
      root = new BSTNode<>(newArtwork);
      size++;
      return true;
    }
    if (addArtworkHelper(newArtwork, root)) {
      size++;
      return true;
    }
    return false;
  }

  /**
   * Recursive helper method to add a new Artwork to an ArtworkGallery rooted at current.
   *
   * @param current    The "root" of the subtree we are inserting new Artwork into.
   * @param newArtwork The Artwork to be added to a BST rooted at current.
   * @return true if the newArtwork was successfully added to this ArtworkGallery, false if a match
   *         with newArtwork is already present in the subtree rooted at current.
   */
  protected static boolean addArtworkHelper(Artwork newArtwork, BSTNode<Artwork> current) {
    if (current.getData().equals(newArtwork)) {
      return false;
    } else if (current.getData().compareTo(newArtwork) < 0) {
      if (current.getRight() == null) {
        current.setRight(new BSTNode<>(newArtwork));
        return true;
      }
      return addArtworkHelper(newArtwork, current.getRight());
    } else {
      if (current.getLeft() == null) {
        current.setLeft(new BSTNode<>(newArtwork));
        return true;
      }
      return addArtworkHelper(newArtwork, current.getLeft());
    }
  }

  /**
   * Gets the recent best Artwork in this BST (meaning the largest artwork in this gallery)
   *
   * @return the best (largest) Artwork (the most recent, highest cost artwork) in this
   *         ArtworkGallery, and null if this tree is empty.
   */
  public Artwork getBestArtwork() {
    if (this.size == 0 || this.root == null) {
      return null;
    }
    BSTNode<Artwork> curr = this.root;
    while (curr.getRight() != null) {
      curr = curr.getRight();
    }
    return curr.getData();
  }

  /**
   * Returns a String representation of all the artwork stored within this BST in the increasing
   * order of year, separated by a newline "\n". For instance
   *
   * "[(Name: Stars, Artist1) (Year: 1988) (Cost: 300)]" + "\n" + "[(Name: Sky, Artist1) (Year:
   * 2003) (Cost: 550)]" + "\n"
   *
   * @return a String representation of all the artwork stored within this BST sorted in an
   *         increasing order with respect to the result of Artwork.compareTo() method (year, cost,
   *         name). Returns an empty string "" if this BST is empty.
   */
  @Override
  public String toString() {
    return toStringHelper(root).trim();
  }

  /**
   * Recursive helper method which returns a String representation of the BST rooted at current. An
   * example of the String representation of the contents of a ArtworkGallery is provided in the
   * description of the above toString() method.
   *
   * @param current reference to the current Artwork within this BST (root of a subtree)
   * @return a String representation of all the artworks stored in the sub-tree rooted at current in
   *         increasing order with respect to the result of Artwork.compareTo() method (year, cost,
   *         name). Returns an empty String "" if current is null.
   */
  protected static String toStringHelper(BSTNode<Artwork> current) {
    if (current == null || current.getData() == null) {
      return "";
    } else {
      return toStringHelper(current.getLeft()) +
          current.getData().toString() + "\n" + toStringHelper(current.getRight());
    }
  }

  /**
   * Computes and returns the height of this BST, counting the number of NODES from root to the
   * deepest leaf.
   *
   * @return the height of this Binary Search Tree
   */
  public int height() {
    if (root == null || isEmpty()) {
      return 0;
    } else {
      return heightHelper(root) + 1;
    }
  }

  /**
   * Recursive helper method that computes the height of the subtree rooted at current counting the
   * number of nodes and NOT the number of edges from current to the deepest leaf
   *
   * @param current pointer to the current BSTNode within a ArtworkGallery (root of a subtree)
   * @return height of the subtree rooted at current
   */
  protected static int heightHelper(BSTNode<Artwork> current) {
    if (current == null) {
      return -1;
    }
    return 1 + Math.max(heightHelper(current.getLeft()), heightHelper(current.getRight()));
  }

  /**
   * Search for all artwork objects created on a given year and have a maximum cost value.
   *
   * @param year creation year of artwork
   * @param cost the maximum cost we would like to search for an artwork
   * @return a list of all the artwork objects whose year equals our lookup year key and maximum
   *         cost. If no artwork satisfies the lookup query, this method returns an empty arraylist
   */
  public ArrayList<Artwork> lookupAll(int year, double cost) {
    return lookupAllHelper(year, cost, root);
  }

  /**
   * Recursive helper method to lookup the list of artworks given their year of creation and a
   * maximum value of cost
   *
   * @param year        the year we would like to search for a artwork
   * @param cost        the maximum cost we would like to search for an artwork
   * @param current     "root" of the subtree we are looking for a match to find within it.
   * @return a list of all the artwork objects whose year equals our lookup year key and maximum
   *         cost stored in the subtree rooted at current. If no artwork satisfies the lookup query,
   *         this method returns an empty arraylist
   */
  protected static ArrayList<Artwork> lookupAllHelper(int year, double cost,
      BSTNode<Artwork> current) {
    ArrayList<Artwork> satisfiedWork = new ArrayList<>();
    if (current == null) {
      return satisfiedWork;
    }
    if (current.getData().getYear() == year && current.getData().getCost() <= cost) {
      satisfiedWork.add(current.getData());
    }
    satisfiedWork.addAll(lookupAllHelper(year, cost, current.getLeft()));
    satisfiedWork.addAll(lookupAllHelper(year, cost, current.getRight()));
    return satisfiedWork;
  }

  /**
   * Buy an artwork with the specified name, year and cost. In terms of BST operation, this is
   * equivalent to finding the specific node and deleting it from the tree
   *
   * @param name name of the artwork, artist
   * @param year creation year of artwork
   * @throws NoSuchElementException with a descriptive error message if there is no Artwork found
   *           with the buying criteria
   */
  public void buyArtwork(String name, int year, double cost) throws NoSuchElementException {
    try {
      Artwork artwork = new Artwork(name, year, cost);
      root = buyArtworkHelper(artwork, root);
      size--;
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("no Artwork found with the buying criteria");
    }
  }

  /**
   * Recursive helper method to buy artwork given the name, year and cost. In terms of BST
   * operation, this is equivalent to finding the specific node and deleting it from the tree
   *
   * @param target  a reference to a Artwork we are searching to remove in the BST rooted at
   *                current.
   * @param current "root" of the subtree we are checking whether it contains a match to target.
   * @return the new "root" of the subtree we are checking after trying to remove target
   * @throws NoSuchElementException with a descriptive error message if there is no Artwork found
   *           with the buying criteria in the BST rooted at current
   */
  protected static BSTNode<Artwork> buyArtworkHelper(Artwork target, BSTNode<Artwork> current)
      throws NoSuchElementException {
    // if current == null (empty subtree rooted at current), no match found, throw an exception
    if (current == null) {
      throw new NoSuchElementException("No match found");
    }

    // Compare the target to the data at current and proceed accordingly
    // Recurse on the left or right subtree with respect to the comparison result
    if (current.getData().compareTo(target) < 0) {
      current.setRight(buyArtworkHelper(target, current.getRight()));
    } else if (current.getData().compareTo(target) > 0) {
      current.setLeft(buyArtworkHelper(target, current.getLeft()));
    } else {
      // if match with target found, three cases should be considered.
      if (current.getLeft() == null && current.getRight() == null) {
        // current may be a leaf (has no children)
        current = null;
      } else if (current.getLeft() != null && current.getRight() == null) {
        // current may have only one child, set current to that child (left child)
        current = current.getLeft();
      } else if (current.getLeft() == null && current.getRight() != null) {
        // current may have only one child, set current to that child (right child)
        current = current.getRight();
      } else {
        // current may have two children,
        Artwork buyArtworkSuccessor = getSuccessor(current);
        current.setRight(buyArtworkHelper(buyArtworkSuccessor, current.getRight()));
        BSTNode<Artwork> leftChildCurr = current.getLeft();
        BSTNode<Artwork> rightChildCurr = current.getRight();
        current = new BSTNode<>(buyArtworkSuccessor, leftChildCurr, rightChildCurr);
      }
    }
    return current;
  }

  /**
   * Helper method to find the successor of a node in a BST (to be used by the delete operation).
   * The successor is defined as the smallest key in the right subtree. We assume by default that
   *  node is not null. If node does not have a right child, return node.getData().
   *
   * @param node node whose successor is to be found in the tree
   * @return returns without removing the key of the successor node
   */
  protected static Artwork getSuccessor(BSTNode<Artwork> node) {
    if (node.getRight() == null) {
      return node.getData();
    }
    BSTNode<Artwork> curr = node.getRight();
    while (curr.getLeft() != null) {
      curr = curr.getLeft();
    }
    return curr.getData();
  }
}
