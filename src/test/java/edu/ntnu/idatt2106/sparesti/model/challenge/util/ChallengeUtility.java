package edu.ntnu.idatt2106.sparesti.model.challenge.util;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LoginRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.user.RegistrationDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.model.EmailCode;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ChallengeUtility {

  public static User createUserA() {
    return User.builder()
            .email("example@guide")
            .firstName("Example")
            .role(Role.USER)
            .userInfo(createUserInfoA())
            .lastName("Guide")
            .password("password")
            .build();
  }

  public static UserInfoDto createUserInfoDtoA() {
    return UserInfoDto.builder()
            .income(1000)
            .livingStatus(1)
            .build();
  }


  public static UserInfo createUserInfoA() {
    return UserInfo.builder()
            .id(1L)
            .income(1000)
            .livingStatus(SsbLivingStatus.fromInteger(1))
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




  public static SharedChallenge createSharedChallengeA() {
    return SharedChallenge.builder()
            .id(1L)
            .title("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .description("Description")
            .progress(Progress.IN_PROGRESS)
            .difficulty(Difficulty.EASY)
            .user(createUserA())
            .build();
  }

  public static SharedChallengeDto createSavingChallengeDto() {
    return SharedChallengeDto.builder()
            .title("Challenge")
            .progress(Progress.IN_PROGRESS)
            .id(1L)
            .description("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .difficulty("EASY")
            .build();
  }

  public static ChallengeDto createChallengeDto() {
    return ChallengeDto.builder()
            .id(1L)
            .description("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .difficulty("EASY")
            .build();
  }

  public static ChallengeUpdateRequestDto createChallengeUpdateRequestDto() {
    return ChallengeUpdateRequestDto.builder()
            .progress(Progress.IN_PROGRESS)
            .build();
  }

  public static RegistrationDto createRegistrationDtoA() {
    return RegistrationDto.builder()
            .emailVerificationCode("123456")
            .email("example@guide.com")
            .firstName("Example")
            .lastName("Guide")
            .password("password")
            .build();
  }

  public static LoginRequestDto createLoginDtoA() {
    return LoginRequestDto.builder()
            .email("example@guide")
            .password("password")
            .build();
  }

  public static EmailCode createEmailCodeA() {
    return EmailCode.builder()
            .email("example@guide")
            .verificationCode("123456")
            .expiryTimestamp(LocalDateTime.of(2021, 12, 31, 23, 59, 59))
            .build();
  }

}
