package edu.ntnu.idatt2106.sparesti;

import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SparestiBackendApplication {

	@Autowired
	UserRepository userRepository;




	public static void main(String[] args) {
		SpringApplication.run(SparestiBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
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

			userRepository.save(user);
		};


	}


}
