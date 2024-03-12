//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project 2: Water Fountain
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
import java.util.Random;
import java.io.File;
import processing.core.PImage;

/**
 * This class contains 4 callback methods which help model a fountain.
 */
public class Fountain {
  private static Random randGen;
  private static PImage fountainImage;
  private static int positionX;
  private static int positionY;
  private static Droplet[] droplets;
  private static int startColor;
  private static int endColor;

  /**
   * This method creates new droplets with amount given by the input. The properties
   * of each droplet at the beginning are randomly assigned in the following range:
   * X position of the droplet: 3 pixels (+/-) of Fountain.positionX (exclusive)
   * Y position of the droplet: 3 pixels (+/-) of Fountain.positionY (exclusive)
   * size: between 4 and 11 (exclusive)
   * color: between Fountain.startColor and Fountain.endColor
   * x velocity: between -1 and 1 (exclusive)
   * y velocity: between -5 and 10 (exclusive)
   * transparency: between 32 and 127 (inclusive)
   * age: between 0 and 40 (inclusive)
   *
   * @param numDroplets number of new droplets are needed to be created
   */
  private static void createNewDroplets(int numDroplets) {
    int numAdded = 0;
    for (int i=0; i<droplets.length; i++) {
      if (droplets[i] == null) {
        droplets[i] = new Droplet();
        droplets[i].setPositionX(positionX + randGen.nextFloat() * 6 - 3);
        droplets[i].setPositionY(positionY + randGen.nextFloat() * 6 - 3);
        droplets[i].setSize(randGen.nextFloat() * 7 + 4);
        droplets[i].setColor(Utility.lerpColor(startColor, endColor, randGen.nextFloat()));
        droplets[i].setVelocityX(randGen.nextFloat() * 2 - 1);
        droplets[i].setVelocityY(randGen.nextFloat() * 5 - 10);
        droplets[i].setTransparency(randGen.nextInt(96) + 32);
        droplets[i].setAge(randGen.nextInt(41));
        numAdded++;
        if (numAdded == numDroplets) {
          break;
        }
      }
    }
  }

  /**
   * This method does the moving, accelerating, and drawing of a droplet that
   * is specified through the provided index.
   *
   * @param index the index of a specific droplet within the droplets array
   */
  private static void updateDroplet(int index) {
    if (droplets[index] != null) {
      droplets[index].setPositionX(droplets[index].getPositionX()
          + droplets[index].getVelocityX());

      droplets[index].setVelocityY(droplets[index].getVelocityY() + 0.3f); // adding 0.3f to make
      // the droplet accelerating by the gravity effect

      droplets[index].setPositionY(droplets[index].getPositionY()
          + droplets[index].getVelocityY());

      Utility.fill(droplets[index].getColor(), droplets[index].getTransparency());

      Utility.circle(droplets[index].getPositionX(),
          droplets[index].getPositionY(), droplets[index].getSize());

      droplets[index].setAge(droplets[index].getAge() + 1);
    }
  }

  /**
   * This method searches through the droplets array for references to droplets
   * with an age that is greater than the specified max age that is given as an input,
   * and remove them (by set its reference to null).
   *
   * @param maxAge the maximum age of a droplet can be
   */
  private static void removeOldDroplets(int maxAge) {
    for (int index=0; index<droplets.length; index++) {
      if (droplets[index] != null && droplets[index].getAge() > maxAge) {
        droplets[index] = null;
      }
    }
  }

  /**
   * This tester initializes the droplets array to hold at least three droplets.
   * Creates a single droplet at position (3,3) with velocity (-1,-2). Then checks
   * whether calling updateDroplet() on this droplet’s index correctly results in
   * changing its position to (2.0, 1.3).
   *
   * @return true when no defect is found, and false otherwise
   */
  private static boolean testUpdateDroplet() {
    droplets = new Droplet[] {null, null, null, new Droplet(), new Droplet(), null};
    droplets[2] = new Droplet();
    droplets[2].setPositionX(3);
    droplets[2].setPositionY(3);
    droplets[2].setVelocityX(-1);
    droplets[2].setVelocityY(-2);
    updateDroplet(2);
    if (droplets[2].getPositionX() - 2.0 > 0.001
        || droplets[2].getPositionY() - 1.3 > 0.001) {
      return false;
    }
    return true;
  }

  /**
   * This tester initializes the droplets array to hold at least three droplets.
   * Calls removeOldDroplets(6) on an array with three droplets (two of which have
   * ages over six and another that does not). Then checks whether the old droplets
   * were removed and the young droplet was left alone.
   *
   * @return true when no defect is found, and false otherwise
   */
  private static boolean testRemoveOldDroplets() {
    droplets = new Droplet[] {new Droplet(), new Droplet(), new Droplet(), null};
    droplets[0].setAge(7);
    droplets[1].setAge(6);
    droplets[2].setAge(11);
    removeOldDroplets(6);
    if (droplets[0] != null || droplets[2] != null || droplets[1] == null) {
      return false;
    }
    return true;
  }

  /**
   * This method initializes the different data fields defined in this class(Fountain.class),
   * and configures the different graphical settings of the application, such as loading the
   * background image, setting the dimensions of the display window, etc.
   */
  public static void setup() {
    testUpdateDroplet();
    testRemoveOldDroplets();
    randGen = new Random();
    positionX = Utility.width()/2;
    positionY = Utility.height()/2;
    fountainImage = Utility.loadImage("images" + File.separator + "fountain.png");
    droplets = new Droplet[800];
    startColor = Utility.color(23,141,235); // blue
    endColor = Utility.color(23,200,255); // lighter blue
  }

  /**
   * This method continuously executes the lines of code contained inside its block
   * until the program is stopped. It continuously draws the application display
   * window and updates its content with respect to any change or any event which
   * affects its appearance.
   */
  public static void draw() {
    Utility.background(Utility.color(253,245,230));
    Utility.image(fountainImage, positionX, positionY);
    createNewDroplets(10);
    for (int index=0; index<droplets.length; index++) {
      updateDroplet(index);
    }
    removeOldDroplets(80);
  }

  /**
   * This method to move the Fountain.positionX and Fountain.positionY
   * to match the position of the mouse whenever the mouse button is pressed.
   */
  public static void mousePressed() {
    Fountain.positionX = Utility.mouseX();
    Fountain.positionY = Utility.mouseY();
  }

  /**
   * This method calls Utility.save("screenshot.png") whenever the key
   * pressed happened to be the s-key (either ’s’ or ’S’).
   * <p>
   */
  public static void keyPressed(char keyPressed) {
    if (keyPressed == 's' || keyPressed == 'S') {
      Utility.save("screenshot.png");
    }
  }

  /**
   * Main method runs the Fountain class.
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    Utility.runApplication(); // starts the application
  }
}
