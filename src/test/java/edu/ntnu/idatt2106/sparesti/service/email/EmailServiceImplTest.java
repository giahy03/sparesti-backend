package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * A test class for the EmailServiceImpl class.
 *
 * @version 1.0
 * @see EmailService
 * @author Jeffrey Yaw Annor Tabiri
 */
@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

  @InjectMocks
  EmailServiceImpl emailService;

  @Mock
  JavaMailSender javaMailSender;

  EmailDetailsDto emailDetailsDto;

  @BeforeEach
  void setUp() {
    emailDetailsDto = EmailVerificationUtility.createEmailDetailsDto();
  }

  @Test
  void Service_SendEmail_VerifyMailSend() {
    emailService.sendEmail(emailDetailsDto);
    Mockito.verify(javaMailSender).send(any(SimpleMailMessage.class));
  }

}