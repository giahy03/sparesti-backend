package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalUpdateValueDto;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repositories.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * A test class for the saving goal service class.
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
public class SavingGoalServiceTest {

    @InjectMocks
    SavingGoalService savingGoalService;

    @Mock
    SavingGoalRepository savingGoalRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SavingGoalMapper savingGoalMapper;

    Principal principal;

    private SavingGoal goal;


    @BeforeEach
    void setUp() {
        goal = SavingGoalUtility.createSavingGoalA();
        principal = () -> "test@test.tea";
    }


    @DisplayName("JUnit test for getSavingGoalById method")
    @Test
    void Service_GetSavingGoal_ReturnSavingGoal() {

        when(savingGoalRepository.findById(1L)).thenReturn(Optional.of(goal));
        when(savingGoalMapper.mapToSavingGoalDto(goal)).thenReturn(SavingGoalUtility.createSavingGoalDto());

        SavingGoalDto savingGoalDto = savingGoalService.getSavingGoalById(SavingGoalUtility.createSavingGoalIdDto());

        assertNotNull(savingGoalDto);
    }

    //@DisplayName("JUnit test for getSavingGoalById method which throws exception")

    @DisplayName("JUnit test of getAllGoalIdsByEmail method")
    @Test
    void Service_GetAllGoalIdsByEmail_ReturnsListOfSavingGoalIds() {

        List<SavingGoal> savingGoalList = new ArrayList<>();
        savingGoalList.add(SavingGoalUtility.createSavingGoalA());
        savingGoalList.add(SavingGoalUtility.createSavingGoalB());

        Pageable pageable = null;
        when(savingGoalRepository.findAllByUser_Username(principal.getName(), pageable)).thenReturn(savingGoalList);

        List<SavingGoalIdDto> returnedIds = savingGoalService.getAllGoalIdsByEmail(principal, pageable);

        assertThat(returnedIds.size()).isEqualTo(2);
    }


    @DisplayName("JUnit test for createSavingGoal method")
    @Test
    void Service_AddSavingGoal_AddSavingGoal() {

        User user = SavingGoalUtility.createUserA();

        when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));
        when(savingGoalMapper.mapToSavingGoal(SavingGoalUtility.createSavingGoalCreationRequestDto(), user)).thenReturn(goal);
        when(savingGoalRepository.save(goal)).thenReturn(goal);

        assertDoesNotThrow(() -> savingGoalService.createSavingGoal(principal, SavingGoalUtility.createSavingGoalCreationRequestDto()));
        assertNotNull(savingGoalService.createSavingGoal(principal, SavingGoalUtility.createSavingGoalCreationRequestDto()));
    }

    @DisplayName("JUnit test for deleteSavingGoal method")
    @Test
    void Service_DeleteSavingGoal_DeleteSavingGoal() {
        assertDoesNotThrow(() -> savingGoalService.deleteSavingGoal(principal, SavingGoalUtility.createSavingGoalIdDto()));
    }




    @DisplayName("JUnit test for editLives method")
    @Test
    void Service_EditLives_EditLives() {
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        SavingGoalUpdateValueDto savingGoalUpdateValueDto = SavingGoalUtility.createSavingGoalUpdateValueDto();

        assertDoesNotThrow(() -> savingGoalService.editLives(principal, savingGoalUpdateValueDto));
        //assertTrue(savingGoalService.editLives(principal, savingGoalUpdateValueDto) is int);
    }

    @DisplayName("JUnit test for updateCurrentTile method")
    @Test
    void Service_UpdateTile_UpdateTile() {

        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        SavingGoalUpdateValueDto savingGoalUpdateValueDto = SavingGoalUtility.createSavingGoalUpdateValueDto();

        assertDoesNotThrow(() -> savingGoalService.updateCurrentTile(principal, savingGoalUpdateValueDto));

    }

    @DisplayName("JUnit test for registerSavingContribution method")
    @Test
    void Service_RegisterSavingContribution_RegisterSavingContribution() {

        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        SavingGoalContributionDto savingGoalContributionDto = SavingGoalUtility.createSavingGoalContributionDto();

        assertDoesNotThrow(() -> savingGoalService.registerSavingContribution(principal, savingGoalContributionDto));

    }

}
