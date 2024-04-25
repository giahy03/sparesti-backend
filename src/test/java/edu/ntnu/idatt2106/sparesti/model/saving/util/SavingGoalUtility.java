package edu.ntnu.idatt2106.sparesti.model.saving.util;

import edu.ntnu.idatt2106.sparesti.dto.saving.*;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class that creates objects to support the testing classes related to saving goals.
 *
 * @author Hanne-Sofie Søreide
 */

public class SavingGoalUtility {

    public static User createUserA() {
        return User.builder()
                .email("test@test.tea")
                .firstName("First Name")
                .lastName("Last Name")
                .role(Role.USER)
                .password("PW")
                .build();
    }


    public static User createUserB() {
        return User.builder()
                .email("test@test.tea")
                .firstName("First Name")
                .lastName("Last Name")
                .role(Role.USER)
                .password("PW")
                .build();
    }


    public static SavingGoal createSavingGoalA() {

        return SavingGoal.builder()
                .id(1L)
                .user(createUserA())
                .goalName("Goal")
                .startDate(LocalDate.of(2024, 4, 15))
                .endDate(LocalDate.of(2024, 5, 3))
                .lives(3)
                .currentTile(5)
                .difficulty(GoalDifficulty.MEDIUM)
                .amount(10000.0)
                .progress(500.0)
                .build();
    }

    public static SavingGoal createSavingGoalB() {

        return SavingGoal.builder()
                .id(2L)
                .user(createUserB())
                .goalName("GoalB")
                .startDate(LocalDate.of(2024, 4, 15))
                .endDate(LocalDate.of(2024, 5, 3))
                .lives(3)
                .currentTile(5)
                .difficulty(GoalDifficulty.MEDIUM)
                .amount(10000.0)
                .progress(500.0)
                .build();
    }

    public static SavingGoalCreationRequestDto createSavingGoalCreationRequestDto() {

        return SavingGoalCreationRequestDto.builder()
                .goalName("New goal")
                .amount(15000.0)
                .progress(100.0)
                .lives(8)
                .currentTile(15)
                .startDate(LocalDate.of(2024, 4, 10))
                .endDate(LocalDate.of(2024, 4, 30))
                .difficulty(GoalDifficulty.HARD)
                .build();

    }

    public static SavingGoalIdDto createSavingGoalIdDto() {

        return SavingGoalIdDto.builder()
                .id(1L)
                .title("Goal")
                .build();
    }

    public static SavingGoalIdDto createSavingGoalIdDto1() {

        return SavingGoalIdDto.builder()
                .id(2L)
                .title("Goal")
                .build();
    }

    public static SavingGoalIdDto createSavingGoalIdDto2() {

        return SavingGoalIdDto.builder()
                .id(3L)
                .title("Goal")
                .build();
    }

    public static SavingGoalUpdateValueDto createSavingGoalUpdateValueDto() {

        return SavingGoalUpdateValueDto.builder()
                .id(1L)
                .value(5)
                .build();

    }

    public static SavingGoalContributionDto createSavingGoalContributionDto() {

        return SavingGoalContributionDto.builder()
                .id(1L)
                .contribution(250.0)
                .build();
    }

    public static String createSavingGoalContributionDtoJson() {
        return  "{"
                + "\"id\":1,"
                + "\"contribution\":250.0"
                + "}";
    }

    public static SavingGoalDto createSavingGoalDto() {

        return SavingGoalDto.builder()
                .id(1L)
                .goalName("New goal")
                .amount(15000.0)
                .progress(100.0)
                .lives(8)
                .currentTile(15)
                .startDate(LocalDate.of(2024, 4, 10))
                .endDate(LocalDate.of(2024, 4, 30))
                .difficulty(GoalDifficulty.HARD)
                .build();
    }


    public static String createSavingGoalIdDtoJson() {

        return  "{"
                + "\"id\":1,"
                + "\"title\":\"Goal\""
                + "}";
    }


    public static String createSavingGoalDtoJson() {

        return  "{"
                + "\"id\":1,"
                + "\"goalName\":\"New goal\","
                + "\"difficulty\":\"HARD\","
                + "\"startDate\":\"2024-04-10\","
                + "\"endDate\":\"2024-04-30\","
                + "\"lives\":8,"
                + "\"amount\":15000.0,"
                + "\"progress\":100.0,"
                + "\"achieved\":false,"
                + "\"currentTile\":15"
                + "}";
    }

    public static String createSavingGoalIdDtoListJson() {

        return  "[" +
                "{\"id\": 1, \"title\": \"Goal\"}," +
                "{\"id\": 2, \"title\": \"Goal\"}," +
                "{\"id\": 3, \"title\": \"Goal\"}" +
                "]";
    }

    public static String createSavingGoalCreationRequestDtoJson() {

        return
                "{"
                    + "\"goalName\":\"New goal\","
                    + "\"difficulty\":\"HARD\","
                    + "\"startDate\":\"2024-04-10\","
                    + "\"endDate\":\"2024-04-30\","
                    + "\"lives\":8,"
                    + "\"amount\":15000.0,"
                    + "\"progress\":100.0,"
                    + "\"achieved\":false,"
                    + "\"currentTile\":15"
                    + "}";
    }

}
