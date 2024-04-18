package edu.ntnu.idatt2106.sparesti.controller.email;

import edu.ntnu.idatt2106.sparesti.dto.EmailCodeDto;
import edu.ntnu.idatt2106.sparesti.dto.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.service.email.EmailServiceImpl;
import edu.ntnu.idatt2106.sparesti.service.email.EmailVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for sending emails.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@Slf4j
public class VerificationController {

    private final EmailVerification emailVerification;

    @PostMapping("/verification")
    public ResponseEntity<String> sendVerification(@RequestBody EmailCodeDto emailCodeDto) {
      log.info("Sending verification email to: " + emailCodeDto.getRegisterEmail());

      emailVerification

      return new ResponseEntity<>("OK", HttpStatus.OK);
    }

  @GetMapping("/verification")
  public ResponseEntity<String> verifyEmailCode(@RequestBody EmailCodeDto emailCodeDto) {

      log.info("Verifying email code for: " + emailCodeDto.getRegisterEmail());

    return new ResponseEntity<>("OK", HttpStatus.OK);
  }



}


