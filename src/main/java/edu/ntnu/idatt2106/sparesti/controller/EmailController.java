package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.service.email.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for sending emails.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
public class EmailController {

  private final EmailServiceImpl emailService;

  @PostMapping("/send-email")
  public ResponseEntity<String> sendEmail(@RequestBody EmailDetailsDto emailDetailsDto) {
    String status = emailService.sendEmail(emailDetailsDto);
    return ResponseEntity.ok(status);
  }

}
