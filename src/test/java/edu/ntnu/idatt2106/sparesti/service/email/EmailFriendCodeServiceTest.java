package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.SharedChallengeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
  void Service_SendJoinCode_SendsACode() {
    // Arrange
    when(sharedChallengeRepository.findById(anyLong())).thenReturn(Optional.of(challenge));
    when(userRepository.findUserByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new User()));

    // Act
    emailFriendCodeService.sendJoinCode(principal, "example@guide", 1L);

    // Assert

    verify(emailService).sendEmail(any(EmailDetailsDto.class));
  }

  @Test
    void Service_SendJoinCode_SendCode() {
        // Arrange
        when(sharedChallengeRepository.findById(anyLong())).thenReturn(Optional.of(challenge));
        when(userRepository.findUserByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new User()));

        // Act
        emailFriendCodeService.sendJoinCode(principal, "example@guide", 1L);

        // Assert
        verify(emailService).sendEmail(any(EmailDetailsDto.class));
    }

}