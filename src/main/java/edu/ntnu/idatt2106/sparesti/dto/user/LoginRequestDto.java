package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing user login information.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
@Builder
public class LoginRequestDto {

  @NonNull
  private String email;

  @NonNull
  private String password;
}
