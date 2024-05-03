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
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
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

  private final ChallengesRepository challengesRepository;
  private final SharedChallengeRepository sharedChallengeRepository;
  private final SharedChallengeCodeRepository sharedChallengeCodeRepository;
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
    return challengesRepository.findByUser_Email(principal.getName(), pageable)
            .stream()
            .map(challenge -> {
              ChallengePreviewDto challengePreviewDto =
                      challengeMapperImpl.challengeIntoChallengePreviewDto(challenge);
              if (challenge instanceof SharedChallenge sharedChallenge) {
                challengePreviewDto.setJoinCode(
                        sharedChallenge.getSharedChallengeCode().getJoinCode());
              }
              return challengePreviewDto;
            })
            .collect(Collectors.toList());
  }


  /**
   * Add a challenge for a specified user.
   *
   * @param principal is the user we want to add a challenge for.
   * @param challenge is the challenge we want to add.
   */
  public void addChallenge(Principal principal, ChallengeDto challenge) {

    User user = getUser(principal);

    if (challenge instanceof SharedChallengeDto) {
      addSharedChallenge(challenge, user);

    } else {
      addCommonChallenge(challenge, user);
    }

  }


  /**
   * Add a common challenge for a specified user.
   *
   * @param challengeDto is the challenge we want to add.
   * @param user      is the user we want to add the challenge for.
   */
  private void addCommonChallenge(ChallengeDto challengeDto, User user) {
    Challenge challenge = challengeMapperImpl.challengeDtoToChallenge(challengeDto);
    challenge.setUser(user);
    challengesRepository.save(challenge);
  }


  /**
   * Add a shared challenge for a specified user.
   *
   * @param challengeDto is the challenge we want to share.
   * @param user         is the user we want to share the challenge for.
   */
  private void addSharedChallenge(ChallengeDto challengeDto, User user) {
    SharedChallenge sharedChallenge = sharedChallengeMapperImpl
            .sharedChallengeDtoToSharedChallenge(
                    (SharedChallengeDto) challengeDto, challengeMapperImpl);
    SharedChallengeCode sharedChallengeCode = generateSharedChallengeCode();

    sharedChallenge.setUser(user);
    sharedChallenge.setSharedChallengeCode(sharedChallengeCode);
    sharedChallenge.getSharedChallengeCode().setSharedChallenges(List.of(sharedChallenge));
    sharedChallengeCodeRepository.save(sharedChallengeCode);
    challengesRepository.save(sharedChallenge);
  }


  /**
   * Get the user from the principal.
   *
   * @param principal is the principal we want to get the user from.
   * @return the user.
   */
  private User getUser(Principal principal) {
    return userRepository
            .findUserByEmailIgnoreCase(principal.getName())
            .orElseThrow(() ->
            new UsernameNotFoundException(
                    "User Not Found with -> username or email: " + principal.getName()));
  }


  /**
   * Generate a shared challenge code for
   * a shared challenge.
   *
   * @return the shared challenge code.
   */
  private SharedChallengeCode generateSharedChallengeCode() {
    return SharedChallengeCode.builder()
            .joinCode(CodeGenerationUtility.generateJoinCode())
            .build();
  }

  /**
   * Join a shared challenge.
   *
   * @param principal is the user that wants to join the challenge.
   * @param joinCode is the join code of the challenge.
   */
  public void joinSharedChallenge(Principal principal, String joinCode) {
    SharedChallenge sharedChallenge =
            sharedChallengeRepository
                    .findSharedChallengeBySharedChallengeCode_JoinCode(joinCode).getFirst();

    User user = getUser(principal);

    challengesRepository.save(copySharedChallenge(sharedChallenge, user));
  }

  /**
   * Copy a shared challenge for a specified user.
   *
   * @param sharedChallenge is the shared challenge we want to copy.
   * @param user is the user we want to copy the shared challenge for.
   * @return the copied shared challenge.
   */
  private  SharedChallenge copySharedChallenge(SharedChallenge sharedChallenge, User user) {
    return SharedChallenge.builder()
            .title(sharedChallenge.getTitle())
            .difficulty(sharedChallenge.getDifficulty())
            .endDate(sharedChallenge.getEndDate())
            .startDate(sharedChallenge.getStartDate())
            .description(sharedChallenge.getDescription())
            .progress(Progress.IN_PROGRESS)
            .user(user)
            .sharedChallengeCode(sharedChallenge.getSharedChallengeCode())
            .build();
  }


  /**
   * Remove a challenge for a specified user.
   *
   * @param principal   is the user we want to remove a challenge for.
   * @param challengeId is the id of the challenge we want to remove.
   */
  public void removeChallenge(Principal principal, long challengeId) {
    checkValidity(checkForChallenge(challengeId), principal.getName());
    challengesRepository.deleteById(challengeId);
  }



  /**
   * Check if the user has access to the challenge.
   *
   * @param challenge is the id of the challenge.
   * @param username  is the username of the user.
   */
  private void checkValidity(Challenge challenge, String username) {
    if (!challenge.getUser().getEmail().equals(username)) {
      throw new UnauthorizedOperationException("User not authorized to change the challenge");
    }
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
    checkValidity(challenge, principal.getName());

    if (challenge instanceof SharedChallenge sharedChallenge) {
      return sharedChallengeMapperImpl
              .sharedChallengeToSharedChallengeDto(sharedChallenge, challengeMapperImpl);
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

  /**
   * Get the users participating in a shared challenge.
   *
   * @param principal is the user that wants to get the participating users.
   * @param sharedChallengeId is the join code of the shared challenge.
   * @return the users participating in the shared challenge.
   */
  public List<SharedChallengePreviewDto> getParticipatingUsers(
          Principal principal, long sharedChallengeId) {
    List<SharedChallenge> sharedChallenge = sharedChallengeRepository
            .findSharedChallengeBySharedChallengeCode_Id(sharedChallengeId);
    List<SharedChallengePreviewDto> sharedChallengeDto = new ArrayList<>();

    getUser(principal);
    for (SharedChallenge challenge : sharedChallenge) {
      if (!challenge.getUser().getEmail().equals(principal.getName())) {
        SharedChallengePreviewDto sharedChallengePreviewDto = SharedChallengePreviewDto.builder()
                .firstName(challenge.getUser().getFirstName())
                .lastName(challenge.getUser().getLastName())
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .difficulty(challenge.getDifficulty().name())
                .progress(challenge.getProgress())
                .build();
        sharedChallengeDto.add(sharedChallengePreviewDto);
      }
    }
    return sharedChallengeDto;
  }
}