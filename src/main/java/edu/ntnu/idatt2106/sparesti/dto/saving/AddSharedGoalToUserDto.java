package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

/**
 *
 * @author Hanne-Sofie Søreide
 */
@Setter
@Getter
@Builder
public class AddSharedGoalToUserDto {
    private String joinCode;
}
