package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeUpdateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.service.challenge.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




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

  /**
   * Get all challenges to the given user.
   *
   * @param principal the user that wants to get the challenges.
   * @param page the page number to get.
   * @param pageSize the number of challenges to get.
   * @return ResponseEntity containing the challenges.
   */
  @Operation(summary = "Get all challenges to the given user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
              description = "The challenges were found and " + "returned")})
  @GetMapping("/challenges")
  public ResponseEntity<List<ChallengePreviewDto>> getChallengesForUser(
          Principal principal,
          @RequestParam int page,
          @RequestParam int pageSize) {

    log.info("Getting challenges for user: {}", principal.getName());
    PageRequest pageRequest = PageRequest.of(page, pageSize);
    List<ChallengePreviewDto> challenges = challengeService.getChallenges(principal, pageRequest);

    log.info("Challenges successfully retrieved for user: {}", principal.getName());
    return new ResponseEntity<>(challenges, HttpStatus.OK);
  }

  /**
   * Add a challenge to the given user.
   *
   * @param principal the user that wants to add the challenge.
   * @param challenge the challenge to add.
   * @return ResponseEntity containing the status of the operation.
   */
  @Operation(summary = "Add a challenge to the given user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The challenge was added successfully"),
      @ApiResponse(responseCode = "400", description = "The challenge could not be added")
  })
  @PostMapping("/challenge")
  public ResponseEntity<String> addChallenge(Principal principal,
                                             @RequestBody ChallengeDto challenge) {

    log.info("Adding challenge for user: {}", principal.getName());
    challengeService.addChallenge(principal, challenge);

    log.info("Challenge successfully added for user: {}", principal.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * Get a specific challenge to the given user.
   *
   * @param principal the user that wants to get the challenge.
   * @param challengeId the id of the challenge to get.
   * @return ResponseEntity containing the challenge.
   */
  @Operation(summary = "Get a specific challenge to the given user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
              description = "The challenge was added successfully"),
      @ApiResponse(responseCode = "400",
              description = "The challenge could not be found"),
      @ApiResponse(responseCode = "401",
              description = "The user is not authorized to change the challenge"),
      @ApiResponse(responseCode = "404",
              description = "The user or object could not be found.")
  })
  @GetMapping("/challenge/{challengeId}")
  public ResponseEntity<ChallengeDto> getChallengeById(Principal principal,
                                                       @PathVariable long challengeId) {

    log.info("Getting challenge for user: {}", principal.getName());
    ChallengeDto challenge = challengeService.getChallenge(principal, challengeId);

    log.info("Challenge successfully retrieved with id {}", challengeId);
    return new ResponseEntity<>(challenge, HttpStatus.OK);
  }

  /**
   * Remove a challenge from the given user.
   *
   * @param principal the user that wants to remove the challenge.
   * @param challengeId the id of the challenge to remove.
   * @return ResponseEntity containing the status of the operation.
   */
  @Operation(summary = "Remove a challenge from the given user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
              description = "The challenge was removed successfully"),
      @ApiResponse(responseCode = "400",
              description = "The challenge could not be removed"),
      @ApiResponse(responseCode = "401",
              description = "The user is not authorized to change the challenge"),
      @ApiResponse(responseCode = "404",
              description = "The user or object could not be found.")
  })
  @DeleteMapping("/challenge/{challengeId}")
  public ResponseEntity<String> removeChallenge(Principal principal,
                                                @PathVariable long challengeId) {

    log.info("Deleting challenge for user: {}", principal.getName());
    challengeService.removeChallenge(principal, challengeId);

    log.info("Challenge successfully deleted with id {}", challengeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * Update a challenge for the given user.
   *
   * @param principal        the user that wants to update the challenge.
   * @param challengeId      the id of the challenge to update.
   * @param updateRequestDto the new challenge data.
   * @return ResponseEntity containing the status of the operation.
   */
  @Operation(summary = "Update a challenge for the given user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
              description = "The challenge was updated successfully."),
      @ApiResponse(responseCode = "400",
              description = "The challenge could not be updated."),
      @ApiResponse(responseCode = "401",
              description = "The user is not authorized to change the challenge."),
      @ApiResponse(responseCode = "404",
              description = "The user could not be found.")
  })
  @PutMapping("/challenge/{challengeId}")
  public ResponseEntity<String> updateChallenge(
          Principal principal,
          @PathVariable long challengeId,
          @RequestBody ChallengeUpdateRequestDto updateRequestDto) {

    log.info("Updating challenge {} for user: {}", challengeId, principal.getName());
    challengeService.updateChallenge(principal, challengeId, updateRequestDto);

    log.info("Challenge successfully updated with id {}", challengeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * Join a shared challenge with a join code.
   *
   * @param principal the user that wants to join the shared challenge.
   * @param joinCode the join code of the shared challenge.
   * @return ResponseEntity containing the status of the operation.
   */
  @Operation(summary = "Join a shared challenge to a given user.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",
                  description = "The user joined the shared challenge successfully"),
          @ApiResponse(responseCode = "400",
                  description = "The challenge could not be updated."),
  })
  @GetMapping("/shared-challenge/{joinCode}")
  public ResponseEntity<String> joinSharedChallenge(Principal principal,
                                                    @PathVariable String joinCode) {
    log.info("Joining shared challenge with join code: {}, for user: {}", joinCode, principal.getName());
    challengeService.joinSharedChallenge(principal, joinCode);
    log.info("Shared challenge successfully joined with join code {}", joinCode);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
  * Get participating users for a shared challenge.
  *
  * @param principal the user that wants to get the participating users.
  * @return ResponseEntity containing the participating users.
  */
  @GetMapping("/shared-challenge/users/{joinCode}")
  public ResponseEntity<List<SharedChallengePreviewDto>> getParticipatingUsers(Principal principal,
                                                                               @PathVariable String joinCode) {
    log.info("Getting participating users for shared challenge with join code: {}", joinCode);
    List<SharedChallengePreviewDto> sharedChallengeDto = challengeService.getParticipatingUsers(principal, joinCode);
    log.info("Participating users successfully retrieved for challenge with id: {}", joinCode);
    return new ResponseEntity<>(sharedChallengeDto, HttpStatus.OK);
  }

}