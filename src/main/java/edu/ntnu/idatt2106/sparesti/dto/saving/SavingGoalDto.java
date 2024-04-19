package edu.ntnu.idatt2106.sparesti.dto.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO to represent a saving goal object
 *
 * @author Hanne-Sofie Søreide
 */

@Getter
@Data
@Builder
public class SavingGoalDto {
    private long id;
    private User user;
    private String goalName;
    private GoalDifficulty difficulty;
    private LocalDate startDate;
    private LocalDate endDate;
    private int lives;
    private double amount;
    private double progress;
    private boolean achieved;
    private int currentTile;
    
}
