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
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeCodeRepository;
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
            .category(AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED)
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
            .level(2)
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

  }
}
