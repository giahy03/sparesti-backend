package edu.ntnu.idatt2106.sparesti.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import java.util.List;
import org.junit.jupiter.api.Test;

class BadgeMapperTest {
  @Test
  void givenBadge_whenMapToBadgeDto_thenReturnValidBadge() {
    // arrange
    Badge badge = new Badge();

    Achievement achievement = Achievement
        .builder()
        .thresholds(List.of(1))
        .category(AchievementCategory.SAVING_STREAK)
        .description("a")
        .build();
    achievement.setCategory(AchievementCategory.SAVING_STREAK);
    String description = "Save money for 5 days in a row";
    achievement.setDescription(description);
    badge.setAchievement(achievement);
    badge.setLevel(1);

    BadgeMapper badgeMapper = new BadgeMapper();

    // act
    BadgePreviewDto badgeDto = badgeMapper.mapToBadgePreviewDto(badge);

    // assert
    assertEquals(badge.getLevel(), badgeDto.getThreshold());
    assertEquals(badge.getAchievedDate(), badgeDto.getAchievementDate());
  }
}


