package edu.ntnu.idatt2106.sparesti.service.achievementstats;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.achievementstats.util.AchievementStatsUtility;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.badge.util.BadgeUtility;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.goal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.model.streak.Streak;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.AchievementStatsRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingContributionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;


/**
 * Test class for the achievement stats service.
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
class AchievementStatsServiceTest {

  @InjectMocks
  AchievementStatsService achievementStatsService;

  @Mock
  UserRepository userRepository;

  @Mock
  AchievementRepository achievementRepository;

  @Mock
  AchievementStatsRepository achievementStatsRepository;

  @Mock
  BadgeMapper badgeMapper;

  @Mock
  BadgeRepository badgeRepository;
  @Mock
  SavingContributionRepository savingContributionRepository;

  @Mock
  ChallengesRepository challengeRepository;

  User user;

  Principal principal;


  @BeforeEach
  void setUp() {
    AchievementStats stats = AchievementStatsUtility.createAchievementStatsA();
    user = AchievementStatsUtility.createUserA();
    principal = user::getEmail;
    user.setStats(stats);
    Streak streak = ChallengeUtility.createStreak1();
    user.setStreak(streak);
  }

  @DisplayName("Test that method to find level returns correct level")
  @Test
  void achievementStatsService_testFindLevel() {

    //Arrange
    List<Integer> levels = List.of(1, 3, 5, 10, 20, 25, 50);

    //Act and assert
    assertThat(achievementStatsService.findLevel(levels, 0)).isZero();
    assertThat(achievementStatsService.findLevel(levels, 2)).isEqualTo(1);
    assertThat(achievementStatsService.findLevel(levels, 3)).isEqualTo(2);
    assertThat(achievementStatsService.findLevel(levels, 60)).isEqualTo(7);

    assertThat(achievementStatsService.findLevel(levels, 0.5)).isZero();
    assertThat(achievementStatsService.findLevel(levels, 1.5)).isEqualTo(1);
    assertThat(achievementStatsService.findLevel(levels, 3.0)).isEqualTo(2);
    assertThat(achievementStatsService.findLevel(levels, 49.5)).isEqualTo(6);
    assertThat(achievementStatsService.findLevel(levels, 50.0)).isEqualTo(7);
  }

  @Test
  void service_UpdateAndCheckAchievement_ReturnListOfInt() {
    // Arrange
    user.getStats().setTotalSaved(0);
    SavingGoal savingGoal = SavingGoalUtility.createSavingGoal1(user);
    savingGoal.setState(GoalState.FINISHED);
    SavingContribution savingContribution = ChallengeUtility.createSavingContribution();
    savingContribution.setGoal(savingGoal);
    savingGoal.setContributions(List.of(savingContribution));
    user.setContributions(List.of(savingContribution));
    Challenge challenge = ChallengeUtility.createSharedChallenge3();

    when(userRepository.findUserByEmailIgnoreCase(anyString()))
            .thenReturn(Optional.ofNullable(user));

    when(badgeRepository.findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(anyString(),
            any(AchievementCategory.class)))
            .thenReturn(Optional.of(BadgeUtility.createBadgeA()));

    when(achievementRepository.findByCategory(any(AchievementCategory.class)))
            .thenReturn(Optional.ofNullable(AchievementStatsUtility.createAchievement()));

    when(challengeRepository.findByUser_Email(anyString(), any(Pageable.class)))
            .thenReturn(List.of(challenge, challenge,
                    challenge, challenge, challenge));

    when(savingContributionRepository
            .findAllContributionsByUser_Email(anyString(), any(Pageable.class)))
            .thenReturn(List.of(savingContribution, savingContribution,
                    savingContribution, savingContribution));


    // Act
    final List<Integer> updatedLevel = achievementStatsService
         .updateAndCheckAchievement(AchievementCategory.EDUCATION, principal);
    final List<Integer> updatedLevel2 = achievementStatsService
            .updateAndCheckAchievement(AchievementCategory.AMOUNT_SAVED, principal);
    final List<Integer> updatedLevel3 = achievementStatsService
            .updateAndCheckAchievement(AchievementCategory.SAVING_STREAK, principal);
    achievementStatsService.updateAndCheckAchievement(
            AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED, principal);
    achievementStatsService.updateAndCheckAchievement(
            AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED, principal);

    // Assert
    assertThat(updatedLevel.getFirst()).isNotZero();
    assertThat(updatedLevel2.getFirst()).isNotZero();
    assertThat(updatedLevel3.getFirst()).isNotZero();
  }

  @Test
  void service_CreateBadge_ReturnsBadge() {
    // Arrange
    when(userRepository.findUserByEmailIgnoreCase(principal.getName()))
            .thenReturn(Optional.ofNullable(AchievementStatsUtility.createUserA()));

    when(achievementRepository.findByCategory(AchievementCategory.AMOUNT_SAVED))
            .thenReturn(Optional.ofNullable(AchievementStatsUtility.createAchievement()));

    when(badgeRepository.save(any(Badge.class)))
            .thenReturn(BadgeUtility.createBadgeA());

    when(badgeMapper.mapToBadgePreviewDto(any(Badge.class)))
            .thenReturn(BadgeUtility.createBadgePreviewDto());

    // Act
    BadgePreviewDto createdBadgeA = achievementStatsService
            .createBadge(AchievementCategory.AMOUNT_SAVED, principal, 4);
    assertThat(createdBadgeA).isNotNull();
  }

}
