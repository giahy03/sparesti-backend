package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChallengeService {

  ChallengesRepository challengesRepository;

  public List<ChallengeDto> getChallengesForUsers(Principal principal, Pageable pageable) {

    List<Challenge> challenges = challengesRepository.findAllByUser_Username(principal.getName(), pageable);

    List<ChallengeDto> challengesResponse = new ArrayList<>();

    for (Challenge challenge : challenges) {
      challengesResponse.add(ChallengeDto.builder()
              .username(challenge.getUser().getUsername())
              .challengeName(challenge.getDescription())
              .description(challenge.getDescription())
              .startDate(challenge.getStartDate())
              .endDate(challenge.getEndDate())
              .difficulty(challenge.getDifficulty().name())
              .build());
    }

    return challengesResponse;
  }

  public void addChallenge(ChallengeDto challengeRequestDto) {

  }

  public void removeChallenge() {

  }

}