package edu.ntnu.idatt2106.sparesti.service.email;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for the EmailFriendCodeService class.
 *
 * @author Jeffrey Yaw Annor Tabiri
 */
@ExtendWith(MockitoExtension.class)
class EmailFriendCodeServiceTest {

  @InjectMocks
  EmailFriendCodeService emailFriendCodeService;

  @Mock
  private EmailServiceImpl emailService;

  @Mock
  private SharedChallengeRepository sharedChallengeRepository;

  @Mock
  private UserRepository userRepository;

  Principal principal;

  SharedChallenge challenge;

  @BeforeEach
  void setUp() {
    principal = () -> "example@guide";
    challenge = ChallengeUtility.createSharedChallengeA();
    challenge.setSharedChallengeCode(ChallengeUtility.createSharedChallengeCodeA());
  }

  @Test
  @DisplayName("Service send join code should send code")
  void service_SendJoinCode_SendsCode() {
    // Arrange
    when(sharedChallengeRepository.findById(anyLong())).thenReturn(Optional.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new User()));

    // Act
    emailFriendCodeService.sendJoinCode(principal, "example@guide", 1L);

    // Assert

    verify(emailService).sendEmail(any(EmailDetailsDto.class));
  }


  @Test
  @DisplayName("Service send join code should send code")
  void service_SendJoinCode_SendCode() {
    // Arrange
    when(sharedChallengeRepository.findById(anyLong())).thenReturn(Optional.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new User()));

    // Act
    emailFriendCodeService.sendJoinCode(principal, "example@guide", 1L);

    // Assert
    verify(emailService).sendEmail(any(EmailDetailsDto.class));
  }
}