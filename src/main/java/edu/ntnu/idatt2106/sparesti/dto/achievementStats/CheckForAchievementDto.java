package edu.ntnu.idatt2106.sparesti.dto.achievementStats;


import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * A Dto containing the type of achievement to check if the user
 * has qualified for a new badge.
 *
 * @author Hanne-Sofie Søredie
 */

@Getter
@Setter
@Builder
public class CheckForAchievementDto {

    private AchievementCategory achievement;
    private LocalDate achievementDate;

}
