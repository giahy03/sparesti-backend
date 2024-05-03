package edu.ntnu.idatt2106.sparesti.model.saving.util;

import edu.ntnu.idatt2106.sparesti.dto.saving.AddSharedGoalToUserDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalUpdateStateDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalUpdateValueDto;
import edu.ntnu.idatt2106.sparesti.model.goal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDate;

/**
 * Utility class that creates objects to support the testing classes.
 *
 * @author Hanne-Sofie Søreide
 */

public class SavingGoalUtility {

  /**
   * Creates a user object.
   *
   * @return a user object.
   */
  public static User createUserA() {
    return User.builder()
            .email("testA@test.tea")
            .firstName("First Name")
            .lastName("Last Name")
            .role(Role.USER)
            .password("PW")
            .build();
  }


  /**
   * Creates a user object.
   *
   * @return a user object.
   */
  public static User createUserB() {
    return User.builder()
            .email("testB@test.tea")
            .firstName("First Name")
            .lastName("Last Name")
            .role(Role.USER)
            .password("PW")
            .build();

  }

  /**
   * Creates a saving contribution object.
   *
   * @return a saving contribution object.
   */
  public static SavingContribution createSavingContributionA() {
    return SavingContribution.builder()
            .id(3L)
            .goal(createSavingGoalA())
            .user(createUserA())
            .contribution(500.0)
            .build();
  }

  /**
   * Creates a saving contribution object.
   *
   * @return a saving contribution object.
   */
  public static SavingContribution createSavingContributionB() {
    return SavingContribution.builder()
            .id(4L)
            .goal(createSavingGoalB())
            .user(createUserB())
            .contribution(500.0)
            .build();
  }

  /**
   * Creates a saving contribution object.
   *
   * @return a saving contribution object.
   */
  public static SavingContribution createSavingContributionC() {
    return SavingContribution.builder()
            .id(4L)
            .goal(createSavingGoalB())
            .user(createUserA())
            .contribution(200.0)
            .build();
  }

  /**
   * Creates a saving goal object.
   *
   * @return a saving goal object.
   */
  public static SavingGoal createSavingGoalA() {

    User user = createUserA();
    return SavingGoal.builder()
            .id(1L)
            .author(user)
            .title("Goal")
            .startDate(LocalDate.of(2024, 4, 15))
            .endDate(LocalDate.of(2024, 5, 3))
            .lives(3)
            .state(GoalState.UNDER_PROGRESS)
            .totalAmount(10000.0)          //          .progress(500.0)
            .build();
  }


  /**
   * Creates a saving goal object.
   *
   * @return a saving goal object.
   */
  public static SavingGoal createSavingGoalB() {
    User user = createUserB();

    return SavingGoal.builder()
            .id(2L)
            .author(user)
            .title("GoalB")
            .startDate(LocalDate.of(2024, 4, 15))
            .endDate(LocalDate.of(2024, 5, 3))
            .lives(3)
            //.contributions(List.of(createSavingContributionB()))
            .state(GoalState.UNDER_PROGRESS)
            .totalAmount(10000.0)
            .build();
  }

  /**
   * Creates a saving goal object.
   *
   * @return a saving goal object.
   */
  public static SavingGoalCreationRequestDto createSavingGoalCreationRequestDto() {

    return SavingGoalCreationRequestDto.builder()
            .goalName("New goal")
            .totalAmount(15000.0)
            .lives(8)
            .startDate(LocalDate.of(2024, 4, 10))
            .endDate(LocalDate.of(2024, 4, 30))
            .build();

  }

  /**
   * Creates a saving goal id dto object.
   *
   * @return a saving goal id dto object.
   */
  public static SavingGoalIdDto createSavingGoalIdDto() {

    return SavingGoalIdDto.builder()
            .id(1L)
            .title("Goal")
            .state(GoalState.UNDER_PROGRESS)
            .build();
  }

  /**
   * Creates a saving goal id dto object.
   *
   * @return a saving goal id dto object.
   */
  public static SavingGoalIdDto createSavingGoalIdDto1() {

    return SavingGoalIdDto.builder()
            .id(2L)
            .title("Goal")
            .state(GoalState.UNDER_PROGRESS)
            .build();
  }

  /**
   * Creates a saving goal id dto object.
   *
   * @return a saving goal id dto object.
   */
  public static SavingGoalIdDto createSavingGoalIdDto2() {

    return SavingGoalIdDto.builder()
            .id(3L)
            .title("Goal")
            .state(GoalState.UNDER_PROGRESS)
            .build();
  }

  /**
   * Creates a saving goal id dto object.
   *
   * @return a saving goal id dto object.
   */
  public static SavingGoalUpdateValueDto createSavingGoalUpdateValueDto() {

    return SavingGoalUpdateValueDto.builder()
            .id(1L)
            .value(5)
            .build();

  }


  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static String createSavingGoalContributionDtoJson() {
    return "{"
            + "\"id\":1,"
            + "\"contribution\":250.0"
            + "}";
  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static SavingGoalDto createSavingGoalDto() {

    return SavingGoalDto.builder()
            .id(1L)
            .author(createUserA().getFirstName())
            .title("New goal")
            .totalAmount(15000.0)
            .lives(8)
            .startDate(LocalDate.of(2024, 4, 10))
            .endDate(LocalDate.of(2024, 4, 30))
            .state(GoalState.UNDER_PROGRESS)
            .build();
  }


  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static String createSavingGoalIdDtoJson() {

    return "{"
            + "\"id\":1,"
            + "\"title\":\"Goal\","
            + "\"state\":\"UNDER_PROGRESS\""
            + "}";
  }



  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static String createSavingGoalDtoJson() {

    return "{"
            + "\"id\":1,"
            + "\"title\":\"New goal\","
            + "\"state\":\"UNDER_PROGRESS\","
            + "\"startDate\":\"2024-04-10\","
            + "\"endDate\":\"2024-04-30\","
            + "\"lives\":8,"
            + "\"totalAmount\":15000.0"
            + "}";
  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static String createSavingGoalIdDtoListJson() {

    return "["
            + "{\"id\": 1, \"title\": \"Goal\", \"state\":\"UNDER_PROGRESS\"},"
            + "{\"id\": 2, \"title\": \"Goal\", \"state\":\"UNDER_PROGRESS\"},"
            + "{\"id\": 3, \"title\": \"Goal\", \"state\":\"UNDER_PROGRESS\"}"
            + "]";
  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static String createSavingGoalCreationRequestDtoJson() {

    return
            "{"
                    + "\"title\":\"New goal\","
                    + "\"state\":\"UNDER_PROGRESS\","
                    + "\"startDate\":\"2024-04-10\","
                    + "\"endDate\":\"2024-04-30\","
                    + "\"lives\":8,"
                    + "\"totalAmount\":15000.0"
                    + "}";
  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static SavingGoalContributionDto createGoalContributionDto() {
    return SavingGoalContributionDto.builder()
            .goalId(1L)

            .contribution(250.0)
            .build();

  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static AddSharedGoalToUserDto createAddSharedGoalToUserDtoA() {
    return AddSharedGoalToUserDto.builder()
            .joinCode("1234")
            .build();
  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static SavingGoalUpdateStateDto createSavingGoalStateA() {
    return SavingGoalUpdateStateDto.builder()
            .id(1L)
            .goalState(GoalState.UNDER_PROGRESS)
            .build();
  }

  /**
   * Creates a saving goal contribution dto object.
   *
   * @return a saving goal contribution dto object.
   */
  public static SavingGoal createSavingGoal1(User user) {
    return SavingGoal.builder()
            .id(1L)
            .author(user)
            .title("Goal")
            .startDate(LocalDate.of(2024, 4, 15))
            .endDate(LocalDate.of(2024, 5, 3))
            .lives(3)
            .state(GoalState.UNDER_PROGRESS)
            .totalAmount(10000.0)
            .build();
  }
}
