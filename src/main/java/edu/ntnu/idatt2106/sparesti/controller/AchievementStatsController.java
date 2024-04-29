package edu.ntnu.idatt2106.sparesti.controller;


import edu.ntnu.idatt2106.sparesti.dto.achievementStats.CheckForAchievementDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.service.achievementStats.AchievementStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller class for handling requests related to achievement stats of the user.
 *
 * @author Hanne-Sofie Søreide
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/achievement-manager")
@CrossOrigin(origins = "http://localhost:8082")
public class AchievementStatsController {

    private final AchievementStatsService achievementStatsService;

    /**
     * Responds to post requests that specify an achievement type to update the user stats for and,
     * if the user qualified for a new badge, returns a DTO providing a preview of the new badge.
     * @param principal The authenticated user
     * @param checkForAchievementDto DTO specifying the achievement type to check and the date.
     * @return ResponseEntity containing OK if the user stat was just updated, CREATED if the user
     * was additionally rewarded with a new badge along with a DTO representing the badge.
     */
    @Operation(summary = "Post request to update user stats related to achievements and receive a badge in return if" +
            "the user was rewarded a new one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user was awarded a new badge",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadgePreviewDto.class))
                    }),
            @ApiResponse(responseCode = "200", description = "The user stats were updated",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PostMapping("/stats")
    public ResponseEntity<BadgePreviewDto> checkForAchievement(Principal principal, @RequestBody CheckForAchievementDto checkForAchievementDto) {

        int level = achievementStatsService.updateAndCheckAchievement(checkForAchievementDto, principal);

        if (level > 0) {
            BadgePreviewDto createdBadge = achievementStatsService.createBadge(checkForAchievementDto, principal, level);
            return new ResponseEntity<>(createdBadge, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
