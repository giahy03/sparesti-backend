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
  private final SavingChallengeMapper savingChallengeMapperImpl = Mappers.getMapper(SavingChallengeMapper.class);
  private final UserRepository userRepository;


  public List<ChallengePreviewDto> getChallenges(Principal principal, Pageable pageable) {

    String username = principal.getName();
    log.info("Getting challenges previews for {}.", username);
    List<Challenge> challenges = challengesRepository.findAllByUser_Username(username, pageable);
    List <ChallengePreviewDto> challengePreviewDtos = new ArrayList<>();

    for (Challenge challenge : challenges) {
      challengePreviewDtos.add(challengeMapperImpl.challengeIntoChallengePreviewDto(challenge));
    }

    return challengePreviewDtos;
  }


  public void addChallenge(Principal principal, ChallengeDto challenge) {
    log.info("Adding challenge");

    String username = principal.getName();

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName()).get();

    checkForUser(username);

    if (challenge instanceof SavingChallengeDto) {
      log.info("Saving, saving challenge");
      SavingChallenge savingChallenge = savingChallengeMapperImpl.savingChallengeDtoToSavingChallenge((SavingChallengeDto) challenge,user, challengeMapperImpl);
      challengesRepository.save(savingChallenge);
      return;
    }

 }

  public void removeChallenge(Principal principal, ChallengePreviewDto challengePreviewDto) {

    String username = principal.getName();
    checkForUser(username);
    checkValidity(challengePreviewDto, username);

    log.info("Removing challenge with id: " + challengePreviewDto.getId());
    challengesRepository.deleteById(challengePreviewDto.getId());
  }

  private void checkValidity(ChallengePreviewDto challengePreviewDto, String username) {
    if (challengesRepository.findById(challengePreviewDto.getId()).get().getUser().getUsername().equals(username)) {
      return;
    } else {
      throw new IllegalArgumentException("User with username " + username + " does not have access to challenge with id " + challengePreviewDto.getId());
    }
  }

  private void checkForUser(String username) {
    User user = userRepository.findUserByEmailIgnoreCase(username).orElseThrow(() ->
            new UsernameNotFoundException("User with username " + username + " not found"));
  }

  public ChallengeDto getChallenge(Principal principal, ChallengePreviewDto challengePreviewDto) {

    checkForUser(principal.getName());
    checkValidity(challengePreviewDto, principal.getName());


    Optional<Challenge> challenge = challengesRepository.findById(challengePreviewDto.getId());
    log.info("Getting challenge with id: " + challengePreviewDto.getId());

    if (challenge.get() instanceof SavingChallenge) {
      return savingChallengeMapperImpl.savingChallengeDto((SavingChallenge) challenge.get(),  challengeMapperImpl);
    }

    return null;
  }

}