package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between saving goal objects and saving goal DTO objects.
 *
 * @author Hanne-Sofie Søreide
 */

@Component
@RequiredArgsConstructor
public class SavingGoalMapper {

    public SavingGoalDto mapToSavingGoalDto(SavingGoal savingGoal) {
        return SavingGoalDto.builder()
                .goalName(savingGoal.getGoalName())
                .deadLine(savingGoal.getDeadline())
                .amount(savingGoal.getAmount())
                .progress(savingGoal.getProgress())
                .achieved(savingGoal.isAchieved())
                .build();
    }

    // --- Preliminary methods for mapper: ---
    // mapToSavingGoal(savingGoalCreationRequestDto, user)
    // mapToAllGoalIdsDto
    //

    // --- Preliminary DTOs to create: ---
    // SavingGoalCreationRequestDto
    // SavingGoalCreationResponseDto
    // AllFoalIdsDto


}
