package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.user.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.PasswordChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
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
 * Controller class responsible for handling user related REST-endpoints.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  /**
   * Changes the password of the currently logged-in user.
   *
   * @param passwordChangeDto The DTO containing old and new password.
   * @param principal The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user password")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User password successfully updated."),
          @ApiResponse(responseCode = "400", description = "Invalid password from user."),
          @ApiResponse(responseCode = "500", description = "Internal server error.",
                  content = @Content)
  })
  @PutMapping(path = "/password")
  public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDto passwordChangeDto,
                                                 Principal principal) {
    String email = principal.getName();
    log.info("Changing password for user with email {}.", email);
    userService.editPassword(passwordChangeDto, email);
    log.info("Password for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Changes the first name of the currently logged-in user.
   *
   * @param firstNameChangeDto The DTO containing the new first name.
   * @param principal The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's first name")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User fist name successfully updated."),
          @ApiResponse(responseCode = "400", description = "Invalid first name from user."),
          @ApiResponse(responseCode = "500", description = "Internal server error.",
                  content = @Content)
  })
  @PutMapping(path = "/first-name")
  public ResponseEntity<Void> changeFirstName(@RequestBody FirstNameChangeDto firstNameChangeDto,
                                                 Principal principal) {
    String email = principal.getName();
    log.info("Changing first name for user with email {}.", email);
    userService.editFistName(firstNameChangeDto, email);
    log.info("First name for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Changes the last name of the currently logged-in user.
   *
   * @param lastNameChangeDto The DTO containing the new last name.
   * @param principal The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's last name")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User fist name successfully updated."),
          @ApiResponse(responseCode = "400", description = "Invalid last name from user."),
          @ApiResponse(responseCode = "500", description = "Internal server error.",
                  content = @Content)
  })
  @PutMapping(path = "/last-name")
  public ResponseEntity<Void> changeLastName(@RequestBody LastNameChangeDto lastNameChangeDto,
                                                 Principal principal) {
    String email = principal.getName();
    log.info("Changing last name for user with email {}.", email);
    userService.editLastName(lastNameChangeDto, email);
    log.info("Last name for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for retrieving user information.
   *
   * @param principal The authenticated principal
   * @return ResponseEntity containing user information DTO on success.
   */
  @Operation(summary = "Retrieve user details")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User information successfully retrieved.",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = UserDetailsDto.class))}
          ),
          @ApiResponse(responseCode = "500", description = "Internal server error.",
                  content = @Content)
  })
  @GetMapping(path = "/details")
  public ResponseEntity<UserDetailsDto> retrieveUserDetails(Principal principal) {
    String username = principal.getName();
    log.info("Retrieving user information for user {}.", username);
    UserDetailsDto userDetails = userService.getUserDetails(username);
    log.info("User information for {} successfully retrieved.", username);
    return new ResponseEntity<>(userDetails, HttpStatus.OK);
  }
}
