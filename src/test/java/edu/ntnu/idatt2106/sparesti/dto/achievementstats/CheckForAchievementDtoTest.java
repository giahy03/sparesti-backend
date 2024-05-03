package edu.ntnu.idatt2106.sparesti.dto.achievementstats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import org.junit.jupiter.api.Test;

/**
 * Test class for the CheckForAchievementDto.
 *
 * @author Tobias Oftedal
 */
class CheckForAchievementDtoTest {

  @Test
  void testGettersAndSetters() {
    // Arrange
    AchievementCategory achievement = AchievementCategory.AMOUNT_SAVED;

    // Act
    CheckForAchievementDto dto = CheckForAchievementDto.builder().build();
    dto.setAchievement(achievement);

    // Assert
    assertEquals(achievement, dto.getAchievement());

    // Act
    AchievementCategory newAchievement = AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED;
    dto.setAchievement(newAchievement);


    // Assert
    assertEquals(newAchievement, dto.getAchievement());
  }

  @Test
  void testBuilder() {
    // Arrange
    AchievementCategory achievement = AchievementCategory.AMOUNT_SAVED;

    // Act
    CheckForAchievementDto dto = CheckForAchievementDto.builder()
        .achievement(achievement)
        .build();

    // Assert
    assertEquals(achievement, dto.getAchievement());
  }
}
