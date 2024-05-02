package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.service.email.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
  SharedChallengeCodeRepository sharedChallengeCodeRepository;

  @Mock
  SharedChallengeRepository sharedChallengeRepository;

  @Mock
  UserRepository userRepository;

  Principal principal;

  private SharedChallenge challenge;

  User user;

  @BeforeEach


  void setUp() {
    challenge = ChallengeUtility.createSharedChallenge1();
    user = ChallengeUtility.createUser1();
    SharedChallengeCode sharedChallengeCode = ChallengeUtility.createSharedChallengeCodeA();
    challenge.setSharedChallengeCode(sharedChallengeCode);
    principal = () -> user.getEmail();

    challenge.setUser(user);
  }

  @Test
  void Service_GetChallenge_ReturnChallenge() {
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    ChallengeDto challengeDto = challengeService.getChallenge(principal, 1L);
    verify(challengeRepository).findById(1L);
  }

  @Test
  void Service_AddChallenge_AddChallenge() {
    //Arrange
    ChallengeDto challengeDto = ChallengeUtility.createChallengeDto();
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));

    //Act
    challengeService.addChallenge(principal, challengeDto);

    //Assert
    verify(challengeRepository).save(any(Challenge.class));
  }

  @Test
  void Service_AddSharedChallenge_AddChallenge() {
    //Arrange
    SharedChallengeDto challengeDto = ChallengeUtility.createSharedChallengeDto();
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));

    //Act
    challengeService.addChallenge(principal, challengeDto);

    //Assert
    verify(challengeRepository).save(any(Challenge.class));
  }

  @Test
  void Service_JoinSharedChallenge_ShouldAddUserToChallenge() {

    String joinCode = "1234";
    //Arrange
    when(sharedChallengeRepository.findSharedChallengeBySharedChallengeCode_JoinCode(joinCode)).thenReturn(List.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));
    //Act
    challengeService.joinSharedChallenge(principal, joinCode);

    //Assert
    verify(challengeRepository).save(any(Challenge.class));
  }


  @Test
  void Service_RemoveChallenge_RemovesChallenge() {
    //Arrange
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    //Act
    challengeService.removeChallenge(principal, 1L);

    //Assert
    verify(challengeRepository).deleteById(1L);
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
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    ChallengeUpdateRequestDto challengeUpdateRequestDto = ChallengeUtility.createChallengeUpdateRequestDto();

    //Act
    challengeService.updateChallenge(principal, 1L, challengeUpdateRequestDto);
    verify(challengeRepository).save(any(Challenge.class));
  }

  @Test
  void Service_GetParticipants_ReturnsParticipants() {
    //Arrange
    User dummyUser = ChallengeUtility.createUserA();
    challenge.setUser(dummyUser);

    when(sharedChallengeRepository.findSharedChallengeBySharedChallengeCode_Id(1L)).thenReturn(List.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(ChallengeUtility.createUserA()));

    //Act
    List<SharedChallengePreviewDto> sharedChallengePreviewDtos = challengeService.getParticipatingUsers(principal, 1L);

    //Assert
    assertNotNull(sharedChallengePreviewDtos);
  }


}