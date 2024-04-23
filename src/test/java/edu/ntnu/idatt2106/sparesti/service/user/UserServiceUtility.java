package edu.ntnu.idatt2106.sparesti.service.user;

import edu.ntnu.idatt2106.sparesti.dto.user.edit.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.IncomeChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LivingStatusChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;

class UserServiceUtility {

  public static PasswordChangeDto createPasswordChangeDto(String oldPassword, String newPassword) {
    return new PasswordChangeDto(oldPassword, newPassword);
  }

  public static FirstNameChangeDto createFirstNameChangeDto(String firstName) {
    FirstNameChangeDto firstNameChangeDto = new FirstNameChangeDto();
    firstNameChangeDto.setNewFirstName(firstName);
    return firstNameChangeDto;
  }

  public static IncomeChangeDto createIncomeChangeDto(int amount) {
    IncomeChangeDto incomeChangeDto = new IncomeChangeDto();
    incomeChangeDto.setNewIncome(amount);
    return incomeChangeDto;
  }
  public static LastNameChangeDto createLastNameChangeDto(String lastName) {
    LastNameChangeDto lastNameChangeDto = new LastNameChangeDto();
    lastNameChangeDto.setNewLastName(lastName);
    return lastNameChangeDto;
  }

  public static LivingStatusChangeDto createLivingStatusChangeDto(int livingStatus) {
    LivingStatusChangeDto livingStatusChangeDto = new LivingStatusChangeDto();
    livingStatusChangeDto.setNewLivingStatus(livingStatus);
    return livingStatusChangeDto;
  }






}