package edu.ntnu.idatt2106.sparesti.service.auth;

import edu.ntnu.idatt2106.sparesti.dto.user.AuthenticationDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LoginRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.user.RegistrationDto;
import edu.ntnu.idatt2106.sparesti.exception.user.EmailAlreadyExistsException;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repositories.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.validation.validators.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that encapsulates the logic for registering new users
 * and authenticating existing users within the database.
 * It uses repositories to perform user-related operations in the database,
 * and uses spring boot security for user authentication.
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository: <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/services/user/AuthenticationService.java">...</a>
 * </p>
 *
 * @author Jeffrey Tabiri
 * @author Ramtin Samavat
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  //CRUD operations on user models.
  private final UserRepository userRepository;

  //Provides services related to JWT.
  private final JwtService jwtService;

  //Authenticates login requests.
  private final AuthenticationManager authManager;

  //Password encoder to hash passwords in a database.
  private final PasswordEncoder passwordEncoder;

  /**
   * Registers a new user based on the provided registration information from the DTO.
   *
   * @param registrationDto DTO containing user registration information.
   * @return An AuthenticationDto containing a token if registration is successful.
   * @throws EmailAlreadyExistsException If the email already exists.
   */
  public AuthenticationDto registerUser(@NonNull RegistrationDto registrationDto)
          throws EmailAlreadyExistsException {

    if (userRepository.findUserByEmailIgnoreCase(registrationDto.getEmail()).isPresent()) {
      throw new EmailAlreadyExistsException();
    }

    UserValidator.validatePassword(registrationDto.getPassword());
    UserValidator.validateEmail(registrationDto.getEmail());
    UserValidator.validateFirstName(registrationDto.getFirstName());
    UserValidator.validateLastName(registrationDto.getLastName());

    String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

    User user = User.builder()
            .password(encodedPassword)
            .email(registrationDto.getEmail())
            .firstName(registrationDto.getFirstName())
            .lastName(registrationDto.getLastName())
            .role(Role.USER)
            .build();

    userRepository.save(user);

    String token = jwtService.generateToken(user);

    return new AuthenticationDto(token);
  }

  /**
   * Login method which contains logic to authenticate a login request.
   *
   * @param loginRequestDto DTO containing user login credentials.
   * @return An AuthenticationDto containing a token if authentication is successful.
   * @throws BadCredentialsException if authentication fails due to invalid credentials.
   * @throws UserNotFoundException if the user based on email is not found in the database.
   */
  public AuthenticationDto authenticateUser(@NonNull LoginRequestDto loginRequestDto)
          throws BadCredentialsException, UsernameNotFoundException {

    authManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginRequestDto.getEmail(), loginRequestDto.getPassword()));

    User user = userRepository.findUserByEmailIgnoreCase(loginRequestDto.getEmail())
            .orElseThrow(() -> new UserNotFoundException("User with email "
                    + loginRequestDto.getEmail() + " not found"));

    String token = jwtService.generateToken(user);

    return new AuthenticationDto(token);
  }
}
