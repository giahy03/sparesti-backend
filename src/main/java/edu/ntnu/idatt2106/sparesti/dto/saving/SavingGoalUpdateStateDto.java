package edu.ntnu.idatt2106.sparesti.dto.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalState;
import lombok.Builder;
import lombok.Data;

/**
 * DTO containing information needed to update the saving goal state.
 *
 * @author Hanne-Sofie Søreide
 */
@Data
@Builder
public class SavingGoalUpdateStateDto {
    private long id;
    private GoalState goalState;
}
