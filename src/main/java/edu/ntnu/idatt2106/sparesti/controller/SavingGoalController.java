package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.saving.*;
import edu.ntnu.idatt2106.sparesti.service.saving.SavingGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
@Slf4j
@RequestMapping("/api/v1/goal-manager")
@CrossOrigin(origins = "http://localhost:8082")
public class SavingGoalController {

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
                            savingGoalService.createSavingGoal(principal, savingGoalCreationRequestDto);
                    log.info("The saving goal '" + savingGoalIdDto.getId() +"' was created and stored");
                    return new ResponseEntity<>(savingGoalIdDto, HttpStatus.CREATED);
    }


    /**
     * Add a user to an existing saving goal.
     *
     * @param addSharedGoalToUserDto Dto containing the join code to match with the goal.
     * @param principal The authenticated user.
     * @return A Dto containing the goal id and title.
     */
    @Operation(summary = "Add a saving goal to the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The goal was successfully added to the user",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalDto.class))
                    }),
            @ApiResponse(responseCode = "500", description =  "Unknown internal server error", content = @Content)
    })
    @PutMapping("/goal")
    public ResponseEntity<SavingGoalDto> addGoalToUser(
            @RequestBody AddSharedGoalToUserDto addSharedGoalToUserDto, Principal principal) {

        log.info("Adding goal to user with email " + principal.getName());

        SavingGoalDto savingGoalDto = savingGoalService.addGoalToUser(principal, addSharedGoalToUserDto);

        log.info("The user was added to saving goal " + savingGoalDto.getTitle());

        return new ResponseEntity<>(savingGoalDto, HttpStatus.OK);
    }


    /**
     * Get a list containing the ID number of all the goals along with their title.
     *
     * @param principal The authenticated user
     * @return ResponseEntity containing a list of saving goal ids or a null response with a status code if something went wrong
     */
    @Operation(summary = "Retrieve a list with the id and title of all saving goals belonging to the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of saving goal ids and titles was retried from the database.",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content =
            @Content)
    })
    @GetMapping("/goals")
    public ResponseEntity<List<SavingGoalIdDto>> getGoals(Principal principal,
                                                          @RequestParam int page,
                                                          @RequestParam int pageSize)                                                   {
        log.info("Returning list of goals for user: " + principal.getName());
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        List<SavingGoalIdDto> goals = savingGoalService.getAllGoalsOfUser(principal, pageRequest);
        log.info("Number of goals being retrieved: " + goals.size());

        return new ResponseEntity<>(goals, HttpStatus.OK);
    }


    /**
     * Get a saving goal of a given id.
     *
     * @param principal The authenticated user
     * @param goalId Unique identifier of goal to retrieve
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
    @GetMapping("/goal/{goalId}")
    public ResponseEntity<SavingGoalDto> getGoalById(Principal principal, @PathVariable long goalId) {
        log.info("Returning Saving Goal: " + goalId);
        SavingGoalDto savingGoalDto = savingGoalService.getSavingGoalById(principal, goalId);
        log.info("Returning Saving Goal: " + savingGoalDto.getTitle());
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
     * Update the number of lives of the mascot.
     *
     * @param updateValueDto The unique id of the goal
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
    public ResponseEntity<String> lives(Principal principal, @RequestBody SavingGoalUpdateValueDto updateValueDto) {
        log.info("Attempting to edit pig lives of goal: " + updateValueDto.getId());
        int lives = savingGoalService.editLives(principal, updateValueDto);
        log.info("Number of lives successfully updated to: " + lives);

        return new ResponseEntity<>("Lives updated successfully", HttpStatus.OK);
    }


    /**
     * Add a newly saved amount to the progress of the goal of the given id.
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
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PutMapping("/goal/save")
    public ResponseEntity<Double> contributeAmount(Principal principal, @RequestBody  SavingGoalContributionDto savingGoalContributionDto) {

        log.info("Adding a new saving to the saving goal: " + savingGoalContributionDto.getGoalId());
        double newTotal = savingGoalService.registerSavingContribution(principal, savingGoalContributionDto);
        log.info("New saved up amount for goal: " + newTotal);

        return new ResponseEntity<>(newTotal, HttpStatus.OK);
    }

    /**
     * Get the currently saved up amount for this goal of the given id.
     *
     * @param principal The authenticated user
     * @param savingGoalIdDto DTO containing the id of the goal to check
     * @return ResponseEntity containing the currently saved up amount for this goal, or a null
     *      response with a status code if something went wrong
     */
    @Operation(summary = "Get the currently saved up amount for this goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The total amount saved for goal was retrieved",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @GetMapping("/goal/save")
    public ResponseEntity<Double> getCurrentlySavedTotal(Principal principal, @RequestBody  SavingGoalIdDto savingGoalIdDto) {

        log.info("Get currently saved up amount for goal: " + savingGoalIdDto.getId());
        double currentTotal = savingGoalService.checkTotalOfContributions(principal, savingGoalIdDto.getId());
        log.info("Currently saved up amount for goal: " + currentTotal);

        return new ResponseEntity<>(currentTotal, HttpStatus.OK);
    }


    /**
     * Update the state of the goal of a given goal-id.
     *
     * @param principal The authenticated user
     * @param updateStateDto DTO containing new state for the goal
     * @return ResponseEntity containing a message indicating the status of the update, or a
     *         null response with a status code if something went wrong
     */
    @Operation(summary = "Update the status of the goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The state of the goal was successfully updated",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @PutMapping("/goal/state")
    public ResponseEntity<String> updateGoalState(Principal principal, @RequestBody  SavingGoalUpdateStateDto updateStateDto) {

        log.info("Updating state of saving goal: " + updateStateDto.getId());
        SavingGoalIdDto updatedGoal = savingGoalService.updateGoalState(principal, updateStateDto);
        log.info("New state of goal: " + updatedGoal.getState());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get all the users contributing to this goal and their currently saved up amount for it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The contributors to this goal were retrieved",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SavingGoalContributorDto.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error", content = @Content)
    })
    @GetMapping("/goal/contributors/{goalId}")
    public ResponseEntity<List<SavingGoalContributorDto>> getContributorsToGoal(Principal principal, @PathVariable long goalId) {

        log.info("Get contributors to the goal of ID: " + goalId);
        List<SavingGoalContributorDto> contributors = savingGoalService.getContributorsToGoal(principal, goalId);
        log.info("Number of contributors retrieved: " + contributors.size());

        return new ResponseEntity<>(contributors, HttpStatus.OK);
    }

}
