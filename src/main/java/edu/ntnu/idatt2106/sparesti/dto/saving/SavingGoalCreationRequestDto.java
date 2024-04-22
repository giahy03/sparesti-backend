package edu.ntnu.idatt2106.sparesti.dto.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

/**
 * DTO containing information to create a new saving goal
 *
 * @author Hanne-Sofie Søreide
 */
@Getter
@Data
@Builder
public class SavingGoalCreationRequestDto {
    @NotBlank
    private String goalName;
    private double amount;
    private double progress;
    private int lives;
    private int currentTile;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private GoalDifficulty difficulty;

}


