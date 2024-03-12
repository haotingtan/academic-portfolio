//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P07: Iterable Song Player - SongPlayer class
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
 * This class models an iterable collection of songs.
 * Songs can be played in the forward or reverse order.
 */
public class SongPlayer implements Iterable<Song> {

  private int size; //size of the list
  private LinkedNode<Song> head; //head of this doubly linked list
  private LinkedNode<Song> tail; //tail of this doubly linked list
  private boolean playingBackward; //true if this song player is reading the list backward

  /**
   * Creates a new instance of song player which contains zero
   * songs and set by default to play songs in the forward direction.
   */
  public SongPlayer() {
    this.size = 0;
    this.head = null;
    this.tail = null;
    this.playingBackward = false;
  }

  /**
   * adds Song at a given position/order within this collection of songs
   *
   * @param index the given index where the new song will be added
   * @param oneSong the song that is going to be added
   * @throws NullPointerException with a descriptive error message if the passed oneSong is null
   * @throws IndexOutOfBoundsException with a descriptive error message if index is
   *                                   out of the 0 .. size() range
   */
  public void add(int index, Song oneSong) throws NullPointerException, IndexOutOfBoundsException {
    if (oneSong == null) {
      throw new NullPointerException("the passed oneSong is null");
    }
    if (index < 0 || index > this.size ) {
      throw new IndexOutOfBoundsException("index is out of the 0 .. size() range");
    }

    if (this.isEmpty()) {
      head = new LinkedNode<Song>(null, oneSong, null);
      tail = head;
    } else if (index == 0) {
      addFirst(oneSong);
      return;
    } else if (index == size) {
      addLast(oneSong);
      return;
    } else {
      // add song at the middle of the list
      LinkedNode<Song> curr = head;
      for (int i=0; i<index-1; i++) {
        curr = curr.getNext();
      }
      LinkedNode<Song> afterCurr = curr.getNext();
      LinkedNode<Song> newNode = new LinkedNode<>(curr, oneSong, afterCurr);
      afterCurr.setPrev(newNode);
      curr.setNext(newNode);
    }
    size++;
  }

  /**
   * add Song as First Song
   *
   * @param oneSong the song that is going to be added to the
   *                head of this doubly linked list of songs
   * @throws NullPointerException with a descriptive error message if the passed oneSong is null
   */
  public void addFirst(Song oneSong) throws NullPointerException {
    if (oneSong == null) {
      throw new NullPointerException("the passed oneSong is null");
    }
    if (this.size == 0) {
      LinkedNode<Song> newNode1 = new LinkedNode<>(null, oneSong, null);
      this.head = newNode1;
      this.tail = this.head;
      size++;
    } else {
      LinkedNode<Song> newNode2 = new LinkedNode<>(null, oneSong, this.head);
      this.head.setPrev(newNode2);
      this.head = newNode2;
      size++;
    }
  }

  /**
   * Adds a Song as Last Song
   *
   * @param oneSong the song that is going to be added to the tail of
   *                this doubly linked list of songs
   */
  public void addLast(Song oneSong) {
    if (this.size==0) {
      LinkedNode<Song> newNode1 = new LinkedNode<>(null, oneSong, null);
      this.head = newNode1;
      this.tail = this.head;
      size++;
      return;
    } else {
      LinkedNode<Song> newNode2 = new LinkedNode<>(this.tail, oneSong, null);
      tail.setNext(newNode2);
      this.tail = newNode2;
      size++;
    }
  }

  /**
   * Removes all of the songs from this list.
   * The list will be empty after this call returns.
   */
  public void clear() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  /**
   * Returns true if this list contains a match with the specified song. More formally,
   * returns true if and only if this list contains at least one song e such that
   * Objects.equals(o, e).
   *
   * @param o song whose presence in this list is to be tested
   * @return true if this list contains a match with the specified song, false otherwise
   */
  public boolean contains(Song o) {
    LinkedNode<Song> curr = head;
    for (int i=0; i<size; i++) {
      if (curr.getData().equals(o)) {
        return true;
      }
      curr = curr.getNext();
    }
    return false;
  }

  /**
   * Returns the song at the specified position in this list.
   *
   * @param index index of the song to return
   * @return the song at the specified position in this list
   * @throws IndexOutOfBoundsException with a descriptive error message if index is out
   *                                   of the 0 .. size()-1 range
   */
  public Song get(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index > (size-1)) {
      throw new IndexOutOfBoundsException("index is out of the 0 .. size()-1 range");
    }
    LinkedNode<Song> curr = head;
    for (int i=0; i<index; i++) {
      curr = curr.getNext();
    }
    return curr.getData();
  }

  /**
   * Returns the first Song in this list.
   *
   * @return the Song at the head of this list
   * @throws NoSuchElementException with a descriptive error message if this list is empty
   */
  public Song getFirst() {
    if (size == 0 || this.head == null) {
      throw new NoSuchElementException("this list is empty");
    }
    return head.getData();
  }

  /**
   * Returns the last Song in this list.
   *
   * @return the Song at the tail of this list
   * @throws NoSuchElementException with a descriptive error message if this list is empty
   */
  public Song getLast() {
    if (size == 0 || this.tail == null) {
      throw new NoSuchElementException("this list is empty");
    }
    return tail.getData();
  }

  /**
   * Returns true if this list is empty.
   *
   * @return true if this list is empty, false otherwise
   */
  public boolean isEmpty() {
    if (size == 0) {
      return true;
    }
    return false;
  }

  /**
   * Returns an iterator to iterate through the songs in this list
   * with respect to current playing direction of this song player
   * (either in the forward or in the backward direction)
   *
   * @return an Iterator to traverse the list of songs in this SongPlayer
   *         with respect to the current playing direction specified by the
   *         playingBackward data field
   */
  @Override
  public Iterator<Song> iterator() {
    if (!this.playingBackward) {
      return new ForwardSongIterator(this.head);
    }
    return new BackwardSongIterator(this.tail);
  }

  /**
   * Plays the songs in this song player in the current playing direction.
   * This method MUST be implemented using an enhanced for-each loop.
   *
   * @return a String representation of the songs in this song player.
   *         String representations of each song are separated by a newline.
   *         If this song player is empty, this method returns an empty string.
   */
  public String play() {
    String songList = "";
    if (size == 0 || this.head == null || this.tail == null) {
      return songList;
    }
    for (Song playSong : this) {
      songList += playSong.toString() + "\n";
    }
    return songList.trim();
  }

  /**
   * Removes the song at the specified position in this list and returns the
   * song that was removed from the list. The order of precedence of the other
   * songs in the list should not be modified.
   *
   * @param index the index of the song to be removed
   * @return the song previously at the specified position
   * @throws IndexOutOfBoundsException with a descriptive error message if index
   *                                   is out of the 0 .. size()-1 range
   */
  public Song remove(int index) throws IndexOutOfBoundsException {
    if ( index < 0 || index >= size ) {
      throw new IndexOutOfBoundsException("index is out of the 0 .. size()-1 range");
    }
    if (this.isEmpty()) {
      return null;
    } else if (index == 0) {
      return removeFirst();
    } else if (index == size-1) {
      return removeLast();
    } else {
      // remove song at the middle of the list
      LinkedNode<Song> curr = head;
      for (int i = 0; i < index; i++) {
        curr = curr.getNext();
      }
      Song removeSong = curr.getData();
      curr.getNext().setPrev(curr.getPrev());
      curr.getPrev().setNext(curr.getNext());
      size--;
      return removeSong;
    }
  }

  /**
   * Removes and returns the first song from this list.
   *
   * @return the first song from this list
   * @throws NoSuchElementException with a descriptive error message if this list is empty
   */
  public Song removeFirst() throws NoSuchElementException {
    if (this.size == 0 || this.head == null) {
      throw new NoSuchElementException("this list is empty");
    }
    Song firstSong = head.getData();
    if (this.size == 1) {
      this.size--;
      this.head = null;
      this.tail = null;
      return firstSong;
    }
    head.getNext().setPrev(null);
    head = head.getNext();
    size--;
    return firstSong;
  }

  /**
   * Removes and returns the last song from this list.
   *
   * @return the last song from this list
   * @throws NoSuchElementException with a descriptive error message if this list is empty
   */
  public Song removeLast() throws NoSuchElementException {
    if (this.size == 0 || this.tail == null) {
      throw new NoSuchElementException("this list is empty");
    }
    Song lastSong = tail.getData();
    if (this.size == 1) {
      this.head = null;
      this.tail = null;
      this.size--;
      return lastSong;
    }
    tail.getPrev().setNext(null);
    tail = tail.getPrev();
    size--;
    return lastSong;
  }

  /**
   * Returns the number of songs in this list.
   *
   * @return the number of songs in this list
   */
  public int size() {
    return this.size;
  }

  /**
   * Mutator of the playingDirection of this song player.
   * It switches the playing direction by setting playingBackward
   * to its opposite value.
   */
  public void switchPlayingDirection() {
    this.playingBackward = !this.playingBackward;
  }
}
