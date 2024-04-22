package edu.ntnu.idatt2106.sparesti.service.auth;

import edu.ntnu.idatt2106.sparesti.dto.user.AuthenticationDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LoginRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.user.RegistrationDto;
import edu.ntnu.idatt2106.sparesti.exception.email.EmailAlreadyExistsException;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.email.EmailVerificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for the AuthService class.
 * The AuthService class is responsible for registering new users and authenticating existing users within the database.
 *
 * @see AuthService
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 *  * <p>
 *  * The code is inspired by Ramtin Samavat's GitHub repository: <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/test/java/edu/ntnu/idatt2105/quizapp/services/user/AuthenticationServiceTest.java">...</a>
 *  * </p>
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @InjectMocks
  private AuthService authService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtService jwtService;

  @Mock
  private EmailVerificationService emailVerificationService;

  @Mock
  private AuthenticationManager authManager;

  @Mock
  private PasswordEncoder passwordEncoder;

  User user;
  @Test
  void Service_RegisterUser_AddsAUser() {
    //Arrange
    RegistrationDto registrationDto = ChallengeUtility.createRegistrationDtoA();
    String expectedPassword = "Password";
    String expectedToken = "MockToken";
    when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn(expectedPassword);
    when(jwtService.generateToken(any(User.class))).thenReturn(expectedToken);
    when(userRepository.save(any(User.class))).thenReturn(ChallengeUtility.createUserA());

    //Act
    AuthenticationDto authenticationDto = authService.registerUser(registrationDto);

    //Assert
    assertNotNull(authenticationDto.getToken());
    assertFalse(authenticationDto.getToken().isBlank());
    assertEquals(expectedToken, authenticationDto.getToken());
  }

  @Test
  void Service_AuthenticateUser_ReturnsToken() {

    //Arrange
    LoginRequestDto loginDtoA = ChallengeUtility.createLoginDtoA();
    User mockUser = ChallengeUtility.createUserA();
    String expectedToken = "MockToken";

    when(userRepository.findUserByEmailIgnoreCase("example@guide")).thenReturn(Optional.ofNullable(mockUser));
    when(jwtService.generateToken(any(User.class))).thenReturn(expectedToken);

    //Act
    AuthenticationDto authenticationDto = authService.authenticateUser(loginDtoA);

    //Assert
    assertNotNull(authenticationDto.getToken());
    assertFalse(authenticationDto.getToken().isBlank());
    assertEquals(expectedToken, authenticationDto.getToken());
  }

  @Test
  void Service_AuthenticateUser_ThrowsUserNotFoundException() {
    //Arrange
    LoginRequestDto loginDtoA = ChallengeUtility.createLoginDtoA();
    when(userRepository.findUserByEmailIgnoreCase("example@guide")).thenReturn(Optional.empty());

    //Act & Assert
    assertThrows(UserNotFoundException.class, () -> authService.authenticateUser(loginDtoA));
  }


  @Test
  void Service_RegisterUser_ThrowsEmailAlreadyExistsException() {
    //Arrange
    RegistrationDto registrationDto = ChallengeUtility.createRegistrationDtoA();
    String expectedEmail = registrationDto.getEmail();
    when(userRepository.findUserByEmailIgnoreCase(expectedEmail)).thenReturn(Optional.ofNullable(ChallengeUtility.createUserA()));
    assertThrows(EmailAlreadyExistsException.class, () -> authService.registerUser(registrationDto));

  }

}