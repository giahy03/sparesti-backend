package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing new first name.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class FirstNameChangeDto {
  @NonNull
  private String newFirstName;
}
