//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P07: Iterable Song Player - ForwardSongIterator class
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
 * This class models an iterator to play songs in the forward
 * direction from a doubly linked list of songs
 */
public class ForwardSongIterator implements Iterator<Song> {

  private LinkedNode<Song> next; //reference to the next linked node in a list of nodes.

  /**
   * Creates a new iterator which iterates through songs in front/head to back/tail order
   *
   * @param first reference to the head of a doubly linked list of songs
   */
  public ForwardSongIterator(LinkedNode<Song> first) {
    this.next = first;
  }

  /**
   * Checks whether there are more songs to return
   *
   * @return true if there are more songs to return, false otherwise
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
   * @return the next song in the iteration
   * @throws NoSuchElementException with a descriptive error message
   *                                if there are no more songs to return in
   *                                the reverse order (meaning if this.hasNext() returns false)
   */
  @Override
  public Song next() throws NoSuchElementException {
    if (!hasNext()) {
      throw new NoSuchElementException("no more songs to return in the reverse order");
    }
    Song toReturn = next.getData();
    next = next.getNext();
    return toReturn;
  }
}
