//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P07: Iterable Song Player - SongPlayerTester class
// Course:   CS 300 Spring 2022
//
// Author:   Haoting Tan
// Lecturer: Hobbes LeGault
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of Song, LinkedNode, SongPlayer
 * ForwardSongIterator and BackwardSongIterator classes in P07 Iterable Song Player assignment.
 *
 */
public class SongPlayerTester {
  /**
   * This method test and make use of the Song constructor, an accessor (getter) method,
   * overridden method toString() and equal() method defined in the Song class.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSong() {
    // 1. test if Song class constructor throws IllegalArgumentException if songName,
    // or artist, or duration is null or is blank
    {
      try {
        new Song(null, "null", "3:50");
        new Song("null", null, "3:50");
        new Song("null", "null", null);
        new Song("", "null", "3:59");
        new Song("        ", "null", "3:59");
        new Song("null", "", "0:59");
        new Song("null", "        ", "0:59");
        new Song("null", "null", "");
        new Song("null", "null", "        ");
        System.out.println("Problem detected: Your Song class does NOT throws exception "
            + "when songName or artist, or duration is null or is blank");
        return false;
      } catch (IllegalArgumentException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your Song class throws the wrong exception "
            + "when songName or artist, or duration is null or is blank");
        return false;
      }
    }

    // 2. test if Song class constructor throws IllegalArgumentException if duration
    // is not formatted as mm:ss where both mm and ss are in the 0..59 range.
    {
      try {
        new Song("song", "artist", "qwer");
        new Song("song", "artist", "1234");
        new Song("song", "artist", "1234:");
        new Song("song", "artist", ":4545");
        new Song("song", "artist", "-5:39");
        new Song("song", "artist", "65:39");
        new Song("song", "artist", "39:-1");
        new Song("song", "artist", "39:99");
        System.out.println("Problem detected: Your Song class does NOT throws exception "
            + "when duration is not formatted as mm:ss where both mm and ss are in the "
            + "0..59 range.");
        return false;
      } catch (IllegalArgumentException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your Song class throws the wrong exception "
            + "when duration is not formatted as mm:ss where both mm and ss are in the "
            + "0..59 range.");
        return false;
      }
    }

    // 3. test if Song class Constructor and getters work functionally when passing valid input
    try {
      Song testSong = new Song("songName", "me", "00:59");
      if (!testSong.getSongName().equals("songName") ||
          !testSong.getArtist().equals("me") ||
          !testSong.getDuration().equals("00:59")) {
        System.out.println("Problem detected: Your Song class Constructor or getters DOES NOT "
            + "work functionally when passing valid input(get unexpected output)");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your Song class throws unexpected exception "
          + "when input is valid");
      return false;
    }

    // 4. test if equals() works functionally
    {
      try {
        Song testSong = new Song("songName", "me", "00:59");
        Song testSong2 = new Song("songName", "me", "00:59");
        Song testSong3 = new Song("songName", "you", "00:59");
        Integer other = new Integer(10);
        if (testSong.equals(other)) {
          System.out.println("Problem detected: Your Song class equals() fails to return "
              + "false when passing input is not an instance of Song");
          return false;
        }
        if (!testSong.equals(testSong2)) {
          System.out.println("Problem detected: Your Song class equals() fails to return "
              + "true when passing song is equals to this song");
          return false;
        }
        if (testSong.equals(testSong3)) {
          System.out.println("Problem detected: Your Song class equals() fails to return "
              + "false when passing song is NOT equals to this song");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your Song class throws unexpected exception "
            + "when input is valid when testing equals()");
        return false;
      }
    }

    // 5. test if toString() works functionally
    {
      try {
        Song testSong = new Song("songName", "me", "00:59");
        String expected = "songName---me---00:59";
        if (!testSong.toString().equals(expected)) {
          System.out.println("Problem detected: Your Song class toString() fails to return "
              + "expected String representation");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your Song class throws unexpected exception "
            + "when input is valid when testing toString()");
        return false;
      }
    }

    return true; // NO bugs detected in Song class; Song class passes this test
  }

  /**
   * This method test and make use of the LinkedNode constructor, an accessor
   * (getter) method, and a mutator (setter) method defined in the LinkedCart class.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testLinkedNode() {
    // 1. test if Constructor throws IllegalArgumentException if data is null
    {
      try {
        new LinkedNode<Integer>(null, null, null);
        new LinkedNode<Song>(null, null, null);
        System.out.println("Problem detected: Your LinkedNode<T> class DOES NOT throw"
            + "exception when passing the data is null");
        return false;
      } catch (IllegalArgumentException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your LinkedNode<T> class throws the WRONG "
            + "exception when passing the data is null");
        return false;
      }
    }

    // 2. test if constructor and getters work functionally
    {
      try {
        Integer oneInt = 10;
        LinkedNode<Integer> newInt = new LinkedNode<>(null, oneInt, null);
        if (!newInt.getData().equals(10)) {
          System.out.println("Problem detected: Your LinkedNode<> class getData() can't "
              + "return the expected data value");
          return false;
        }
        if (newInt.getPrev() != null) {
          System.out.println("Problem detected: Your LinkedNode<> class getPrev() can't "
              + "return the expected data value");
          return false;
        }
        if (newInt.getNext() != null) {
          System.out.println("Problem detected: Your LinkedNode<> class getNext() can't "
              + "return the expected data value");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your LinkedNode<> class throws unexpected exception "
            + "when input is valid");
        return false;
      }
    }

    // 3. test if mutators work functionally
    {
      try {
        LinkedNode<Integer> newInt1 = new LinkedNode<>(null, 10, null);
        LinkedNode<Integer> newInt2 = new LinkedNode<>(null, 20, null);
        newInt1.setNext(newInt2);
        newInt2.setPrev(newInt1);
        if (newInt1.getNext() != newInt2 || newInt2.getPrev() != newInt1) {
          System.out.println("Problem detected: Your LinkedNode<> class' mutators can't "
              + "set the LinkedNode<> with expected output");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your LinkedNode<> class throws unexpected exception "
            + "when input is valid when testing mutators");
        return false;
      }
    }

    return true; // No bugs in LinkedNode<> class. LinkedNode<> class passes this test.
  }

  /**
   * This method checks for the correctness of addFirst(), addLast() and add(int index)
   * method in SongPlayer class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongPlayerAdd() {
    // 1. test addFirst() and add(int index) throws NullPointerException if data is null
    {
      SongPlayer songPlayer = new SongPlayer();
      try {
        songPlayer.addFirst(null);
        songPlayer.add(0, null);
        System.out.println("Problem detected: Your addFirst() or add(int index) do NOT throw "
            + "exception when data is null");
        return false;
      } catch (NullPointerException e1) {
        //true;
      } catch (Exception e2) {
        System.out.println("Problem detected: Your addFirst() or add(int index) throws the Wrong "
            + "exception when data is null");
        return false;
      }
    }

    // 2. test if add(int index) throws IndexOutOfBoundsException if index is out of range
    {
      SongPlayer songPlayer = new SongPlayer();
      Song oneSong = new Song("hey you", "is me", "2:50");
      try {
        songPlayer.add(-1, oneSong);
        songPlayer.add(1, oneSong);
        songPlayer.add(100, oneSong);
        System.out.println("Problem detected: Your add(int index) does NOT throws "
            + "exception when index if out of bounds");
        return false;
      } catch (IndexOutOfBoundsException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your add(int index) throws the Wrong "
            + "exception when index if out of bounds");
        return false;
      }
    }

    SongPlayer songPlayer = new SongPlayer();
    Song song1 = null;
    Song song2 = null;
    Song song3 = null;
    Song song4 = null;
    Song song5 = null;
    try {
      song1 = new Song("they", "their", "3:50");
      song2 = new Song("we", "ours", "2:50");
      song3 = new Song("there", "here", "0:00");
      song4 = new Song("she", "her", "9:50");
      song5 = new Song("he", "him", "2:30");
    } catch (Exception e) {
      e.printStackTrace();
    }

    // 3. test if addFirst and addLast works functionally
    {
      try {
        songPlayer.addFirst(song1);
        if (songPlayer.getFirst() != song1 || songPlayer.getLast() != song1
            || songPlayer.size() != 1) {
          System.out.println("Problem detected: Your addFirst or addLast return the unexpected "
              + "output when inputs are all valid");
          return false;
        }
        songPlayer.addFirst(song2);
        if (songPlayer.getFirst() != song2 || songPlayer.getLast() != song1
            || songPlayer.size() != 2) {
          System.out.println("Problem detected: Your addFirst or addLast return the unexpected "
              + "output when inputs are all valid");
          return false;
        }
        songPlayer.addLast(song3);
        if (songPlayer.getFirst() != song2 || songPlayer.getLast() != song3
            || songPlayer.size() != 3) {
          System.out.println("Problem detected: Your addFirst or addLast return the unexpected "
              + "output when inputs are all valid");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your addFirst or addLast throws the unexpected "
            + "exception when inputs are all valid");
        return false;
      }
    }

    // 4. test add(int index) works functionally
    {
      try {
        songPlayer.add(3, song4);
        if (songPlayer.getLast() != song4 || songPlayer.getFirst() != song2
            || songPlayer.size() != 4) {
          System.out.println("Problem detected: Your add(int index) return the unexpected "
              + "output when inputs are all valid");
          return false;
        }
        songPlayer.add(0, song5);
        if (songPlayer.getLast() != song4 || songPlayer.getFirst() != song5
            || songPlayer.size() != 5) {
          System.out.println("Problem detected: Your add(int index) return the unexpected "
              + "output when inputs are all valid");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your add(int index) throws the unexpected "
            + "exception when inputs are all valid");
        return false;
      }
    }

    return true; // no bugs detected in addFirst(), addLast() and add(int index) methods.
  }

  /**
   * This method checks for the correctness of getFirst(), getLast() and get(int index)
   * method in SongPlayer class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongPlayerGet() {
    // Create and initialize a new SongPlayer and 3 new songs
    SongPlayer testSP = new SongPlayer();
    Song song1 = new Song("Song 1", "artist 1", "1:00");
    Song song2 = new Song("Song 2", "artist 2", "2:00");
    Song song3 = new Song("Song 3", "artist 2", "30:00");

    // Case 1: test with throwing NoSuchElementExceptions
    {
      // test with GetFirst() method
      try {
        Song testSong = testSP.getFirst();
        System.out.println("Problem detetced: Your getFirst() method does not throw expected "
            + " exception in testSongPlayerGet(): Case 1");
        return false;
      } catch (NoSuchElementException e1) {
        // expected condition, good
      } catch (Exception e2) {
        System.out.println("Problem detected: Your getFirst() method throws unexpected "
            + e2.getClass() + "in testSongPlayerGet(): Case 1");
        e2.printStackTrace();
      }

      // test with GetLast() method
      try {
        Song testSong = testSP.getLast();
        System.out.println("Problem detetced: Your getLast() method does not throw expected "
            + " exception in testSongPlayerGet(): Case 1");
        return false;
      } catch (NoSuchElementException e1) {
        // expected condition, good
      } catch (Exception e2) {
        System.out.println("Problem detected: Your getLast() method throws unexpected "
            + e2.getClass() + "in testSongPlayerGet(): Case 1");
        e2.printStackTrace();
      }
    }

    // Case 2: test with throwing IndexOutOfBoundsException
    {
      // test with Get(int index) method
      testSP.addLast(song1);
      try {
        Song testSong = testSP.get(1);
        System.out.println("Problem detetced: Your get(int index) method does not throw expected "
            + " exception in testSongPlayerGet(): Case 2");
        return false;
      } catch (IndexOutOfBoundsException e1) {
        // expected condition, good
      } catch (Exception e2) {
        System.out.println("Problem detected: Your get(int index) method throws unexpected "
            + e2.getClass() + "in testSongPlayerGet(): Case 2");
        e2.printStackTrace();
      }
    }

    // Case 3: test with getFirst(), getLast(), get(int index) when they are functional
    {
      testSP.clear();
      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.addLast(song3);

      // test with getFirst() method
      try {
        Song testSong = testSP.getFirst();
        if (testSong != song1) {
          System.out.println("Problem detected: Your getFirst() method returns the unexpected "
              + "result in testSongPlayerGet(): Case 3");
          return false;
        }
      } catch (Exception e1) {
        System.out.println("Problem detected: Your getFirst() method throws unexpected "
            + e1.getClass() + "in testSongPlayerGet(): Case 3");
        e1.printStackTrace();
      }

      // test with getLast() method
      try {
        Song testSong = testSP.getLast();
        if (testSong != song3) {
          System.out.println("Problem detected: Your getLast() method returns the unexpected "
              + "result in testSongPlayerGet(): Case 3");
          return false;
        }
      } catch (Exception e1) {
        System.out.println("Problem detected: Your getLast() method throws unexpected "
            + e1.getClass() + "in testSongPlayerGet(): Case 3");
        e1.printStackTrace();
      }

      // test with get(int index) method
      try {
        Song testSong1 = testSP.get(0);
        Song testSong2 = testSP.get(1);
        Song testSong3 = testSP.get(2);
        if (testSong1 != song1 || testSong2 != song2 || testSong3 != song3) {
          System.out.println(testSong1.getSongName());
          System.out.println(testSong2.getSongName());
          System.out.println(testSong3.getSongName());
          System.out.println("Problem detected: Your get(int index) method returns the unexpected "
              + "result in testSongPlayerGet(): Case 3");
          return false;
        }
      } catch (Exception e1) {
        System.out.println("Problem detected: Your get(int index) method throws unexpected "
            + e1.getClass() + "in testSongPlayerGet(): Case 3");
        e1.printStackTrace();
      }
    }

    return true;
  }

  /**
   * This method checks for the correctness of removeFirst(), removeLast() and remove(int index)
   * method in SongPlayer class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongPlayerRemove() {
    // Create and initialize a new SongPlayer and 3 new songs
    SongPlayer testSP = new SongPlayer();
    Song song1 = new Song("Song 1", "artist 1", "1:00");
    Song song2 = new Song("Song 2", "artist 2", "2:00");
    Song song3 = new Song("Song 3", "artist 2", "30:00");
    Song song4 = new Song("Song 4", "artist 3", "5:45");

    // Case 1: test with throwing NoSuchElementException
    {
      // Test with removeFirst() method
      try {
        Song testSong = testSP.removeFirst();
        System.out.println("Problem detected: Your getFirst() method does not throw the expected "
            + "NoSuchElementException in testSongPlayerRemove(): Case 1");
        return false;
      } catch (NoSuchElementException e1) {
        // expected condition, good
      } catch (Exception e2) {
        System.out.println("Problem detected: Your getFirst() method throws unexpected "
            + e2.getClass() + "in testSongPlayerRemove(): Case 1");
        e2.printStackTrace();
        return false;
      }

      // Test with removeLast() method
      try {
        Song testSong = testSP.removeLast();
        System.out.println("Problem detected: Your getLast() method does not throw the expected "
            + "NoSuchElementException in testSongPlayerRemove(): Case 1");
        return false;
      } catch (NoSuchElementException e1) {
        // expected condition, good
      } catch (Exception e2) {
        System.out.println("Problem detected: Your getLast() method throws unexpected "
            + e2.getClass() + "in testSongPlayerRemove(): Case 1");
        e2.printStackTrace();
        return false;
      }
    }

    // Case 2: test with throwing IndexOutOfBoundsException
    {
      // Test with remove(int index) method
      testSP.addLast(song1);
      try {
        Song testSong = testSP.remove(1);
        System.out.println("Problem detected: Your remove(int index) method does not throw "
            + "expected IndexOutOfBoundsException in testSongPlayerRemove(): Case 2");
        return false;
      } catch (IndexOutOfBoundsException e1) {
        // expected condition, good
      } catch (Exception e2) {
        System.out.println("Problem detected: Your remove(int index) method throws unexpected "
            + e2.getClass() + "in testSongPlayerRemove(): Case 2");
        e2.printStackTrace();
        return false;
      }
    }

    // Case 3: test with removeFirst(), removeLast(), and remove(int index) methods when they are
    // supposed to be functional
    {
      testSP.clear();
      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.addLast(song3);
      testSP.addLast(song4);

      // Test with remove(int index) method
      try {
        Song testSong1 = testSP.remove(3);
        Song testSong2 = testSP.remove(0);
        if (testSP.size() != 2 || testSong2 != song1 || testSong1 != song4) {
          System.out.println("Problem detected: Your remove(int index) returns the unexpected "
              + "result or revised the size of SongPlayer wrongly in testSongPlayerRemove()"
              + ": Case 3");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your remove(int index) method throws unexpected "
            + e.getClass() + "in testSongPlayerRemove(): Case 3");
        e.printStackTrace();
        return false;
      }

      // Test with removeLast() method
      try {
        Song testSong1 = testSP.removeLast();
        if (testSP.size() != 1 || testSong1 != song3) {
          System.out.println("Problem detected: Your removeLast() method returns the unexpected "
              + "result or revised the size of SongPlayer wrongly in testSongPlayerRemove()"
              + ": Case 3");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your removeLast() method throws unexpected "
            + e.getClass() + "in testSongPlayerRemove(): Case 3");
        e.printStackTrace();
        return false;
      }

      // Test with removeFirst() method
      try {
        Song testSong1 = testSP.removeFirst();
        if (testSP.size() != 0 || testSong1 != song2) {
          System.out.println("Problem detected: Your removeFirst() method returns the unexpected "
              + "result or revised the size of SongPlayer wrongly in testSongPlayerRemove()"
              + ": Case 3");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your removeFirst() method throws unexpected "
            + e.getClass() + "in testSongPlayerRemove(): Case 3");
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks for the correctness of iterator(), switchPlayingDirection()
   * and String play() method in SongPlayer class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongPlayerIterator() {
    SongPlayer testSP = new SongPlayer();
    Song song1 = new Song("Song 1", "artist 1", "1:00");
    Song song2 = new Song("Song 2", "artist 2", "2:00");
    Song song3 = new Song("Song 3", "artist 2", "30:00");
    Song song4 = new Song("Song 4", "artist 3", "5:45");


    // Case 1: test with iterator() when the SongPlayer is playing forward.
    {
      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.addLast(song3);
      testSP.addLast(song4);
      Iterator<Song> testIt = testSP.iterator();
      if (!(testIt instanceof ForwardSongIterator)) {
        System.out.println("Problem detected: wrong initialization of boolean variable"
            + " playingBackward or iterable() method of class SongPlayer in "
            + "testSongPlayerIterator(): Case 1");
        return false;
      }
      String testPlay = testSP.play();
      String expectedPlay = "Song 1---artist 1---1:00\n" + "Song 2---artist 2---2:00\n"
          + "Song 3---artist 2---30:00\n" + "Song 4---artist 3---5:45";
      if (!testPlay.equals(expectedPlay)) {
        System.out.println("Problem detected: play() method returns the unexpected result in "
            + "testSongPlayerIterator(): Case 1");
        return false;
      }
    }

    // Case 2: test with switchPlayDirection() method
    {
      testSP.clear();
      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.addLast(song3);
      testSP.addLast(song4);
      testSP.switchPlayingDirection();
      Iterator<Song> testIt = testSP.iterator();
      if (!(testIt instanceof BackwardSongIterator)) {
        System.out.println("Problem detected: switchPlayingDirection() method does not function "
            + "as expected in testSongPlayerIterator(): Case 2");
        return false;
      }
    }

    // Case 3: test with iterator() when the SongPlayer is playing backward.
    {
      testSP.clear();
      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.addLast(song3);
      testSP.addLast(song4);
      Iterator<Song> testIt = testSP.iterator();
      if (!(testIt instanceof BackwardSongIterator)) {
        System.out.println("Problem detected: iterator() method does not function as expected in "
            + "testSongPlayerIterator(): Case 3");
        return false;
      }
      String testPlay = testSP.play();
      String expectedPlay = "Song 4---artist 3---5:45\n" + "Song 3---artist 2---30:00\n"
          + "Song 2---artist 2---2:00\n" + "Song 1---artist 1---1:00";
      if (!testPlay.equals(expectedPlay)) {
        System.out.println("Problem detected: play() method returns the unexpected result in "
            + "testSongPlayerIterator(): Case 3");
        return false;

      }
    }
    return true;
  }

  /**
   * This method checks for the correctness of contains(Object song), clear(),
   * isEmpty()and size() method in SongPlayer class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongPlayerCommonMethod() {
    SongPlayer testSP = new SongPlayer();
    Song song1 = new Song("Song 1", "artist 1", "1:00");
    Song song2 = new Song("Song 2", "artist 2", "2:00");
    Song song3 = new Song("Song 3", "artist 2", "30:00");
    Song song4 = new Song("Song 4", "artist 3", "5:45");

    // Case 1: test with size() method
    {
      if (testSP.size() != 0) {
        System.out.println("Problem detected: size() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 1");
        return false;
      }
      testSP.add(0, song2);
      testSP.add(1, song3);
      testSP.addFirst(song1);
      testSP.addLast(song4);
      if (testSP.size() != 4) {
        System.out.println("Problem detected: size() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 1");
        return false;
      }
      testSP.removeFirst();
      testSP.removeLast();
      testSP.remove(0);
      if (testSP.size() != 1) {
        System.out.println("Problem detected: size() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 1");
        return false;
      }
    }

    // case 2: test with clear() method
    {
      testSP.clear();
      if (testSP.size() != 0) {
        System.out.println("Problem detected: clear() method does not function as expected in "
            + "testSongPlayerCommonMethod(): Case 2");
        return false;
      }

      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.clear();
      if (testSP.size() != 0) {
        System.out.println("Problem detected: clear() method does not function as expected in "
            + "testSongPlayerCommonMethod(): Case 2");
        return false;
      }
    }

    // case 3: test with isEmpty() method
    {
      testSP.clear();
      if (!testSP.isEmpty()) {
        System.out.println("Problem detected: isEmpty() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 3");
        return false;
      }

      testSP.addLast(song1);
      testSP.addLast(song2);
      if (testSP.isEmpty()) {
        System.out.println("Problem detected: isEmpty() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 3");
        return false;
      }
      testSP.clear();
      if (!testSP.isEmpty()) {
        System.out.println("Problem detected: isEmpty() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 3");
        return false;
      }
    }

    // case 4: test with contains(Object song) method
    {
      testSP.clear();
      testSP.addLast(song1);
      testSP.addLast(song2);
      testSP.addLast(song3);
      if (!testSP.contains(song1) || !testSP.contains(song2) || !testSP.contains(song3)
          || testSP.contains(song4)) {
        System.out.println("Problem detected: contains() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 4");
        return false;
      }
      testSP.removeFirst();
      testSP.removeLast();
      if (!testSP.contains(song2) || testSP.contains(song1) || testSP.contains(song3)
          || testSP.contains(song4)) {
        System.out.println("Problem detected: contains() method returns unexpected result in "
            + "testSongPlayerCommonMethod(): Case 4");
        return false;
      }
    }

    return true;
  }

  /**
   * This method checks for the correctness of ForwardSongIterator class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testForwardSongIterator() {
    // Initialize some new Songs
    Song song1 = new Song("Song 1", "artist 1", "1:00");
    Song song2 = new Song("Song 2", "artist 2", "2:00");
    Song song3 = new Song("Song 3", "artist 2", "30:00");
    Song song4 = new Song("Song 4", "artist 3", "5:45");

    // Create expected output of the iterator
    Song[] expectedCollection = new Song[4];
    Song[] outputCollection = new Song[10]; // oversize Array
    int outputSize = 0;
    expectedCollection[0] = song1;
    expectedCollection[1] = song2;
    expectedCollection[2] = song3;
    expectedCollection[3] = song4;

    // Initialize some linked nodes
    LinkedNode<Song> node1 = new LinkedNode<Song>(null, song1, null);
    LinkedNode<Song> node2 = new LinkedNode<Song>(null, song2, null);
    LinkedNode<Song> node3 = new LinkedNode<Song>(null, song3, null);
    LinkedNode<Song> node4 = new LinkedNode<Song>(null, song4, null);
    node1.setNext(node2);
    node2.setNext(node3);
    node3.setNext(node4);
    node4.setPrev(node3);
    node3.setPrev(node2);
    node2.setPrev(node1);

    // Initialize a ForwardSongIterator
    ForwardSongIterator testIT = new ForwardSongIterator(node1);
    for (; testIT.hasNext();) {
      outputCollection[outputSize] = testIT.next();
      outputSize++;
      if (outputSize > 4) {
        System.out.println("Problem detected: ForwardSongIterator return more objects "
            + "in a Iterator than expected");
      }
    }

    if (outputSize != 4) {
      System.out.println("Problem detected: ForwardSongIterator Class returns the unexpected "
          + "result in testForwardSongIterator()");
      return false;
    }
    for (int i = 0; i < expectedCollection.length; ++i) {
      if (outputCollection[i] != expectedCollection[i]) {
        System.out.println("Problem detected: ForwardSongIterator Class returns the unexpected "
            + "result in testForwardSongIterator()");
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks for the correctness of BackwardSongIterator class
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testBackwardSongIterator() {
    // Initialize some new Songs
    Song song1 = new Song("Song 1", "artist 1", "1:00");
    Song song2 = new Song("Song 2", "artist 2", "2:00");
    Song song3 = new Song("Song 3", "artist 2", "30:00");
    Song song4 = new Song("Song 4", "artist 3", "5:45");

    // Create expected output of the iterator
    Song[] expectedCollection = new Song[4];
    Song[] outputCollection = new Song[10]; // oversize Array
    int outputSize = 0;
    expectedCollection[0] = song4;
    expectedCollection[1] = song3;
    expectedCollection[2] = song2;
    expectedCollection[3] = song1;

    // Initialize some linked nodes
    LinkedNode<Song> node1 = new LinkedNode<Song>(null, song1, null);
    LinkedNode<Song> node2 = new LinkedNode<Song>(null, song2, null);
    LinkedNode<Song> node3 = new LinkedNode<Song>(null, song3, null);
    LinkedNode<Song> node4 = new LinkedNode<Song>(null, song4, null);
    node1.setNext(node2);
    node2.setNext(node3);
    node3.setNext(node4);
    node4.setPrev(node3);
    node3.setPrev(node2);
    node2.setPrev(node1);

    // Initialize a BackwardSongIterator
    BackwardSongIterator testIT = new BackwardSongIterator(node4);
    for (; testIT.hasNext();) {
      outputCollection[outputSize] = testIT.next();
      outputSize++;
      if (outputSize > 4) {
        System.out.println("Problem detected: BackwardSongIterator return more objects "
            + "in a Iterator than expected");
      }
    }

    if (outputSize != 4) {
      System.out.println("Problem detected: BackwardSongIterator Class returns the unexpected "
          + "result in testForwardSongIterator()");
      return false;
    }
    for (int i = 0; i < expectedCollection.length; ++i) {
      if (outputCollection[i] != expectedCollection[i]) {
        System.out.println("Problem detected: BackwardSongIterator Class returns the unexpected "
            + "result in testForwardSongIterator()");
        return false;
      }
    }
    return true;
  }

  /**
   * This method calls all the test methods defined and implemented in your SongPlayerTester
   * class.
   *
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {
    boolean result = true;
    boolean crtResult = true;

    crtResult = testSong();
    result &= crtResult;
    System.out.println("testSong()---------------------" + (crtResult ? "Passed" : "Failed"));

    crtResult = testLinkedNode();
    result &= crtResult;
    System.out.println("tesLinkedNode()----------------" + (crtResult ? "Passed" : "Failed"));

    crtResult = testSongPlayerAdd();
    result &= crtResult;
    System.out.println("testSongPlayerAdd()------------" + (crtResult ? "Passed" : "Failed"));

    crtResult = testSongPlayerGet();
    result &= crtResult;
    System.out.println("testSongPlayerGet()------------" + (crtResult ? "Passed" : "Failed"));

    crtResult = testSongPlayerRemove();
    result &= crtResult;
    System.out.println("testSongPlayerRemove()---------" + (crtResult ? "Passed" : "Failed"));

    crtResult = testSongPlayerIterator();
    result &= crtResult;
    System.out.println("testSongPlayerIterator()-------" + (crtResult ? "Passed" : "Failed"));

    crtResult = testSongPlayerCommonMethod();
    result &= crtResult;
    System.out.println("testSongPlayerCommonMethod()---" + (crtResult ? "Passed" : "Failed"));

    crtResult = testForwardSongIterator();
    result &= crtResult;
    System.out.println("testForwardSongIterator():-----" + (crtResult ? "Passed" : "Failed"));

    crtResult = testBackwardSongIterator();
    result &= crtResult;
    System.out.println("testBackwardSongIterator()-----" + (crtResult ? "Passed" : "Failed"));

    return result;
  }

  /**
   * Driver method defined in this SongPlayerTester class
   *
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    System.out.println("runAllTests()------------------" + (runAllTests() ? "Passed" : "Failed"));
    System.out.println();
    SongPlayer songList = new SongPlayer();
    songList.addFirst(new Song("Mojito", "Jay Chou", "3:05"));
    songList.addFirst(new Song("Secret", "Jay Chou", "4:56"));
    songList.addFirst(new Song("Clear Day", "Jay Chou", "4:59"));
    songList.addFirst(new Song("Dragon Fist", "Jay Chou", "4:32"));
    songList.addFirst(new Song("Out of Time", "The Weeknd", "3:34"));
    songList.addLast(new Song("StarBoy", "The Weeknd", "3:50"));
    songList.addLast(new Song("Save Your Tears", "The Weeknd", "3:35"));
    songList.add(1, new Song("Simple Love", "Jay Chou", "4:30"));
    songList.add(2, new Song("Superman Can’t Fly", "Jay Chou", "4:59"));
    songList.addLast(new Song("Oh My God", "Adele", "3:45"));
    songList.addLast(new Song("Levitating", "Dua Lipa", "3:23"));
    songList.add(6, new Song("Be Kind", "Marshmello & Halsey", "2:53"));
    System.out.println("---------------- Play Forward -----------------");
    System.out.println(songList.play());
    System.out.println("------------------------------------------------");
    System.out.println("songList.remove(6) -- Be Kind -- removed\n"
        + "songList.removeFirst(); -- Out of Time -- removed\n"
        + "songList.removeLast(); -- Levitating -- removed\n");
    songList.remove(6);
    songList.removeFirst();
    songList.removeLast();
    System.out.println("---------------- Play Forward -----------------");
    System.out.println(songList.play());
    System.out.println("------------------------------------------------");
    Song oneSong = new Song("Mojito", "Jay Chou", "3:05");
    System.out.print("songList.contains(new Song(\"Mojito\", \"Jay Chou\", \"3:05\"): ");
    System.out.println(songList.contains(oneSong));
    System.out.println();
    System.out.println("songList.size(): " + songList.size());
    System.out.println();
    System.out.print("songList.contains(new Song(\"Be Kind\", \"Marshmello & Halsey\", \"2:53\"): ");
    oneSong = new Song("Be Kind", "Marshmello & Halsey", "2:53");
    System.out.println(songList.contains(oneSong));
    System.out.println();
    System.out.println("---------------- Play Forward -----------------");
    System.out.println(songList.play());
    System.out.println();
    System.out.println("---------------- Play Backward -----------------");
    songList.switchPlayingDirection();
    System.out.println(songList.play());
    System.out.println("------------------------------------------------");
  }
}
