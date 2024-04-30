package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author Hanne-Sofie Søreide
 */

@Component
@RequiredArgsConstructor
public class AchievementMapper {

   public AchievementPreviewDto mapToAchievementPreviewDto(Achievement achievement) {
       return AchievementPreviewDto.builder()
               .category(achievement.getCategory())
               .thresholds(achievement.getThresholds())
               .build();
   }


}
