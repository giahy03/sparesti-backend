package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.springframework.stereotype.Component;

import java.util.*;

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
                .title(savingGoal.getTitle())
                .startDate(savingGoal.getStartDate())
                .endDate(savingGoal.getEndDate())
                .totalAmount(savingGoal.getTotalAmount())
                .state(savingGoal.getState())
                .lives(savingGoal.getLives())
                .build();
    }


/*    public SavingGoal mapToSavingGoal(SavingGoalDto savingGoalDto, Set<User> users) {

        HashMap<Long, Double> contributions = new HashMap<>();
        for (User user : users) {
            contributions.put(user.getUserId(), 0.0);
        }

        return SavingGoal.builder()
                .author(user)
                .users(users)
                .title(savingGoalCreationRequestDto.getGoalName())
                .startDate(savingGoalCreationRequestDto.getStartDate())
                .endDate(savingGoalCreationRequestDto.getEndDate())
                .totalAmount(savingGoalCreationRequestDto.getTotalAmount())
                .lives(savingGoalCreationRequestDto.getLives())
                .contributions(contributions)
                .build();
    }*/

    public SavingGoal mapToSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto, User user) {

        return SavingGoal.builder()
                .author(user)
                .users(Set.of(user))
                .title(savingGoalCreationRequestDto.getGoalName())
                .startDate(savingGoalCreationRequestDto.getStartDate())
                .endDate(savingGoalCreationRequestDto.getEndDate())
                .totalAmount(savingGoalCreationRequestDto.getTotalAmount())
                .lives(savingGoalCreationRequestDto.getLives())
                .contributions(Map.of(user.getUserId(), 0.0))
                .build();
    }


    public SavingGoalIdDto mapToSavingGoalIdDto(SavingGoal savingGoals) {
        return SavingGoalIdDto.builder()
                .id(savingGoals.getId())
                .title(savingGoals.getTitle())
                .build();
    }

    // TODO:   public SavingGoalPreviewDto

}
