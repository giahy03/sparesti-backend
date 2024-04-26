package edu.ntnu.idatt2106.sparesti.exception.analysis;

/**
 * The class represents a custom exception to indicate an error when calling an external API.
 *
 * @author Tobias Oftedal
 */
public class ExternalApiException extends RuntimeException {
  /**
   * Constructs a new ExternalApiException with the specified detail message.
   *
   * @param message The detail message.
   */
  public ExternalApiException(String message) {
    super(message);
  }

  /**
   * Constructs a new ExternalApiException with the default detail message.
   */
  public ExternalApiException() {
    super("External API error.");
  }

}
