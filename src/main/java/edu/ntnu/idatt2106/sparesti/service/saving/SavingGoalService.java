package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

/**
 * Service class for operations related to Saving Goals.
 * The service provides creation of new Saving Goals and retrieval of existing ones.
 *
 * @author Hanne-Sofie Søreide
 */


@Service
@Slf4j
@AllArgsConstructor
public class SavingGoalService {

//    @NonNull
//    private final UserRepository userRepository;
    @NonNull
    private final SavingGoalRepository savingGoalRepository;
    @NonNull
    private final SavingGoalMapper savingGoalMapper;


    /**
     * Create a saving goal and store it in the database.
     *
     * @param savingGoalCreationRequestDto DTO containing the information needed to create a saving goal
     * @param principal The authenticated user
     * @return The response DTO containing the ID of the created saving goal
     */

   /* public SavingGoalIdDto createSavingGoal(SavingGoalCreationRequestDto savingGoalCreationRequestDto,
                                                          Principal principal) {
        String email = principal.getName();

        User user = userRepository.findUserByUsernameIgnoreCase(email).orElseThrow(() ->
                new UsernameNotFoundException("User with email " + email + " not found"));

        SavingGoal createdSavingGoal = savingGoalMapper.mapToSavingGoal(savingGoalCreationRequestDto, user);

        SavingGoal savedSavingGoal = savingGoalRepository.save(createdSavingGoal);

        return SavingGoalIdDto.builder()
                .id(savedSavingGoal.getId())
                .build();
    }
*/

    /**
     * Retrieves a list containing the ID number of all the goals belonging to the authenticated user.
     *
     * @param principal The authenticated user
     * @return List of DTOs containing the id of each goal of the user
     */

   /* public List<SavingGoalIdDto> getAllGoalIdsByEmail(Principal principal, Pageable pageable) {

        String email = principal.getName();

        return savingGoalRepository.findAllByUser_Username(email, pageable)
                .stream()
                .map(savingGoalMapper::mapToSavingGoalIdDto)
                .toList();
    }
*/
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

    public void deleteSavingGoal(Principal principal, SavingGoalIdDto savingGoalIdDto) {

//        String email = principal.getName();
//        checkForUser(email);
//        checkValidity(savingGoalIdDto, email);

        savingGoalRepository.deleteById(savingGoalIdDto.getId());

    }

    public void editCurrentTile(Principal principal, SavingGoalIdDto savingGoalIdDto) {
//        String email = principal.getName();
//        checkForUser(email);
//        checkValidity(savingGoalIdDto, email);

    }

    public void editLives(Principal principal, SavingGoalIdDto savingGoalIdDto) {
//        String email = principal.getName();
//        checkForUser(email);
//        checkValidity(savingGoalIdDto, email);
    }

    public void registerSavingContribution(Principal principal, SavingGoalContributionDto savingGoalContributionDto) {
//        String email = principal.getName();
//        checkForUser(email);

    }





/*
    private void checkValidity(SavingGoalIdDto savingGoalIdDto, String email) {
        if (SavingGoalRepository.findById(savingGoalIdDto.getId()).get().getUser().getUsername().equals(email)) {
            return;
        } else {
            throw new IllegalArgumentException("User with username " + email + " does not have access to challenge with id " + SavingGoalIdDto.getId());
        }
    }

    private void checkForUser(String email) {
        User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + email + " not found"));
    }
*/



}
