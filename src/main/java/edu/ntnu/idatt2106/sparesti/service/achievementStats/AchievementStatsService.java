package edu.ntnu.idatt2106.sparesti.service.achievementStats;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.CheckForAchievementDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.*;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.badge.BadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    private final SavingGoalRepository savingGoalRepository;
    private final BadgeRepository badgeRepository;
    private final BadgeMapper badgeMapper;


    public int checkAchievement (CheckForAchievementDto checkForAchievementDto, Principal principal) {

        AchievementCategory category = checkForAchievementDto.getAchievement();

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));


        return switch (category) {
            case SAVING_STREAK ->
                    updateSavingStreak(user) ? checkSavingStreakLevel(principal, user) : 0;
            case CHALLENGE_STREAK ->
                    0;   // Temporarily
            case AMOUNT_SAVED ->
                    updateTotalSaved(user, principal) ? checkTotalSaved(principal, user) : 0;
            case NUMBER_OF_CHALLENGES_COMPLETED ->
                    updateNumberOfChallengesFinished(user, principal) ? checkChallengesCompleted(principal, user) : 0;
            case NUMBER_OF_SAVING_GOALS_ACHIEVED ->
                    updateSavingGoalsAchieved(user, principal) ? checkGoalsCompleted(principal, user) : 0;
            case EDUCATION ->
                    updateEducation(user, principal) ? checkEducation(user) : 0;
        };
    }


    public BadgePreviewDto createBadge (CheckForAchievementDto checkForAchievementDto, Principal principal, int level) {

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));


        Badge badge = Badge.builder()
                .achievement(getAchievementOfCategory(checkForAchievementDto.getAchievement(), principal))
                .achievedDate(checkForAchievementDto.getAchievementDate())
                .level(level)
                .user(user)
                .build();

        Badge savedBadge = badgeRepository.save(badge);
        return badgeMapper.mapToBadgePreviewDto(savedBadge);
    }


    /**
     * Return a DTO representing the achievement that the badge belongs to.
     *
     */
    public Achievement getAchievementOfCategory(AchievementCategory achievementCategory, Principal principal) {
        return achievementRepository.findByCategory(achievementCategory).orElseThrow();
    }


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

    private boolean updateTotalSaved(User user, Principal principal) {

        double totalProgress = savingGoalRepository.findAllByUser_Username(principal.getName(), Pageable.unpaged())
                .stream().mapToDouble(SavingGoal::getProgress).sum();
        double oldTotal = user.getStats().getTotalSaved();


        if (totalProgress > oldTotal){
            user.getStats().setTotalSaved(totalProgress);
            return true;
        } else {
            return false;
        }
    }


    private boolean updateNumberOfChallengesFinished(User user, Principal principal) {

        int finishedChallenges = 4;   // Temporarily until Challenge::state is defined.

       /* int finishedChallenges = savingGoalRepository.findAllByUser_Username(principal.getName(), Pageable.unpaged())
                .stream().filter(Challenge::getState == ENUM.FINISHED ).count();*/

        int oldCount = user.getStats().getChallengesAchieved();

        if (finishedChallenges > oldCount){
            user.getStats().setTotalSaved(finishedChallenges);
            return true;
        } else {
            return false;
        }
    }


    private boolean updateSavingGoalsAchieved(User user, Principal principal) {

        int achievedGoals = 4;  // Temporarily

        /*int achievedGoals = savingGoalRepository.findAllByUser_Username(principal.getName(), Pageable.unpaged())
                .stream().filter(SavingGoal::getState() == GoalState.FINISHED).count();*/

        int oldCount = user.getStats().getSavingGoalsAchieved();

        if (achievedGoals > oldCount){
            user.getStats().setTotalSaved(achievedGoals);
            return true;
        } else {
            return false;
        }
    }

    private boolean updateEducation(User user, Principal principal) {

        boolean alreadyAchieved = user.getStats().isReadNews();

        if (alreadyAchieved){
            return false;
        } else {
            user.getStats().setReadNews(true);
            return true;
        }

    }



    private int checkSavingStreakLevel(Principal principal, User user) {

        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.SAVING_STREAK, principal)
                .getThresholds();

        // Hente dette fra repo eller ha lagret i achievement stats?
        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.SAVING_STREAK)
                .getLevel();

        int calculatedLevel = findLevel(thresholds, user.getStats().getStreak());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;
    }




    private int checkTotalSaved(Principal principal, User user) {

        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.AMOUNT_SAVED, principal)
                .getThresholds();

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.AMOUNT_SAVED)
                .getLevel();

        int calculatedLevel = findLevel(thresholds, user.getStats().getTotalSaved());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;

    }



    private int checkGoalsCompleted(Principal principal, User user) {
        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED, principal)
                .getThresholds();

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.NUMBER_OF_SAVING_GOALS_ACHIEVED)
                .getLevel();

        int calculatedLevel = findLevel(thresholds, user.getStats().getSavingGoalsAchieved());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;
    }

    private int checkEducation(User user) {

        if (user.getStats().isReadNews()) {
            return 0;
        } else {
            user.getStats().setReadNews(true);
            return 1;
        }
    }

    private int checkChallengesCompleted(Principal principal, User user) {
        List<Integer> thresholds = getAchievementOfCategory(AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED, principal)
                .getThresholds();

        int currentLevel = badgeRepository
                .findFirstByUser_EmailAndAchievement_Category_OrderByLevelDesc(principal.getName(), AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED)
                .getLevel();

        int calculatedLevel = findLevel(thresholds, user.getStats().getChallengesAchieved());

        return calculatedLevel > currentLevel ? calculatedLevel : 0;
    }




    private int findLevel(List<Integer> thresholds, int value) {
        int level = 0;
        for (int i = 0; i < thresholds.size(); i++) {
            if (thresholds.get(i) <= value) {
                level = i+1;
            }
        }
        return level;
    }

    private int findLevel(List<Integer> thresholds, double value) {
        int level = 0;
        for (int i = 0; i < thresholds.size(); i++) {
            if (thresholds.get(i) <= value) {
                level = i+1;
            }
        }
        return level;
    }


}
