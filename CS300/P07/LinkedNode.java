//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P07: Iterable Song Player - LinkedNode<T> generic class
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
 * This class creates nodes within a doubly linked list.
 */
public class LinkedNode<T> {

  private T data; //data field of this linked node
  private LinkedNode<T> prev; //reference to the previous linked node in a list of nodes
  private LinkedNode<T> next; //reference to the next linked node in a list of nodes

  /**
   * Initializes a new node with the provided information.
   *
   * @param prev node, which comes before this node in a doubly linked list
   * @param data to be stored within this node
   * @param next node, which comes after this node in a doubly linked list
   * @throws IllegalArgumentException with a descriptive error message if data is null
   */
  public LinkedNode(LinkedNode<T> prev, T data, LinkedNode<T> next)
      throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("The given data is null");
    }
    this.prev = prev;
    this.data = data;
    this.next = next;
  }

  /**
   * Accessor method for this node's data.
   *
   * @return the data stored within this node.
   */
  public T getData() {
    return this.data;
  }

  /**
   * Accessor method for this node's next node reference.
   *
   * @return the next reference to the node that comes after this one in the
   *         list, or null when this is the last node in that list
   */
  public LinkedNode<T> getNext() {
    return this.next;
  }

  /**
   * Accessor method for this node's previous node reference.
   *
   * @return the reference to the node that comes before this
   *         one in the list or null when this is the first node in that list
   */
  public LinkedNode<T> getPrev() {
    return this.prev;
  }

  /**
   * Mutator method for this node's next node reference.
   *
   * @param next node, which comes after this node in a doubly linked list
   */
  public void setNext(LinkedNode<T> next) {
    this.next = next;
  }

  /**
   * Mutator method for this node's previous node reference.
   *
   * @param prev node, which comes before this node in a doubly linked list
   */
  public void setPrev(LinkedNode<T> prev) {
    this.prev = prev;
  }
}
