package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;

/**
 * Interface for sending email services.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
public interface EmailService {

  /**
   * Sends an email with the provided details.
   *
   * @param emailDetailsDto the details of the email to be sent.
   */
  void sendEmail(EmailDetailsDto emailDetailsDto);
}