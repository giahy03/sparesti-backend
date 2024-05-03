package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between achievement objects and achievement DTO objects.
 *
 * @author Hanne-Sofie Søreide
 */
@Component
@RequiredArgsConstructor
public class AchievementMapper {

  /**
   * Maps an achievement to an AchievementPreviewDto.
   *
   * @param achievement the achievement to be mapped
   * @return the mapped AchievementPreviewDto
   */
  public AchievementPreviewDto mapToAchievementPreviewDto(Achievement achievement) {
    return AchievementPreviewDto.builder()
        .category(achievement.getCategory())
        .thresholds(achievement.getThresholds())
        .description(achievement.getDescription())
        .build();
  }


}
