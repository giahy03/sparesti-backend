package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.*;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for operations related to Saving Goals.
 * The service provides creation of new Saving Goals, retrieval of existing ones and
 * update of lives, currentTile and saved amount associated with the saving goal.
 *
 * @author Hanne-Sofie Søreide
 */


@Service
@Slf4j
@RequiredArgsConstructor
public class SavingGoalService {

    private final UserRepository userRepository;
    private final SavingGoalRepository savingGoalRepository;
    private final SavingGoalMapper savingGoalMapper;


    /**
     * Create a saving goal and store it in the database.
     *
     * @param savingGoalCreationRequestDto DTO containing the information needed to create a saving goal
     * @param principal The authenticated user
     * @return The response DTO containing the ID of the created saving goal
     */
    public SavingGoalIdDto createSavingGoal( Principal principal,
                                             SavingGoalCreationRequestDto savingGoalCreationRequestDto) {

        String email = principal.getName();
        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));


        SavingGoal createdSavingGoal = savingGoalMapper.mapToSavingGoal(savingGoalCreationRequestDto, user);

        SavingGoal savedSavingGoal = savingGoalRepository.save(createdSavingGoal);

        return SavingGoalIdDto.builder()
                .id(savedSavingGoal.getId())
                .build();
    }


    public SavingGoalIdDto addGoalToUser(Principal principal, AddSharedGoalToUserDto addUserToGoalRequestDto) {

        User newUser = userRepository.findUserByEmailIgnoreCase(principal.getName()).orElseThrow();

        SavingGoal savingGoal = savingGoalRepository.findByJoinCode(addUserToGoalRequestDto.getJoin_code()).orElseThrow();

        savingGoal.getUsers().add(newUser);
        savingGoal.getContributions().put(newUser.getUserId(), 0.0);

        savingGoalRepository.save(savingGoal);

        return SavingGoalIdDto.builder()
                .id(savingGoal.getId())
                .title(savingGoal.getTitle())
                .build();
    }




    /**
     * Retrieve a list containing the ID number of all the goals belonging to the authenticated user.
     *
     * @param principal The authenticated user
     * @return List of DTOs containing the id and title of each goal of the user
     */
/*    public List<SavingGoalIdDto> getAllGoalIdsByEmail(Principal principal, Pageable pageable) {

        String email = principal.getName();

        return savingGoalRepository.findAllByUser_Username(email, pageable)
                .stream()
                .map(savingGoalMapper::mapToSavingGoalIdDto)
                .toList();
    }*/

    /**
     * Retrieve a list containing the ID number of all the goals associated with the authenticated user.
     *
     * @param principal The authenticated user
     * @return List of DTOs containing the id and title of each goal of the user
     */
    public List<SavingGoalIdDto> getAllGoalsOfUser(Principal principal, Pageable pageable) {

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));

        List<SavingGoal> goals = user.getGoals().stream().toList();

        return goals
                .stream()
                .map(savingGoalMapper::mapToSavingGoalIdDto)
                .toList();
    }


    /**
     * Retrieves a goal from the database based on its id.
     *
     * @param savingGoalIdDto DTO containing the id of the saving goal
     * @return DTO containing the saving goal
     */

    public SavingGoalDto getSavingGoalById(SavingGoalIdDto savingGoalIdDto) {

        SavingGoal savingGoal = savingGoalRepository.findById(savingGoalIdDto.getId()).orElseThrow();
        return savingGoalMapper.mapToSavingGoalDto(savingGoal);
    }


    /**
     * Delete a saving goal based on its unique id.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto DTO containing the unique goal id
     */
    public void deleteSavingGoal(Principal principal, SavingGoalIdDto savingGoalIdDto) {
        savingGoalRepository.deleteById(savingGoalIdDto.getId());
    }



    /**
     * Updates the number of lives of the mascot.
     *
     * @param principal The authenticated user
     * @param updateValueDto DTO containing the unique saving goal id
     * @return the number of lives left for the mascot after updating
     */
    public int editLives(Principal principal, SavingGoalUpdateValueDto updateValueDto) {

        SavingGoal savingGoal = savingGoalRepository.findById(updateValueDto.getId()).orElseThrow();
        savingGoal.setLives(updateValueDto.getValue());
        savingGoalRepository.save(savingGoal);

        return savingGoalRepository.findById(updateValueDto.getId()).get().getLives();
    }


    /**
     * Add a new saved amount to the saving goal and return the updated total amount saved up for the
     * given goal.
     *
     * @param principal The authenticated user
     * @param savingGoalContributionDto DTO containing the saving goal id and the saved amount to add to the goal's progress
     * @return the updated progress to the saving goal after adding the saved amount
     */
    public double registerSavingContribution(Principal principal, SavingGoalContributionDto savingGoalContributionDto) {

        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));

        SavingGoal savingGoal = savingGoalRepository.findById(savingGoalContributionDto.getGoalId()).orElseThrow();

        // savingGoal.get - hashmap - på rett user, legg til bidrag.
        savingGoal.getContributions().compute(user.getUserId(), (k, oldContribution) -> oldContribution + savingGoalContributionDto.getContribution());

        savingGoalRepository.save(savingGoal);

        return savingGoalRepository.findById(savingGoalContributionDto.getGoalId()).get().getTotalProgress();
    }

}
