//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 5: Treasure Hunt Adventure Game - Button class
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
import processing.core.PApplet;

/**
 * This class implements a graphic clickable Button
 */
public class Button implements Clickable {

  private static final int WIDTH = 140; // Width of the Button
  private static final int HEIGHT = 25; // Height of the Button
  protected static PApplet processing; // PApplet object where the button will be displayed

  private int x; // x-position of this button in the display window
  private int y; // y-position of this button in the display window
  // Note that the position (x,y) of this button is the position of its left upper corner

  protected String label; // text/label that represents this button

  /**
   * Creates a new Button at a given position within a pApplet object
   *
   * @param label label to be assigned to this button
   * @param x     x-position where this button will be added to the display window
   * @param y     y-position where this button will be added to the display window
   */
  public Button(String label, int x, int y) {
    this.x = x;
    this.y = y;
    this.label = label;
  }

  /**
   * Sets the PApplet display window where this button is displayed and handled
   *
   * @param processing the processing to set
   */
  public static void setProcessing(PApplet processing) {
    Button.processing = processing;
  }


  /**
   * Draws this button to the display window
   */
  @Override
  public void draw() {
    processing.stroke(0);// set line value to black
    if (isMouseOver()) {
      // set the fill color to dark blue if the mouse is over the button
      processing.fill(processing.color(0, 100, 205));
    } else { // set the fill color to light blue otherwise
      processing.fill(processing.color(153, 205, 255));
    }
    // draw the button (rectangle with a centered text)
    processing.rect(x, y, x + WIDTH, y + HEIGHT);
    if (isMouseOver()) {
      processing.fill(255); // set the fill color to white
    } else {
      processing.fill(0); // set the fill color to black
    }
    // display the text of the current button
    processing.text(label, x + WIDTH / 2.0f, y + HEIGHT / 2.0f);
  }

  /**
   * Implements the default behavior of this button when the mouse is pressed. This method must be
   * overridden in the sub-classes to implement a specific behavior if needed.
   */
  @Override
  public void mousePressed() {
    if (isMouseOver())
      System.out.println("A button was pressed.");
  }

  /**
   * Implements the default behavior of this button when the mouse is released. This method must be
   * overridden in the sub-classes to implement a specific behavior if needed.
   */
  @Override
  public void mouseReleased() {
  }

  /**
   * Checks whether the mouse is over this button
   *
   * @return true if the mouse is over this button, false otherwise.
   */
  @Override
  public boolean isMouseOver() {
    if (processing.mouseX > x && processing.mouseY > y
        && processing.mouseX < x+WIDTH
        && processing.mouseY < y+HEIGHT) {
      return true;
    }
    return false;
  }
}
