package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.GoalState;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.service.challenge.CodeGenerationUtility;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between saving goal objects and saving goal DTO objects.
 *
 * @author Hanne-Sofie Søreide
 */

@Component
public class SavingGoalMapper {

    public SavingGoalDto mapToSavingGoalDto(SavingGoal savingGoal) {

        return SavingGoalDto.builder()
                .id(savingGoal.getId())
                .author(savingGoal.getAuthor().getFirstName() + " " + savingGoal.getAuthor().getLastName())
                .title(savingGoal.getTitle())
                .startDate(savingGoal.getStartDate())
                .endDate(savingGoal.getEndDate())
                .totalAmount(savingGoal.getTotalAmount())
                .state(savingGoal.getState())
                .lives(savingGoal.getLives())
                .joinCode(savingGoal.getJoinCode())
                .build();
    }


    public SavingGoal mapToSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto, User user) {

        return SavingGoal.builder()
                .author(user)
                .title(savingGoalCreationRequestDto.getGoalName())
                .startDate(savingGoalCreationRequestDto.getStartDate())
                .endDate(savingGoalCreationRequestDto.getEndDate())
                .totalAmount(savingGoalCreationRequestDto.getTotalAmount())
                .lives(savingGoalCreationRequestDto.getLives())
                .state(GoalState.UNDER_PROGRESS)
                .joinCode(CodeGenerationUtility.generateJoinCode())
                .build();
    }


    public SavingGoalIdDto mapToSavingGoalIdDto(SavingGoal savingGoal) {
        return SavingGoalIdDto.builder()
                .id(savingGoal.getId())
                .title(savingGoal.getTitle())
                .state(savingGoal.getState())
                .build();
    }

    // TODO:   Liste over brukere knyttet til målet?

}
