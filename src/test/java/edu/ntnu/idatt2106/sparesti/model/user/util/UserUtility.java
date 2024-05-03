package edu.ntnu.idatt2106.sparesti.model.user.util;

import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;

/**
 * Utility class that creates objects to support testing classes.
 *
 * @author Hanne-Sofie Søreide
 */

public class UserUtility {


    public static PasswordChangeDto createPasswordChangeDto() {

        return new PasswordChangeDto("abc", "123");
    }

    public static String createPasswordChangeDtoJson() {

        return  "{"
                + "\"oldPassword\":\"abc\","
                + "\"newPassword\":\"123\""
                + "}";
    }

    public static String createFirstNameChangeDtoJson() {
        return  "{"
                + "\"newFirstName\":\"Anne\""
                + "}";
    }

    public static String createLastNameChangeDtoJson() {
        return  "{"
                + "\"newLastName\":\"Larsen\""
                + "}";
    }

    public static String createIncomeChangeDtoJson() {
        return  "{"
                + "\"newIncome\":50000.0"
                + "}";
    }


    public static String createLivingStatusChangeDtoJson() {
        return  "{"
                + "\"newLivingStatus\":2"
                + "}";
    }


    public static String createUserDetailsDtoJson() {
        String livingStatus = SsbLivingStatus.fromInteger(2).getStatus();
        return  "{"
                + "\"firstName\":\"Ole\","
                + "\"lastName\":\"Hansen\","
                + "\"income\":50000.0,"
                + "\"livingStatus\":\"LivingStatus\""
                + "}";
    }

    public static UserDetailsDto createUserDetailsDto() {
        return UserDetailsDto.builder()
                .firstName("Ole")
                .lastName("Hansen")
                .income(50000.0)
                .livingStatus("LivingStatus")
                .savingPercentage(20)
                .build();
    }

    public static String createUserInfoDtoJson() {
        return "{"
                + "\"income\":50000.0,"
                + "\"livingStatus\":2"
                + "}";
    }

    public static String createResetPasswordDtoJson() {
        return "{"
                + "\"email\":\"abc@email.com\","
                + "\"emailVerificationCode\":\"CODE\","
                + "\"newPassword\":\"long-password\""
                + "}";
    }
}
