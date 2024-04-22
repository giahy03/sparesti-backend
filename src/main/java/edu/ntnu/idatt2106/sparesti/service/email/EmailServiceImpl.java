package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service implementation that encapsulates logic for sending emails.
 * It extends {@link EmailService} and uses {@link JavaMailSender} to send emails via SMTP.
 *
 * @author Jeff Tabiri
 * @author Ramtin Samavart
 * @version 1.0
 * @see EmailService
 * @see JavaMailSender
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String sender;

  /**
   * The method sends an email to the recipient.
   *
   * @param emailDetailsDto the details of the email to be sent.
   * @throws MailException if there is an error sending the email.
   */
  @Override
  public void sendEmail(@NonNull EmailDetailsDto emailDetailsDto) throws MailException {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(sender);
    message.setTo(emailDetailsDto.getRecipient());
    message.setText(emailDetailsDto.getBody());
    message.setSubject(emailDetailsDto.getSubject());

    mailSender.send(message);
  }
}
