package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.IncomeChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LivingStatusChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.ResetPasswordDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
   * REST-endpoint for changing the password of the currently logged-in user.
   *
   * @param passwordChangeDto The DTO containing old and new password.
   * @param principal         The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's password")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User password successfully updated."),
      @ApiResponse(responseCode = "400", description = "Invalid password from user."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
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
   * REST-endpoint for changing the first name of the currently logged-in user.
   *
   * @param firstNameChangeDto The DTO containing the new first name.
   * @param principal          The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's first name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User fist name successfully updated."),
      @ApiResponse(responseCode = "400", description = "Invalid first name from user."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @PutMapping(path = "/first-name")
  public ResponseEntity<Void> changeFirstName(@RequestBody FirstNameChangeDto firstNameChangeDto,
                                              Principal principal) {
    String email = principal.getName();
    log.info("Changing first name for user with email {}.", email);
    userService.editFirstName(firstNameChangeDto, email);
    log.info("First name for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for changing the last name of the currently logged-in user.
   *
   * @param lastNameChangeDto The DTO containing the new last name.
   * @param principal         The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's last name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User last name successfully updated."),
      @ApiResponse(responseCode = "400", description = "Invalid last name from user."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
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
   * REST-endpoint for changing the income of the currently logged-in user.
   *
   * @param incomeChangeDto The DTO containing the new income.
   * @param principal       The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's income")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User income successfully updated."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @PutMapping(path = "/income")
  public ResponseEntity<Void> changeIncome(@RequestBody IncomeChangeDto incomeChangeDto,
                                           Principal principal) {
    String email = principal.getName();
    log.info("Changing income for user with email {}.", email);
    userService.editIncome(incomeChangeDto, email);
    log.info("Income for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for changing the saving percentage of the currently logged-in user.
   *
   * @param savingPercentage The new saving percentage.
   * @param principal        The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's saving percentage")
  @ApiResponse(responseCode = "200", description = "User saving percentage successfully updated.")
  @ApiResponse(responseCode = "400", description = "Invalid saving percentage from user.")
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  @PutMapping(path = "/saving-percentage")
  public ResponseEntity<Void> changeSavingPercentage(@RequestBody Integer savingPercentage,
                                                     Principal principal) {
    String email = principal.getName();
    log.info("Changing saving percentage for user with email {}.", email);
    userService.editSavingPercentage(savingPercentage, email);
    log.info("Saving percentage for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for changing the living status of the currently logged-in user.
   *
   * @param livingStatusChangeDto The DTO containing the new living status.
   * @param principal             The principal object representing the currently authenticated
   *                              user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Change user's living status")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Living status successfully updated."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @PutMapping(path = "/living-status")
  public ResponseEntity<Void> changeLivingStatus(@RequestBody
                                                 LivingStatusChangeDto livingStatusChangeDto,
                                                 Principal principal) {
    String email = principal.getName();
    log.info("Changing living status for user with email {}.", email);
    userService.editLivingStatus(livingStatusChangeDto, email);
    log.info("Living status for {} successfully updated.", email);
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
      @ApiResponse(responseCode = "200",
          description = "User information successfully retrieved.",
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

  /**
   * REST-endpoint for adding additional user information of the currently logged-in user.
   *
   * @param userInfoDto The DTO containing the user information.
   * @param principal   The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Register additional user information.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Information successfully registered."),
      @ApiResponse(responseCode = "500", description = "Internal server error.",
          content = @Content)
  })
  @PostMapping(path = "/info")
  public ResponseEntity<Void> registerUserInformation(@RequestBody UserInfoDto userInfoDto,
                                                      Principal principal) {
    String email = principal.getName();
    log.info("Attempting to register additional user information for user with email {}.", email);
    userService.addUserInfo(userInfoDto, email);
    log.info("User information for {} successfully registered.", email);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * REST-endpoint for deleting the currently logged-in user.
   *
   * @param principal The principal object representing the currently authenticated user.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Delete user account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User account successfully deleted."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @DeleteMapping
  public ResponseEntity<Void> deleteUser(@RequestParam String verificationCode,
                                         Principal principal) {
    String email = principal.getName();
    log.info("Attempting to delete user with email {}.", email);
    userService.deleteUserByEmail(email, verificationCode);
    log.info("User with email {} successfully deleted.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for resetting a user's password.
   *
   * @param resetPasswordDto The DTO containing necessary information to reset the password.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Reset user's password")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Password successfully reset."),
      @ApiResponse(responseCode = "400", description = "Invalid password or verification code."),
      @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
  })
  @PutMapping("/password-reset")
  public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
    log.info("Attempting to reset password for user with email {}.", resetPasswordDto.getEmail());
    userService.resetPassword(resetPasswordDto);
    log.info("Password for user with email {} successfully reset.", resetPasswordDto.getEmail());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
