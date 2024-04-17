package edu.ntnu.idatt2106.sparesti.service.challenge;


import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SavingChallengeDto;
import edu.ntnu.idatt2106.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.mapper.SavingChallengeMapper;
import edu.ntnu.idatt2106.sparesti.mapper.challenge.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.mapper.challenge.SavingChallengeMapper;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  private final SavingChallengeMapper savingChallengeMapperImpl = Mappers.getMapper(SavingChallengeMapper.class);

  public List<ChallengePreviewDto> getChallenges(Principal principal, Pageable pageable) {

    log.info("Getting challenges");
    List<Challenge> challenges = challengesRepository.findAll();
    List <ChallengePreviewDto> challengePreviewDtos = new ArrayList<>();

    for (Challenge challenge : challenges) {
      challengePreviewDtos.add(challengeMapperImpl.challengeIntoChallengePreviewDto(challenge));
    }

    return challengePreviewDtos;
  }


  public void addChallenge(ChallengeDto challenge) {
    log.info("Adding challenge");

    if (challenge instanceof SavingChallengeDto) {
      log.info("Saving, saving challenge");
      SavingChallenge savingChallenge = savingChallengeMapperImpl.savingChallengeDtoToSavingChallenge((SavingChallengeDto) challenge, challengeMapperImpl);
      challengesRepository.save(savingChallenge);
      return;
    }

 }

  public void removeChallenge(Principal principal, ChallengePreviewDto challengePreviewDto) {

    String username = principal.getName();

    User user = userRepository.findUserByUsernameIgnoreCase(username).orElseThrow(() ->
            new UsernameNotFoundException("User with username " + username + " not found"));

    log.info("Removing challenge with id: " + challengePreviewDto.getId());
    challengesRepository.deleteById(challengePreviewDto.getId());
  }

  public ChallengeDto getChallenge(Principal principal, ChallengePreviewDto challengePreviewDto) {
    log.info("Getting challenge with id: " + challengePreviewDto.getId());
    Optional<Challenge> challenge = challengesRepository.findById(challengePreviewDto.getId());
    if (challenge.get() instanceof SavingChallenge) {
      return savingChallengeMapperImpl.savingChallengeDto((SavingChallenge) challenge.get(), challengeMapperImpl);
    }
    return challenge.map(challengeMapperImpl::challengeIntoChallengeDto).orElse(null);
  }

}