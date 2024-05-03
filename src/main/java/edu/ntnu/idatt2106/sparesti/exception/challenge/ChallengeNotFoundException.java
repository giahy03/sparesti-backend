package edu.ntnu.idatt2106.sparesti.exception.challenge;


/**
 * The class represents a custom exception for when a quiz object does not exist.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class ChallengeNotFoundException extends RuntimeException {

  /**
   * Constructs an instance of the class with  the specified error message.
   *
   * @param message The error message.
   */
  public ChallengeNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public ChallengeNotFoundException() {
    super("Challenge not found.");
  }
}