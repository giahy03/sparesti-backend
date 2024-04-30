package edu.ntnu.idatt2106.sparesti.service.achievementStats;


import edu.ntnu.idatt2106.sparesti.model.achievementStats.util.AchievementStatsUtility;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

/**
 * Test class for the achievement stats service
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
public class AchievementStatsServiceTest {

    Principal principal;

    AchievementStats stats;

    @BeforeEach
    void setUp() {
        stats = AchievementStatsUtility.createAchievementStats();
        principal = () -> "test@test.tea";
    }

}
