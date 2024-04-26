package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.AchievementStatsDto;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper class that converts between AchievementStats and
 * AchievementStatsDto.
 *
 * @author Hanne-Sofie Søreide
 */

@Mapper
public interface AchievementStatsMapper {
    @Mapping(target = "user", ignore = true)

    AchievementStats achievementStatsDtoToAchievementStats(AchievementStatsDto achievementStatsDto);

    AchievementStatsDto achievementStatsToAchievementStatsDto(AchievementStats achievementStats);

}
