package com.kratosgado.pms.utils;

import com.kratosgado.pms.utils.exceptions.InputValidationException;

public class Console {

  public static String getEmailInput() throws InputValidationException {
    return ConsoleMenu.getInput("Enter Email: ", ValidationUtils::isValidEmail);
  }

  public static String getPasswordInput(final String prompt) throws InputValidationException {
    return ConsoleMenu.getInput(prompt, ValidationUtils::isValidPassword);
  }

  public static String getString(final String prompt) throws InputValidationException {
    return ConsoleMenu.getInput(prompt, input -> input);
  }

  public static int getPositiveIntInput(final String prompt) throws InputValidationException {
    return ConsoleMenu.getInput(prompt, ValidationUtils::isPositive);
  }

  public static int getIntInput(final String prompt) throws InputValidationException {
    final int number = ConsoleMenu.getInput(prompt, ValidationUtils::isInt);
    return number;
  }

  public static double getDoubleInput(final String prompt) throws InputValidationException {
    final double number = ConsoleMenu.getInput(prompt, ValidationUtils::isDouble);
    return number;
  }
}
