package edu.ntnu.idatt2106.sparesti.dto.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalState;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for retrieving just the saving goal IDs.
 *
 * @author Hanne-Sofie Søreide
 */


@Data
@Builder
public class SavingGoalIdDto {
  private long id;
  private String title;
  private GoalState state;
}


