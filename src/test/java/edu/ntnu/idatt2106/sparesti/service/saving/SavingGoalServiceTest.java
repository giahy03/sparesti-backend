package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalUpdateValueDto;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.savingGoal.SavingGoal;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.SavingContributionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * A test class for the saving goal service class.
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
class SavingGoalServiceTest {

    @InjectMocks
    SavingGoalService savingGoalService;

    @Mock
    SavingGoalRepository savingGoalRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SavingContributionRepository savingContributionRepository;
    @Mock
    SavingGoalMapper savingGoalMapper;
    Principal principal;
    Pageable pageable;
    private SavingGoal goal;


    @BeforeEach
    void setUp() {
        goal = SavingGoalUtility.createSavingGoalA();
        principal = () -> "testA@test.tea";
    }


    @DisplayName("JUnit test for getSavingGoalById method")
    @Test
    void Service_GetSavingGoal_ReturnSavingGoal() {
        // Arrange
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.of(goal));
        when(savingGoalMapper.mapToSavingGoalDto(goal)).thenReturn(SavingGoalUtility.createSavingGoalDto());

        // Act
        SavingGoalDto savingGoalDto = savingGoalService.getSavingGoalById(principal, 1L);

        // Assert
        assertNotNull(savingGoalDto);
    }


    @DisplayName("JUnit test of getAllGoalIdsByEmail method")
    @Test
    void Service_GetAllGoalIdsByEmail_ReturnsListOfSavingGoalIds() {

        // Arrange
        User user = SavingGoalUtility.createUserA();
        List<SavingContribution> contributions = List.of(SavingGoalUtility.createSavingContributionA(),
                SavingGoalUtility.createSavingContributionC());

        when(savingContributionRepository.findAllContributionsByUser_Email(principal.getName(), pageable)).thenReturn(contributions);

        // Act
        List<SavingGoalIdDto> returnedIds = savingGoalService.getAllGoalsOfUser(principal, pageable);

        // Assert
        assertThat(returnedIds.size()).isEqualTo(2);
    }


    @DisplayName("JUnit test for createSavingGoal method")
    @Test
    void Service_AddSavingGoal_AddSavingGoal() {

        // Arrange
        User user = SavingGoalUtility.createUserA();
        when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));
        when(savingGoalMapper.mapToSavingGoal(SavingGoalUtility.createSavingGoalCreationRequestDto(), user)).thenReturn(goal);
        when(savingGoalRepository.save(goal)).thenReturn(goal);

        // Act and assert
        assertDoesNotThrow(() -> savingGoalService.createSavingGoal(principal, SavingGoalUtility.createSavingGoalCreationRequestDto()));
        assertNotNull(savingGoalService.createSavingGoal(principal, SavingGoalUtility.createSavingGoalCreationRequestDto()));
    }

    @DisplayName("JUnit test for deleteSavingGoal method")
    @Test
    void Service_DeleteSavingGoal_DeleteSavingGoal() {
        // Act and assert
        assertDoesNotThrow(() -> savingGoalService.deleteSavingGoal(SavingGoalUtility.createSavingGoalIdDto()));
    }



    @DisplayName("JUnit test for editLives method")
    @Test
    void Service_EditLives_EditLives() {

        // Arrange
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        SavingGoalUpdateValueDto savingGoalUpdateValueDto = SavingGoalUtility.createSavingGoalUpdateValueDto();

        // Act and assert
        assertDoesNotThrow(() -> savingGoalService.editLives(savingGoalUpdateValueDto));
        //assertTrue(Integer.class.isInstance(savingGoalService.editLives(principal, savingGoalUpdateValueDto)));
    }


//
//
//
//    @DisplayName("JUnit test for registerSavingContribution method")
//    @Test
//    void Service_RegisterSavingContribution_RegisterSavingContribution() {
//
//        // Arrange
//        User user = SavingGoalUtility.createUserA();
//        when(savingGoalRepository.findById(1L)).thenReturn(Optional.of(goal));
//        when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));
//
//
//        System.out.println("HERE:    ----------");
//        System.out.println(goal.getContributions().get(user.getUserId()));;
//        SavingGoalContributionDto savingGoalContributionDto = SavingGoalUtility.createGoalContributionDto();
//
//        // Act and assert
//        assertDoesNotThrow(() -> savingGoalService.registerSavingContribution(principal, savingGoalContributionDto));
//    }


}
