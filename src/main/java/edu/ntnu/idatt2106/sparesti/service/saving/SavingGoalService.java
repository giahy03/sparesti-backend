package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service class for operations related to Saving Goals.
 * The service provides creation of new Saving Goals and retrieval of existing ones.
 *
 * @author Hanne-Sofie Søreide
 */


@Service
@AllArgsConstructor
public class SavingGoalService {

    @NonNull
    private final SavingGoalRepository savingGoalRepository;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final SavingGoalMapper savingGoalMapper;


    /**
     * Create a saving goal and store it in the database.
     *
     * @param savingGoalCreationRequestDto DTO containing the information needed to create a saving goal
     * @param principal The authenticated user
     * @return The response DTO containing the ID of the created saving goal
     */

    public SavingGoalCreationResponseDto createSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto,
                                                          Principal principal) {
        String email = principal.getName();

        User user = userRepository.findUserByUsernameIgnoreCase(email).orElseThrow(() ->
                new UsernameNotFoundException("User with email " + email + " not found"));

        SavingGoal createdSavingGoal = savingGoalMapper.mapToSavingGoal(savingGoalCreationRequestDto, user);

        SavingGoal savedSavingGoal = savingGoalRepository.save(createdSavingGoal);

        return SavingGoalCreationResponseDto.builder()
                .savingGoalId(savedSavingGoal.getGoalId())
                .build();
    }


    /**
     * Retries a list containing the ID number of all the goals belonging to the authenticated user.
     *
     * @param principal The authenticated user
     * @return DTO containing the ids of all the goals
     */

    public AllGoalIdsDto getAllGoalIdsByEmail(Principal principal) {
        return savingGoalRepository.findAllGoalsByEmail(principal.getName())
                .stream()
                .map(savingGoalMapper::mapToAllGoalIdsDto)
                .toList();
    }


    /**
     * Retrieves a goal from the database based on its id.
     *
     * @param goalId The id of the saving goal
     * @return DTO containing the saving goal
     */

    public SavingGoalDto getSavingGoalById(long goalId) {
        SavingGoal savingGoal = savingGoalRepository.findSavingGoalById(goalId).orElseThrow(new Exception("Preliminary goal not found error"));

        return savingGoalMapper.mapToSavingGoalDto(savingGoal);
    }

}
