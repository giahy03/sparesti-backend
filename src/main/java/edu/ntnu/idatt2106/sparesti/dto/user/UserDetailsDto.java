package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) for retrieving user details.
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

  @NonNull
  private Double income;

  @NonNull
  private Integer savingPercentage;

  @NonNull
  private String livingStatus;
}
