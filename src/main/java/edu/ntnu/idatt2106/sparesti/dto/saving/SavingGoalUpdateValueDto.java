package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.Builder;
import lombok.Data;

/**
 * DTO containing a single int value and the id of a given goal.
 * The value can represent number of lives for mascot or current tile.
 *
 * @author Hanne-Sofie Søreide
 */

@Data
@Builder
public class SavingGoalUpdateValueDto {
    private long id;
    private int value;
}
