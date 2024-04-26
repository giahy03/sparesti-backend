package edu.ntnu.idatt2106.sparesti.service.achievementStats;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.CheckForAchievementDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementStats;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.AchievementStatsRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 *
 * @author Hanne-Sofie Søreide
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementStatsService {

    private final AchievementStatsRepository achievementStatsRepository;
     private final AchievementRepository achievementRepository;
     private final UserRepository userRepository;


    public boolean checkAchievement (CheckForAchievementDto checkForAchievementDto, Principal principal) {

        AchievementCategory category = checkForAchievementDto.getAchievement();

        switch (category) {
            case SAVING_STREAK -> checkSavingStreak(principal);
            case CHALLENGE_STREAK -> checkChallengeStreak(principal);
            case AMOUNT_SAVED -> checkTotalSaved(principal);
            case NUMBER_OF_CHALLENGES_COMPLETED -> checkChallengesCompreted(principal);
            case NUMBER_OF_SAVING_GOALS_ACHIEVED -> checkGoalsCompleted(principal);
            case EDUCATION -> checkEducation(principal);

        }

        return false;
    }


    /**
     * Return a DTO representing the achievement that the badge belongs to.
     *
     */
    public Achievement getAchievementOfCategory(AchievementCategory achievementCategory, Principal principal) {
        return achievementRepository.findByCategory(achievementCategory).orElseThrow();
    }


    private boolean checkSavingStreak(Principal principal) {

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));


        AchievementStats achievementStats = achievementStatsRepository.findAchievementStatsByUserEmail(email).orElseThrow();

        // Return level achieved.
        int streak = user.getStreak().getNumberOfDays();

        int oldStreak = achievementStats.getStreak();

        if (streak > oldStreak) {
            achievementStats.setStreak(streak);
            return true;
        } else {
            return false;
        }

    }


    private boolean checkTotalSaved(Principal principal) {

        return false;
    }


    private boolean checkChallengeStreak(Principal principal) {
        return false;
    }

    private boolean checkGoalsCompleted(Principal principal) {
        return false;
    }

    private boolean checkEducation(Principal principal) {
        return false;
    }

    private boolean checkChallengesCompreted(Principal principal) {
        return false;
    }

}
