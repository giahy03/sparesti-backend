package edu.ntnu.idatt2106.sparesti.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import java.util.List;
import org.junit.jupiter.api.Test;

class AchievementMapperTest {

  @Test
  void givenValidAchievement_whenMapToAchievementPreviewDto_thenReturnAchievementPreviewDto() {
    // arrange
    Achievement achievement = new Achievement();
    AchievementCategory achievementCategory = AchievementCategory.AMOUNT_SAVED;
    List<Integer> thresholds = List.of(1, 2, 3);
    String description = "Description";

    achievement.setCategory(achievementCategory);
    achievement.setThresholds(thresholds);
    achievement.setDescription("Description");

    // act
    AchievementPreviewDto achievementPreviewDto =
        new AchievementMapper().mapToAchievementPreviewDto(achievement);

    // assert
    assertEquals(achievementCategory, achievementPreviewDto.getCategory());
    assertEquals(thresholds, achievementPreviewDto.getThresholds());
    assertEquals(description, achievementPreviewDto.getDescription());
  }
}




