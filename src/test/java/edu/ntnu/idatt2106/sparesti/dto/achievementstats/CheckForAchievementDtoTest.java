package edu.ntnu.idatt2106.sparesti.dto.achievementstats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.CheckForAchievementDto;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CheckForAchievementDtoTest {

  @Test
  void testGettersAndSetters() {
    // Arrange
    AchievementCategory achievement = AchievementCategory.AMOUNT_SAVED;
    LocalDate achievementDate = LocalDate.now();

    // Act
    CheckForAchievementDto dto = CheckForAchievementDto.builder().build();
    dto.setAchievement(achievement);
    dto.setAchievementDate(achievementDate);

    // Assert
    assertEquals(achievement, dto.getAchievement());
    assertEquals(achievementDate, dto.getAchievementDate());

    // Act
    AchievementCategory newAchievement = AchievementCategory.CHALLENGE_STREAK;
    LocalDate newAchievementDate = LocalDate.now().plusDays(1);
    dto.setAchievement(newAchievement);
    dto.setAchievementDate(newAchievementDate);

    // Assert
    assertEquals(newAchievement, dto.getAchievement());
    assertEquals(newAchievementDate, dto.getAchievementDate());
  }

  @Test
  void testBuilder() {
    // Arrange
    AchievementCategory achievement = AchievementCategory.AMOUNT_SAVED;
    LocalDate achievementDate = LocalDate.now();

    // Act
    CheckForAchievementDto dto = CheckForAchievementDto.builder()
        .achievement(achievement)
        .achievementDate(achievementDate)
        .build();

    // Assert
    assertEquals(achievement, dto.getAchievement());
    assertEquals(achievementDate, dto.getAchievementDate());
  }
}
