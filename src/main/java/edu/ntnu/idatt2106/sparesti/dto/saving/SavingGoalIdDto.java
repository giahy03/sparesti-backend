package edu.ntnu.idatt2106.sparesti.dto.saving;

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

}


