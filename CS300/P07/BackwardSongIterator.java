//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P07: Iterable Song Player - BackwardSongIterator class
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
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class models an iterator to play songs in the reverse
 * backward direction from a doubly linked list of songs
 */
public class BackwardSongIterator implements Iterator<Song> {

  private LinkedNode<Song> next; //reference to the next linked node in a list of nodes

  /**
   * Creates a new iterator which iterates through songs in back/tail to front/head order
   *
   * @param last reference to the tail of a doubly linked list of songs
   */
  public BackwardSongIterator(LinkedNode<Song> last) {
    this.next = last;
  }

  /**
   * Checks whether there are more songs to return in the reverse order
   *
   * @return true if there are more songs to return in the reverse order, false otherwise
   */
  @Override
  public boolean hasNext() {
    if (this.next != null) {
      return true;
    }
    return false;
  }

  /**
   * Returns the next song in the iteration
   *
   * @return next song in the iteration
   * @throws NoSuchElementException with a descriptive error message if
   *                                there are no more songs to return in the reverse
   *                                order (meaning if this.hasNext() returns false)
   */
  public Song next() throws NoSuchElementException {
    if (!hasNext()) {
      throw new NoSuchElementException("no more songs to return in the reverse order");
    }
    Song toReturn = next.getData();
    next = next.getPrev();
    return toReturn;
  }
}
