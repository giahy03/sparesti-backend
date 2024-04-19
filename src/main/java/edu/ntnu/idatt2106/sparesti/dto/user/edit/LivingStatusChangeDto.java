package edu.ntnu.idatt2106.sparesti.dto.user.edit;

import lombok.Data;

/**
 * Data Transfer Object (DTO) containing new living status for user.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class LivingStatusChangeDto {
  private int newLivingStatus;
}
