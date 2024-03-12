//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 5: Treasure Hunt Adventure Game - InteractiveObject class
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
import processing.core.PImage;
import java.io.File;
import java.util.NoSuchElementException;

/**
 * This class models a clickable interactive object used
 * in cs300 Spring 2022 p05 Treasure Hunt application.
 */
public class InteractiveObject implements Clickable {

  // reference to the PApplet where this object will be drawn
  protected static TreasureHunt processing;

  private final String NAME; // name of this interactive object

  protected PImage image; // reference to the image of this object

  private int x; // x-position of this interactive object in the screen

  private int y; // y-position of this interactive object in the screen

  private boolean isActive; // tells whether or not this object is active

  private boolean wasClicked; // tells whether this object was already clicked

  private String message; // message to be displayed when this object is clicked

  // Object to be activated when this object is
  // clicked the first time, if any
  private InteractiveObject nextClue;

  /**
   * Creates a new interactive object with no next clue (nextClue == null) and sets its
   * image, name, x and y positions, its message, and activation status.
   *
   * @param name name of the new interactive object
   * @param x x coordinate of the top-left corner of the object
   * @param y y coordinate of the top-left corner of the object
   * @param message object's message
   */
  public InteractiveObject(String name, int x, int y, String message) {
    this.NAME = name;
    this.image = processing.loadImage("images" + File.separator + this.NAME + ".png");
    this.message = message;
    this.x = x;
    this.y = y;
    this.isActive = true;
    this.wasClicked = false;
    this.nextClue = null;
  }

  /**
   * Creates a new interactive object with a next clue (nextClue != null) to be activated
   * when this interactive object is clicked for the first time.
   *
   * @param name name of the new interactive object
   * @param xPosition x coordinate of the top-left corner of the object
   * @param yPosition y coordinate of the top-left corner of the object
   * @param message object's message
   */
  public InteractiveObject(String name, int xPosition, int yPosition,
      String message, InteractiveObject nextClue){
    this(name, xPosition, yPosition, message);
    this.nextClue = nextClue;
    this.nextClue.deactivate();
  }

  /**
   * Activates this interactive object, meaning setting its isActive data field to true.
   */
  public void activate() {
    this.isActive = true;
  }

  /**
   * Activates the next clue of this interactive object and adds it to the
   * treasure hunt application
   * @throws NoSuchElementException with a descriptive error message if the
   *         nextClue of this interactive object is null
   */
  protected void activateNextClue() throws NoSuchElementException {
    if (this.nextClue == null) {
      throw new NoSuchElementException("No next clue");
    }
    this.nextClue.activate();
    processing.add(this.nextClue);
  }

  /**
   * Deactivates this interactive object, meaning setting its isActive data field to false.
   */
  public void deactivate() {
    this.isActive = false;
  }

  /**
   * Draws this interactive object (meaning drawing its image)
   * to the display window at positions x and y.
   */
  @Override
  public void draw() {
      processing.image(this.image, this.x, this.y);
  }

  /**
   * Gets the x-position of this interactive object
   *
   * @return the x-position of this interactive object
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y-position of this interactive object
   *
   * @return the x-position of this interactive object
   */
  public int getY() {
    return this.y;
  }

  /**
   * Checks whether the name of this interactive object equals to
   * the name passed as input parameter.
   *
   * @param name name to be checked
   * @return true if object equals to the name passed as input parameter, false otherwise.
   */
  public boolean hasName(String name) {
    if (this.NAME.equals(name)) {
      return true;
    }
    return false;
  }

  /**
   * Checks whether this interactive object is active.
   *
   * @return true if this interactive object is active, false otherwise.
   */
  public boolean isActive() {
    if (this.isActive) {
      return true;
    }
    return false;
  }

  /**
   * Checks whether the mouse is over the image of this interactive object
   *
   * @return true if the mouse is over the image of this interactive object,
   * false otherwise.
   */
  @Override
  public boolean isMouseOver() {
    if (processing.mouseX <= (this.getX() + this.image.width)
        && processing.mouseX >= this.getX()
        && processing.mouseY <= (this.getY() + this.image.height)
          && processing.mouseY >= this.getY()) {
        return true;
    }
    return false;
  }

  /**
   * Gets the message of this interactive object.
   *
   * @return the message of this interactive object.
   */
  public String message() {
    return this.message;
  }

  /**
   * This method operates each time the mouse is pressed.
   */
  @Override
  public void mousePressed() {
    if (isMouseOver()) {
      System.out.println(this.message());
      this.wasClicked = true;
      if (this.nextClue != null) {
        this.activateNextClue();
      }
    }
  }

  /**
   * This method operates each time the mouse is released.
   */
  @Override
  public void mouseReleased() {
  }

  /**
   * Moves the current x and y positions of this
   * interactive object with dx and dy, respectively
   *
   * @param dx distance of x-position need to be moved
   * @param dy distance of y-position need to be moved
   */
  public void move(int dx, int dy) {
    this.x = this.x + dx;
    this.y = this.y + dy;
  }

  /**
   * Sets the next clue associated to this interactive object
   *
   * @param nextClue next clue associated to this interactive object
   */
  public void setNextClue(InteractiveObject nextClue) {
    this.nextClue = nextClue;
  }

  /**
   * Sets the PApplet object of the treasure hunt application where
   * all the interactive objects will be drawn.
   *
   * @param processing the PApplet object of the treasure hunt application
   */
  public static void setProcessing(TreasureHunt processing) {
    InteractiveObject.processing = processing;
  }
}
