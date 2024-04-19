package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.repositories.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * A test class for challenge service.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

  @InjectMocks
  ChallengeService challengeService;

  @Mock
  ChallengesRepository challengeRepository;

  @Mock
  UserRepository userRepository;

  Principal principal;

  private SavingChallenge challenge;

  @BeforeEach
  void setUp() {
    challenge = ChallengeUtility.createSavingChallenge();
    principal = () -> "example@guide";
  }

  @Test
  void Service_GetChallenge_ReturnChallenge() {
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(ChallengeUtility.createUserA()));
    ChallengeDto challengeDto = challengeService.getChallenge(principal, 1L);

    assertNotNull(challengeDto);
  }

  @Test
  void Service_AddChallenge_AddChallenge() {

    //Arrange
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(ChallengeUtility.createUserA()));

    //Act
    assertDoesNotThrow(() -> challengeService.addChallenge(principal, ChallengeUtility.createSavingChallengeDto()));
  }

  @Test
  void Service_RemoveChallenge_RemovesChallenge() {

    //Arrange
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(ChallengeUtility.createUserA()));
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    //Act
    assertDoesNotThrow(() -> challengeService.removeChallenge(principal, 1L));

  }

  @Test
  void Service_GetChallenges_ReturnsChallenges() {

    //Arrange
    Pageable pageable = Pageable.unpaged();
    when(challengeRepository.findByUser_Email(principal.getName(), pageable)).thenReturn(List.of(challenge));


    int expected = 1;

    //Act
    int actual = challengeService.getChallenges(principal, pageable).size();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void Service_UpdateChallenge_UpdatesChallenge() {

    //Arrange
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(ChallengeUtility.createUserA()));
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    ChallengeUpdateRequestDto challengeUpdateRequestDto = ChallengeUtility.createChallengeUpdateRequestDto();

    //Act
    assertDoesNotThrow(() -> challengeService.updateChallenge(principal, 1L, challengeUpdateRequestDto));
  }
}