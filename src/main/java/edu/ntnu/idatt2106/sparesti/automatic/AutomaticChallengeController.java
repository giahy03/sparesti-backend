package edu.ntnu.idatt2106.sparesti.automatic;

import java.security.Principal;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for creating random challenge recommendations.
 */
@RequestMapping("/api/v1/random-challenges")
@Controller
@Slf4j
@RequiredArgsConstructor
public class AutomaticChallengeController {
  @NonNull
  AutomaticChallengeService automaticChallengeService;

  /**
   * Get challenge recommendations for the user.
   *
   * @param principal the user to get recommendations for.
   * @return a list of challenge recommendations.
   */
  @GetMapping("/")
  public ResponseEntity<List<ChallengeRecommendationDto>> getRecommendations(Principal principal) {
    log.info("Getting challenge recommendations for user: {}", principal.getName());
    return ResponseEntity.ok(
        automaticChallengeService.getChallengeRecommendationsForUser(principal));
  }

}
