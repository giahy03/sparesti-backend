package edu.ntnu.idatt2106.sparesti.dto.achievementstats;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO providing a preview of the achievement.
 *
 * @author Hanne-Sofie Søreide
 */

@Getter
@Setter
@Builder
public class AchievementPreviewDto {

  private AchievementCategory category;
  private List<Integer> thresholds;
  private String description;

}
