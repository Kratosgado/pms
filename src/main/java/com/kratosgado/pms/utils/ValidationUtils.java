package com.kratosgado.pms.utils;

import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.enums.TaskStatus;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.InputValidationException;

public class ValidationUtils {

  public static TaskStatus validateTaskStatus(final String status) throws InputValidationException {
    switch (status) {
      case "Pending":
        return TaskStatus.PENDING;
      case "In Progress":
        return TaskStatus.IN_PROGRESS;
      case "Completed":
        return TaskStatus.COMPLETED;
    }
    throw new InputValidationException("Invalid Task Status");
  }

  public static UserRole validateUserRole(final String role) throws InputValidationException {
    switch (role) {
      case "Admin":
        return UserRole.ADMIN;
      case "Regular":
        return UserRole.REGULAR;
    }
    throw new InputValidationException("Invalid User Role");
  }

  public static ProjectType validateProjectType(final String type) throws InputValidationException {
    switch (type) {
      case "Software":
        return ProjectType.SOFTWARE;
      case "Hardware":
        return ProjectType.HARDWARE;
    }
    throw new InputValidationException("Invalid User Role");
  }

  public static String isValidEmail(final String email) throws InputValidationException {
    if (!email.contains("@"))
      throw new InputValidationException("Email is invalid");
    return email;
  }

  public static String isValidPassword(final String password) throws InputValidationException {
    if (password.length() < 8)
      throw new InputValidationException("Password is too short");
    return password;
  }

  public static String isValidName(final String username) throws InputValidationException {
    if (username.length() < 3)
      throw new InputValidationException("Username is too short");
    return username;
  }

  public static int isPositive(final String number) throws InputValidationException {
    final int num = isInt(number);
    if (num < 0)
      throw new InputValidationException("Number must be positive");
    return num;
  }

  public static int isInt(final String number) throws InputValidationException {
    if (!number.matches("[0-9]+"))
      throw new InputValidationException("Number is invalid. Must be an integer");
    return Integer.parseInt(number);
  }

  public static double isDouble(final String number) throws InputValidationException {
    if (!number.matches("[0-9]+\\.?[0-9]*"))
      throw new InputValidationException("Number is invalid. Must be a double");
    return Double.parseDouble(number);
  }

}
