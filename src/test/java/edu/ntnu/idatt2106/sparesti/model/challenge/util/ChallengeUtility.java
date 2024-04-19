package edu.ntnu.idatt2106.sparesti.model.challenge.util;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SavingChallengeDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;

import java.time.LocalDate;
import java.util.Date;

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

  public static User createUserC() {
    return User.builder()
            .email("test@test.com")
            .firstName("Jane")
            .role(Role.USER)
            .lastName("Smith")
            .password("testpassword")
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

  public static SavingChallengeDto createSavingChallengeDto() {
    return SavingChallengeDto.builder()
            .id(1L)
            .title("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .difficulty("EASY")
            .lives(3)
            .currentTile(0)
            .build();
  }

  public static ChallengeUpdateRequestDto createChallengeUpdateRequestDto() {
    return ChallengeUpdateRequestDto.builder()
            .currentTiles(1)
            .lives(2)
            .build();
  }

}
