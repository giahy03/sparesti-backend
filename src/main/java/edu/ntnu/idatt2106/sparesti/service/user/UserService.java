package edu.ntnu.idatt2106.sparesti.service.user;

import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.IncomeChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LivingStatusChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.mapper.UserInfoMapper;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repositories.user.UserInfoRepository;
import edu.ntnu.idatt2106.sparesti.repositories.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.validation.validators.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that encapsulates the logic for handling user-related operations.
 * It uses the {@link UserRepository} and  to perform the operations in the database.
 *
 * @author Ramtin Samavat
 * @version  1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

  //CRUD operations on User models.
  private final UserRepository userRepository;

  //CRUD operations on UserInfo models.
  private final UserInfoRepository userInfoRepository;

  //Password encoder to hash passwords in a database.
  private final PasswordEncoder passwordEncoder;

  //Map UserInfoDto to UserInfo entity.
  private final UserInfoMapper userInfoMapper;

  /**
   * The method edits the password of the user.
   *
   * @param passwordChangeDto The DTO containing old and new password.
   * @param email The email of the user.
   */
  public void editPassword(@NonNull PasswordChangeDto passwordChangeDto, @NonNull String email) {
    User user = findUser(email);

    UserValidator.validatePasswordChange(user.getPassword(),
            passwordChangeDto.getOldPassword(),
            passwordChangeDto.getNewPassword());

    user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));

    userRepository.save(user);
  }

  /**
   * The method edits the first name of the user.
   *
   * @param firstNameChangeDto The DTO containing new first name.
   * @param email The email of the user.
   */
  public void editFistName(@NonNull FirstNameChangeDto firstNameChangeDto, @NonNull String email) {
    User user = findUser(email);

    UserValidator.validateFirstName(firstNameChangeDto.getNewFirstName());

    user.setFirstName(firstNameChangeDto.getNewFirstName());

    userRepository.save(user);
  }

  /**
   * The method edits the last name of the user.
   *
   * @param lastNameChangeDto The DTO containing new last name.
   * @param email The email of the user.
   */
  public void editLastName(@NonNull LastNameChangeDto lastNameChangeDto, @NonNull String email) {
    User user = findUser(email);

    UserValidator.validateFirstName(lastNameChangeDto.getNewLastName());

    user.setLastName(lastNameChangeDto.getNewLastName());

    userRepository.save(user);
  }

  /**
   * The method edits the user's income.
   *
   * @param incomeChangeDto The DTO containing new income.
   * @param email The email of the user.
   */
  public void editIncome(@NonNull IncomeChangeDto incomeChangeDto, @NonNull String email) {
    User user = findUser(email);

    user.getUserInfo().setIncome(incomeChangeDto.getNewIncome());

    userRepository.save(user);
  }

  /**
   * The method edits the last name of the user.
   *
   * @param livingStatusChangeDto The DTO containing new last name.
   * @param email The email of the user.
   */
  public void editLivingStatus(@NonNull LivingStatusChangeDto livingStatusChangeDto,
                               @NonNull String email) {

    User user = findUser(email);

    SsbLivingStatus newLivingStatus = SsbLivingStatus.fromInteger(
            livingStatusChangeDto.getNewLivingStatus());

    user.getUserInfo().setLivingStatus(newLivingStatus);

    userRepository.save(user);
  }

  /**
   * The method retrieves the user's details for the specified email.
   *
   * @param email The email of the user to retrieve the information for.
   * @return The UserDetailsDto containing user details.
   */
  public UserDetailsDto getUserDetails(@NonNull String email) {
    User user = findUser(email);
    return new UserDetailsDto(user.getFirstName(), user.getLastName());
  }

  /**
   * The method adds additional information about the user.
   *
   * @param userInfoDto The DTO containing user additional information.
   * @param email The email of the user.
   */
  public void addUserInfo(@NonNull UserInfoDto userInfoDto, @NonNull String email) {
    User user = findUser(email);

    UserInfo userInfo = userInfoMapper.toUserInfo(userInfoDto);

    SsbLivingStatus livingStatus = SsbLivingStatus.fromInteger(userInfoDto.getLivingStatus());

    userInfo.setLivingStatus(livingStatus);
    userInfo.setUser(user);

    userInfoRepository.save(userInfo);
  }

  /**
   * The method finds a user in the database by email.
   *
   * @param email The email of the user to find.
   * @return The user with the given email.
   * @throws UserNotFoundException If the user with the given email is not found.
   */
  private User findUser(@NonNull String email) throws UserNotFoundException {
    return userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
            new UserNotFoundException("User with email " + email + " not found."));
  }
}
