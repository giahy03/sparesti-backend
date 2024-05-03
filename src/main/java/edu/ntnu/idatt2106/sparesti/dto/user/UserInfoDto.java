package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing additional user information.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
@Builder
public class UserInfoDto {
  private double income;
  private int livingStatus;
  private int savingPercentage;
}
