package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.service.badge.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller class for handling requests related to badges.
 *
 * @author Hanne-Sofie Søreide
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/badge-manager")
@CrossOrigin(origins = "http://localhost:8082")
public class BadgeController {

    private final BadgeService badgeService;



    /**
     * Get a list containing all the badges achieved by the user.
     *
     * @param principal The authenticated user
     * @return ResponseEntity containing a list of achieved badges or a null response with a status code if something went wrong
     */
    @Operation(summary = "Retrieve a list with the all the badges achieved by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of badges was retried from the database.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadgePreviewDto.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
            @Content)
    })
    @GetMapping("/badges")
    public ResponseEntity<List<BadgePreviewDto>> getBadgesByEmail(Principal principal, Pageable pageable) {
        log.info("Returning list of Badges from database: " + principal.getName());
        List<BadgePreviewDto> badges = badgeService.getAllBadgesByEmail(principal, pageable);
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }





    /**
     * Get a list of all the possible achievements and their thresholds to qualify for a badge.
     * Each achievement category and threshold combination correspond to a possible badge.
     *
     * @return A list of DTOs representing the possible achievements and their associated thresholds.
     */
    @Operation(summary = "Retrieve a list with the all the possible badges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of possible achievements and their " +
                    "thresholds was retried from the database.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AchievementPreviewDto.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
            @Content)
    })
    @GetMapping("/possible-badges")
    public ResponseEntity<List<AchievementPreviewDto>> getPossibleBadges(){
        log.info("Returning list of achievements and their thresholds from database... ");
        List<AchievementPreviewDto> possibleBadges = badgeService.getAchievementPreviews();
        log.info("Number of achievements returned: " + possibleBadges.size());
        return new ResponseEntity<>(possibleBadges, HttpStatus.OK);
    }


}
