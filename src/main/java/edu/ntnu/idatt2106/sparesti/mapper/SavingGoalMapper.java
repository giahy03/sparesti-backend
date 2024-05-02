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

  /**
   * Maps a SavingGoal to a SavingGoalDto.
   *
   * @param savingGoal the SavingGoal to be mapped
   * @return the mapped SavingGoalDto
   */
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

  /**
   * Maps a SavingGoalCreationRequestDto to a SavingGoal.
   *
   * @param savingGoalCreationRequestDto the SavingGoalCreationRequestDto to be mapped
   * @param user                         the user that the saving goal belongs to
   * @return the mapped SavingGoal
   */
  public SavingGoal mapToSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto,
                                    User user) {

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

  /**
   * Maps a SavingGoal to a SavingGoalIdDto.
   *
   * @param savingGoal the SavingGoal to be mapped
   * @return the mapped SavingGoalIdDto
   */
  public SavingGoalIdDto mapToSavingGoalIdDto(SavingGoal savingGoal) {
    return SavingGoalIdDto.builder()
        .id(savingGoal.getId())
        .title(savingGoal.getTitle())
        .state(savingGoal.getState())
        .build();
  }

}
