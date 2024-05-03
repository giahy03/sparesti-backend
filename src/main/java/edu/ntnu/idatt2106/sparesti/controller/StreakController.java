package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.streak.StreakRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.streak.StreakResponseDto;
import edu.ntnu.idatt2106.sparesti.service.streak.StreakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling streak related REST-endpoints.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/streak")
public class StreakController {

  private final StreakService streakService;

  /**
   * REST-endpoint for changing the streak.
   *
   * @param streakRequestDto The streak request containing the action to be performed on the streak.
   * @param principal        The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's streak")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User's streak successfully updated."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @PutMapping()
  public ResponseEntity<Void> changeStreak(@RequestBody StreakRequestDto streakRequestDto,
                                           Principal principal) {

    String email = principal.getName();
    log.info("Changing number of days in the streak for user {}", email);
    streakService.updateStreak(streakRequestDto, email);
    log.info("Changed number of days in the streak for user {}", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for retrieving the current user streak.
   *
   * @param principal The principal object representing the currently authenticated user.
   * @return ResponseEntity containing the streak response DTO on success.
   */
  @Operation(summary = "Retrieve user streak")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Streak successfully retrieved.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = StreakResponseDto.class))}),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @GetMapping()
  public ResponseEntity<StreakResponseDto> retrieveStreak(Principal principal) {
    String email = principal.getName();
    log.info("Retrieving streak for user {}", email);
    StreakResponseDto streakResponseDto = streakService.getStreak(email);
    log.info("Retrieved streak for user {}", email);
    return new ResponseEntity<>(streakResponseDto, HttpStatus.OK);
  }
}

