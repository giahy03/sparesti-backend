package edu.ntnu.idatt2106.sparesti.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * Configuration class for email.
 * This class configures the email sender.
 * The email sender is configured to use the Gmail SMTP server.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {
  private static final int GMAIL_SMTP_PORT = 587;

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.username}")
  private String user;

  @Value("${spring.mail.password}")
  private String password;

  /**
   * Configures the email sender.
   *
   * @return the email sender.
   */
  @Bean
  public JavaMailSender getJavaMailSender() {

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(GMAIL_SMTP_PORT);

    mailSender.setUsername(user);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    return mailSender;
  }
}
