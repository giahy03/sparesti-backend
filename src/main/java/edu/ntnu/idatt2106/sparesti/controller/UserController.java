package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.user.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.PasswordChangeDto;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  @PutMapping(path = "/first-name")
  public ResponseEntity<Void> changeFirsName(@RequestBody FirstNameChangeDto firstNameChangeDto,
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
  @PutMapping(path = "/last-name")
  public ResponseEntity<Void> changeLastName(@RequestBody LastNameChangeDto lastNameChangeDto,
                                                 Principal principal) {
    String email = principal.getName();
    log.info("Changing last name for user with email {}.", email);
    userService.editLastName(lastNameChangeDto, email);
    log.info("Last name for {} successfully updated.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
