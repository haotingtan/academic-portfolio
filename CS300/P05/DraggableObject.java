//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 5: Treasure Hunt Adventure Game - DraggableObject class
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
/**
 * This class models a draggable object.
 * A draggable object is a clickable interactive object which can follow the mouse moves
 * when being dragged.
 */
public class DraggableObject extends InteractiveObject {
  private boolean isDragging; // indicates whether this object is being dragged or not

  private int oldMouseX; // old x-position of the mouse

  private int oldMouseY; // old y-position of the mouse

  /**
   * Creates a new Draggable object with a given name,
   * x and y position, and "Drag Me!" as a default message.
   *
   * @param name name - name to be assigned to this draggable object
   * @param x x-position of this draggable object in the display window
   * @param y y-position of this draggable object in the display window
   */
  public DraggableObject(String name, int x, int y) {
    super(name, x, y, "Drag Me!");
    this.isDragging = false;
  }

  /**
   * Creates a new Draggable object with a given name, x and y position, and a
   * specific message. When created a new draggable object is NOT dragging.
   *
   * @param name name to be assigned to this draggable object
   * @param x x-position of this draggable object in the display window
   * @param y y-position of this draggable object in the display window
   * @param message message to be displayed when this draggable object is clicked.
   *                We assume that message is VALID (meaning it is NOT null and NOT
   *                an empty string).
   */
  public DraggableObject(String name, int x, int y, String message) {
    super(name, x, y, message);
    this.isDragging = false;
  }

  /**
   * Draws this draggable object to the display window. If this object is dragging,
   * this method sets its position to follow the mouse moves. Then, it draws its
   * image to its current position.
   */
  @Override
  public void draw() {
    if (isDragging()) {
      processing.image(this.image, processing.mouseX - (this.oldMouseX - this.getX()),
          processing.mouseY - (this.oldMouseY - this.getY()));
    } else {
      processing.image(this.image, this.getX(), this.getY());
    }
  }

  /**
   * Checks whether this draggable object is being dragged.
   *
   * @return true if this object is being dragged, false otherwise
   */
  public boolean isDragging() {
    return this.isDragging;
  }

  /**
   * Starts dragging this object when it is clicked (meaning when
   * the mouse is over it).
   */
  @Override
  public void mousePressed() {
    if (isMouseOver()) {
      this.startDragging();
    }
  }

  /**
   * Stops dragging this object if the mouse is released
   */
  @Override
  public void mouseReleased() {
    if (isDragging()) {
      this.stopDragging();
    }
  }

  /**
   * Starts dragging this draggable object and updates the
   * oldMouseX and oldMouseY positions to the current position of the mouse.
   */
  public void startDragging() {
    this.isDragging = true;
    this.oldMouseX = processing.mouseX;
    this.oldMouseY = processing.mouseY;
  }

  /**
   * Stops dragging this draggable object
   */
  public void stopDragging() {
    int distanceX = processing.mouseX - this.oldMouseX;
    int distanceY = processing.mouseY - this.oldMouseY;
    this.move(distanceX, distanceY);
    this.isDragging = false;
  }
}
