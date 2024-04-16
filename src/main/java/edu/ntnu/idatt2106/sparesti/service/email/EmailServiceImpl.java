package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.EmailDetailsDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String sender;

  /**
   * Sends an email to the recipient.
   *
   * @param emailDetailsDto the details of the email to be sent.
   * @return a string indicating the status of the email.
   */
  @Override
  public String sendEmail(EmailDetailsDto emailDetailsDto) {

    SimpleMailMessage message = new SimpleMailMessage();

    try {
      message.setFrom(sender);
      message.setTo(emailDetailsDto.getRecipient());
      message.setText(emailDetailsDto.getBody());
      message.setSubject(emailDetailsDto.getSubject());

      mailSender.send(message);
      log.info("Email sent to: " + emailDetailsDto.getRecipient());

      return "Mail sent successfully!";

    } catch (Exception e) {
      log.error("Error sending email: ", e);
      return "Mail not sent!";
    }
  }

  @Override
  public String sendEmailWithAttachment(EmailDetailsDto emailDetailsDto) {
    return null;
  }

}
