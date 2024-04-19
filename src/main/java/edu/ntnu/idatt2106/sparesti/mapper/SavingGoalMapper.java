package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
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
                .id(savingGoal.getId())
                .goalName(savingGoal.getGoalName())
                .startDate(savingGoal.getStartDate())
                .endDate(savingGoal.getEndDate())
                .amount(savingGoal.getAmount())
                .progress(savingGoal.getProgress())
                .lives(savingGoal.getLives())
                .currentTile(savingGoal.getLives())
                .achieved(savingGoal.isAchieved())
                .build();
    }


    public SavingGoal mapToSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto, User user) {

        return SavingGoal.builder()
                .user(user)
                .goalName(savingGoalCreationRequestDto.getGoalName())
                .startDate(savingGoalCreationRequestDto.getStartDate())
                .endDate(savingGoalCreationRequestDto.getEndDate())
                .amount(savingGoalCreationRequestDto.getAmount())
                .progress(savingGoalCreationRequestDto.getProgress())
                .lives(savingGoalCreationRequestDto.getLives())     // Set to 3 upon creation?
                .currentTile(savingGoalCreationRequestDto.getCurrentTile())   // 'update' upon creation
                .achieved(false)    // 'Calculate' from amount and progress
                .build();
    }


    public SavingGoalIdDto mapToSavingGoalIdDto(SavingGoal savingGoals) {
        return SavingGoalIdDto.builder()
                .id(savingGoals.getId())
                .title(savingGoals.getGoalName())
                .build();
    }

}
