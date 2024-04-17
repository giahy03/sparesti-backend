package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing user registration information.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class RegistrationDto {

  @NonNull
  private String password;

  @NonNull
  private String email;

  @NonNull
  private String firstName;

  @NonNull
  private String lastName;
}
