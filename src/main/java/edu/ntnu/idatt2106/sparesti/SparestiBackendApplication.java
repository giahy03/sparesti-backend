package edu.ntnu.idatt2106.sparesti;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;


import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SparestiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparestiBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, SavingGoalRepository savingGoalRepository) {
		return args -> {
			System.out.println("Creating default user");

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			User user = User.builder()
					.email("admin@example.com")
					.firstName("Admin")
					.lastName("Admin")
					.password(passwordEncoder.encode("password"))
					.role(Role.USER)
					.build();
			user.setUserInfo(UserInfo.builder()
					.livingStatus(SsbLivingStatus.LIVING_ALONE)
					.income(10000)
					.user(user)
					.build());

			userRepository.save(user);

			SavingGoal savingGoal = SavingGoal.builder()
					.id(1L)
					.goalName("Goal")
					.startDate(LocalDate.of(2024, 1, 2))
					.endDate(LocalDate.of(2024, 2, 5))
					.lives(5)
					.currentTile(20)
					.amount(1000.0)
					.progress(100.0)
					.user(user)
					.difficulty(GoalDifficulty.EASY)
					.build();

			savingGoalRepository.save(savingGoal);


		};


	}


}
