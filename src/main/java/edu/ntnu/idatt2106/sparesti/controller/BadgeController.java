package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeCreateDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeCreateRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeIdDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
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
    private final AchievementRepository achievementRepository;
    private final BadgeMapper badgeMapper;

    /**
     * Get a badge of a given id.
     *
     * @param badgeIdDto The unique id of the saving goal
     * @return ResponseEntity containing the retrieved badge, or an error message
     */
    @Operation(summary = "Get a badge by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The badge was found and returned",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadgePreviewDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "The badge was not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @GetMapping("/badge")
    public ResponseEntity<BadgePreviewDto> getBadgeById(@RequestBody BadgeIdDto badgeIdDto) {
        log.info("Returning Badge: " + badgeIdDto.getId());
        BadgePreviewDto badgeDto = badgeService.getBadgeById(badgeIdDto);
        log.info("Returning Badge: " + badgeDto.getAchievement().toString().toLowerCase() + " - Level: " + badgeDto.getLevel());
        return new ResponseEntity<>(badgeDto, HttpStatus.OK);
    }


    /**
     * Get a badge of a given id.
     *
     * @param badgeCreateRequestDto The unique id of the saving goal
     * @return ResponseEntity containing the retrieved badge, or an error message
     */
    @Operation(summary = "Create badge for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The badge was found and returned",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BadgePreviewDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "The badge was not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PostMapping("/badge")
    public ResponseEntity<BadgePreviewDto> createBadge(@RequestBody BadgeCreateRequestDto badgeCreateRequestDto, Principal principal) {
        log.info("Creating Badge of: " + badgeCreateRequestDto.getAchievement() + " achievement for user: " + principal.getName());

        Achievement achievement = badgeService.getAchievementOfCategory(badgeCreateRequestDto.getAchievement(), principal);

        BadgeCreateDto badgeCreateDto = badgeMapper.mapToBadgeCreateDto(badgeCreateRequestDto, achievement);

        BadgePreviewDto badgeDto = badgeService.createBadge(badgeCreateDto, principal);

        log.info("Saved Badge: " + badgeDto.getAchievement().toString() + " - Level: " + badgeDto.getLevel());

        return new ResponseEntity<>(badgeDto, HttpStatus.OK);
    }


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
    public ResponseEntity<List<BadgePreviewDto>> getGoalsByEmail(Principal principal, Pageable pageable) {
        log.info("Returning list of Badges from database: " + principal.getName());
        List<BadgePreviewDto> badges = badgeService.getAllBadgesByEmail(principal, pageable);
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }



    /**
     * Delete a badge by its id.
     *
     * @param principal The authenticated user
     * @param badgeIdDto Unique id of the badge to be deleted
     * @return ResponseEntity containing a message indicating the status of the deletion, or a
     *          null response with a status code if something went wrong.
     */
    @Operation(summary = "Delete a badge by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The badge was deleted",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
            @ApiResponse(responseCode = "404", description = "The badge was not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user is not authorized to delete the badge", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @DeleteMapping("/badge")
    public ResponseEntity<String> deleteBadge(Principal principal, @RequestBody BadgeIdDto badgeIdDto) {
        log.info("Attempting to delete goal: " + badgeIdDto.getId());
        badgeService.deleteBadgeById(principal, badgeIdDto);
        log.info("Goal deleted: " + badgeIdDto.getId());
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }


}
