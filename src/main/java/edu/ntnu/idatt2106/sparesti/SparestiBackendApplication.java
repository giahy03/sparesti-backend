package edu.ntnu.idatt2106.sparesti;

import edu.ntnu.idatt2106.sparesti.filehandling.SpareBank1Reader;
import edu.ntnu.idatt2106.sparesti.filehandling.SpareBank1Reader;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.repository.BankStatementRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.nio.file.Path;
import java.util.ArrayList;
import edu.ntnu.idatt2106.sparesti.service.analysis.BankStatementAnalysisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SparestiBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(SparestiBackendApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(UserRepository userRepository, BankStatementRepository bankStatementRepository, BankStatementAnalysisService bankStatementAnalysisService) {
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

      for (int i = 0; i < 4 ; i++) {
        SpareBank1Reader reader = new SpareBank1Reader();

        BankStatement bankStatement = reader.readStatement(
                Path.of("src/main/resources" + "/bankstatements/sparebank1/sparebank1example.pdf").toFile());

        bankStatement.getTransactions().forEach(transaction -> transaction.setBankStatement(bankStatement));
        bankStatement.setAccountNumber("11111111111" + i);
        bankStatement.setUser(user);

        bankStatementAnalysisService.analyze(bankStatement, user.getUserInfo());
        bankStatementRepository.save(bankStatement);
      }
    };

  }
}
