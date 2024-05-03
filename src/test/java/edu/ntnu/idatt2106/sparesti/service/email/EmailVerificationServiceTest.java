package edu.ntnu.idatt2106.sparesti.service.email;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.exception.email.EmailAlreadyExistsException;
import edu.ntnu.idatt2106.sparesti.exception.email.VerificationCodeExpiredException;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.EmailCodeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
  @DisplayName("Service send code to email should save code")
  void service_SendCodeToEmail_ThenVerified() {
    emailVerificationService.sendCodeToEmail(user.getEmail());
    verify(emailCodeRepository).save(any(EmailCode.class));
  }

  @Test
  @DisplayName("Service verify email code should not throw exceptions")
  void service_VerifyEmailCode_DoesNotThrowExceptions() {
    when(emailCodeRepository.findByEmail(user.getEmail())).thenReturn(
            Optional.of(EmailVerificationUtility.createVerificationCodeA(user.getEmail())));
    assertDoesNotThrow(() -> emailVerificationService.verifyEmailCode(user.getEmail(), "123456"));
  }

  @Test
  @DisplayName("Service verify email code should throw exceptions")
  void service_VerifyEmailCode_ThrowsExceptions() {
    when(emailCodeRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(
            EmailVerificationUtility.createVerificationCodeB(user.getEmail())));
    assertThrows(VerificationCodeExpiredException.class, () ->
            emailVerificationService.verifyEmailCode(user.getEmail(), "123456"));
    assertThrows(IllegalArgumentException.class,
            () -> emailVerificationService.verifyEmailCode(user.getEmail(), "12345"));
  }

  @Test
  @DisplayName("Service verify email is available should not throw exception")
  void service_VerifyEmailIsAvailable_ThrowsException() {
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
    assertThrows(EmailAlreadyExistsException.class, () ->
            emailVerificationService.verifyEmailIsAvailable(user.getEmail()));
  }

  @Test
  @DisplayName("Service clear email code should delete all expired codes")
  void service_ClearEmailCode_VerifyDelete() {
    emailVerificationService.cleanupExpiredCodes();
    verify(emailCodeRepository).deleteAllByExpiryTimestamp(any(LocalDateTime.class));
  }

}