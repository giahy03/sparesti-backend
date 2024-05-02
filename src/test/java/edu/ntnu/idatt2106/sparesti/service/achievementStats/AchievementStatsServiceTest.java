package edu.ntnu.idatt2106.sparesti.service.achievementStats;


import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.achievementStats.util.AchievementStatsUtility;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingContributionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test class for the achievement stats service
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
//@PrepareForTest({Badge.class})
class AchievementStatsServiceTest {

    @InjectMocks
    AchievementStatsService achievementStatsService;

    @Mock
    UserRepository userRepository;

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    BadgeMapper badgeMapper;

    @Mock
    BadgeRepository badgeRepository;

    @Mock
    SavingContributionRepository savingContributionRepository;

    Principal principal;

    @BeforeEach
    void setUp() {
        AchievementStats stats = AchievementStatsUtility.createAchievementStatsA();
        principal = () -> "example@email";
    }



    @DisplayName("Test that method to find level returns correct level")
    @Test
    void AchievementStatsService_testFindLevel() {

        //Arrange
        List<Integer> levels = List.of(1, 3, 5, 10, 20, 25, 50);

        //Act and assert
        assertThat(achievementStatsService.findLevel(levels, 0)).isEqualTo(0);
        assertThat(achievementStatsService.findLevel(levels, 2)).isEqualTo(1);
        assertThat(achievementStatsService.findLevel(levels, 3)).isEqualTo(2);
        assertThat(achievementStatsService.findLevel(levels, 60)).isEqualTo(7);

        assertThat(achievementStatsService.findLevel(levels, 0.5)).isEqualTo(0);
        assertThat(achievementStatsService.findLevel(levels, 1.5)).isEqualTo(1);
        assertThat(achievementStatsService.findLevel(levels, 3.0)).isEqualTo(2);
        assertThat(achievementStatsService.findLevel(levels, 49.5)).isEqualTo(6);
        assertThat(achievementStatsService.findLevel(levels, 50.0)).isEqualTo(7);
    }
/*

    @DisplayName("Test that a badge is correctly created")
    @Test
    void AchievementStatsService_testCreateBadge() {
        // Arrange
        CheckForAchievementDto checkDto = AchievementStatsUtility.CheckForAchievementDtoA();


        when(userRepository.findUserByEmailIgnoreCase(principal.getName()))
                .thenReturn(Optional.ofNullable(AchievementStatsUtility.createUserA()));

        when(achievementRepository.findByCategory(AchievementCategory.AMOUNT_SAVED))
                .thenReturn(Optional.ofNullable(AchievementStatsUtility.createAchievement()));

        Badge badge = Mockito.moc(Badge.class);

        when(badgeMapper.mapToBadgePreviewDto(badge))
                .thenReturn(AchievementStatsUtility.createBadgePreviewDto());

        // Act
        BadgePreviewDto createdBadgeA = achievementStatsService.createBadge(checkDto, principal, 4);

        // Assert
        assertThat(createdBadgeA).isNotNull();
        assertThat(createdBadgeA.getAchievement()).isEqualTo(checkDto.getAchievement());
        assertThat(createdBadgeA.getAchievementDate()).isEqualTo(checkDto.getAchievementDate());
        assertThat(createdBadgeA.getLevel()).isEqualTo(4);
        System.out.println(createdBadgeA.getThreshold());


    }
*/

/*
    @DisplayName("Test for getAllBadgesByEmail")
    @Test
    void Badge_getAllBadgesByEmail_ReturnBadges() {

        // Arrange
        List<Badge> badgeList = new ArrayList<>();
        badgeList.add(BadgeUtility.createBadgeA());
        badgeList.add(BadgeUtility.createBadgeB());

        when(badgeRepository.findAllByUser_Username(principal.getName(), pageable)).thenReturn(badgeList);

        // Act
        List<BadgePreviewDto> returnedPreviewBadges = badgeService.getAllBadgesByEmail(principal, pageable);

        // Assert
        assertThat(returnedPreviewBadges.size()).isEqualTo(2);
    }*/


}
