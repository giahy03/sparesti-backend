package edu.ntnu.idatt2106.sparesti.model.saving.util;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;

import java.time.LocalDate;

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


    public static SavingGoal createSavingGoal() {

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


}
