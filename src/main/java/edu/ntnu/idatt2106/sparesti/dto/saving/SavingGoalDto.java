package edu.ntnu.idatt2106.sparesti.dto.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalState;
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
    private String author;
    private String title;
    private GoalState state;
    private LocalDate startDate;
    private LocalDate endDate;
    private int lives;
    private double totalAmount;
    private String joinCode;
    
}
