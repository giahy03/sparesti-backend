package edu.ntnu.idatt2106.sparesti.commandline;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.*;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
@Profile("!test")
@RequiredArgsConstructor
public class LoadTestData implements CommandLineRunner {

  private final UserRepository userRepository;

  private final ChallengesRepository challengesRepository;

  private final BadgeRepository badgeRepository;

  private final AchievementRepository achievementRepository;

  private final SharedChallengeCodeRepository sharedChallengeCodeRepository;
  private final SavingGoalRepository savingGoalRepository;
  private final SavingContributionRepository savingContributionRepository;

  @Override
  public void run(String... args) throws Exception {

    System.out.println("Creating default user");

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    User user = User.builder()
            .email("admin@example.com")
            .firstName("Admin")
            .lastName("Admin")
            .password(passwordEncoder.encode("password"))
            .role(Role.USER)
            .bankStatements(new ArrayList<>())
            .build();

    User user2 = User.builder()
            .email("asuduo@example.com")
            .firstName("Asu")
            .lastName("Duo")
            .password(passwordEncoder.encode("password"))
            .role(Role.USER)
            .bankStatements(new ArrayList<>())
            .build();

    user.setUserInfo(UserInfo.builder()
            .livingStatus(SsbLivingStatus.LIVING_ALONE)
            .income(10000)
            .user(user)
            .build());

    SharedChallenge sharedChallenge = SharedChallenge.builder()
            .id(1L)
            .title("Buy less food")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .description("You need buy less food this month.")
            .progress(Progress.IN_PROGRESS)
            .difficulty(Difficulty.EASY)
            .user(user)
            .build();

    SharedChallenge sharedChallenge2 = SharedChallenge.builder()
            .title("Buy less food")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .description("You need buy less food this month.")
            .progress(Progress.IN_PROGRESS)
            .difficulty(Difficulty.EASY)
            .user(user2)
            .build();

    SharedChallengeCode sharedChallengeCode = SharedChallengeCode.builder()
            .sharedChallenges(List.of(sharedChallenge))
            .joinCode("1234")
            .build();

    sharedChallenge.setSharedChallengeCode(sharedChallengeCode);

    sharedChallenge2.setSharedChallengeCode(sharedChallengeCode);

    sharedChallengeCode.setSharedChallenges(List.of(sharedChallenge, sharedChallenge2));

    sharedChallengeCodeRepository.save(sharedChallengeCode);






    Achievement achievementA = Achievement.builder()
            .category(AchievementCategory.CHALLENGE_STREAK)
            .description("Complete a certain number of saving challenges in Sparesti.")
            .thresholds(List.of(new Integer[]{10, 20, 50, 100, 500}))
            .build();

    Achievement achievementB = Achievement.builder()
            .category(AchievementCategory.AMOUNT_SAVED)
            .description("Save up a specific amount of money through Sparesti.")
            .thresholds(List.of(new Integer[]{1000, 2000, 5000, 10000, 50000}))
            .build();


    Badge badgeA = Badge.builder()
            .user(user)
            .achievement(achievementA)
            .achievedDate(LocalDate.of(2024, 4, 3))
            .level(2)
            .build();

    Badge badgeB = Badge.builder()
            .user(user)
            .achievement(achievementB)
            .achievedDate(LocalDate.of(2024, 4, 3))
            .level(3)
            .build();

    SavingGoal savingGoalA = SavingGoal.builder()
            .lives(5)
            .state(GoalState.UNDER_PROGRESS)
            .totalAmount(25000.0)
            .joinCode("abc")
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .author(user)
            .title("Felles tur til Spania")
            .build();

    SavingGoal savingGoalB = SavingGoal.builder()
            .lives(3)
            .state(GoalState.UNDER_PROGRESS)
            .totalAmount(250000.0)
            .joinCode("123")
            .startDate(LocalDate.of(2024, 4, 1))
            .endDate(LocalDate.of(2024, 4, 20))
            .author(user2)
            .title("Ny bil")
            .build();

    SavingContribution savingContribution1 = SavingContribution.builder()
            .user(user)
            .goal(savingGoalA)
            .contribution(2500.0)
            .build();

    SavingContribution savingContribution2 = SavingContribution.builder()
            .user(user2)
            .goal(savingGoalA)
            .contribution(3000.0)
            .build();

    SavingContribution savingContribution3 = SavingContribution.builder()
            .user(user2)
            .goal(savingGoalB)
            .contribution(20000.0)
            .build();


    userRepository.save(user);
    userRepository.save(user2);

    challengesRepository.save(sharedChallenge);
    challengesRepository.save(sharedChallenge2);

    user.setBadges(Set.of(badgeA, badgeB));

    challengesRepository.save(sharedChallenge);

    achievementRepository.save(achievementA);
    achievementRepository.save(achievementB);
    badgeRepository.save(badgeA);
    badgeRepository.save(badgeB);

    savingGoalRepository.save(savingGoalA);
    savingGoalRepository.save(savingGoalB);

    savingContributionRepository.save(savingContribution1);
    savingContributionRepository.save(savingContribution2);
    savingContributionRepository.save(savingContribution3);

  }
}
