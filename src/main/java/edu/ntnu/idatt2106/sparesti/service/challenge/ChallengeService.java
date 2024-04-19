package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SavingChallengeDto;
import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.exception.challenge.ChallengeNotFoundException;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.mapper.SavingChallengeMapper;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repositories.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
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

  private final ChallengesRepository challengesRepository;
  private final ChallengeMapper challengeMapperImpl = Mappers.getMapper(ChallengeMapper.class);
  private final SavingChallengeMapper savingChallengeMapperImpl =
          Mappers.getMapper(SavingChallengeMapper.class);
  private final UserRepository userRepository;


  /**
   * Get the various challenges for a specified user.
   *
   * @param principal is the user we want to get challenges for.
   * @param pageable  is the page number and size of the page.
   * @return a list of challenges for the specified user.
   */
  public List<ChallengePreviewDto> getChallenges(Principal principal, Pageable pageable) {

    String username = principal.getName();
    List<Challenge> challenges = challengesRepository.findByUser_Email(username, pageable);
    List<ChallengePreviewDto> challengePreviewDto = new ArrayList<>();

    for (Challenge challenge : challenges) {
      challengePreviewDto.add(challengeMapperImpl.challengeIntoChallengePreviewDto(challenge));
    }

    log.info("Getting {} challenges previews for {}.", challengePreviewDto.size(), username);
    return challengePreviewDto;
  }


  /**
   * Add a challenge for a specified user.
   *
   * @param principal is the user we want to add a challenge for.
   * @param challenge is the challenge we want to add.
   */
  public void addChallenge(Principal principal, ChallengeDto challenge) {

    String username = principal.getName();
    User user = userRepository.findUserByEmailIgnoreCase(principal.getName()).get();
    checkForUser(username);

    if (challenge instanceof SavingChallengeDto) {
      log.info("Adding a saving challenge for user {}.", username);
      SavingChallenge savingChallenge =
              savingChallengeMapperImpl.savingChallengeDtoToSavingChallenge(
                      (SavingChallengeDto) challenge, user, challengeMapperImpl);
      challengesRepository.save(savingChallenge);
    }

  }


  /**
   * Remove a challenge for a specified user.
   *
   * @param principal   is the user we want to remove a challenge for.
   * @param challengeId is the id of the challenge we want to remove.
   */
  public void removeChallenge(Principal principal, long challengeId) {

    String username = principal.getName();
    checkForUser(username);
    checkValidity(challengeId, username);

    log.info("Removing challenge with id: " + challengeId);
    challengesRepository.deleteById(challengeId);
  }


  /**
   * Check if the user has access to the challenge.
   *
   * @param objectId is the id of the challenge.
   * @param username is the username of the user.
   */
  private void checkValidity(long objectId, String username) {
    if (!challengesRepository.findById(objectId).get().getUser().getUsername().equals(username)) {
      throw new UnauthorizedOperationException(
              "User: " + username + " does not have access to challenge with id " + objectId);
    }
  }

  /**
   * Check if the challenge exists.
   *
   * @param challenge is the challenge we want to check.
   */
  private void checkForChallenge(Challenge challenge) {
    if (challenge == null) {
      throw new ChallengeNotFoundException("Challenge not found");
    }
  }


  /**
   * Check if the user exists.
   *
   * @param username is the username of the user.
   */
  private void checkForUser(String username) {
    User user = userRepository.findUserByEmailIgnoreCase(username).orElseThrow(() ->
            new UserNotFoundException("User with username " + username + " not found"));
  }


  /**
   * Get a challenge for a specified user.
   *
   * @param principal   is the user we want to get a challenge for.
   * @param challengeId is the id of the challenge we want to get.
   * @return the challenge for the specified user.
   */
  public ChallengeDto getChallenge(Principal principal, long challengeId) {

    Challenge challenge = challengesRepository.findById(challengeId).get();
    String username = principal.getName();
    checkForUser(username);
    checkValidity(challengeId, username);
    checkForChallenge(challenge);

    log.info("Getting challenge with id: " + challengeId);

    if (challenge instanceof SavingChallenge) {
      return savingChallengeMapperImpl.savingChallengeDto(
              (SavingChallenge) challenge, challengeMapperImpl);
    } else {
      throw new IllegalArgumentException("Not an appropriate challenge type.");
    }
  }


  /**
   * Update a challenge for a specified user.
   *
   * @param principal   user that wants to update the challenge
   * @param challengeId id of the challenge to update
   * @param challenge   new challenge data
   */
  public void updateChallenge(Principal principal,
                              long challengeId,
                              ChallengeUpdateRequestDto challenge) {
    checkForUser(principal.getName());
    checkValidity(challengeId, principal.getName());

    Challenge challengeToUpdate = challengesRepository.findById(challengeId).get();

    if (challengeToUpdate instanceof SavingChallenge savingChallenge) {
      savingChallenge.setLives(challenge.getLives());
      savingChallenge.setCurrentTile(challenge.getCurrentTiles());
      challengesRepository.save(savingChallenge);
    } else {
      throw new ChallengeNotFoundException("Challenge with id " + challengeId + " not found");
    }
  }

}