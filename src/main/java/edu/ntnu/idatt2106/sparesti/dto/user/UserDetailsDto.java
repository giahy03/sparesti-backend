package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) representing user details.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class UserDetailsDto {
  @NonNull
  private String firstName;

  @NonNull
  private String lastName;
}
