package edu.ntnu.idatt2106.sparesti;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SparestiBackendApplication {


  public static void main(String[] args) {
    SpringApplication.run(SparestiBackendApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(UserRepository userRepository, ChallengesRepository challengesRepository, SharedChallengeRepository sharedChallengeCodeRepository, SharedChallengeCodeRepository codeRepository) {
    return args -> {
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
          .title("Test challenge")
          .description("Test description")
          .startDate(LocalDate.now())
          .endDate(LocalDate.now().plusDays(7))
          .progress(Progress.NOT_STARTED)
          .difficulty(Difficulty.EASY)
          .user(user)
          .build();

      SharedChallenge sharedChallenge2 = SharedChallenge.builder()
              .title("Test challenge")
              .description("Test description")
              .startDate(LocalDate.now())
              .endDate(LocalDate.now().plusDays(7))
              .progress(Progress.NOT_STARTED)
              .difficulty(Difficulty.EASY)
              .user(user2)
              .build();



      SharedChallengeCode sharedChallengeCode = SharedChallengeCode.builder()
            .joinCode("12345678")
            .sharedChallenges(List.of(sharedChallenge))
            .build();

      sharedChallenge2.setSharedChallengeCode(sharedChallenge.getSharedChallengeCode());



      sharedChallenge.setSharedChallengeCode(sharedChallengeCode);
      userRepository.save(user2);
      userRepository.save(user);
      codeRepository.save(sharedChallengeCode);
      sharedChallengeCodeRepository.save(sharedChallenge);
      sharedChallengeCodeRepository.save(sharedChallenge2);
    };


  }
}
