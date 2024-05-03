package edu.ntnu.idatt2106.sparesti.service.user;

import edu.ntnu.idatt2106.sparesti.dto.user.edit.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.IncomeChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LivingStatusChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;

class UserServiceUtility {

  /**
   * Creates a password change dto.
   *
   * @param oldPassword the old password
   * @param newPassword the new password
   * @return the password change dto
   */
  public static PasswordChangeDto createPasswordChangeDto(String oldPassword, String newPassword) {
    return new PasswordChangeDto(oldPassword, newPassword);
  }


  /**
   * Creates a first name change dto.
   *
   * @param firstName the new first name
   * @return the first name change dto
   */
  public static FirstNameChangeDto createFirstNameChangeDto(String firstName) {
    FirstNameChangeDto firstNameChangeDto = new FirstNameChangeDto();
    firstNameChangeDto.setNewFirstName(firstName);
    return firstNameChangeDto;
  }


  /**
   * Creates an income change dto.
   *
   * @param amount the new income
   * @return the income change dto
   */
  public static IncomeChangeDto createIncomeChangeDto(int amount) {
    IncomeChangeDto incomeChangeDto = new IncomeChangeDto();
    incomeChangeDto.setNewIncome(amount);
    return incomeChangeDto;
  }


  /**
   * Creates a last name change dto.
   *
   * @param lastName the new last name
   * @return the last name change dto
   */
  public static LastNameChangeDto createLastNameChangeDto(String lastName) {
    LastNameChangeDto lastNameChangeDto = new LastNameChangeDto();
    lastNameChangeDto.setNewLastName(lastName);
    return lastNameChangeDto;
  }


  /**
   * Creates a living status change dto.
   *
   * @param livingStatus the new living status
   * @return the living status change dto
   */
  public static LivingStatusChangeDto createLivingStatusChangeDto(int livingStatus) {
    LivingStatusChangeDto livingStatusChangeDto = new LivingStatusChangeDto();
    livingStatusChangeDto.setNewLivingStatus(livingStatus);
    return livingStatusChangeDto;
  }

}