package edu.ntnu.idatt2106.sparesti.dto.achievementStats;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO object representing a set of achievement statistics for the user.
 *
 * @author Hanne-Sofie Søreide
 */

@Getter
@Setter
@Builder
public class AchievementStatsDto {
    private long id;
    private int streak;
    private int challengeStreak;
    private int challengesAchieved;
    private int savingGoalsAchieved;
    private double totalSaved;
    private boolean readNews;

}
