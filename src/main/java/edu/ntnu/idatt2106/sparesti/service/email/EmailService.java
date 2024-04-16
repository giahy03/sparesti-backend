package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.EmailDetailsDto;

/**
 * Interface class for sending email services.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
public interface EmailService {
  String sendEmail(EmailDetailsDto emailDetailsDto);

  String sendEmailWithAttachment(EmailDetailsDto emailDetailsDto);
}