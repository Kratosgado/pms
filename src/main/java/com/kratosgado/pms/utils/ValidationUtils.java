
package com.kratosgado.pms.utils;

public class ValidationUtils {

  public static TaskStatus validateTaskStatus(final String status) throws IllegalArgumentException {
    switch (status) {
      case "Pending":
        return TaskStatus.PENDING;
      case "In Progress":
        return TaskStatus.IN_PROGRESS;
      case "Completed":
        return TaskStatus.COMPLETED;
    }
    throw new IllegalArgumentException("Invalid Task Status");
  }

  public static String isValidEmail(final String email) throws IllegalArgumentException {
    if (!email.contains("@"))
      throw new IllegalArgumentException("Email is invalid");
    return email;
  }

  public static String isValidPassword(final String password) throws IllegalArgumentException {
    if (password.length() < 8)
      throw new IllegalArgumentException("Password is too short");
    return password;
  }

  public static String isValidName(final String username) throws IllegalArgumentException {
    if (username.length() < 3)
      throw new IllegalArgumentException("Username is too short");
    return username;
  }

  public static int isPositive(final String number) throws IllegalArgumentException {
    final int num = isInt(number);
    if (num < 0)
      throw new IllegalArgumentException("Number must be positive");
    return num;
  }

  public static int isInt(final String number) throws IllegalArgumentException {
    if (!number.matches("[0-9]+"))
      throw new IllegalArgumentException("Number is invalid. Must be an integer");
    return Integer.parseInt(number);
  }

  public static double isDouble(final String number) throws IllegalArgumentException {
    if (!number.matches("[0-9]+\\.?[0-9]*"))
      throw new IllegalArgumentException("Number is invalid. Must be a double");
    return Double.parseDouble(number);
  }

}
