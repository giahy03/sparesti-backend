package edu.ntnu.idatt2106.sparesti.controller.email;

import edu.ntnu.idatt2106.sparesti.dto.EmailCodeDto;
import edu.ntnu.idatt2106.sparesti.service.email.EmailVerification;
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

    return new ResponseEntity<>("OK", HttpStatus.OK);
  }


  /**
   * Verifies the email code.
   *
   * @param emailCodeDto the email code dto.
   * @return a response entity.
   */
  @PostMapping("/verification")
  public ResponseEntity<String> verifyEmailCode(@RequestBody EmailCodeDto emailCodeDto) {
    log.info("Verifying verification email to: {}", emailCodeDto.getEmail());

    emailVerification.verifyEmailCode(emailCodeDto.getEmail(), emailCodeDto.getVerificationCode());

    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

}


