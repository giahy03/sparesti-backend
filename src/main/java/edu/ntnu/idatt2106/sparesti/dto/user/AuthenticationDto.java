package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing a token used for authentication.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class AuthenticationDto {
  @NonNull
  private String token;
}
