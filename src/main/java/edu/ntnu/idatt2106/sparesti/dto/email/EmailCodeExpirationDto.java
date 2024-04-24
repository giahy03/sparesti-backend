package edu.ntnu.idatt2106.sparesti.dto.email;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing email code with expiration time.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class EmailCodeExpirationDto {
  @NonNull
  private LocalDateTime expirationTimestamp;
}
