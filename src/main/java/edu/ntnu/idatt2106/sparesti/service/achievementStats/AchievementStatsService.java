package edu.ntnu.idatt2106.sparesti.service.achievementStats;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.goal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.*;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


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


    /**
     * Updates the user stats related to the achievement type specified in the input Dto object. If a stat is updated,
     * the user's statistics are compared to the corresponding achievement thresholds to determine if the user
     * qualifies for a new badge. If so, an int indicated the level of the badge is returned.
     * If the user does not qualify for a new badge 0 is returned.
     *
     * @param category The achievement type to update and check.
     * @param principal The authenticated user
     * @return An int indicating the level of the new badge the user qualifies for, or 0 if the user
     *          does not qualify for a new badge.
     */
    public int updateAndCheckAchievement(AchievementCategory category, Principal principal) {

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));

        return switch (category) {
            case SAVING_STREAK ->
                    updateSavingStreak(user) ? checkSavingStreakLevel(principal, user) : 0;
            case AMOUNT_SAVED ->
                    updateTotalSaved(user, principal) ? checkTotalSaved(principal, user) : 0;
            case NUMBER_OF_CHALLENGES_COMPLETED ->
                    updateNumberOfChallengesFinished(user, principal) ? checkChallengesCompleted(principal, user) : 0;
            case NUMBER_OF_SAVING_GOALS_ACHIEVED ->
                    updateSavingGoalsAchieved(user, principal) ? checkGoalsCompleted(principal, user) : 0;
            case EDUCATION ->
                    updateEducation(user);
        };
    }

    /**
     * Creates and stores a new badge for the user.
     * @param category The achievement type and the date it was achieved.
     * @param principal The authenticated user.
     * @param level The level of the badge to be created.
     * @return A DTO containing info to preview a Badge object.
     */
    public BadgePreviewDto createBadge (AchievementCategory category, Principal principal, int level) {

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));

        Badge badge = Badge.builder()
                .achievement(getAchievementOfCategory(category))
                .achievedDate(LocalDate.now())
                .level(level)
                .user(user)
                .build();

        System.out.println("Badge created: " + badge);
        Badge savedBadge = badgeRepository.save(badge);

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
     * Compares the stored streak in user stats object against the user streak and updates the stored stats if a
     * higher streak is achieved.
     * If a change was made to the stats, true is returned. If there is no change, false is returned.
     * @param user The user the streak belongs to.
     * @return True if the user stats has been updated, false otherwise.
     */
    private boolean updateSavingStreak(User user) {

        int streak = user.getStreak().getNumberOfDays();
        int oldStreak = user.getStats().getStreak();

        if (streak > oldStreak){
            user.getStats().setStreak(oldStreak);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Summarizes the saving progress of all the user's goals. If the total is larger than the one stored in the
     * user's stats, the value is updated and true is returned. If it is not updated, false is returned.
     * @param user The user for which the total saved amount may be updated.
     * @param principal The authenticated user.
     * @return True if the total saved amount is updated, false if not.
     */
    private boolean updateTotalSaved(User user, Principal principal) {

        double totalContribution = savingContributionRepository
                .findAllContributionsByUser_Email(principal.getName(), Pageable.unpaged())
                .stream()
                .map(SavingContribution::getContribution).mapToDouble(f -> f).sum();

        double oldTotal = user.getStats().getTotalSaved();

        if (totalContribution > oldTotal){
            user.getStats().setTotalSaved(totalContribution);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compares the total number of completed challenges for the user with the corresponding number stored
     * in the user's stats. If it has increased, the user's stats is updated and true is returned, if not false is
     * returned.
     * @param user The user for which teh total number of completed challenges may be updated.
     * @param principal The authenticated user.
     * @return True if the total number of completed challenges in increased, false otherwise.
     */
    private boolean updateNumberOfChallengesFinished(User user, Principal principal) {

        int completedChallenges = (int) challengesRepository.findByUser_Email(principal.getName(), Pageable.unpaged())
                .stream()
                .filter(challenge -> challenge.getProgress().equals(Progress.COMPLETED))
                .count();

        int oldCount = user.getStats().getChallengesAchieved();

        if (completedChallenges > oldCount){
            user.getStats().setTotalSaved(completedChallenges);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compares the total number of completed saving goals for the user with the corresponding number stored
     * in the user's stats. If it has increased, the user's stats is updated and true is returned, if not false is
     * returned.
     * @param user The user for which teh total number of completed saving goals may be updated.
     * @param principal The authenticated user.
     * @return True if the total number of completed saving goals in increased, false otherwise.
     */
    private boolean updateSavingGoalsAchieved(User user, Principal principal) {

        List<SavingGoal> goals = savingContributionRepository.findAllContributionsByUser_Email(principal.getName(), Pageable.unpaged())
                .stream().map(SavingContribution::getGoal).toList();

        int achievedGoals = (int) goals.stream()
                .filter(goal -> goal.getState().equals(GoalState.FINISHED))
                .count();

        int oldCount = user.getStats().getSavingGoalsAchieved();

        if (achievedGoals > oldCount){
            user.getStats().setTotalSaved(achievedGoals);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the user's stat related to reading news in the application. If it has changed, the
     * user's stat is updated and true is returned, otherwise false is returned.
     * @param user The user for which the education stat may be updated.
     * @return True if the user's stat related to reading news is updated, false otherwise.
     */
    private int updateEducation(User user) {

        if (user.getStats().isReadNews()){
            return 0;
        } else {
            user.getStats().setReadNews(true);
            return 1;
        }
    }


    /**
     * Checks if the user qualifies for a new badge related to saving streak with the current user stats. If so,
     * an int representing the level of the new badge is returned.
     *
     * @param principal The authenticated user.
     * @param user The user who may have qualified for a new badge.
     * @return An int indicating the level of the new badge or 0 if the user did not qualify for a new badge.
     */
    private int checkSavingStreakLevel(Principal principal, User user) {

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.SAVING_STREAK)
                .getLevel();

        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.SAVING_STREAK)
                .getThresholds();

        int calculatedLevel = findLevel(thresholds, user.getStats().getStreak());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;
    }


    /**
     * Checks if the user qualifies for a new badge related to total saved amount with the current user stats.
     * If so, an int representing the level of the new badge is returned.
     *
     * @param principal The authenticated user.
     * @param user The user who may have qualified for a new badge.
     * @return An int indicating the level of the new badge or 0 if the user did not qualify for a new badge.
     */
    private int checkTotalSaved(Principal principal, User user) {

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.AMOUNT_SAVED)
                .getLevel();

        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.AMOUNT_SAVED)
                .getThresholds();

        int calculatedLevel = findLevel(thresholds, user.getStats().getTotalSaved());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;

    }


    /**
     * Checks if the user qualifies for a new badge related to the number of saving goal completed with the current
     * user stats. If so, an int representing the level of the new badge is returned.
     *
     * @param principal The authenticated user.
     * @param user The user who may have qualified for a new badge.
     * @return An int indicating the level of the new badge or 0 if the user did not qualify for a new badge.
     */
    private int checkGoalsCompleted(Principal principal, User user) {

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED)
                .getLevel();

        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED)
                .getThresholds();

        int calculatedLevel = findLevel(thresholds, user.getStats().getSavingGoalsAchieved());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;
    }


    /**
     * Checks if the user qualifies for a new badge related to the number of completed challenges with the current
     * user stats. If so, an int representing the level of the new badge is returned.
     *
     * @param principal The authenticated user.
     * @param user  The user who may have qualified for a new badge.
     * @return An int indicating the level of the new badge or 0 if the user did not qualify for a new badge.
     */
    private int checkChallengesCompleted(Principal principal, User user) {

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED)
                .getLevel();

        // int maxLevel = getAchievementOfCategory(AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED).getNumberOfLevels();
        // if (currentLevel == maxLevel) return 0;

        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED)
                .getThresholds();

        int calculatedLevel = findLevel(thresholds, user.getStats().getChallengesAchieved());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;
    }


    /**
     * Checks which level a particular int value corresponds to among an achievement object's thresholds.
     * A number between 1 and the maximum level of the achievement is returned.
     *
     * @param thresholds The thresholds for the possible badges to qualify for within an achievement type.
     * @param value An integer to compare with the thresholds.
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
     * Checks which level a particular double value corresponds to among an achievement object's thresholds.
     * A number between 1 and the maximum level of the achievement is returned.
     *
     * @param thresholds The thresholds for the possible badges to qualify for within an achievement type.
     * @param value A decimal number to compare with the thresholds.
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
