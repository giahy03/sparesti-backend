package edu.ntnu.idatt2106.sparesti.controller.saving;

import edu.ntnu.idatt2106.sparesti.service.saving.SavingGoalService;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/goal")
@CrossOrigin(origins = "http://localhost:8082")
public class SavingGoalController {

    @NonNull
    private final SavingGoalService savingGoalService;

    /**
     * Create a new saving goal.
     *
     * @param savingGoalCreationRequestDto DTO containing the saving goal to be created
     * @param authenticatedPrincipal The currently authenticated user
     * @return ResponseEntity containing the created Saving Goal, or an error message
     */

/*
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
            @RequestBody SavingGoalCreationRequestDto savingGoalCreationRequestDto, Principal principal) {
                    log.info("Adding Saving Goal for user: " + principal.getName());
                    SavingGoalCreationResponseDto savingGoalCreationResponseDto =
                            savingGoalService.createSavingGoal(savingGoalCreationRequestDto, principal);
                    //log.info("The saving goal '" + savingGoalCreationResponseDto.getName() +"' was created");
                    log.info("The saving goal was stored");

                    return new ResponseEntity<>(savingGoalCreationResponseDto, HttpStatus.OK);
    }
*/



    /**
     * Get a list containing the ID number of all the goals.
     *
     * @param principal The currently authenticated user
     * @return ResponseEntity containing a list of saving goal ids or a null response with a status code if something went wrong
     */

/*    @Operation(summary = "Retrieve a list with the ids of all saving goals belonging to the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of saving goal ids was retried from the database.",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
            @Content)
    })
    @GetMapping("/goals")
    public ResponseEntity<List<Long>> getGoalsByEmail(Principal principal) {

        log.info("Returning list of goals for user: " + principal.getName());
        SavingGoalIdsDto goals = savingGoalService.getAllGoalIdsByEmail(principal);
        return new ResponseEntity<>(goals, HttpStatus.OK);  // Returns all goal ID's.
    }

    */

    /**
     * Get a saving goal of a given id.
     *
     * @param goalId The unique id of the saving goal
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
    @GetMapping("goal/{goalId}")
    public ResponseEntity<SavingGoalDto> getGoalById(@PathVariable long goalId) {  // Change to a UID?

        //log.info("Returning Saving Goal: " + goalId);
        SavingGoalDto goal = savingGoalService.getSavingGoalById(goalId);
        //log.info("Returning Saving Goal: " + goal.getName());
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }


//    PUT /updatePigLife (goalID)
//    PUT /updateCurrentTile (goalID)
//    PUT /updateSavedAmount (goalID, addedValue)


}
