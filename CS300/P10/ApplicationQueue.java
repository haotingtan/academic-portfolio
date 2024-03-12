//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Project 10 Open Position - ApplicationQueue class
// Course:   CS 300 Spring 2022
//
// Author:   Haoting Tan
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Applications. Guarantees the
 * min-heap invariant, so that the Application at the root should have the lowest score, and
 * children always have a higher or equal score as their parent. The root of a non-empty queue is
 * always at index 0 of this array-heap.
 */
public class ApplicationQueue implements PriorityQueueADT<Application>, Iterable<Application> {
  private Application[] queue; // array min-heap of applications representing this priority queue
  private int size; // size of this priority queue

  /**
   * Get the index of left child of the node at the given index.
   *
   * @param index the index of current node
   * @return the index of current node's left child
   */
  private int getLeft(int index) {
    return 2 * index + 1;
  }

  /**
   * Get the index of right child of the node at the given index.
   *
   * @param index the index of current node
   * @return the index of current node's right child
   */
  private int getRight(int index) {
    return 2 * index + 2;
  }

  /**
   * Get the index of parent of the node at the given index.
   *
   * @param index the index of current node
   * @return the index of current node's parent
   */
  private int getParent(int index) {
    return (index - 1) / 2;
  }

  /**
   * Creates a new empty ApplicationQueue with the given capacity
   *
   * @param capacity Capacity of this ApplicationQueue
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public ApplicationQueue(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException("The given capacity is not a positive number");
    }
    this.queue = new Application[capacity];
    this.size = 0;
  }

  /**
   * Checks whether this ApplicationQueue is empty
   *
   * @return {@code true} if this ApplicationQueue is empty
   */
  @Override
  public boolean isEmpty() {
    return (this.size == 0);
  }

  /**
   * Returns the size of this ApplicationQueue
   *
   * @return the size of this ApplicationQueue
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Adds the given Application to this ApplicationQueue and use the percolateUp() method to
   * maintain min-heap invariant of ApplicationQueue. Application should be compared using the
   * Application.compareTo() method.
   *
   *
   * @param o Application to add to this ApplicationQueue
   * @throws NullPointerException  if the given Application is null
   * @throws IllegalStateException with a descriptive error message if this ApplicationQueue is full
   */
  @Override
  public void enqueue(Application o) {
    // TODO verify the application
    if (o == null) {
      throw new NullPointerException("The given application is null!");
    }

    // TODO verify that the queue is not full
    if (this.size == this.queue.length) {
      throw new IllegalStateException("The queue is full!");
    }

    // TODO if allowed, add the application to the queue and percolate to restore the heap condition
    this.queue[this.size] = o;
    this.size += 1;
    percolateUp(this.size - 1); // TODO fix this argument
    return;
  }

  /**
   * Removes and returns the Application at the root of this ApplicationQueue, i.e. the Application
   * with the lowest score.
   *
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException with a descriptive error message if this ApplicationQueue is
   *                                empty
   */
  @Override
  public Application dequeue() throws NoSuchElementException {
    // TODO verify that the queue is not empty
    if (this.isEmpty()) {
      throw new NoSuchElementException("The priorityqueue is empty! You cannot dequeue!");
    }
    // TODO save the lowest-scoring application
    Application lowestApplication = this.queue[0];
    // TODO replace the root of the heap and percolate to restore the heap condition
    this.queue[0] = this.queue[this.size - 1];
    this.queue[size - 1] = null;
    this.size -= 1;
    percolateDown(0); // TODO fix this argument

    // TODO return the lowest-scoring application
    return lowestApplication; // TODO fix
  }

  /**
   * An implementation of percolateDown() method. Restores the min-heap invariant of a given subtree
   * by percolating its root down the tree. If the element at the given index does not violate the
   * min-heap invariant (it is due before its children), then this method does not modify the heap.
   * Otherwise, if there is a heap violation, then swap the element with the correct child and
   * continue percolating the element down the heap.
   *
   * This method may be implemented recursively OR iteratively.
   *
   * @param i index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateDown(int i) {
    // TODO implement the min-heap percolate down algorithm to modify the heap in place
    while (getLeft(i) < this.size) {
      int childIndex = getLeft(i);
      Application current = this.queue[i];

      while (childIndex < this.size) {
        int minIndex = -1;
        Application minApplication = current;

        for (int j = 0; j < 2 && (childIndex + j) < this.size; ++j) {
          if (this.queue[j + childIndex].compareTo(minApplication) < 0) {
            minApplication = this.queue[j + childIndex];
            minIndex = j + childIndex;
          }
        }

        if (minApplication == current) {
          return;
        } else {
          Application temp = this.queue[i];
          this.queue[i] = this.queue[minIndex];
          this.queue[minIndex] = temp;
          i = minIndex;
          childIndex = 2 * i + 1;
        }
      }
    }
    return;
  }

  /**
   * An implementation of percolateUp() method. Restores the min-heap invariant of the tree by
   * percolating a leaf up the tree. If the element at the given index does not violate the min-heap
   * invariant (it occurs after its parent), then this method does not modify the heap. Otherwise,
   * if there is a heap violation, swap the element with its parent and continue percolating the
   * element up the heap.
   *
   * This method may be implemented recursively OR iteratively.
   *
   * Feel free to add private helper methods if you need them.
   *
   * @param i index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateUp(int i) {
    // TODO implement the min-heap percolate up algorithm to modify the heap in place
    while (i > 0) {
      if (this.queue[i].compareTo(this.queue[getParent(i)]) > 0) {
        return;
      } else {
        Application temp = this.queue[i];
        this.queue[i] = this.queue[getParent(i)];
        this.queue[getParent(i)] = temp;
        i = getParent(i);
      }
    }
    return;
  }

  /**
   * Returns the Application at the root of this ApplicationQueue, i.e. the Application with the
   * lowest score.
   *
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException if this ApplicationQueue is empty
   */
  @Override
  public Application peek() {
    // TODO verify that the queue is not empty
    if(this.size == 0) {
      throw new NoSuchElementException("This queue is currently empty!");
    }

    // TODO return the lowest-scoring application
    return this.queue[0];
  }

  /**
   * Returns a deep copy of this ApplicationQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate
   * applications. Only the instance of the heap (including the array and its size) will be
   * duplicated.
   *
   * @return a deep copy of this ApplicationQueue. The returned new application queue has the same
   *         length and size as this queue.
   */
  public ApplicationQueue deepCopy() {
    // TODO implement this method according to its Javadoc comment
    ApplicationQueue newQueue = new ApplicationQueue(this.queue.length);
    for(int i = 0; i < this.size; ++i) {
      newQueue.enqueue(this.queue[i]);
    }
    return newQueue;
  }

  /**
   * Returns a String representing this ApplicationQueue, where each element (application) of the
   * queue is listed on a separate line, in order from the lowest score to the highest score.
   *
   * This implementation is provided.
   *
   * @see Application#toString()
   * @see ApplicationIterator
   * @return a String representing this ApplicationQueue
   */
  @Override
  public String toString() {
    StringBuilder val = new StringBuilder();

    for (Application a : this) {
      val.append(a).append("\n");
    }

    return val.toString();
  }

  /**
   * Returns an Iterator for this ApplicationQueue which proceeds from the lowest-scored to the
   * highest-scored Application in the queue.
   *
   * This implementation is provided.
   *
   * @see ApplicationIterator
   * @return an Iterator for this ApplicationQueue
   */
  @Override
  public Iterator<Application> iterator() {
    return new ApplicationIterator(this);
  }
}
