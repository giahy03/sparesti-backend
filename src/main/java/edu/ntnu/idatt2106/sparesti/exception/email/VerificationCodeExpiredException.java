package edu.ntnu.idatt2106.sparesti.exception.email;

/**
 * The class represents a custom exception to indicate that the email verification code has expired.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class VerificationCodeExpiredException extends RuntimeException {

  /**
   * Constructs an instance of the class with the specified error message.
   *
   * @param message The error message.
   */
  public VerificationCodeExpiredException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public VerificationCodeExpiredException() {
    super("Verification code has expired.");
  }
}
