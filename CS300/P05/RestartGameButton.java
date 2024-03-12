//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 5: Treasure Hunt Adventure Game - RestartGameButton class
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
 * This class models a restart game button in the Treasure Hunt adventure style game application
 */
public class RestartGameButton extends Button {
  /**
   * Creates a new RestartGameButton object labeled "Restart Game"
   * at a specific position on the screen
   *
   * @param x x-position to be assigned to this restart game button
   * @param y y-position to be assigned to this restart game button
   */
  public RestartGameButton(int x, int y) {
    super("Restart Game", x, y);
  }

  /**
   * Defines the behavior of this button when the mouse is pressed.
   * This button initializes the game if it is clicked (meaning if
   * the mouse is over it)
   */
  public void mousePressed() {
    if (isMouseOver()) {
      ((TreasureHunt) processing).initGame();
    }
  }
}
