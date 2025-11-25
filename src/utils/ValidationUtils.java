
package utils;

public class ValidationUtils {

  public static TaskStatus validateTaskStatus(String status) throws IllegalArgumentException {
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

  public static void isValidEmail(String email) throws IllegalArgumentException {
    if (!email.contains("@"))
      throw new IllegalArgumentException("Email is invalid");
  }

  public static void isValidPassword(String password) throws IllegalArgumentException {
    if (password.length() < 8)
      throw new IllegalArgumentException("Password is too short");
  }

  public static void isValidUsername(String username) throws IllegalArgumentException {
    if (username.length() < 3)
      throw new IllegalArgumentException("Username is too short");
  }

  public static void isPositive(int number) throws IllegalArgumentException {
    if (number < 0)
      throw new IllegalArgumentException("Number must be positive");
  }

  public static int isInt(String number) throws IllegalArgumentException {
    if (!number.matches("[0-9]+"))
      throw new IllegalArgumentException("Number is invalid");
    return Integer.parseInt(number);
  }

  public static double isDouble(String number) throws IllegalArgumentException {
    if (!number.matches("[0-9]+\\.?[0-9]*"))
      throw new IllegalArgumentException("Number is invalid");
    return Double.parseDouble(number);
  }

}
