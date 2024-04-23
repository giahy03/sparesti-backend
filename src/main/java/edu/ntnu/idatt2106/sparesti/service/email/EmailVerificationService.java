package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.exception.email.EmailAlreadyExistsException;
import edu.ntnu.idatt2106.sparesti.exception.email.VerificationCodeExpiredException;
import edu.ntnu.idatt2106.sparesti.model.EmailCode;
import edu.ntnu.idatt2106.sparesti.repository.EmailCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for email verification.
 * This class contains methods for generating a verification token, sending
 * the token to the given email and verifying the token.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @author Ramtin Samavat
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private static final int LENGTH = 6;

  private static final SecureRandom random = new SecureRandom();

  private final EmailCodeRepository emailCodeRepository;

  private final EmailServiceImpl emailService;

  private final UserRepository userRepository;

  /**
   * Generates a verification token.
   * The token is a random string of length 6.
   *
   * @return the generated token.
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
   * @param email the email to send the verification code to.
   * @param code  the verification code.
   * @return the email dto which is to be sent.
   */
  public EmailDetailsDto buildEmailDto(String email, String code) {
    return EmailDetailsDto.builder()
        .recipient(email)
        .subject("Verification code")
        .body("Your verification code is: " + code)
        .build();
  }

  /**
   * Creates an email code object to prime it for permanent storage.
   *
   * @param email the email to send the verification code to.
   * @param code  the verification code.
   * @return the email code dto, which is to be saved.
   */
  public EmailCode buildEmailCode(String email, String code) {
    return EmailCode.builder()
        .email(email)
        .verificationCode(code)
        .expiryTimestamp(LocalDateTime.now().plusMinutes(15))
        .build();
  }

  /**
   * Sends the verification token to the given email.
   *
   * @param email the email to send the verification token to.
   */
  public void sendCodeToEmail(String email) {
    String token = generateVerificationToken();
    EmailDetailsDto emailDetailsDto = buildEmailDto(email, token);
    EmailCode emailCode = buildEmailCode(email, token);

    Optional<EmailCode> existingEmailCode = emailCodeRepository.findByEmail(email);
    existingEmailCode.ifPresent(emailCodeRepository::delete);

    emailCodeRepository.save(emailCode);
    emailService.sendEmail(emailDetailsDto);
  }

  /**
   * Verifies the email code.
   *
   * @param email the email to verify.
   * @param code  the code to verify.
   * @throws NoSuchElementException If the EmailCode entity is not found.
   */
  public void verifyEmailCode(String email, String code) {

    EmailCode emailCode = emailCodeRepository.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Email code not found."));

    if (!emailCode.getVerificationCode().equals(code)) {
      throw new IllegalArgumentException("Invalid verification code.");
    }

    if (emailCode.getExpiryTimestamp().isBefore(LocalDateTime.now())) {
      throw new VerificationCodeExpiredException();
    }
  }

  /**
   * Verifies if the provided email already exists in the database.
   *
   * @param email the email address to be verified.
   * @throws EmailAlreadyExistsException If the email already exists.
   */
  public void verifyEmailExist(String email) {
    if (userRepository.findUserByEmailIgnoreCase(email).isPresent()) {
      throw new EmailAlreadyExistsException();
    }
  }

  /**
   * The method cleans up expired EmailCode entities in the database.
   */
  public void cleanupExpiredCodes() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    emailCodeRepository.deleteAllByExpiryTimestamp(currentDateTime);
  }
}