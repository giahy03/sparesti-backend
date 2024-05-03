package edu.ntnu.idatt2106.sparesti.service.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

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
    challenge = ChallengeUtility.createSharedChallenge2();
    user = ChallengeUtility.createUser1();
    SharedChallengeCode sharedChallengeCode = ChallengeUtility.createSharedChallengeCodeA();
    challenge.setSharedChallengeCode(sharedChallengeCode);
    principal = () -> user.getEmail();

    challenge.setUser(user);
  }

  @Test
  @DisplayName("Service get challenge should return challenge")
  void service_GetChallenge_ReturnChallenge() {
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    ChallengeDto challengeDto = challengeService.getChallenge(principal, 1L);
    verify(challengeRepository).findById(1L);
  }

  @Test
  @DisplayName("Service add challenge should add challenge")
  void service_AddChallenge_AddChallenge() {
    //Arrange
    ChallengeDto challengeDto = ChallengeUtility.createChallengeDto();
    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(Optional.of(user));

    //Act
    challengeService.addChallenge(principal, challengeDto);

    //Assert
    verify(challengeRepository).save(any(Challenge.class));
  }

  @Test
  @DisplayName("Service add shared challenge should add challenge")
  void service_AddSharedChallenge_AddChallenge() {
    //Arrange
    SharedChallengeDto challengeDto =
            ChallengeUtility.createSharedChallengeDto();
    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(Optional.of(user));

    //Act
    challengeService.addChallenge(principal, challengeDto);

    //Assert
    verify(challengeRepository).save(any(Challenge.class));
  }

  @Test
  @DisplayName("Service join shared challenge should add user to challenge")
  void service_JoinSharedChallenge_ShouldAddUserToChallenge() {
    String joinCode = "1234";

    //Arrange
    when(sharedChallengeRepository
            .findSharedChallengeBySharedChallengeCode_JoinCode(joinCode))
            .thenReturn(List.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName()))
            .thenReturn(Optional.of(user));
    //Act
    challengeService.joinSharedChallenge(principal, joinCode);

    //Assert
    verify(challengeRepository).save(any(Challenge.class));
  }


  @Test
  @DisplayName("Service remove challenge should remove challenge")
  void service_RemoveChallenge_RemovesChallenge() {
    //Arrange
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    //Act
    challengeService.removeChallenge(principal, 1L);

    //Assert
    verify(challengeRepository).deleteById(1L);
  }


  @Test
  @DisplayName("Service get challenges should return challenges")
  void service_GetChallenges_ReturnsChallenges() {
    //Arrange
    Pageable pageable = Pageable.unpaged();
    when(challengeRepository.findByUser_Email(
            principal.getName(), pageable)).thenReturn(List.of(challenge));


    int expected = 1;

    //Act
    int actual = challengeService.getChallenges(principal, pageable).size();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Service update challenge should update challenge")
  void service_UpdateChallenge_UpdatesChallenge() {
    //Arrange
    when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    ChallengeUpdateRequestDto challengeUpdateRequestDto =
            ChallengeUtility.createChallengeUpdateRequestDto();

    //Act
    challengeService.updateChallenge(principal, 1L, challengeUpdateRequestDto);
    verify(challengeRepository).save(any(Challenge.class));
  }

  @Test
  @DisplayName("Service get shared challenges should return shared challenges")
  void service_GetParticipants_ReturnsParticipants() {
    //Arrange
    User dummyUser = ChallengeUtility.createUserA();
    challenge.setUser(dummyUser);

    when(sharedChallengeRepository
            .findSharedChallengeBySharedChallengeCode_Id(1L)).thenReturn(List.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName()))
            .thenReturn(Optional.of(ChallengeUtility.createUserA()));

    //Act
    List<SharedChallengePreviewDto> sharedChallengePreviewDtos =
            challengeService.getParticipatingUsers(principal, 1L);

    //Assert
    assertNotNull(sharedChallengePreviewDtos);
  }


}