package edu.ntnu.idatt2106.sparesti.exception;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

/**
 * The class represents an error response containing an error message and the timestamp
 * when the error occurred.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class ErrorResponse {
  @NonNull
  private final String errorMessage;
  @NonNull
  private final LocalDateTime timestamp;
}
