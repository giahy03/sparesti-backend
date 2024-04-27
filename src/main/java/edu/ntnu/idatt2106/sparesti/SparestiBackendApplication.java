package edu.ntnu.idatt2106.sparesti;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
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

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SparestiBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(SparestiBackendApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(UserRepository userRepository, ChallengesRepository challengesRepository) {
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
      user.setUserInfo(UserInfo.builder()
          .livingStatus(SsbLivingStatus.LIVING_ALONE)
          .income(10000)
          .user(user)
          .build());



      userRepository.save(user);

      SharedChallenge sharedChallenge = SharedChallenge.builder()
              .title("You have to quit smoking.")
              .difficulty(Difficulty.MEDIUM)
              .endDate(LocalDate.now().plusDays(30))
              .startDate(LocalDate.now())
              .description("You have to quit smoking for 30 days.")
              .progress(Progress.IN_PROGRESS)
              .user(user)
              .build();

      challengesRepository.save(sharedChallenge);
    };


  }
}
