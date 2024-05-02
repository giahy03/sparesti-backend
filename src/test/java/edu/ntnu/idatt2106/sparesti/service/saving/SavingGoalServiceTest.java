package edu.ntnu.idatt2106.sparesti.service.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributorDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalUpdateValueDto;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingContribution;
import edu.ntnu.idatt2106.sparesti.model.goal.SavingGoal;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        when(savingContributionRepository.findByUser_EmailAndGoal_Id(principal.getName(), 1L)).thenReturn(Optional.ofNullable(SavingGoalUtility.createSavingContributionA()));

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
        when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));

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
        // Arrange
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        // Act and assert
        assertDoesNotThrow(() -> savingGoalService.deleteSavingGoal(principal, SavingGoalUtility.createSavingGoalIdDto()));
    }



    @DisplayName("JUnit test for editLives method")
    @Test
    void Service_EditLives_EditLives() {

        // Arrange
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        SavingGoalUpdateValueDto savingGoalUpdateValueDto = SavingGoalUtility.createSavingGoalUpdateValueDto();
        when(savingContributionRepository.findByUser_EmailAndGoal_Id(principal.getName(), 1L)).thenReturn(Optional.ofNullable(SavingGoalUtility.createSavingContributionA()));

        // Act and assert
        assertDoesNotThrow(() -> savingGoalService.editLives(principal, savingGoalUpdateValueDto));
        //assertTrue(Integer.class.isInstance(savingGoalService.editLives(principal, savingGoalUpdateValueDto)));
    }


    @DisplayName("JUnit test for registerSavingContribution method")
    @Test
    void Service_RegisterSavingContribution_RegisterSavingContribution() {

        SavingGoalContributionDto savingGoalContributionDto = SavingGoalUtility.createGoalContributionDto();

        // Arrange
        when(savingContributionRepository.findByUser_EmailAndGoal_Id(anyString(), anyLong())).thenReturn(Optional.ofNullable(SavingGoalUtility.createSavingContributionA()));


        // Act and assert
        assertDoesNotThrow(() -> savingGoalService.registerSavingContribution(principal, savingGoalContributionDto));
    }

    @Test
    void Service_getContributorsToGoal_ReturnContributors() {
        // Arrange
        List<SavingContribution> contributions = List.of(SavingGoalUtility.createSavingContributionA(),
                SavingGoalUtility.createSavingContributionC());
        when(savingContributionRepository.findAllContributionsByGoal_Id(1L)).thenReturn(contributions);
        when(savingContributionRepository.findByUser_EmailAndGoal_Id(anyString(), anyLong())).thenReturn(Optional.ofNullable(SavingGoalUtility.createSavingContributionA()));

        // Act
        List<SavingGoalContributorDto> returnedContributors = savingGoalService.getContributorsToGoal(principal, 1L);

        // Assert
        assertThat(returnedContributors.size()).isEqualTo(2);
    }

    @Test
    void Service_addGoalToUser_ReturnContributors() {
        // Arrange
        when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(SavingGoalUtility.createUserA()));
        when(savingGoalRepository.findByJoinCode(anyString())).thenReturn(Optional.ofNullable(goal));
        when(savingContributionRepository.findByUser_EmailAndGoal_Id(principal.getName(), 1L)).thenReturn(Optional.ofNullable(SavingGoalUtility.createSavingContributionA()));
        when(savingGoalMapper.mapToSavingGoalDto(any(SavingGoal.class))).thenReturn(SavingGoalUtility.createSavingGoalDto());
        // Act and assert
        SavingGoalDto savingGoalDto = savingGoalService.addGoalToUser(principal, SavingGoalUtility.createAddSharedGoalToUserDtoA());

        assertNotNull(savingGoalDto);
    }


    @Test
    void Service_updateGoal_ReturnContributors() {
        // Arrange
        when(savingGoalRepository.findById(1L)).thenReturn(Optional.ofNullable(goal));
        when(savingGoalMapper.mapToSavingGoalIdDto(goal)).thenReturn(SavingGoalUtility.createSavingGoalIdDto1());
        when(savingContributionRepository.findByUser_EmailAndGoal_Id(principal.getName(), 1L)).thenReturn(Optional.ofNullable(SavingGoalUtility.createSavingContributionA()));
        when(savingGoalRepository.save(goal)).thenReturn(goal);


        // Act and assert
        SavingGoalIdDto savingGoalIdDto = savingGoalService.updateGoalState(principal, SavingGoalUtility.createSavingGoalStateA());

        assertNotNull(savingGoalIdDto);
    }






}
