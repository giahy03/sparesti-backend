package edu.ntnu.idatt2106.sparesti.model.achievementStats.util;

import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;

public class AchievementStatsUtility {

    public static AchievementStats createAchievementStats() {

        return AchievementStats.builder()
                .id(1L)
                .build();
    }


}
