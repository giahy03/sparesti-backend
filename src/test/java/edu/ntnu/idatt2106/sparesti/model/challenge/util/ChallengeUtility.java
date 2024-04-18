package edu.ntnu.idatt2106.sparesti.model.challenge.util;

import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;

import java.time.LocalDate;

public class ChallengeUtility {

  public static User createUserA() {
    return User.builder()
            .email("example@guide")
            .firstName("Example")
            .role(Role.USER)
            .lastName("Guide")
            .password("password")
            .build();
  }

  public static User createUserB() {
    return User.builder()
            .email("example@guide")
            .firstName("Example")
            .role(Role.USER)
            .lastName("Guide")
            .password("password")
            .build();
  }

  public static SavingChallenge createSavingChallenge() {
    return (SavingChallenge) SavingChallenge.builder()
            .id(1L)
            .title("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .isCompleted(false)
            .currentAmount(0)
            .totalAmount(100)
            .difficulty(Difficulty.EASY)
            .lives(3)
            .currentTile(0)
            .user(createUserA())
            .build();
  }

}
