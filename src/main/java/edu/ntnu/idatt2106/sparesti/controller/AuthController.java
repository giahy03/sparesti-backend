package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.user.AuthenticationDto;
import edu.ntnu.idatt2106.sparesti.dto.user.LoginRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.user.RegistrationDto;
import edu.ntnu.idatt2106.sparesti.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling user authentication operations
 * such as registration and login.
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository:
 * <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/controller/AuthenticationController.java">...</a>
 * </p>
 *
 * @author Ramtin Samavat
 * @author Jeffrey Tabiri
 * @author Tobias Oftedal
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  /**
   * REST-endpoint to register a user.
   * If the user is registered successfully, a token is generated and returned.
   *
   * @param registrationDto DTO containing user registration information.
   * @return ResponseEntity containing a DTO with a token on success, or error on failure.
   */
  @Operation(summary = "Register a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User registered successfully",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = AuthenticationDto.class))}
      ),
      @ApiResponse(responseCode = "400", description = "Invalid input from user."),
      @ApiResponse(responseCode = "409", description = "Email already in use."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @PostMapping("/register")
  public ResponseEntity<AuthenticationDto> registerUser(
      @RequestBody RegistrationDto registrationDto) {
    log.info("Attempting to register user: {}", registrationDto.getEmail());
    AuthenticationDto authenticationDto = authService.registerUser(registrationDto);
    log.info("User {} registered successfully.", registrationDto.getEmail());
    return new ResponseEntity<>(authenticationDto, HttpStatus.CREATED);
  }

  /**
   * REST-endpoint to authenticate a user login request.
   * If the user is authenticated, a token is generated and returned.
   *
   * @param loginRequestDto DTO containing user login credentials.
   * @return ResponseEntity containing a DTO with a token on success, or UNAUTHORIZED on failure.
   */
  @Operation(summary = "Login for user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User logged in successfully",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = AuthenticationDto.class))}
      ),
      @ApiResponse(responseCode = "401", description = "User is unauthorized to log in."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @PostMapping("/login")
  public ResponseEntity<AuthenticationDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
    log.info("User {} has attempted to log in.", loginRequestDto.getEmail());
    AuthenticationDto authenticationDto = authService.authenticateUser(loginRequestDto);
    log.info("User {} successfully logged in.", loginRequestDto.getEmail());
    return new ResponseEntity<>(authenticationDto, HttpStatus.OK);
  }
}