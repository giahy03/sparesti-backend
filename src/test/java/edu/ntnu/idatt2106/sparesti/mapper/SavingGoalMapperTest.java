package edu.ntnu.idatt2106.sparesti.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.model.goal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SavingGoalMapperTest {

  @Test
  void givenValidSavingGoal_whenMapToSavingGoalDto_thenReturnSavingGoalDto() {
    // Arrange

    SavingGoalMapper savingGoalMapper = new SavingGoalMapper();
    User author = new User();
    String title = "title";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now();
    int totalAmount = 100;
    GoalState state = GoalState.UNDER_PROGRESS;
    int lives = 3;
    String joinCode = "joinCode";

    // Act
    SavingGoal savingGoal = SavingGoal.builder()
        .id(1L)
        .author(author)
        .title(title)
        .startDate(startDate)
        .endDate(endDate)
        .totalAmount(totalAmount)
        .state(state)
        .lives(lives)
        .joinCode(joinCode)
        .build();

    SavingGoalDto savingGoalDto = savingGoalMapper.mapToSavingGoalDto(savingGoal);

    // Assert
    assertEquals(author.getFirstName() + " " + author.getLastName(), savingGoalDto.getAuthor());
    assertEquals(title, savingGoalDto.getTitle());
    assertEquals(startDate, savingGoalDto.getStartDate());
    assertEquals(endDate, savingGoalDto.getEndDate());
    assertEquals(totalAmount, savingGoalDto.getTotalAmount());
    assertEquals(state, savingGoalDto.getState());
    assertEquals(lives, savingGoalDto.getLives());
    assertEquals(joinCode, savingGoalDto.getJoinCode());
  }

  @Test
  void givenValidSavingGoalCreationRequestDto_whenMapToSavingGoal_thenReturnSavingGoal() {
    // Arrange

    SavingGoalMapper savingGoalMapper = new SavingGoalMapper();
    User user = new User();
    String goalName = "goalName";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now();
    int totalAmount = 100;
    int lives = 3;

    // Act
    SavingGoal savingGoal = savingGoalMapper.mapToSavingGoal(SavingGoalCreationRequestDto.builder()
        .goalName(goalName)
        .startDate(startDate)
        .endDate(endDate)
        .totalAmount(totalAmount)
        .lives(lives)
        .build(), user);

    // Assert
    assertEquals(user, savingGoal.getAuthor());
    assertEquals(goalName, savingGoal.getTitle());
    assertEquals(startDate, savingGoal.getStartDate());
    assertEquals(endDate, savingGoal.getEndDate());
    assertEquals(totalAmount, savingGoal.getTotalAmount());
    assertEquals(GoalState.UNDER_PROGRESS, savingGoal.getState());
    assertEquals(lives, savingGoal.getLives());
  }

  @Test
  void givenValidSavingGoal_whenMapToSavingGoalIdDto_thenReturnSavingGoalIdDto() {
    // Arrange

    SavingGoalMapper savingGoalMapper = new SavingGoalMapper();
    User author = new User();
    String title = "title";
    GoalState state = GoalState.UNDER_PROGRESS;

    // Act
    SavingGoal savingGoal = SavingGoal.builder()
        .id(1L)
        .author(author)
        .title(title)
        .state(state)
        .startDate(LocalDate.now())
        .endDate(LocalDate.now())
        .build();

    SavingGoalIdDto savingGoalIdDto = savingGoalMapper.mapToSavingGoalIdDto(savingGoal);

    // Assert
    assertEquals(savingGoal.getId(), savingGoalIdDto.getId());
    assertEquals(title, savingGoalIdDto.getTitle());
    assertEquals(state, savingGoalIdDto.getState());
  }
}
