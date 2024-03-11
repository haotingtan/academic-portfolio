// --== CS400 File Header Information ==--
// Name: Haoting Tan
// Email: htan47@wisc.edu
// Notes to Grader: None

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.text.DecimalFormat;

/**
 * Front end of the navigation system
 * @author Haoting Tan
 */
public class NavigationFrontEnd {

  // format for cost, time, and distance, two decimal places
  private static final DecimalFormat df = new DecimalFormat("0.00");
  private static NavigationBackEnd navigationSystem;
  private static int setupTimes = 0; // number of times of setup

  /**
   * This create a new navigation system that implements the data from external files
   * @throws FileNotFoundException if file not found
   */
  public static void setup() throws FileNotFoundException {
    navigationSystem = new NavigationBackEnd();
  }

  /**
   * taking user input to navigate them from one place to another place
   */
  public static void navigate() {
    boolean exit = false;
    boolean getUserStart = false;
    String startLocation = "";
    while (!getUserStart) {
      System.out.println("Enter your start location below: (or enter \"x\" back to main menu)");
      Scanner inputStart = new Scanner(System.in);
      startLocation = inputStart.nextLine();
      if (startLocation == null || startLocation.isBlank()) {
        System.out.println("Can't have null or blank input, please try again");
        System.out.println();
        continue;
      }
      if (startLocation.trim().toLowerCase().equals("x")) {
        exit = true;
        break;
      }
      if (!navigationSystem.locations.containsKey(startLocation.trim())) {
        System.out.println("Can't find the location in our system, please try again");
        System.out.println();
        continue;
      } else {
        getUserStart = true;
        startLocation = startLocation.trim();
      }
    }
    if (exit) {
      return;
    }

    boolean getUserEnd = false;
    String endLocation = "";
    while (!getUserEnd) {
      System.out.println("Enter your destination location below: (or enter \"x\" "
          + "back to main menu)");
      Scanner inputStart = new Scanner(System.in);
      endLocation = inputStart.nextLine();
      if (endLocation == null || endLocation.isBlank()) {
        System.out.println("Can't have null or blank input, please try again");
        System.out.println();
        continue;
      }
      if (endLocation.trim().toLowerCase().equals("x")) {
        exit = true;
        break;
      }
      if (!navigationSystem.locations.containsKey(endLocation.trim())) {
        System.out.println("Can't find the location in our system, please try again");
        System.out.println();
        continue;
      } else {
        getUserEnd = true;
        endLocation = endLocation.trim();
      }
    }
    if (exit) {
      return;
    }

    System.out.println();
    if (startLocation.equals(endLocation)) {
      System.out.println("You are right there, will be back to the main menu");
      System.out.println();
      return;
    }
    char[] sequence = new char[]{'c', 't', 'd'};
    for (int i=0; i<sequence.length; i++) {
      Path shortestPath = null;
      List<Road> roadSequence = null;
      try {
        shortestPath =
            navigationSystem.dijkstrasShortestPath(startLocation, endLocation, sequence[i]);
        roadSequence = shortestPath.roadSequence;
      } catch (NoSuchElementException e) {
        System.out.println("Sorry, no Path found");
        return;
      }
      if (i==0) {
        double time = 0.0;
        double distance = 0.0;
        for (int j=0; j<roadSequence.size(); j++) {
          time = time + roadSequence.get(j).time;
          distance = distance + roadSequence.get(j).distance;
        }
        System.out.println("1. Lowest Cost Path: $" + df.format(shortestPath.weight));
      }
      if (i==1) {
        double cost = 0.0;
        double distance = 0.0;
        for (int j=0; j<roadSequence.size(); j++) {
          cost = cost + roadSequence.get(j).cost;
          distance = distance + roadSequence.get(j).distance;
        }
        System.out.println("2. Lowest Time Path: " + df.format(shortestPath.weight) + " minutes");
      }
      if (i==2) {
        double cost = 0.0;
        double time = 0.0;
        for (int j=0; j<roadSequence.size(); j++) {
          cost = cost + roadSequence.get(j).cost;
          time = time + roadSequence.get(j).time;
        }
        System.out.println("3. Lowest Distance Path: "  + df.format(shortestPath.weight) + " km");
      }
    }
    System.out.println("choose one option above: 1, 2, or 3:");
    boolean routeChoose = false;
    int routeNum = 0;
    while (!routeChoose) {
      Scanner input = new Scanner(System.in);
      String userChose = input.nextLine();
      if (userChose == null || userChose.isBlank()) {
        continue;
      }
      try {
        routeNum = Integer.parseInt(userChose.trim());
        if (routeNum > 3 || routeNum<1) {
          System.out.println("Invalid Number Input");
          continue;
        }
        routeChoose = true;
      } catch (NumberFormatException e) {
        System.out.println("Invalid Input");
        continue;
      }
    }
    System.out.println("Your chosen path information shown below: ");
    System.out.println(startLocation);
    if (routeNum == 1) {
      List<Road> roadSequence
          = navigationSystem.dijkstrasShortestPath(startLocation, endLocation, 'c').roadSequence;
      for (int i=0; i<roadSequence.size(); i++) {
        System.out.println("--" + roadSequence.get(i).roadName + "--");
      }
    } else if (routeNum == 2) {
      List<Road> roadSequence
          = navigationSystem.dijkstrasShortestPath(startLocation, endLocation, 't').roadSequence;
      for (int i=0; i<roadSequence.size(); i++) {
        System.out.println("--" + roadSequence.get(i).roadName + "--");
      }
    } else if (routeNum == 3) {
      List<Road> roadSequence
          = navigationSystem.dijkstrasShortestPath(startLocation, endLocation, 'd').roadSequence;
      for (int i=0; i<roadSequence.size(); i++) {
        System.out.println("--" + roadSequence.get(i).roadName + "--");
      }
    }
    System.out.println(endLocation);
    System.out.println();
    boolean canExit = false;
    while (!canExit) {
      System.out.println("Type anything other than spaces to go back to main screen");
      Scanner input = new Scanner(System.in);
      String content = input.nextLine();
      if (content == null || content.isBlank()) {
        continue;
      } else {
        canExit = true;
      }
    }
  }

  /**
   * this is function for update location's address, road's cost, time, or distance. Taking input
   * from user.
   */
  public static void update() {
    System.out.println("1. update location's address");
    System.out.println("2. update road's cost");
    System.out.println("3. update road's time");
    System.out.println("4. update road's distance");
    boolean getInput = false;
    int userInput = 0;
    while (!getInput) {
      Scanner input = new Scanner(System.in);
      try {
        userInput = input.nextInt();
        if (userInput <1 || userInput >4) {
          System.out.println("Invalid input, try again");
          continue;
        }
        getInput = true;
      } catch (Exception e) {
        System.out.println("Invalid input, try again");
        continue;
      }
    }

    // update address
    if (userInput == 1) {
      System.out.println("Enter the location name that needed to change address");
      boolean getLocation = false;
      String locationName ="";
      while (!getLocation) {
        Scanner input = new Scanner(System.in);
        locationName = input.nextLine();
        if (locationName == null || locationName.isBlank()) {
          System.out.println("can't read the input, try again");
          continue;
        }
        if (!navigationSystem.containsLocation(locationName.trim())) {
          System.out.println("can't find this location, try again");
          continue;
        }
        getLocation = true;
      }

      boolean getNewAddress = false;
      String newAddress = "";
      while (!getNewAddress) {
        System.out.println("Please enter the new address: ");
        Scanner input = new Scanner(System.in);
        newAddress = input.nextLine();
        if (locationName == null || locationName.isBlank()) {
          System.out.println("invalid address format");
          continue;
        }
        if (navigationSystem.updateLocationAddress(locationName.trim(), newAddress.trim())) {
          getNewAddress = true;
          System.out.println("Successfully update address");
          System.out.println();
          return;
        } else {
          System.out.println("encounter problem updating address");
          System.out.println();
          return;
        }
      }
    }

    boolean getRoad = false;
    String roadName = "";
    while (!getRoad) {
      System.out.println("Please enter the road name below:");
      Scanner input = new Scanner(System.in);
      roadName = input.nextLine();
      if (!navigationSystem.containsRoad(roadName)) {
        System.out.println("Can't find this road in our system");
        continue;
      } else {
        getRoad = true;
      }
    }

    String[] updateItem = new String[]{"cost", "time", "distance"};
    boolean getNewData = false;
    double newData;
    while (!getNewData) {
      System.out.println("Please Enter the new " + updateItem[userInput-2]);
      Scanner input = new Scanner(System.in);
      try {
        newData = input.nextDouble();
      } catch (Exception e) {
        System.out.println("Invalid input, try again");
        continue;
      }
      if (newData < 0) {
        System.out.println("can't have a data item <0");
        continue;
      }
      if (userInput ==2) {
        if (navigationSystem.updateRoadCost(roadName.trim(), newData)) {
          System.out.println("Successfully update cost");
          System.out.println();
          return;
        } else {
          System.out.println("encounter problem updating road's cost");
          System.out.println();
          return;
        }
      }
      if (userInput ==3) {
        if (navigationSystem.updateRoadTime(roadName.trim(), newData)) {
          System.out.println("Successfully update time");
          System.out.println();
          return;
        } else {
          System.out.println("encounter problem updating road's time");
          System.out.println();
          return;
        }
      }
      if (userInput ==4) {
        if (navigationSystem.updateRoadDistance(roadName.trim(), newData)) {
          System.out.println("Successfully update distance");
          System.out.println();
          return;
        } else {
          System.out.println("encounter problem updating road's distance");
          System.out.println();
          return;
        }
      }
    }
  }


  /**
   * This method functions for inserting a road or location that user desires
   */
  public static void insert() {
    System.out.println("(1) insert road");
    System.out.println("(2) insert location");
    boolean getChoose = false;
    int userChoose = 0;
    while (!getChoose) {
      System.out.println("Enter your choose");
      Scanner input = new Scanner(System.in);
      try {
        userChoose = input.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("invalid input, try again");
      }
      if (userChoose == 1 || userChoose == 2) {
        getChoose = true;
      }
    }

    // insert road
    if (userChoose == 1) {
      boolean getRoadName = false;
      String newRoadName = "";
      while (!getRoadName) {
        System.out.println("Please enter the road name to be inserted: ");
        Scanner input = new Scanner(System.in);
        newRoadName = input.nextLine();
        if (newRoadName == null || newRoadName.isBlank()) {
          System.out.println("Invalid road Name, try again");
          continue;
        }
        if (navigationSystem.roads.containsKey(newRoadName)) {
          System.out.println("road Name already exist in the system, try different one");
          continue;
        }
        getRoadName = true;
      }

      boolean getRoadSource = false;
      String roadSource = "";
      while (!getRoadSource) {
        System.out.println("Please enter where the road started from: ");
        Scanner input = new Scanner(System.in);
        roadSource = input.nextLine();
        if (roadSource == null || roadSource.isBlank()) {
          System.out.println("Invalid input, try again");
          continue;
        }
        if (!navigationSystem.containsLocation(roadSource.trim())) {
          System.out.println("Can't find this location in our system, try again");
          continue;
        }
        roadSource = roadSource.trim();
        getRoadSource = true;
      }

      boolean getRoadTarget = false;
      String roadTarget = "";
      while (!getRoadTarget) {
        System.out.println("Please enter where the road end to: ");
        Scanner input = new Scanner(System.in);
        roadTarget = input.nextLine();
        if (roadTarget == null || roadTarget.isBlank()) {
          System.out.println("Invalid input, try again");
          continue;
        }
        if (!navigationSystem.containsLocation(roadTarget.trim())) {
          System.out.println("Can't find this location in our system, try again");
          continue;
        }
        roadTarget = roadTarget.trim();
        getRoadTarget = true;
      }

      boolean getRoadInfo = false;
      while (!getRoadInfo) {
        System.out.println("Please enter road's cost, time, distance separated by space: ");
        Scanner input = new Scanner(System.in);
        String[] roadInfo = input.nextLine().trim().split(" ");
        if (roadInfo.length != 3) {
          System.out.println("incorrect format, try again");
        }
        double cost = 0;
        double time = 0;
        double distance = 0;
        try {
          cost = Double.valueOf(roadInfo[0]);
          time = Double.valueOf(roadInfo[1]);
          distance = Double.valueOf(roadInfo[2]);
        } catch (NumberFormatException e) {
          System.out.println("Invalid Input, try again");
          continue;
        }
        if (navigationSystem.insertRoad(roadSource,roadTarget,newRoadName,cost,time,distance)) {
          System.out.println("Successfully insert the road by given information");
          System.out.println();
          return;
        } else {
          System.out.println("Encounter problem when inserting the road by given information");
          System.out.println();
          return;
        }
      }
    }

    // insert location
    if (userChoose == 2) {
      System.out.println("Enter the location name that going to insert: ");
      boolean getLocation = false;
      String locationName ="";
      while (!getLocation) {
        Scanner input = new Scanner(System.in);
        locationName = input.nextLine();
        if (locationName == null || locationName.isBlank()) {
          System.out.println("can't read the input, try again");
          continue;
        }
        if (navigationSystem.containsLocation(locationName)) {
          System.out.println("Location already exist, try different one");
          continue;
        }
        locationName = locationName.trim();
        getLocation = true;
      }

      boolean getNewAddress = false;
      String newAddress = "";
      while (!getNewAddress) {
        System.out.println("Please enter the new address: ");
        Scanner input = new Scanner(System.in);
        newAddress = input.nextLine();
        if (locationName == null || locationName.isBlank()) {
          System.out.println("invalid address format");
          continue;
        }
        if (navigationSystem.insertLocation(locationName, newAddress.trim())) {
          getNewAddress = true;
          System.out.println("Successfully insert location");
          System.out.println();
          return;
        } else {
          System.out.println("encounter problem inserting location");
          System.out.println();
          return;
        }
      }
    }
  }

  /**
   * This method functions for remove a road or location that user is going to choose
   */
  public static void remove() {
    System.out.println("(1) remove road");
    System.out.println("(2) remove location");
    boolean getChoose = false;
    int userChoose = 0;
    while (!getChoose) {
      System.out.println("Enter your choose");
      Scanner input = new Scanner(System.in);
      try {
        userChoose = input.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("invalid input, try again");
      }
      if (userChoose == 1 || userChoose == 2) {
        getChoose = true;
      }
    }

    // remove road
    if (userChoose == 1) {
      boolean getRoadSource = false;
      String roadSource = "";
      while (!getRoadSource) {
        System.out.println("Please enter where the road started from: ");
        Scanner input = new Scanner(System.in);
        roadSource = input.nextLine();
        if (roadSource == null || roadSource.isBlank()) {
          System.out.println("Invalid input, try again");
          continue;
        }
        if (!navigationSystem.containsLocation(roadSource.trim())) {
          System.out.println("Can't find this location in our system, try again");
          continue;
        }
        roadSource = roadSource.trim();
        getRoadSource = true;
      }

      boolean getRoadTarget = false;
      String roadTarget = "";
      while (!getRoadTarget) {
        System.out.println("Please enter where the road end to: ");
        Scanner input = new Scanner(System.in);
        roadTarget = input.nextLine();
        if (roadTarget == null || roadTarget.isBlank()) {
          System.out.println("Invalid input, try again");
          continue;
        }
        if (!navigationSystem.containsLocation(roadTarget.trim())) {
          System.out.println("Can't find this location in our system, try again");
          continue;
        }
        roadTarget = roadTarget.trim();
        getRoadTarget = true;
      }

      if (navigationSystem.removeRoad(roadSource, roadTarget)) {
        System.out.println("Successfully remove selected road");
        System.out.println();
        return;
      } else {
        System.out.println("encounter problem when removing selected road");
        System.out.println();
        return;
      }
    }

    if (userChoose == 2) {
      boolean getRemoveLocation = false;
      String removeLocation = "";
      while (!getRemoveLocation) {
        System.out.println("Please enter the location to be removed: ");
        Scanner input = new Scanner(System.in);
        removeLocation = input.nextLine();
        if (removeLocation == null || removeLocation.isBlank()) {
          System.out.println("Invalid input, try again");
          continue;
        }
        if (!navigationSystem.containsLocation(removeLocation.trim())) {
          System.out.println("Can't find this location in our system, try again");
          continue;
        }
        removeLocation = removeLocation.trim();
        getRemoveLocation = true;
      }

      if (navigationSystem.removeLocation(removeLocation)) {
        System.out.println("Successfully remove selected location");
        System.out.println();
        return;
      } else {
        System.out.println("encounter problem when removing selected location");
        System.out.println();
        return;
      }
    }
  }

  /**
   * This method functions for helping user to know a location address by using its name
   */
  public static void lookupAddr() {
    boolean getLocation = false;
    String locationName = "";
    while (!getLocation) {
      System.out.println("Please enter the location to lookup address: ");
      Scanner input = new Scanner(System.in);
      locationName = input.nextLine();
      if (locationName == null || locationName.isBlank()) {
        System.out.println("Invalid input, try again");
        continue;
      }
      if (!navigationSystem.containsLocation(locationName.trim())) {
        System.out.println("Can't find this location in our system, try again");
        continue;
      }
      locationName = locationName.trim();
      getLocation = true;
    }

    System.out.println();
    System.out.println("The address for " + locationName + " is: ");
    System.out.println(navigationSystem.lookupLocationAddress(locationName));
    System.out.println();
  }

  /**
   * launch and run the navigation system
   * @param args arugments, if any
   */
  public static void main(String[] args) {
    if (setupTimes < 1) {
      try {
        setup();
        setupTimes += 1;
      } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
        return;
      }
    }
    System.out.println("Welcome to City Navigation, choose one of the option below");
    System.out.println("(1) Navigate");
    System.out.println("(2) Update info");
    System.out.println("(3) insert road/location");
    System.out.println("(4) remove road/location");
    System.out.println("(5) look-up address");
    System.out.println("(6) Location list");
    System.out.println("(7) exit");
    boolean sucGetInput = false;
    int userChoose = 0;
    while (!sucGetInput) {
      System.out.print("Please enter your choose: ");
      Scanner input = new Scanner(System.in);
      try {
        userChoose = input.nextInt();
        if (userChoose < 1 || userChoose > 7) {
          System.out.println("Invalid input, try again.");
          continue;
        }
        sucGetInput = true;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input, try again.");
      }
    }
    if (userChoose == 1) {
      navigate();
      main(null);
    } else if (userChoose == 2) {
      update();
      main(null);
    } else if (userChoose == 3) {
      insert();
      main(null);
    } else if (userChoose == 4) {
      remove();
      main(null);
    } else if (userChoose == 5) {
      lookupAddr();
      main(null);
    } else if (userChoose == 6) {
      System.out.println(navigationSystem.printLocations());
      main(null);
    } else if (userChoose == 7) {
      System.out.println("Thank you for using our system, goodbye!");
    }
  }
}
