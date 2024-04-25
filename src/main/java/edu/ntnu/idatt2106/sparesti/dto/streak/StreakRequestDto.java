package edu.ntnu.idatt2106.sparesti.dto.streak;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a streak request
 * indicating whether to increment or decrement a streak.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class StreakRequestDto {
  private boolean increment;
}
