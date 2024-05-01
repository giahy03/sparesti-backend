package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.Builder;
import lombok.Data;

/**
 * A DTO containing the unique id of a saving goal and the saved amount to add to its progress.
 *
 * @author Hanne-Sofie Søreide
 */
@Data
@Builder
public class SavingGoalContributionDto {
    private long goalId;
    private double contribution;
}
