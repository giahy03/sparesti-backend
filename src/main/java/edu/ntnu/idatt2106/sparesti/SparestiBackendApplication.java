package edu.ntnu.idatt2106.sparesti;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SparestiBackendApplication {


  public static void main(String[] args) {
    SpringApplication.run(SparestiBackendApplication.class, args);
  }

}
