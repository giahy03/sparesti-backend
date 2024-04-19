package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.model.EmailCode;
import edu.ntnu.idatt2106.sparesti.repository.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerification {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int LENGTH = 6;
  private final Random random = new Random();
  private final EmailCodeRepository emailCodeRepository;
  private final EmailServiceImpl emailService;


  public String generateVerificationToken() {
    StringBuilder token = new StringBuilder(LENGTH);

    for (int i = 0; i < LENGTH; i++) {
      token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return token.toString();
  }

  public EmailDetailsDto createEmailDto(String email, String token) {
    return EmailDetailsDto.builder()
                    .recipient(email)
                    .subject("Verification token")
                    .body("Your verification token is: " + token)
                    .build();
  }

  public EmailCode createEmailCodeDto(String email, String token) {
    return EmailCode.builder()
                    .email(email)
                    .verificationCode(token)
                    .build();
  }

  public void sendCodeToEmail(String email) {
    String token = generateVerificationToken();
    EmailDetailsDto emailDetailsDto = createEmailDto(email, token);
    EmailCode emailCode = createEmailCodeDto(email, token);
    emailCodeRepository.save(emailCode);
    emailService.sendEmail(emailDetailsDto);
  }

  public boolean checkForRegisterEmail(String email) {
    return emailCodeRepository.findByEmail(email) != null;
  }

  public void verifyEmailCode(String email, String token) {
    EmailCode emailCode = emailCodeRepository.findByEmail(email);

    if (emailCode.getEmail().equals(email) && emailCode.getVerificationCode().equals(token)) {
      emailCodeRepository.deleteByEmail(email);
      return;
    }

  throw new IllegalArgumentException("Invalid token");
  }
}