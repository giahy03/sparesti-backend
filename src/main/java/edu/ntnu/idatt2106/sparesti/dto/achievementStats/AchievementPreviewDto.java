package edu.ntnu.idatt2106.sparesti.dto.achievementStats;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
