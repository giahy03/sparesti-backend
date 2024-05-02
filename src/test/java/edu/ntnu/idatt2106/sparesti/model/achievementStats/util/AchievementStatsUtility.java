package edu.ntnu.idatt2106.sparesti.model.achievementStats.util;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.CheckForAchievementDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;

import java.time.LocalDate;
import java.util.List;

public class AchievementStatsUtility {

    public static AchievementStats createAchievementStatsA() {

        return AchievementStats.builder()
                .id(1L)
                .challengesAchieved(9)
                .savingGoalsAchieved(9)
                .readNews(false)
                .streak(9)
                .totalSaved(99.0)
                .user(createUserA())
                .build();
    }

    public static AchievementStats createAchievementStatsB() {

        return AchievementStats.builder()
                .id(2L)
                .challengesAchieved(15)
                .savingGoalsAchieved(15)
                .readNews(true)
                .streak(15)
                .totalSaved(120.0)
                .user(createUserB())
                .build();
    }

    public static User createUserA() {
        return User.builder()
                .email("example@email")
                .firstName("Kari")
                .role(Role.USER)
                .lastName("Normann")
                .password("password")
                .build();
    }

    public static User createUserB() {
        return User.builder()
                .email("example2@email")
                .firstName("Kari")
                .role(Role.USER)
                .lastName("Normann")
                .password("password")
                .build();
    }

    public static Achievement createAchievement() {
        return Achievement.builder()
                .description("Saving streak")
                .thresholds(List.of(1,2,3,4))
                .category(AchievementCategory.AMOUNT_SAVED)
                .id(1)
                .build();
    }

    public static CheckForAchievementDto CheckForAchievementDtoA() {
        return CheckForAchievementDto.builder()
                .achievement(AchievementCategory.AMOUNT_SAVED)
                .achievementDate(LocalDate.of(2024, 4, 1))
                .build();
    }

    public static CheckForAchievementDto CheckForAchievementDtoB() {
        return CheckForAchievementDto.builder()
                .achievement(AchievementCategory.SAVING_STREAK)
                .achievementDate(LocalDate.of(2024, 4, 1))
                .build();
    }

    public static BadgePreviewDto createBadgePreviewDto() {
        return BadgePreviewDto.builder()
                .id(2L)
                .level(4)
                .threshold(15)
                .achievementDate(LocalDate.of(2024, 4, 1))
                .achievement(AchievementCategory.SAVING_STREAK)
                .build();
    }

}
