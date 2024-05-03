package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.repository.AchievementStatsRepository;
import edu.ntnu.idatt2106.sparesti.service.achievementStats.AchievementStatsService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling requests related to achievement stats of the user.
 *
 * @author Hanne-Sofie Søreide
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/achievement-manager")
public class AchievementStatsController {

  private final AchievementStatsService achievementStatsService;
  private final AchievementStatsRepository achievementStatsRepository;

  /**
   * Responds to post requests that specify an achievement type to update the user stats for and,
   * if the user qualified for a new badge, returns a DTO providing a preview of the new badge.
   *
   * @param principal The authenticated user
   * @param category  DTO specifying the achievement type to check and the date.
   * @return ResponseEntity containing OK if the user stat was just updated, CREATED if the user
   *        was additionally rewarded with a new badge along with a DTO representing the badge.
   */
  @Operation(summary =
      "Post request to update user stats related to achievements and receive a badge in return if"
          + "the user was rewarded a new one.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "The user was awarded a new badge",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation =
                  BadgePreviewDto.class))
          }),
      @ApiResponse(responseCode = "200", description = "The user stats were updated",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
      @Content)
  })
  @GetMapping("/stats/{category}")
  public ResponseEntity<BadgePreviewDto> checkForAchievement(
      Principal principal, @PathVariable AchievementCategory category) {

    log.info("Checking if badge of the category {} was qualified for.", category);
    int level = achievementStatsService.updateAndCheckAchievement(category, principal);

    log.info("If 0, no badge awarded, if a number, a badge of that level was awarded: {}", level);

    if (level > 0) {
      log.info("Returning a badge preview DTO");
      BadgePreviewDto createdBadge =
          achievementStatsService.createBadge(category, principal, level);
      return new ResponseEntity<>(createdBadge, HttpStatus.CREATED);
    } else {
      log.info("No new badge received, but the stats were updated");
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }

  /**
   * Get the total amount saved in Sparesti by the current user.
   *
   * @param principal The authenticated user.
   * @return The total amount saved by the user.
   */
  @Operation(summary = "Get the total amount of money saved by the current user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The total saved amount was retrieved",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation =
                  Double.class))
          }),
      @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
      @Content)
  })
  @GetMapping("/total")
  public ResponseEntity<Double> getTotalSavedByUser(Principal principal) {

    log.info("Returning total saved amount by user: {}", principal.getName());
    double total = achievementStatsRepository.findAchievementStatsByUserEmail(principal.getName())
        .orElseThrow().getTotalSaved();
    log.info("Total saved amount in Sparesti: {}", total);

    return new ResponseEntity<>(total, HttpStatus.OK);
  }


}
