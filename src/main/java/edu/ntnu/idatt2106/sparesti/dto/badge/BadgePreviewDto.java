package edu.ntnu.idatt2106.sparesti.dto.badge;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO to represent a badge achieved by the user.
 * The DTO only contains the fields of interest to display the badge at the front-end.
 *
 * @author Hanne-Sofie Søreide
 */
@Getter
@Setter
@Builder
public class BadgePreviewDto {
  private AchievementCategory achievement;
  private int threshold;
  private LocalDate achievementDate;
}
