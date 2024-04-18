package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.service.challenge.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

/**
 * Controller class for challenges.
 * This class is responsible for handling HTTP requests related to challenges.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/challenge-management")
public class ChallengeController {

  private final ChallengeService challengeService;

  @GetMapping("/challenges")
  public ResponseEntity<List<ChallengePreviewDto>> getChallengesForUser(Principal principal,
                                                     @RequestParam int page,
                                                     @RequestParam int pageSize) {

    log.info("Getting challenges for user: {}", principal.getName());
    PageRequest pageRequest = PageRequest.of(page, pageSize);
    List<ChallengePreviewDto> challenges = challengeService.getChallenges(principal, pageRequest);

    return new ResponseEntity<>(challenges, HttpStatus.OK);
  }

  @PostMapping("/challenge")
  public ResponseEntity<String> addChallenge(Principal principal,
                                             @RequestBody ChallengeDto challenge) {

    challengeService.addChallenge(principal, challenge);

    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @GetMapping("/challenge/{challengeId}")
  public ResponseEntity<ChallengeDto> getChallengeById(Principal principal, @PathVariable long challengeId) {

    log.info("Getting challenge for user: {}", principal.getName());

    ChallengeDto challenge = challengeService.getChallenge(principal, challengeId);

    return new ResponseEntity<>(challenge, HttpStatus.OK);
  }

  @DeleteMapping("/challenge/{challengeId}")
  public ResponseEntity<String> removeChallenge(Principal principal, @PathVariable long challengeId) {
    log.info("Deleting challenge for user: {}", principal.getName());
    challengeService.removeChallenge(principal, challengeId);
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

}