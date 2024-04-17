package edu.ntnu.idatt2106.sparesti.controller;


import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.service.challenge.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class ChallengeController {

  private final ChallengeService challengeService;

  @GetMapping("/challenges")
  public ResponseEntity<String> getChallengesForUser() {
    challengeService.getChallengesForUsers(null, null);
    return ResponseEntity.ok("Accepted");
  }
}