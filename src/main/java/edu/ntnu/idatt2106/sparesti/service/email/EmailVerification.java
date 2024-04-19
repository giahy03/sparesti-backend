package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.model.EmailCode;
import edu.ntnu.idatt2106.sparesti.repository.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Random;


/**
 * Service class for email verification.
 * This class contains methods for generating a verification token, sending
 * the token to the given email and verifying the token.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
@Service
@RequiredArgsConstructor
public class EmailVerification {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int LENGTH = 6;
  private final Random random = new Random();
  private final EmailCodeRepository emailCodeRepository;
  private final EmailServiceImpl emailService;


  /**
   * Generates a verification token.
   * The token is a random string of length 6.
   * @return the generated token.
   * //TODO might need to refactor to be more secure??? Does not implement TRUE random.
   */
  private String generateVerificationToken() {
    StringBuilder token = new StringBuilder(LENGTH);

    for (int i = 0; i < LENGTH; i++) {
      token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }

    return token.toString();
  }


  /**
   * Creates an email dto.
   *
   * @param email the email to send the verification token to.
   * @param token the verification token.
   * @return the email dto which is to be sent.
   */
  public EmailDetailsDto createEmailDto(String email, String token) {
    return EmailDetailsDto.builder()
                    .recipient(email)
                    .subject("Verification token")
                    .body("Your verification token is: " + token)
                    .build();
  }

  /**
   * Creates an email code object to prime it for permanent storage.
   *
   * @param email the email to send the verification token to.
   * @param token the verification token.
   * @return the email code dto which is to be saved.
   */
  public EmailCode createEmailCodeDto(String email, String token) {
    return EmailCode.builder()
                    .email(email)
                    .verificationCode(token)
                    .build();
  }


  /**
   * Sends the verification token to the given email.
   *
   * @param email the email to send the verification token to.
   */
  public void sendCodeToEmail(String email) {
    String token = generateVerificationToken();
    EmailDetailsDto emailDetailsDto = createEmailDto(email, token);
    EmailCode emailCode = createEmailCodeDto(email, token);
    emailCodeRepository.save(emailCode);
    emailService.sendEmail(emailDetailsDto);
  }


  /**
   * Verifies the email code.
   * @param email the email to verify.
   * @param token the token to verify.
   */
  public void verifyEmailCode(String email, String token) {
    EmailCode emailCode = emailCodeRepository.findByEmail(email);

    if (emailCode.getEmail().equals(email) && emailCode.getVerificationCode().equals(token)) {
      return;
    }

    throw new IllegalArgumentException("Invalid verification code");
  }
}