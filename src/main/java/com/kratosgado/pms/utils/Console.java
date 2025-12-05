package com.kratosgado.pms.utils;

public class Console {

  public static String getEmailInput() throws IllegalArgumentException {
    return ConsoleMenu.getInput("Enter Email: ", e -> {
      return ValidationUtils.isValidEmail(e);
    });
  }

  public static String getPasswordInput(final String prompt) throws IllegalArgumentException {
    return ConsoleMenu.getInput(prompt, e -> {
      return ValidationUtils.isValidPassword(e);
    });
  }

  public static String getString(final String prompt) throws IllegalArgumentException {
    return ConsoleMenu.getInput(prompt, input -> {
      return input;
    });
  }

  public static int getPositiveIntInput(final String prompt) throws IllegalArgumentException {
    return ConsoleMenu.getInput(prompt, input -> {
      return ValidationUtils.isPositive(input);
    });
  }

  public static int getIntInput(final String prompt) throws IllegalArgumentException {
    final int number = ConsoleMenu.getInput(prompt, input -> {
      return ValidationUtils.isInt(input);
    });
    return number;
  }

  public static double getDoubleInput(final String prompt) throws IllegalArgumentException {
    final double number = ConsoleMenu.getInput(prompt, input -> {
      return ValidationUtils.isDouble(input);
    });
    return number;
  }
}
