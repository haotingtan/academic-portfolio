//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P07: Iterable Song Player - Song class
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
 * This class is about Song objects are used to store the song name,
 * artist, duration.
 */
public class Song {

  private String songName; // name or title of the song
  private String artist; // artist of the song
  private String duration; // duration of the song

  /**
   * A Constructor that Creates a new Song given the song name,
   * the name of the artist, and the duration of the song
   *
   * @param songName title of the song
   * @param artist name of the artist who performed this song
   * @param duration duration of the song in the format mm:ss
   * @throws IllegalArgumentException with a descriptive error message if either of songName,
   *                                  or artist, or duration is null or is blank, or if the
   *                                  duration is not formatted as mm:ss where both mm and ss
   *                                  are in the 0..59 range.
   */
  public Song(String songName, String artist, String duration) throws IllegalArgumentException {
    if (songName == null || songName.isBlank()) {
      throw new IllegalArgumentException("The song name is null or blank");
    }
    if (artist == null || artist.isBlank()) {
      throw new IllegalArgumentException("The artist is null or blank");
    }
    if (duration == null || duration.isBlank()) {
      throw new IllegalArgumentException("The duration is null or blank");
    }
    String[] durationContent = duration.split(":");
    if (durationContent.length != 2 || durationContent[0].equals("")) {
      throw new IllegalArgumentException("The duration should have ONLY one \":\" symbol "
                    + "and \":\" cannot be at the first index");
    }
    try {
      if (Integer.valueOf(durationContent[0]) > 59 ||
          Integer.valueOf(durationContent[0]) < 0 ||
          Integer.valueOf(durationContent[1]) > 59 ||
          Integer.valueOf(durationContent[1]) < 0 ) {
        throw new IllegalArgumentException("the duration mm:ss where mm or ss "
            + "is not in the 0..59 range");
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("the duration is not in format of mm:ss, "
          + "where mm or ss cannot be convert to numbers");
    }

    this.songName = songName;
    this.artist = artist;
    this.duration = duration;
  }

  /**
   * Checks if this song's name and artist strings equals to the
   * other song's name and artist strings. If an object that is not
   * an instance of Song is ever passed to this method, it should return false.
   *
   * @param other Song object to compare this object to
   * @return true when this song's name and artist strings equals to the
   * other song's name and artist strings, and false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Song)) {
      return false;
    }
    Song otherIsSong = (Song) other;
    if (this.songName.equals(otherIsSong.songName) && this.artist.equals(otherIsSong.artist)) {
      return true;
    }
    return false;
  }

  /**
   * Gets the name of the artist who performed this song
   *
   * @return the name of the artist who performed this song
   */
  public String getArtist() {
    return this.artist;
  }

  /**
   * Gets the duration of this song
   *
   * @return the duration of this song
   */
  public String getDuration() {
    return this.duration;
  }

  /**
   * Gets the title or name of this song
   *
   * @return name of this song
   */
  public String getSongName() {
    return this.songName;
  }

  /**
   * Returns a string representation of this song. This string should be
   * formatted as follows. "songName---artist---duration" where songName
   * is the title of the song, artist is the name of the artist, and duration
   * is the duration of the song.
   *
   * @return the string representation of this song.
   */
  @Override
  public String toString() {
    return this.songName + "---" + this.artist + "---" + this.duration;
  }
}
