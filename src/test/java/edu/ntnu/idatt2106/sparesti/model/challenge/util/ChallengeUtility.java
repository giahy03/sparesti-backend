package edu.ntnu.idatt2106.sparesti.model.challenge.util;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LoginRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.user.RegistrationDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.challenge.Difficulty;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.streak.Streak;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.List;


public class ChallengeUtility {


  public static Streak createStreak1() {
    return Streak.builder()
            .numberOfDays(20)
            .build();
  }

  public static User createUser1() {
    return User.builder()
            .email("alice.johnson@example.com")
            .firstName("Alice")
            .lastName("Johnson")
            .role(Role.USER)
            .password("password012")
            .build();
  }

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

  public static User createUser2() {
    return User.builder()
            .email("bob.smith@example.com")
            .firstName("Bob")
            .lastName("Smith")
            .role(Role.USER)
            .password("password789")
            .build();
  }
  public static User createUserD() {
    return User.builder()
            .email("Anna@gmail.com")
            .firstName("Anna")
            .lastName("Smith")
            .role(Role.USER)
            .userInfo(createUserInfoA())
            .lastName("Guide")
            .password("password")
            .build();
  }

  public static User createUserE() {
    return User.builder()
            .email("Anna@gmail.com")
            .firstName("Anna")
            .lastName("Smith")
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
            .savingPercentage(10)
            .build();
  }


  public static UserInfo createUserInfoA() {
    return UserInfo.builder()
            .income(1000)
            .livingStatus(SsbLivingStatus.fromInteger(1))
            .savingPercentage(10)
            .build();
  }

  public static UserInfo createUserInfoD(User user) {
    return UserInfo.builder()
            .income(1000)
            .livingStatus(SsbLivingStatus.fromInteger(1))
            .user(user)
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

  public static SharedChallengeDto createSharedChallengeDto() {
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
            .title("Challenge")
            .progress(Progress.IN_PROGRESS)
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

  public static SharedChallengeCode createSharedChallengeCodeA() {
    return SharedChallengeCode.builder()
            .joinCode("ABCDEF")
            .sharedChallenges(List.of())
            .build();
  }


  public static SharedChallengeCode createSharedChallengeCodeB() {
    return SharedChallengeCode.builder()
            .joinCode("12345")
            .sharedChallenges(List.of())
            .build();
  }

  public static SharedChallenge createSharedChallenge2() {
    return SharedChallenge.builder()
            .id(1L)
            .title("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .description("Description")
            .progress(Progress.IN_PROGRESS)
            .difficulty(Difficulty.EASY)
            .build();
  }

  public static SharedChallenge createSharedChallenge3() {
    return SharedChallenge.builder()
            .id(1L)
            .title("Challenge")
            .startDate(LocalDate.parse("2021-10-10"))
            .endDate(LocalDate.parse("2021-10-20"))
            .description("Description")
            .progress(Progress.COMPLETED)
            .difficulty(Difficulty.EASY)
            .build();
  }

  public static SavingContribution createSavingContribution() {
    return SavingContribution.builder()
            .contribution(100)
            .build();
  }

  public static BankStatement createBankStatement1() {
    return new BankStatement("123456789", List.of(), YearMonth.now());
  }

  public static Transaction createTransaction1() {
    return new Transaction(MonthDay.now(), "Transaction", 100.0, false);
  }

  public static BankStatementAnalysis createBankStatementAnalysis1(List<AnalysisItem> analysisItems) {
    return new BankStatementAnalysis(analysisItems);
  }

  public static AnalysisItem createAnalysisItem1() {
    AnalysisItem analysisItem = new AnalysisItem();
    analysisItem.setId(1L);
    analysisItem.setPurchaseCategory(SsbPurchaseCategory.FOOD);
    analysisItem.setExpectedValue(100.0);
    analysisItem.setActualValue(100.0);
    return analysisItem;

  }

  public static TransactionDto createTransactionDto1() {
    return TransactionDto.builder()
            .amount(100.0)
            .category(SsbPurchaseCategory.EDUCATION)
            .description("Transaction")
            .isIncoming(false)
            .date(MonthDay.now())
            .build();
  }
}
