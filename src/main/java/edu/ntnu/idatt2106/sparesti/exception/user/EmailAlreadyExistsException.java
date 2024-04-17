package edu.ntnu.idatt2106.sparesti.exception.user;

/**
 * The class represents a custom exception to indicate that an email
 * is already in use by another user.
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository: <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/exception/user/EmailAlreadyExistsException.java">...</a>
 * </p>
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class EmailAlreadyExistsException extends RuntimeException {

  /**
   * Constructs an instance of the class with the specified error message.
   *
   * @param message The error message.
   */
  public EmailAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructs an instance of the clas with a predefined error message.
   */
  public EmailAlreadyExistsException() {
    super("Email already in use by another user.");
  }
}