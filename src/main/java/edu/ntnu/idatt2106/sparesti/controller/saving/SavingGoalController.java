package edu.ntnu.idatt2106.sparesti.controller.saving;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * Controller class for handling requests related to saving goals.
 *
 * @author Hanne-Sofie Søreide
 */

@RestController
@RequestMapping("/api/goal-management")
@CrossOrigin(origins = "http://localhost:3000")
public class SavingGoalController {

    @NonNull
    private final SavingGoalService savingGoalService;


    @Operation(summary = "Creating a new Savings Goal and store it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The saving goal was successfully created and saved",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalCreationResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description =  "Unknown internal server error", content = @Content)
    })
    @PostMapping("/goals")
    public ResponseEntity<SavingGoalCreationResponseDto> createSavingGoal(
            @RequestBody SavingGoalCreationRequestDto savingGoalCreationRequestDto, Principal authenticatedPrincipal) {
                    log.info("Adding Saving Goal for user: " + authenticatedPrincipal.getName());
                    SavingGoalCreationResponseDto savingGoalCreationResponseDto =
                            savingGoalService.createSavingGoal(savingGoalCreationRequestDto, authenticatedPrincipal);
                    //log.info("The saving goal '" + savingGoalCreationResponseDto.getName() +"' was created");
                    log.info("The saving goal was stored");

                    return new ResponseEntity<>(savingGoalCreationResponseDto, HttpStatus.OK);
    }



    @Operation(summary = "Retrieve a list with the ids of all saving goals belonging to the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of saving goal ids was retried from the database.",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
            @Content)
    })
    @GetMapping("/goals/{email}")
    public ResponseEntity<List<Long>> getGoalsByEmail(Principal principal, @PathVariable String email) {  // user_id?

        log.info("Returning list of goals for user: " + email);
        GoalsDto goals = savingGoalService.findAllGoalIdByEmail(principal, email);
        return new ResponseEntity<>(goals, HttpStatus.OK);  // Returns all goal ID's.
    }


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
    @GetMapping("goals/{goalId}")
    public ResponseEntity<SavingGoalDto> getGoalById(Principal principal, @PathVariable long goalId) {  // Change to a UID?

        log.info("Returning Saving Goal: " + goalId);
        SavingGoalDto goal = savingGoalService.getSavingGoalById(principal, goalId);
        log.info("Returning Saving Goal: " + goal.getName());
        return new ResponseEntity<>(SavingGoalDto, HttpStatus.OK);
    }


    // Put pig life  (goalID)

    // Update current tile (goalID)

    // Update progress (addedSaved)


}
