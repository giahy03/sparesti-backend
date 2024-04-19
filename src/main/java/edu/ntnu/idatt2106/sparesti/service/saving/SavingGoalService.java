package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repositories.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public SavingGoalIdDto createSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto,
                                                          Principal principal) {
        String email = principal.getName();

        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UserNotFoundException("User with email " + email + " not found"));

        SavingGoal createdSavingGoal = savingGoalMapper.mapToSavingGoal(savingGoalCreationRequestDto, user);

        SavingGoal savedSavingGoal = savingGoalRepository.save(createdSavingGoal);

        return SavingGoalIdDto.builder()
                .id(savedSavingGoal.getId())
                .build();
    }


    /**
     * Retrieve a list containing the ID number of all the goals belonging to the authenticated user.
     *
     * @param principal The authenticated user
     * @return List of DTOs containing the id of each goal of the user
     */
    public List<SavingGoalIdDto> getAllGoalIdsByEmail(Principal principal, Pageable pageable) {

        String email = principal.getName();

        return savingGoalRepository.findAllByUser_Username(email, pageable)
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
     * Updates the current tile of the saving goal based on how many days the user is into the goal,
     * which equals the number of days from start today's date is.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto DTO representing a saving goal object
     * @return the number of days away today's date is from the start date of the saving goal
     */
    public int updateCurrentTile(Principal principal, SavingGoalIdDto savingGoalIdDto) {

        SavingGoal savingGoal = savingGoalRepository.findById(savingGoalIdDto.getId()).orElseThrow();

        long daysIntoChallenge;
        if (LocalDate.now().isAfter(savingGoal.getEndDate())) {
            daysIntoChallenge = ChronoUnit.DAYS.between(savingGoal.getStartDate(), savingGoal.getEndDate());
        } else {
            daysIntoChallenge = ChronoUnit.DAYS.between(savingGoal.getStartDate(), LocalDate.now());
        }

        savingGoal.setCurrentTile((int) daysIntoChallenge);
        savingGoalRepository.save(savingGoal);

        return savingGoalRepository.findById(savingGoalIdDto.getId()).get().getCurrentTile();
    }

    /**
     * Updates the number of lives of the mascot.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto DTO containing the unique saving goal id
     * @return the number of lives left for the mascot after updating
     */
    public int editLives(Principal principal, SavingGoalIdDto savingGoalIdDto) {

        // Temporarily just decrement:  If more than 3 days since active -1, if game success +1
        SavingGoal savingGoal = savingGoalRepository.findById(savingGoalIdDto.getId()).orElseThrow();
        int lives = savingGoal.getLives() -1;
        savingGoal.setLives(lives);
        savingGoalRepository.save(savingGoal);
        return savingGoalRepository.findById(savingGoalIdDto.getId()).get().getLives();
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

        SavingGoal savingGoal = savingGoalRepository.findById(savingGoalContributionDto.getId()).orElseThrow();
        double newProgress = savingGoal.getProgress() + savingGoalContributionDto.getContribution();
        savingGoal.setProgress(newProgress);
        savingGoalRepository.save(savingGoal);

        return savingGoalRepository.findById(savingGoalContributionDto.getId()).get().getProgress();
    }

}
