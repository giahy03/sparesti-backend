package edu.ntnu.idatt2106.sparesti.service.achievementStats;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.goal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.AchievementStatsRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingContributionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service class to handle operations related to the achievement stats of the user.
 * The service checks the relevant data and updated the corresponding property of the
 * user's achievement stat object.
 *
 * @author Hanne-Sofie Søreide
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementStatsService {

  private final AchievementRepository achievementRepository;
  private final UserRepository userRepository;
  private final BadgeRepository badgeRepository;
  private final BadgeMapper badgeMapper;
  private final ChallengesRepository challengesRepository;
  private final SavingContributionRepository savingContributionRepository;
  private final AchievementStatsRepository achievementStatsRepository;


  /**
   * Updates the user stats related to the achievement type
   * specified in the input category. If a stat is updated,
   * the user's statistics are compared to the
   * corresponding achievement thresholds to determine if the user
   * qualifies for one or several new badges. If so, a list containing
   * two integers is returned. The first integer represents the highest
   * level of the badge to be created and the second integer gives hos many
   * new badges the user was qualified for.
   * If the user does not qualify for a new badge both integers are 0.
   * If the user checks its stats for the first time, a new achievement
   * stats object is created and assigned to the user.
   *
   * @param category  The achievement type to update and check.
   * @param principal The authenticated user.
   * @return A list containing two integers specifying how many new badges to
   *        create and of which level.
   */
  public List<Integer> updateAndCheckAchievement(AchievementCategory category, Principal principal) {

    String email = principal.getName();

    User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
            new UserNotFoundException("User with email " + email + " not found"));

    if(user.getStats()==null) {
      AchievementStats stats = AchievementStats.builder()
              .savingGoalsAchieved(0)
              .challengesAchieved(0)
              .totalSaved(0.0)
              .readNews(false)
              .streak(0)
              .user(user)
              .build();
      AchievementStats storedStats = achievementStatsRepository.save(stats);
      user.setStats(storedStats);
      userRepository.save(user);
    }

    return switch (category) {
      case SAVING_STREAK ->
              updateSavingStreak(user) ? checkSavingStreakLevel(principal, user) : Arrays.asList(0,0);
      case AMOUNT_SAVED ->
              updateTotalSaved(user, principal) ? checkTotalSaved(principal, user) :  Arrays.asList(0,0);
      case NUMBER_OF_CHALLENGES_COMPLETED ->
              updateNumberOfChallengesFinished(user, principal) ?
                      checkChallengesCompleted(principal, user) :  Arrays.asList(0,0);
      case NUMBER_OF_SAVING_GOALS_ACHIEVED ->
              updateSavingGoalsAchieved(user, principal) ?
                      checkGoalsCompleted(principal, user) :  Arrays.asList(0,0);
      case EDUCATION ->
              updateEducation(user);
    };


  }

  /**
   * Creates and stores a new badge of a given achievement category and
   * level for the user.
   *
   * @param category  The achievement type and the date it was achieved.
   * @param principal The authenticated user.
   * @param level     The level of the badge to be created.
   * @return A DTO containing info to preview the Badge object.
   */
  public BadgePreviewDto createBadge(AchievementCategory category, Principal principal, int level) {
    log.info("Inside: createBadge");

    String email = principal.getName();

    User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
            new UserNotFoundException("User with email " + email + " not found"));

    Badge badge = Badge.builder()
            .achievement(getAchievementOfCategory(category))
            .achievedDate(LocalDate.now())
            .level(level)
            .user(user)
            .build();

    Badge savedBadge = badgeRepository.save(badge);
    if (user.getBadges() == null) {
      user.setBadges(Set.of(badge));
      userRepository.save(user);
    } else {
      user.getBadges().add(badge);
    }

    return badgeMapper.mapToBadgePreviewDto(savedBadge);
  }


  /**
   * Get the achievement object associated with the given category.
   *
   * @param achievementCategory The category to get achievement object of.
   * @return An achievement object of the given category.
   */
  public Achievement getAchievementOfCategory(AchievementCategory achievementCategory) {
    return achievementRepository.findByCategory(achievementCategory).orElseThrow();
  }


  /**
   * Compares the stored streak in user stats object
   * against the user streak and updates the stored stats if a
   * higher streak is achieved.
   * If a change was made to the stats, true is returned.
   * If there is no change, false is returned.
   *
   * @param user The user the streak belongs to.
   * @return True if the user stats were updated, false otherwise.
   */
  private boolean updateSavingStreak(User user) {

    int streak = user.getStreak().getNumberOfDays();
    int oldStreak = user.getStats().getStreak();

    if (streak > oldStreak) {
      user.getStats().setStreak(streak);
      userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }


  /**
   * Summarizes the saving progress of all the user's goals.
   * If the total is larger than the one stored in the
   * user's stats, the value is updated and true is returned.
   * If it is not updated, false is returned.
   *
   * @param user      The user for which the total saved amount
   *                  may be updated.
   * @param principal The authenticated user.
   * @return True if the total saved amount has increased, false if not.
   */
  private boolean updateTotalSaved(User user, Principal principal) {

    double totalContribution = savingContributionRepository
            .findAllContributionsByUser_Email(principal.getName(), Pageable.unpaged())
            .stream()
            .map(SavingContribution::getContribution).mapToDouble(f -> f).sum();

    double oldTotal = user.getStats().getTotalSaved();

    if (totalContribution > oldTotal) {
      user.getStats().setTotalSaved(totalContribution);
      userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Compares the total number of completed challenges
   * for the user with the corresponding number stored
   * in the user's stats. If it has increased, the user's
   * stats is updated and true is returned, if not false is
   * returned.
   *
   * @param user      The user for which teh total number of
   *                  completed challenges may be updated.
   * @param principal The authenticated user.
   * @return True if the total number of completed challenges
   *          in increased, false otherwise.
   */
  private boolean updateNumberOfChallengesFinished(
          User user, Principal principal) {

    int completedChallenges = (int) challengesRepository
            .findByUser_Email(principal.getName(), Pageable.unpaged())
            .stream()
            .filter(challenge -> challenge.getProgress().equals(Progress.COMPLETED))
            .count();

    int oldCount = user.getStats().getChallengesAchieved();

    if (completedChallenges > oldCount) {
      user.getStats().setChallengesAchieved(completedChallenges);
      userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Compares the total number of completed saving
   * goals for the user with the corresponding number stored
   * in the user's stats. If it has increased, the
   * user's stats is updated and true is returned, if not false is
   * returned.
   *
   * @param user      The user for which teh total
   *                  number of completed saving goals may be updated.
   * @param principal The authenticated user.
   * @return True if the total number of completed
   *         saving goals in increased, false otherwise.
   */
  private boolean updateSavingGoalsAchieved(User user, Principal principal) {

    List<SavingGoal> goals = savingContributionRepository
            .findAllContributionsByUser_Email(principal.getName(), Pageable.unpaged())
            .stream().map(SavingContribution::getGoal).toList();

    int achievedGoals = (int) goals.stream()
            .filter(goal -> goal.getState().equals(GoalState.FINISHED))
            .count();

    int oldCount = user.getStats().getSavingGoalsAchieved();

    if (achievedGoals > oldCount){
      user.getStats().setSavingGoalsAchieved(achievedGoals);
      userRepository.save(user);
      return true;
    } else {
      return false;
    }

  }

  /**
   * Updates the user's stat related to reading news in the application.
   * A list containing two integers is returned. If the user should
   * receive the education badge, the list contains 1 and 1, so
   * signal creation of one badge of level one.
   * If not, the list contains 0 and 0.
   *
   * @param user The user for which the education stat may be updated.
   * @return A list with two integers, the first one signifying the highest achieved badge level
   *          and the second one the number of new badges to create.
   */
  private List<Integer> updateEducation(User user) {

    if (user.getStats().isReadNews()){
      return Arrays.asList(0,0);
    } else {
      user.getStats().setReadNews(true);
      userRepository.save(user);
      return Arrays.asList(1,1);
    }

  }


  /**
   * Checks if the user qualifies for a new badge
   * related to saving streak with the current user stats.
   * A list containing two integers is returned.
   * If the user qualifies for a new badge, the first entry in
   * the list contains the level of the highest badge to create
   * while the second entry contains the number of badges to create.
   * If the user did not qualify for a new badge, the list contains
   * 0 and 0.
   *
   * @param principal The authenticated user.
   * @param user      The user who may have qualified for a new badge.
   * @return A list containing two integers. The first specifies the highest
   *        level badge to create and the second specifies the number of new
   *        badges to create.
   */
  private List<Integer> checkSavingStreakLevel(Principal principal, User user) {

    Badge currentTopBadge = badgeRepository
            .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(
                    principal.getName(), AchievementCategory.SAVING_STREAK).orElse(null);

    List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.SAVING_STREAK)
            .getThresholds();

    int currentLevel = currentTopBadge == null ? 0 : currentTopBadge.getLevel();
    int calculatedLevel = findLevel(thresholds, user.getStats().getStreak());

    int newTopLevel = calculatedLevel > currentLevel ? calculatedLevel : 0;

    return Arrays.asList(newTopLevel, calculatedLevel-currentLevel);  }


  /**
   * Checks if the user qualifies for a new badge
   * related to total saved amount with the current user stats.
   * A list containing two integers is returned.
   * If the user qualifies for a new badge, the first entry in
   * the list contains the level of the highest badge to create
   * while the second entry contains the number of badges to create.
   * If the user did not qualify for a new badge, the list contains
   * 0 and 0.
   *
   * @param principal The authenticated user.
   * @param user      The user who may have qualified for a new badge.
   * @return A list containing two integers. The first specifies the highest
   *        level badge to create and the second specifies the number of new
   *        badges to create.
   */
  private List<Integer> checkTotalSaved(Principal principal, User user) {

    Badge currentTopBadge = badgeRepository
            .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(
                    principal.getName(), AchievementCategory.AMOUNT_SAVED).orElse(null);

    List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.AMOUNT_SAVED)
            .getThresholds();

    int currentLevel = currentTopBadge == null ? 0 : currentTopBadge.getLevel();
    int calculatedLevel = findLevel(thresholds, user.getStats().getTotalSaved());

    int newTopLevel = calculatedLevel > currentLevel ? calculatedLevel : 0;

    return Arrays.asList(newTopLevel, calculatedLevel-currentLevel);

  }


  /**
   * Checks if the user qualifies for a new badge related
   * to the number of saving goal completed with the current
   * user stats. A list containing two integers is returned.
   * If the user qualifies for a new badge, the first entry in
   * the list contains the level of the highest badge to create
   * while the second entry contains the number of badges to create.
   * If the user did not qualify for a new badge, the list contains
   * 0 and 0.
   *
   * @param principal The authenticated user.
   * @param user      The user who may have qualified for a new badge.
   * @return A list containing two integers. The first specifies the highest
   *         level badge to create and the second specifies the number of new
   *         badges to create.
   */
  private List<Integer> checkGoalsCompleted(Principal principal, User user) {

    Badge currentTopBadge = badgeRepository
            .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(
                    principal.getName(),
                    AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED).orElse(null);

    List<Integer> thresholds = getAchievementOfCategory(
            AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED)
            .getThresholds();

    int currentLevel = currentTopBadge == null ? 0 : currentTopBadge.getLevel();
    int calculatedLevel = findLevel(thresholds, user.getStats().getSavingGoalsAchieved());

    int newTopLevel = calculatedLevel > currentLevel ? calculatedLevel : 0;

    return Arrays.asList(newTopLevel, calculatedLevel-currentLevel);
  }


  /**
   * Checks if the user qualifies for a new badge
   * related to the number of completed challenges with the current
   * user stats. A list containing two integers is returned.
   * If the user qualifies for a new badge, the first entry in
   * the list contains the level of the highest badge to create
   * while the second entry contains the number of badges to create.
   * If the user did not qualify for a new badge, the list contains
   * 0 and 0.
   *
   * @param principal The authenticated user.
   * @param user      The user who may have qualified for a new badge.
   * @return A list containing two integers. The first specifies the highest
   *         level badge to create and the second specifies the number of new
   *         badges to create.
   */
  private List<Integer> checkChallengesCompleted(Principal principal, User user) {

    Badge currentTopBadge = badgeRepository
            .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(),
                    AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED).orElse(null);

    List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED)
            .getThresholds();

    int currentLevel = currentTopBadge == null ? 0 : currentTopBadge.getLevel();
    int calculatedLevel = findLevel(thresholds, user.getStats().getChallengesAchieved());

    int newTopLevel = calculatedLevel > currentLevel ? calculatedLevel : 0;

    return Arrays.asList(newTopLevel, calculatedLevel - currentLevel);

  }


  /**
   * Checks which level a particular int value
   * corresponds to among an achievement object's thresholds.
   * A number between 1 and the maximum level of the achievement is returned.
   *
   * @param thresholds The thresholds for the possible
   *                   badges to qualify for within an achievement type.
   * @param value      An integer to compare with the thresholds.
   * @return The level that the particular value corresponds to, given the thresholds.
   */
  public int findLevel(List<Integer> thresholds, int value) {
    for (int i = 0; i < thresholds.size(); i++) {
      if (thresholds.get(i) > value) {
        return i;
      }
    }
    return value >= thresholds.getLast() ? thresholds.size() : 0;
  }


  /**
   * Checks which level a particular double value
   * corresponds to among an achievement object's thresholds.
   * A number between 1 and the maximum level of the achievement is returned.
   *
   * @param thresholds The thresholds for the possible
   *                   badges to qualify for within an achievement type.
   * @param value      A decimal number to compare with the thresholds.
   * @return The level that the particular value corresponds to, given the thresholds.
   */
  public int findLevel(List<Integer> thresholds, double value) {
    for (int i = 0; i < thresholds.size(); i++) {
      if (thresholds.get(i) > value) {
        return i;
      }
    }
    return value >= thresholds.getLast() ? thresholds.size() : 0;

  }
}
