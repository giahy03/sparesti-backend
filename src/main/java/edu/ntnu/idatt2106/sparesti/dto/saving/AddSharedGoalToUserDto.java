package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.*;

/**
 *
 * @author Hanne-Sofie Søreide
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSharedGoalToUserDto {
    private String joinCode;
}
