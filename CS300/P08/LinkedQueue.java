//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P08: DNA Transcription - LinkedQueue<T> generic class
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
 * A generic class that shows implementation of a linked queue
 */
public class LinkedQueue<T> implements QueueADT<T> {
  protected Node<T> back; // The node at the back of the queue, added MOST recently
  protected Node<T> front; // The node at the front of the queue, added LEAST recently
  private int n; // The number of elements in the queue

  /**
   * Removes and returns the item from this queue that was least recently added
   *
   * @return the item from this queue that was least recently added
   * @throws NoSuchElementException with descriptive message if this queue is empty
   */
  @Override
  public T dequeue() throws NoSuchElementException {
    if (this.n == 0 || this.front == null) {
      throw new NoSuchElementException("this queue is empty");
    }
    T front = this.front.getData();
    if (this.n == 1) {
      this.front = null;
      this.back = null;
      this.n = 0;
    } else {
      this.front = this.front.getNext();
      this.n--;
    }
    return front;
  }

  /**
   * Adds the given data to this queue; every addition to a queue is made at the back
   *
   * @param data the data to add
   */
  @Override
  public void enqueue(T data) {
    Node newNode = new Node(data);
    if (this.n == 0) {
      this.front = newNode;
      this.back = front;
    } else {
      this.back.setNext(newNode);
      this.back = newNode;
    }
    this.n++;
  }

  /**
   * Checks whether the queue contains any elements
   *
   * @return true if this queue is empty; false otherwise
   */
  @Override
  public boolean isEmpty() {
    if (this.front == null || this.back == null || this.n == 0) {
      return true;
    }
    return false;
  }

  /**
   * Returns the item least recently added to this queue without removing it
   *
   * @return the item least recently added to this queue
   * @throws NoSuchElementException with descriptive message if this queue is empty
   */
  @Override
  public T peek() throws NoSuchElementException {
    if (this.n == 0 || this.front == null) {
      throw new NoSuchElementException("this queue is empty");
    }
    return this.front.getData();
  }

  /**
   * Returns the number of items in this queue
   *
   * @return the number of items in this queue
   */
  @Override
  public int size() {
    return this.n;
  }

  /**
   * Returns a string representation of this queue, beginning at the
   * front (least recently added) of the queue and ending at the back
   * (most recently added). An empty queue is represented as an empty
   * string; otherwise items in the queue are represented using their
   * data separated by spaces
   *
   * @return the sequence of items in FIFO order, separated by spaces
   */
  @Override
  public String toString() {
    String queueList = "";
    if (isEmpty()) {
      return queueList;
    }
    Node<T> curr = this.front;
    for (int i=0; i<this.n; i++) {
      queueList += curr.getData() + " ";
      curr = curr.getNext();
    }
    return queueList.trim();
  }
}
