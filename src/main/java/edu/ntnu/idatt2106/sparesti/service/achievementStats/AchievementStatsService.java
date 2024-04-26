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

    private final AchievementStatsRepository achievementStatsRepository;
     private final AchievementRepository achievementRepository;
     private final UserRepository userRepository;


    public boolean checkAchievement (CheckForAchievementDto checkForAchievementDto, Principal principal) {

        AchievementCategory category = checkForAchievementDto.getAchievement();

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));

        AchievementStats achievementStats = achievementStatsRepository.findAchievementStatsByUserEmail(user.getEmail()).orElseThrow();

        switch (category) {
            case SAVING_STREAK -> checkSavingStreak(user, achievementStats);
            case CHALLENGE_STREAK -> checkChallengeStreak(user, achievementStats);
            case AMOUNT_SAVED -> checkTotalSaved(user, achievementStats);
            case NUMBER_OF_CHALLENGES_COMPLETED -> checkChallengesCompreted(user, achievementStats);
            case NUMBER_OF_SAVING_GOALS_ACHIEVED -> checkGoalsCompleted(user, achievementStats);
            case EDUCATION -> checkEducation(user, achievementStats);

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


    private boolean checkSavingStreak(User user, AchievementStats achievementStats) {

        return false;

    }


    private boolean checkTotalSaved(User user, AchievementStats achievementStats) {

        return false;
    }


    private boolean checkChallengeStreak(User user, AchievementStats achievementStats) {
        return false;
    }

    private boolean checkGoalsCompleted(User user, AchievementStats achievementStats) {
        return false;
    }

    private boolean checkEducation(User user, AchievementStats achievementStats) {
        return false;
    }

    private boolean checkChallengesCompreted(User user, AchievementStats achievementStats) {
        return false;
    }

}
