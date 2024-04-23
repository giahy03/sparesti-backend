package edu.ntnu.idatt2106.sparesti.dto.streak;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a streak response
 * containing the number of consecutive days.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
@Builder
public class StreakResponseDto {
  private int numberOfDays;
}
