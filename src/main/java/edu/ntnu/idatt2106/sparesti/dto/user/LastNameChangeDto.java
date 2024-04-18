package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing new last name.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class LastNameChangeDto {
  @NonNull
  private String newLastName;
}
