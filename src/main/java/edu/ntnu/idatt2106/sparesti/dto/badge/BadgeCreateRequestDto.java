package edu.ntnu.idatt2106.sparesti.dto.badge;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
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
public class BadgeCreateRequestDto {
  private AchievementCategory achievement;
  private LocalDate achievementDate;
  private int level;
}