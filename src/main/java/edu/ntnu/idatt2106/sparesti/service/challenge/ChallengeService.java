package edu.ntnu.idatt2106.sparesti.service.challenge;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.repository.ChallengesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

  @Autowired
  ChallengesRepository challengesRepository;

  public List<ChallengeDto> getChallengesForUsers(Principal principal, Pageable pageable) {

//    List<Challenge> challenges = challengesRepository.findAllByUser_Username(principal.getName(), pageable);

    List<Challenge> testChallenges = challengesRepository.findAll();

    for (Challenge testChallenge : testChallenges) {
      if (testChallenge instanceof SavingChallenge) {
        System.out.println("Saving challenge");
      }
    }
    //TODO create mappers

    return null;
  }

  public void addChallenge(ChallengeDto challengeRequestDto) {

  }

  public void removeChallenge() {

  }

}