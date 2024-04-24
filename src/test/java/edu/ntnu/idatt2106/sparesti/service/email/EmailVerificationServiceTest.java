package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.exception.email.EmailAlreadyExistsException;
import edu.ntnu.idatt2106.sparesti.exception.email.VerificationCodeExpiredException;
import edu.ntnu.idatt2106.sparesti.model.EmailCode;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.EmailCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A test class for the EmailVerificationService class.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see EmailVerificationService
 */
@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {
  @InjectMocks
  EmailVerificationService emailVerificationService;

  @Mock
  EmailCodeRepository emailCodeRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  EmailServiceImpl emailService;

  User user;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUserA();
  }

  @Test
  void Service_SendCodeToEmail_ThenVerified() {
    emailVerificationService.sendCodeToEmail(user.getEmail());
    verify(emailCodeRepository).save(any(EmailCode.class));
  }

  @Test
  void Service_VerifyEmailCode_DoesNotThrowExceptions() {
    when(emailCodeRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(EmailVerificationUtility.createVerificationCodeA(user.getEmail())));
    assertDoesNotThrow(() -> emailVerificationService.verifyEmailCode(user.getEmail(), "123456"));
  }

  @Test
  void Service_VerifyEmailCode_ThrowsExceptions() {
    when(emailCodeRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(EmailVerificationUtility.createVerificationCodeB(user.getEmail())));
    assertThrows(VerificationCodeExpiredException.class, () -> emailVerificationService.verifyEmailCode(user.getEmail(), "123456"));
    assertThrows(IllegalArgumentException.class, () -> emailVerificationService.verifyEmailCode(user.getEmail(), "12345"));
  }

  @Test
  void Service_VerifyEmailExist_ThrowsException() {
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
    assertThrows(EmailAlreadyExistsException.class, () -> emailVerificationService.verifyEmailExist(user.getEmail()));
  }

  @Test
  void Service_ClearEmailCode_VerifyDelete() {
    emailVerificationService.cleanupExpiredCodes();
    verify(emailCodeRepository).deleteAllByExpiryTimestamp(any(LocalDateTime.class));
  }

}