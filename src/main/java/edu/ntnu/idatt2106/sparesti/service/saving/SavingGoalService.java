package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.*;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.SavingContributionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
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
    private final SavingContributionRepository savingContributionRepository;


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
                .title(savedSavingGoal.getTitle())
                .state(savedSavingGoal.getState())
                .build();
    }


    /**
     * Add a new contributor to an existing goal with the given join-code.
     * The authenticated user is the one being added to the goal.
     *
     * @param principal The authenticated user
     * @param addUserToGoalRequestDto DTO containing the join code from the user
     * @return DTO containing basic information about the saving goal that the user was added to
     */
    public SavingGoalIdDto addGoalToUser(Principal principal, AddSharedGoalToUserDto addUserToGoalRequestDto) {

        User newUser = userRepository.findUserByEmailIgnoreCase(principal.getName()).orElseThrow();

        SavingGoal savingGoal = savingGoalRepository.findByJoinCode(addUserToGoalRequestDto.getJoinCode()).orElseThrow();

        // If the user is already associated with this goal, do not add again.
        SavingContribution contribution = savingContributionRepository
                .findByUser_EmailAndGoal_Id(principal.getName(), savingGoal.getId());

        if (contribution == null) {
            SavingContribution newContribution = SavingContribution.builder()
                    .goal(savingGoal).user(newUser).contribution(0.0).build();

            savingContributionRepository.save(newContribution);
        }

        return SavingGoalIdDto.builder()
                .id(savingGoal.getId())
                .title(savingGoal.getTitle())
                .state(savingGoal.getState())
                .build();
    }


    /**
     * Retrieves all the goals belonging to the authenticated user. The goals are represented by
     * a list of DTOs containing the goal id, title and state.
     *
     * @param principal The authenticated user
     * @param pageable Pageable object defining page and page size
     * @return List of DTOs containing basic information of each goal
     */
    public List<SavingGoalIdDto> getAllGoalsOfUser(Principal principal, Pageable pageable) {

        List<SavingGoal> goals = savingContributionRepository.findAllContributionsByUser_Email(principal.getName(), pageable)
                .stream().map(SavingContribution::getGoal).toList();

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
     * @param savingGoalIdDto DTO containing the unique goal id
     */
    public void deleteSavingGoal(SavingGoalIdDto savingGoalIdDto) {
        savingGoalRepository.deleteById(savingGoalIdDto.getId());
    }



    /**
     * Updates the number of lives of the mascot.
     *
     * @param updateValueDto DTO containing the unique saving goal id
     * @return the number of lives left for the mascot after updating
     */
    public int editLives(SavingGoalUpdateValueDto updateValueDto) {

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

        if (savingGoalContributionDto.getContribution() > 0) {
            SavingContribution contribution = savingContributionRepository
                    .findByUser_EmailAndGoal_Id(principal.getName(), savingGoalContributionDto.getGoalId());

            double oldContribution = contribution.getContribution();
            contribution.setContribution(oldContribution + savingGoalContributionDto.getContribution());
            savingContributionRepository.save(contribution);
        }

        // Return the total saved up amount on this goal (from all users)
        return checkTotalOfContributions(savingGoalContributionDto.getGoalId());
    }



    /**
     * Updates the state of a given goal.
     *
     * @param updateStateDto DTO containing the goal id and new state
     * @return A DTO containing base information about the updated goal
     */
    public SavingGoalIdDto updateGoalState(Principal principal, SavingGoalUpdateStateDto updateStateDto) {
        SavingGoal savingGoal = savingGoalRepository.findById(updateStateDto.getId()).orElseThrow();
        savingGoal.setState(updateStateDto.getGoalState());
        savingGoalRepository.save(savingGoal);
        return savingGoalMapper.mapToSavingGoalIdDto(savingGoal);
    }


    /**
     * Summarizes and returns all the contributions made to the saving goal of the given id.
     *
     * @param goalId Unique identifyer of the goal to get the currently total saved amount for
     * @return The currently saved up amount for this goal
     */
    public double checkTotalOfContributions(Long goalId) {
        return savingContributionRepository.findAllContributionsByGoal_Id(goalId)
                .stream()
                .map(SavingContribution::getContribution).mapToDouble(f -> f).sum();
    }

}
