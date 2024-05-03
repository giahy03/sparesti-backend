package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailCodeExpirationDto;
import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.email.EmailVerificationDto;
import edu.ntnu.idatt2106.sparesti.service.email.EmailFriendCodeService;
import edu.ntnu.idatt2106.sparesti.service.email.EmailServiceImpl;
import edu.ntnu.idatt2106.sparesti.service.email.EmailVerificationService;
import io.github.cdimascio.dotenv.Dotenv;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling email related REST-endpoints.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @author Ramtin Samavt
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

  private final EmailServiceImpl emailService;

  private final EmailFriendCodeService emailFriendCodeService;

  private final EmailVerificationService emailVerificationService;

  private static final String ADMIN_EMAIL = Dotenv.load().get("ADMIN_EMAIL");

  /**
   * REST-endpoint for sending an email.
   *
   * @param emailDetailsDto the details of the email to be sent.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Send email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Email successfully sent."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @PostMapping("/contact")
  public ResponseEntity<Void> sendEmail(@RequestBody EmailDetailsDto emailDetailsDto) {
    log.info("Sending email to: {}", ADMIN_EMAIL);

    emailDetailsDto.setRecipient(ADMIN_EMAIL);
    emailService.sendEmail(emailDetailsDto);
    log.info("Email sent to: {}", emailDetailsDto.getRecipient());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for verifying an email verification code.
   *
   * @param emailVerificationDto the request containing the email address and verification code.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Verify email verification code")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Email successfully verified."),
      @ApiResponse(responseCode = "400", description = "Invalid verification code."),
      @ApiResponse(responseCode = "404", description = "Email not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @PostMapping("/verification/verify")
  public ResponseEntity<Void> verifyEmailCode(
      @RequestBody EmailVerificationDto emailVerificationDto) {

    log.info("Verifying email {}.", emailVerificationDto.getEmail());
    emailVerificationService.verifyEmailCode(emailVerificationDto.getEmail(),
        emailVerificationDto.getVerificationCode());
    log.info("Email {} verified.", emailVerificationDto.getEmail());
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * REST-endpoint for sending a verification email.
   *
   * @param email the email address to which the verification email will be sent.
   * @return A ResponseEntity with status OK if the operation is successful.
   */
  @Operation(summary = "Send verification email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Verification email successfully sent.",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = EmailCodeExpirationDto.class))}),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/verification/code")
  public ResponseEntity<EmailCodeExpirationDto> sendEmailCode(@RequestParam String email) {
    log.info("Sending verification email to: {}", email);
    EmailCodeExpirationDto emailCodeExpirationDto = emailVerificationService.sendCodeToEmail(email);
    log.info("Verification email sent to: {}", email);
    return new ResponseEntity<>(emailCodeExpirationDto, HttpStatus.OK);
  }

  /**
   * REST-endpoint for verifying if an email is available for use.
   *
   * @param email the email address to be verified.
   * @return A ResponseEntity with status OK if the email does not exist.
   */
  @Operation(summary = "Verify email availability")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Email is available."),
      @ApiResponse(responseCode = "409", description = "Email already in use."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/available")
  public ResponseEntity<Void> verifyIsAvailable(@RequestParam String email) {
    log.info("Verifying if email {} is available for use.", email);
    emailVerificationService.verifyEmailIsAvailable(email);
    log.info("Email {} is available for use.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for verifying if an email exists.
   *
   * @param email the email address to be verified.
   * @return A ResponseEntity with status OK if the email does not exist.
   */
  @Operation(summary = "Verify email existence")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Email exist."),
      @ApiResponse(responseCode = "404", description = "Email does not exists."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/exist")
  public ResponseEntity<Void> verifyExistence(@RequestParam String email) {
    log.info("Verifying existence of email {}.", email);
    emailVerificationService.verifyEmailExist(email);
    log.info("Email {} exist.", email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * REST-endpoint for verifying if an email exists.
   *
   * @param email the email address to be verified.
   * @return A ResponseEntity with status OK if the email does not exist.
   */
  @Operation(summary = "Send challenge join code to user via email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Request successfully processed."),
      @ApiResponse(responseCode = "404", description = "Username not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/challenge/join/{id}")
  public ResponseEntity<Void> sendJoinCode(Principal principal, @RequestParam String email,
                                           @PathVariable long id) {
    log.info("Sending join code about challenge with id: {} to: {}", id, email);
    emailFriendCodeService.sendJoinCode(principal, email, id);
    log.info("Join code about challenge with id: {} sent to: {}", id, email);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
