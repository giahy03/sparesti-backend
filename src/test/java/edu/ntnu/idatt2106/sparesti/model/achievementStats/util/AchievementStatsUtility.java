package edu.ntnu.idatt2106.sparesti.model.achievementStats.util;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;

public class AchievementStatsUtility {

    public static AchievementStats createAchievementStats() {

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


}
