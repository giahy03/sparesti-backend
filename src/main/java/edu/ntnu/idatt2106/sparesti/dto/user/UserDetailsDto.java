package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) representing user information.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
@Builder
public class UserDetailsDto {

  @NonNull
  private String firstName;

  @NonNull
  private String lastName;

  private double income;

  @NonNull
  private String livingStatus;;
}
