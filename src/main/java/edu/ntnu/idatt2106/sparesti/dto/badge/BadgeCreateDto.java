package edu.ntnu.idatt2106.sparesti.dto.badge;

import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto for creating a badge.
 */
@Getter
@Setter
@Builder
public class BadgeCreateDto {
  private Achievement achievement;
  private LocalDate achievementDate;
  private int level;
}
