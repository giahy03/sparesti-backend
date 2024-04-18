package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SavingGoalContributionDto {
    private long id;
    private double contribution;
}
