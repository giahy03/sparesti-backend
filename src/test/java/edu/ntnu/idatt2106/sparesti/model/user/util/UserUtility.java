package edu.ntnu.idatt2106.sparesti.model.user.util;

import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;

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




}
