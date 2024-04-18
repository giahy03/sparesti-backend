package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SavingChallengeDto;
import edu.ntnu.idatt2106.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.mapper.SavingChallengeMapper;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repositories.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    String email = principal.getName();
    log.info("Getting challenges previews for {}.", email);

    List<Challenge> challenges = challengesRepository.findByUser_Email(email, pageable);
    List<ChallengePreviewDto> challengePreviewDtos = new ArrayList<>();
    for (Challenge challenge : challenges) {
      challengePreviewDtos.add(challengeMapperImpl.challengeIntoChallengePreviewDto(challenge));
    }

    log.info("Found {} challenges for user {}.", challenges.size(), email);
    return challengePreviewDtos;
  }


  /**
   * Add a challenge for a specified user.
   *
   * @param principal is the user we want to add a challenge for.
   * @param challenge is the challenge we want to add.
   */
  public void addChallenge(Principal principal, ChallengeDto challenge) {
    String username = principal.getName();

    log.info("Adding challenge for user {}.", username);

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName()).get();
    checkForUser(username);

    if (challenge instanceof SavingChallengeDto) {
      log.info("Saving, saving challenge");
      SavingChallenge savingChallenge =
              savingChallengeMapperImpl.savingChallengeDtoToSavingChallenge(
              (SavingChallengeDto) challenge, user, challengeMapperImpl);
      challengesRepository.save(savingChallenge);
      return;
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
   * @param challengeId is the id of the challenge.
   * @param username    is the username of the user.
   */
  private void checkValidity(long challengeId, String username) {
    if (challengesRepository.findById(challengeId).get().getUser().getUsername().equals(username)) {
      return;
    } else {
      throw new IllegalArgumentException(
              "User: " + username + " does not have access to challenge with id " + challengeId);
    }
  }

  /**
   * Check if the user exists.
   *
   * @param username is the username of the user.
   */
  private void checkForUser(String username) {
    User user = userRepository.findUserByEmailIgnoreCase(username).orElseThrow(() ->
            new UsernameNotFoundException("User with username " + username + " not found"));
  }

  /**
   * Get a challenge for a specified user.
   *
   * @param principal   is the user we want to get a challenge for.
   * @param challengeId is the id of the challenge we want to get.
   * @return the challenge for the specified user.
   */
  public ChallengeDto getChallenge(Principal principal, long challengeId) {

    checkForUser(principal.getName());
    checkValidity(challengeId, principal.getName());


    Optional<Challenge> challenge = challengesRepository.findById(challengeId);
    log.info("Getting challenge with id: " + challengeId);

    if (challenge.get() instanceof SavingChallenge) {
      return savingChallengeMapperImpl.savingChallengeDto(
              (SavingChallenge) challenge.get(), challengeMapperImpl);
    }

    return null;
  }
}