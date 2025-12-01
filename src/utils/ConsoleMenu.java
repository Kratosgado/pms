package utils;

import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import services.MainService;

public class ConsoleMenu {
  public static Scanner scanner = new Scanner(System.in);
  public static Stack<MainService> runningServices = new Stack<>();
  boolean running = true;

  public ConsoleMenu() {
    final MainService mainService = new MainService();
    runningServices.add(mainService);
    mainService.authenticateUser();
  }

  /**
   * Starts the main loop of the application
   */
  public void run() {
    do {
      runningServices.peek().displayMenu();
      final int choice = Console.getPositiveIntInput("Enter your choice: ");
      final int result = runningServices.peek().handleChoice(choice);

      switch (result) {
        case -1: // Global value for handled choice
          break;
        case 0: // Global choice for going back
          navigateBack();
          break;
        case 9: // Global choice for exiting the application
          confirmExit();
          break;
        default:
          displayError("Invalid Choice");
      }
    } while (running);

  }

  /**
   * Navigate back to the previous menu If there is only one menu, exit the
   * application
   */
  private void navigateBack() {
    if (runningServices.size() == 1) {
      System.out.println("You can't go back anymore");
      confirmExit();
      return;
    }
    runningServices.removeLast();
  }

  /**
   * @param <T>
   * @param prompt   the text to display to the user
   * @param function the validation function to apply to the user's input
   * @return
   */
  public static <T> T getInput(final String prompt, final Function<String, T> function) {
    T input;
    do {
      System.out.print(prompt);
      final String choiceStr = scanner.nextLine().trim();
      try {
        input = function.apply(choiceStr);
      } catch (final Exception e) {
        displayError(e.getMessage());
        input = null;
      }
    } while (input == null);
    return input;
  }

  /**
   * Asks the user if they are sure they want to exit the application
   */
  private void confirmExit() {
    System.out.print("Are you sure you want to exit? (y/n): ");
    final String choice = scanner.nextLine();
    if (choice.equals("y")) {
      running = false;
    }
  }

  /**
   * Displays an error message to the user
   * 
   * @param error the error message to display to the user
   */
  public final static void displayError(final String error) {
    System.out.println("\n❌ ERROR: " + error);
  }

  /**
   * Displays a success message to the user
   * 
   * @param success the success message to display to the user
   */
  public final static void displaySuccess(final String success) {
    System.out.println("\n✅ " + success);
  }

  /**
   * Appends a table header to a string builder
   * 
   * @param stringBuilder the string builder to append the table header to
   * @param title         the title of the table
   */
  public final static void appendTableHeader(final StringBuilder stringBuilder, final String title) {
    stringBuilder.append("||").append("-".repeat(CustomUtils.UI_MAX_WIDTH)).append("||").append("\n");
    stringBuilder.append(title).append("\n");
    stringBuilder.append("||").append("-".repeat(CustomUtils.UI_MAX_WIDTH)).append("||").append("\n");
  }

  /**
   * Displays a header to the user
   * 
   * @param title the title of the header
   */
  public final static void displayHeader(final String title) {
    final int halfLength = title.length() % 2 == 0 ? title.length() / 2 : title.length() / 2 + 1;
    System.out.println("\n" + "=".repeat(CustomUtils.UI_MAX_WIDTH));
    System.out.printf("|| %s %s %s||\n", " ".repeat(45 - halfLength), title,
        " ".repeat(48 - halfLength));
    System.out.println("=".repeat(CustomUtils.UI_MAX_WIDTH));
  }

  /**
   * Verifies that the user is an admin
   */
  public final static void requireAdmin() {
    if (!MainService.getCurrentUser().isAdmin()) {
      throw new IllegalArgumentException("Only Admin Users can perform this action");
    }
  }

}
