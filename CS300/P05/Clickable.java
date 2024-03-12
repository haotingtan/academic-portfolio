//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 5: Treasure Hunt Adventure Game - Clickable interface
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
 * Clickable interface includes 4 abstract methods.
 */
public interface Clickable {
  public void draw(); // draws this Clickable object to the screen

  public void mousePressed(); // defines the behavior of this Clickable object
  // each time the mouse is pressed

  public void mouseReleased(); // defines the behavior of this Clickable object
  // each time the mouse is released

  public boolean isMouseOver(); // returns true if the mouse is over this clickable object
}
