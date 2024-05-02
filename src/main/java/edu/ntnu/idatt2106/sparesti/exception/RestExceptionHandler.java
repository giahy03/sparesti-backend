package edu.ntnu.idatt2106.sparesti.exception;

import edu.ntnu.idatt2106.sparesti.exception.analysis.ExternalApiException;
import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.exception.challenge.ChallengeNotFoundException;
import edu.ntnu.idatt2106.sparesti.exception.email.EmailAlreadyExistsException;
import edu.ntnu.idatt2106.sparesti.exception.email.VerificationCodeExpiredException;
import edu.ntnu.idatt2106.sparesti.exception.stock.StockNotFoundException;
import edu.ntnu.idatt2106.sparesti.exception.stock.StockProcessingException;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The class serves as a global exception handler for REST controllers. Based on the
 * exceptions that are thrown within the controllers, it returns a ResponseEntity with
 * an appropriate error response.
 *
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository:
 * <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/exception/RestExceptionHandler.java">...</a>
 * </p>
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * The method handles situations where a null pointer exception occurs.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing the ErrorResponse with HTTP status code 400 (BAD_REQUEST).
   */
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ErrorResponse> handleNullPointerException(@NonNull Exception ex) {
    String errorMessage = "Invalid input. Data is null, please provide valid data.";
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.BAD_REQUEST);
  }

  /**
   * The method handles situations where IllegalArgumentException or
   * VerificationCodeExpiredException occurs.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing the ErrorResponse with HTTP status code 400 (BAD_REQUEST).
   */
  @ExceptionHandler({IllegalArgumentException.class, VerificationCodeExpiredException.class})
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(@NonNull Exception ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.BAD_REQUEST);
  }

  /**
   * The method handles exceptions from when a user authentication fails due to bad credentials.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing the ErrorResponse with HTTP status code 401 (UNAUTHORIZED).
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(@NonNull Exception ex) {
    String errorMessage = "Email or password was incorrect.";
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.UNAUTHORIZED);
  }

  /**
   * The method handles exceptions from when a user is not authorized to perform the operation.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing the ErrorResponse with HTTP status code 401 (UNAUTHORIZED).
   */
  @ExceptionHandler(UnauthorizedOperationException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedOperationException(@NonNull Exception ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.UNAUTHORIZED);
  }

  /**
   * The method handles exceptions from when a requested resource is not found.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing the ErrorResponse with HTTP status code 404 (NOT_FOUND).
   */
  @ExceptionHandler({
      UserNotFoundException.class,
      UsernameNotFoundException.class,
      NoSuchElementException.class,
      ChallengeNotFoundException.class,
      EntityNotFoundException.class,
      StockNotFoundException.class,
  })
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(@NonNull Exception ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.NOT_FOUND);
  }

  /**
   * The method handles exceptions related to username or email already existing.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing the ErrorResponse with HTTP status code 409 (CONFLICT).
   */
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleConflict(@NonNull Exception ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.CONFLICT);
  }

  /**
   * The method handles exceptions related to external API calls.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing ErrorResponse with HTTP status 500 (INTERNAL_SERVER_ERROR).
   */
  @ExceptionHandler(ExternalApiException.class)
  public ResponseEntity<ErrorResponse> handleExternalApiException(@NonNull Exception ex) {
    String errorMessage = ex.getMessage();
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * The method handles exceptions related to processing stock data.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing ErrorResponse with HTTP status 500 (INTERNAL_SERVER_ERROR).
   */
  @ExceptionHandler(StockProcessingException.class)
  public ResponseEntity<ErrorResponse> handleStockProcessException(@NonNull Exception ex) {
    String errorMessage = "Failed to process stock data due to a server error."
        + " Please try again later.";
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * The method handles unexpected exceptions that may occur during the execution
   * of the application.
   *
   * @param ex The exception to handle.
   * @return ResponseEntity containing ErrorResponse with HTTP status 500 (INTERNAL_SERVER_ERROR).
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpectedException(@NonNull Exception ex) {
    String errorMessage = "Server error. Please try again later.";
    return buildResponseEntityWithErrorResponse(ex, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * The method builds a ResponseEntity containing an ErrorResponse based on the given
   * exception and HTTP status.
   *
   * @param ex     The exception to handle.
   * @param status The HTTP status to set in the response.
   * @return ResponseEntity containing the ErrorResponse with the specified HTTP status.
   */
  private ResponseEntity<ErrorResponse> buildResponseEntityWithErrorResponse(
      Exception ex, String errorMessage, HttpStatus status) {
    log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
    log.debug("Stack Trace:", ex);
    ErrorResponse errorResponse = new ErrorResponse(errorMessage, LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, status);
  }
}
