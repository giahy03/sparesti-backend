package edu.ntnu.idatt2106.sparesti.controller.saving;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.service.saving.SavingGoalService;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;


/**
 * Controller class for handling requests related to saving goals.
 *
 * @author Hanne-Sofie Søreide
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/goal-manager")
@CrossOrigin(origins = "http://localhost:8082")
public class SavingGoalController {

    @NonNull
    private final SavingGoalService savingGoalService;

    /**
     * Create a new saving goal.
     *
     * @param savingGoalCreationRequestDto DTO containing the saving goal to be created
     * @param principal The currently authenticated user
     * @return ResponseEntity containing the created Saving Goal, or an error message
     */
    @Operation(summary = "Creating a new Savings Goal and store it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The saving goal was successfully created and saved",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalIdDto.class))
            }),
            @ApiResponse(responseCode = "500", description =  "Unknown internal server error", content = @Content)
    })
    @PostMapping("/goals")
    public ResponseEntity<SavingGoalIdDto> createSavingGoal(
            @RequestBody SavingGoalCreationRequestDto savingGoalCreationRequestDto, Principal principal) {
                    log.info("Adding Saving Goal for user: " + principal.getName());
                    SavingGoalIdDto savingGoalIdDto =
                            savingGoalService.createSavingGoal(savingGoalCreationRequestDto, principal);
                    log.info("The saving goal '" + savingGoalIdDto.getId() +"' was created and stored");
                    return new ResponseEntity<>(savingGoalIdDto, HttpStatus.CREATED);
    }


    /**
     * Get a list containing the ID number of all the goals.
     *
     * @param principal The authenticated user
     * @return ResponseEntity containing a list of saving goal ids or a null response with a status code if something went wrong
     */
    @Operation(summary = "Retrieve a list with the ids of all saving goals belonging to the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of saving goal ids was retried from the database.",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
            @Content)
    })
    @GetMapping("/goals")
    public ResponseEntity<List<SavingGoalIdDto>> getGoalsByEmail(Principal principal, Pageable pageable) {
        log.info("Returning list of goals for user: " + principal.getName());
        List<SavingGoalIdDto> goals = savingGoalService.getAllGoalIdsByEmail(principal, pageable);
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }


    /**
     * Get a saving goal of a given id.
     *
     * @param savingGoalIdDto The unique id of the saving goal
     * @return ResponseEntity containing the retrieved saving goal, or an error message
     */
    @Operation(summary = "Get a saving goal by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The goal was found and returned",
                content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "The saving goal was not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @GetMapping("/goal")
    public ResponseEntity<SavingGoalDto> getGoalById( @RequestBody SavingGoalIdDto savingGoalIdDto) {
        log.info("Returning Saving Goal: " + savingGoalIdDto.getId());
        SavingGoalDto savingGoalDto = savingGoalService.getSavingGoalById(savingGoalIdDto);
        log.info("Returning Saving Goal: " + savingGoalDto.getGoalName());
        return new ResponseEntity<>(savingGoalDto, HttpStatus.OK);
    }


    /**
     * Delete a saving goal by its id.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto Unique id of the goal
     * @return ResponseEntity containing a message indicating the status of the deletion, or a
     *          null response with a status code if something went wrong.
     */
    @Operation(summary = "Delete a saving goal by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The saving goal was deleted",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "404", description = "The saving goal was not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "The user is not authorized to delete the saving goal", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @DeleteMapping("/goal")
    public ResponseEntity<String> deleteSavingGoal(Principal principal, @RequestBody SavingGoalIdDto savingGoalIdDto) {
        log.info("Attempting to delete goal: " + savingGoalIdDto.getId());
        savingGoalService.deleteSavingGoal(principal, savingGoalIdDto);
        log.info("Goal deleted: " + savingGoalIdDto.getId());
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }


    /**
     * Update the number of lives of the mascot. The updated number of lives is returned.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto The unique id of the goal
     * @return ResponseEntity containing a message indicating the status of the update of the mascot life, or a
     *          null response with a status code if something went wrong
     */
    @Operation(summary = "Edit lives of saving mascot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The number of lives was edited",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PutMapping("/goal/lives")
    public ResponseEntity<String> lives(Principal principal, @RequestBody  SavingGoalIdDto savingGoalIdDto) {
        log.info("Attempting to edit pig lives of goal: " + savingGoalIdDto.getId());
        int lives = savingGoalService.editLives(principal, savingGoalIdDto);
        log.info("Number of lives successfully updated to: " + lives);
        return new ResponseEntity<>("Lives updated successfully", HttpStatus.OK);

    }


    /**
     * Update the current tile based on today's date. The new tile value is returned and represents the number
     * of days since saving goal start.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto The unique id of the goal
     * @return ResponseEntity containing a message indicating the status of updating the current tile, or a
     *          null response with a status code if something went wrong
     */
    @Operation(summary = "Update current tile of saving goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The current tile was updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PutMapping("/goal/tile")
    public ResponseEntity<String> tile(Principal principal, @RequestBody  SavingGoalIdDto savingGoalIdDto) {
        log.info("Attempting to update current tile of goal: " + savingGoalIdDto.getId());
        int tile = savingGoalService.updateCurrentTile(principal, savingGoalIdDto);
        log.info("Current tile has been successfully updated to: " + tile);
        return new ResponseEntity<>("Current tile updated successfully", HttpStatus.OK);

    }


    /**
     * Add a newly saved amount to the goal of the given id. The amount is added to the goal's progress and the new
     * progress amount is returned.
     *
     * @param principal The authenticated user
     * @param savingGoalContributionDto DTO containing the goal id and the saved amount to add to the goal's progress
     * @return ResponseEntity containing a message indicating the status of updating the saved amount of the goal,
     *          or a null response with a status code if something went wrong
     */
    @Operation(summary = "Add a new saved amount to goal total saved amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The total amount saved for goal was updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PutMapping("/goal/save")
    public ResponseEntity<String> savedAmount(Principal principal, @RequestBody  SavingGoalContributionDto savingGoalContributionDto) {

        log.info("Adding a new saving to the saving goal: " + savingGoalContributionDto.getId());
        double newProgress = savingGoalService.registerSavingContribution(principal, savingGoalContributionDto);
        log.info("New saved up amount for goal: " + newProgress);

        return new ResponseEntity<>("Amount saved for goal was successfully updated", HttpStatus.OK);
    }

}
