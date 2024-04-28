package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.mapper.SharedChallengeMapper;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * A service class for services related to challenges.
 * A user can get challenges, add challenges and remove challenges.
 *
 * @author Jeffrey Yaw Annor
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeService {

  private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int LENGTH = 8;
  private static final SecureRandom random = new SecureRandom();

  private final ChallengesRepository challengesRepository;

  private final SharedChallengeRepository sharedChallengeRepository;

  private final ChallengeMapper challengeMapperImpl = Mappers.getMapper(ChallengeMapper.class);
  private final SharedChallengeMapper sharedChallengeMapperImpl =
          Mappers.getMapper(SharedChallengeMapper.class);

  private final UserRepository userRepository;


  /**
   * Get the various challenges for a specified user.
   *
   * @param principal is the user we want to get challenges for.
   * @param pageable  is the page number and size of the page.
   * @return a list of challenges for the specified user.
   */
  public List<ChallengePreviewDto> getChallenges(Principal principal, Pageable pageable) {
    List<Challenge> challenges = challengesRepository.findByUser_Email(principal.getName(), pageable);

    return challenges.stream()
            .map(challengeMapperImpl::challengeIntoChallengePreviewDto)
            .collect(Collectors.toList());
  }



  /**
   * Generates a verification token.
   * The token is a random string of length 6.
   *
   * @return the generated token.
   */
  private String generateJoinCode() {
    StringBuilder token = new StringBuilder(LENGTH);
    for (int i = 0; i < LENGTH; i++) {
      token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return token.toString();
  }



  /**
   * Add a challenge for a specified user.
   *
   * @param principal is the user we want to add a challenge for.
   * @param challenge is the challenge we want to add.
   */
  public void addChallenge(Principal principal, ChallengeDto challenge) {
    User user = userRepository.findUserByEmailIgnoreCase(principal.getName()).orElseThrow(() ->
            new UsernameNotFoundException("User Not Found with -> username or email: " + principal.getName()));

    if (challenge instanceof SharedChallengeDto) {
      addSharedChallengeToUser(challenge, user);

    } else {
      addCommonChallenge(challenge, user);
    }
  }


  /**
   * Add a common challenge for a specified user.
   *
   * @param challenge is the challenge we want to add.
   * @param user is the user we want to add the challenge for.
   */
  private void addCommonChallenge(ChallengeDto challenge, User user) {
    Challenge newChallenge = challengeMapperImpl.challengeDtoToChallenge(challenge);
    newChallenge.setUser(user);
    challengesRepository.save(newChallenge);
  }



  /**
   * Add a shared challenge for a specified user.
   *
   * @param challengeDto is the challenge we want to share.
   * @param user is the user we want to share the challenge for.
   */
  private void addSharedChallengeToUser(ChallengeDto challengeDto, User user) {
    SharedChallenge sharedChallenge = sharedChallengeMapperImpl.sharedChallengeDtoToSharedChallenge((SharedChallengeDto) challengeDto, challengeMapperImpl);
    sharedChallenge.setUser(user);
    sharedChallenge.setSharedChallengeCode(generateSharedChallengeCode());
    challengesRepository.save(sharedChallenge);
  }

  private SharedChallengeCode generateSharedChallengeCode() {
    return SharedChallengeCode.builder()
            .joinCode(generateJoinCode())
            .build();
  }

  public void joinSharedChallenge(Principal principal, String joinCode) {
    SharedChallenge sharedChallenge = sharedChallengeRepository.findSharedChallengeBySharedChallengeCode_JoinCode(joinCode).getFirst();
    if (sharedChallenge == null) {
      throw new NoSuchElementException("Challenge not found");
    }

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName()).orElseThrow(() ->
            new UsernameNotFoundException("User Not Found with -> username or email: " + principal.getName()));

    SharedChallenge.builder()
            .title(sharedChallenge.getTitle())
            .difficulty(sharedChallenge.getDifficulty())
            .endDate(sharedChallenge.getEndDate())
            .startDate(sharedChallenge.getStartDate())
            .description(sharedChallenge.getDescription())
            .progress(Progress.IN_PROGRESS)
            .user(user)
            .sharedChallengeCode(sharedChallenge.getSharedChallengeCode())
            .build();

    challengesRepository.save(sharedChallenge);
  }






  /**
   * Remove a challenge for a specified user.
   *
   * @param principal is the user we want to remove a challenge for.
   * @param challengeId is the id of the challenge we want to remove.
   */
  public void removeChallenge(Principal principal, long challengeId) {
    checkForUser(principal.getName());
    checkValidity(checkForChallenge(challengeId), principal.getName());
    challengesRepository.deleteById(challengeId);
  }


  /**
   * Check if the user has access to the challenge.
   *
   * @param challenge is the id of the challenge.
   * @param username is the username of the user.
   */
  private void checkValidity(Challenge challenge, String username) {
    if (!challenge.getUser().getEmail().equals(username)) {
      throw new UnauthorizedOperationException("User not authorized to change the challenge");
    }
  }

  /**
   * Check if the user exists.
   *
   * @param username is the username of the user.
   */
  private void checkForUser(String username) {
    userRepository.findUserByEmailIgnoreCase(username).orElseThrow(() ->
            new UsernameNotFoundException("User Not Found with -> username or email: " + username));
  }


  /**
   * Get a challenge for a specified user.
   *
   * @param principal   is the user we want to get a challenge for.
   * @param challengeId is the id of the challenge we want to get.
   * @return the challenge for the specified user.
   */
  public ChallengeDto getChallenge(Principal principal, long challengeId) {

    Challenge challenge = checkForChallenge(challengeId);
    checkForUser(principal.getName());
    checkValidity(challenge, principal.getName());

    if (challenge instanceof SharedChallenge sharedChallenge) {
      return sharedChallengeMapperImpl.sharedChallengeToSharedChallengeDto(sharedChallenge, challengeMapperImpl);
    } else {
      return challengeMapperImpl.challengeIntoChallengeDto(challenge);
    }
  }



  /**
   * Update a challenge for a specified user.
   *
   * @param principal   user that wants to update the challenge
   * @param challengeId id of the challenge to update
   * @param challenge   new challenge data
   */
  public void updateChallenge(Principal principal, long challengeId,
                              ChallengeUpdateRequestDto challenge) {

    Challenge foundChallenge = checkForChallenge(challengeId);
    checkForUser(principal.getName());
    checkValidity(foundChallenge, principal.getName());

    foundChallenge.setProgress(challenge.getProgress());
    challengesRepository.save(foundChallenge);
  }


  /**
   * Check if the challenge exists.
   *
   * @param challengeId is the id of the challenge.
   * @return the challenge.
   */
  private Challenge checkForChallenge(long challengeId) {
    return challengesRepository.findById(challengeId)
            .orElseThrow(() -> new NoSuchElementException("Challenge not found"));
  }

  public List<SharedChallengePreviewDto> getParticipatingUsers(Principal principal, String joinCode) {
    List<SharedChallenge> sharedChallenge = sharedChallengeRepository.findSharedChallengeBySharedChallengeCode_JoinCode(joinCode);
    List<SharedChallengePreviewDto> sharedChallengeDto = new ArrayList<>();

    checkForUser(principal.getName());
    for (SharedChallenge challenge : sharedChallenge) {
      SharedChallengePreviewDto sharedChallengePreviewDto = SharedChallengePreviewDto.builder()
              .firstName(challenge.getUser().getFirstName())
              .lastName(challenge.getUser().getLastName())
              .progress(challenge.getProgress())
              .build();
      sharedChallengeDto.add(sharedChallengePreviewDto);
    }
    return sharedChallengeDto;
  }
}