package edu.ntnu.idatt2106.sparesti.dto.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalDifficulty;
import lombok.*;

import java.util.Date;

/**
 * DTO to represent a saving goal
 *
 * @author Hanne-Sofie Søreide
 */

@Getter
@Data
@Builder
public class SavingGoalDto {
    // user, description ?
    private String goalName;
    private GoalDifficulty difficulty;
    private Date startDate;
    private Date endDate;
    private int lives;
    private double amount;
    private double progress;
    private boolean achieved;
    
}
