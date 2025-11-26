
package utils;

public class Console {

  public static String getEmailInput() throws IllegalArgumentException {
    return ConsoleMenu.getInput("Enter Email: ", e -> {
      return ValidationUtils.isValidEmail(e);
    });
  }

  public static String getPasswordInput(String prompt) throws IllegalArgumentException {
    return ConsoleMenu.getInput(prompt, e -> {
      return ValidationUtils.isValidPassword(e);
    });
  }

  public static String getString(String prompt) throws IllegalArgumentException {
    return ConsoleMenu.getInput(prompt, e -> {
      return ValidationUtils.isValidName(e);
    });
  }

  public static int getPositiveIntInput(String prompt) throws IllegalArgumentException {
    return ConsoleMenu.getInput(prompt, input -> {
      return ValidationUtils.isPositive(input);
    });
  }

  public static int getIntInput(String prompt) throws IllegalArgumentException {
    int number = ConsoleMenu.getInput(prompt, input -> {
      return ValidationUtils.isInt(input);
    });
    return number;
  }

  public static double getDoubleInput(String prompt) throws IllegalArgumentException {
    double number = ConsoleMenu.getInput(prompt, input -> {
      return ValidationUtils.isDouble(input);
    });
    return number;
  }
}
