package edu.ntnu.idatt2106.sparesti.controller.email;

import edu.ntnu.idatt2106.sparesti.dto.verification.response.EmailCodeRequestDto;
import edu.ntnu.idatt2106.sparesti.service.email.EmailVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for sending emails.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email-verification")
@Slf4j
public class VerificationController {

  private final EmailVerification emailVerification;

  /**
   * Sends the email code.
   *
   * @param email the email.
   * @return a response entity.
   */
  @GetMapping("/email")
  public ResponseEntity<String> getEmailCode(@RequestParam String email) {
    log.info("Sending verification email to: {}", email);

    emailVerification.sendCodeToEmail(email);

    return new ResponseEntity<>("Email sent to user: " + email, HttpStatus.OK);
  }


  /**
   * Verifies the email code.
   *
   * @param emailCodeRequestDto the email code dto.
   * @return a response entity.
   */
  @PostMapping("/verification")
  public ResponseEntity<String> verifyEmailCode(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
    log.info("Verifying verification email to: {}", emailCodeRequestDto.getEmail());

    emailVerification.verifyEmailCode(emailCodeRequestDto.getEmail(), emailCodeRequestDto.getVerificationCode());

    return new ResponseEntity<>("Verified user with email: " + emailCodeRequestDto.getEmail(), HttpStatus.OK);
  }

}


