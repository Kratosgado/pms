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

  public void run() {
    do {
      runningServices.peek().displayMenu();
      final int choice = Console.getPositiveIntInput("Enter your choice: ");
      final int result = runningServices.peek().handleChoice(choice);

      switch (result) {
        case -1:
          break;
        case 0:
          goBack();
          break;
        case 9:
          confirmExit();
          break;
        default:
          displayError("Invalid Choice");
      }
    } while (running);

  }

  private void goBack() {
    if (runningServices.size() == 1) {
      System.out.println("You can't go back anymore");
      confirmExit();
      return;
    }
    runningServices.removeLast();
  }

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

  private void confirmExit() {
    System.out.print("Are you sure you want to exit? (y/n): ");
    final String choice = scanner.nextLine();
    if (choice.equals("y")) {
      running = false;
    }
  }

  public final static void displayError(final String error) {
    System.out.println("\n❌ ERROR: " + error);
  }

  public final static void displaySuccess(final String success) {
    System.out.println("\n✅ " + success);
  }

  public final static void appendTableHeader(final StringBuilder stringBuilder, final String title) {
    stringBuilder.append("||").append("-".repeat(CustomUtils.UI_MAX_WIDTH)).append("||").append("\n");
    stringBuilder.append(title).append("\n");
    stringBuilder.append("||").append("-".repeat(CustomUtils.UI_MAX_WIDTH)).append("||").append("\n");
  }

  public final static void displayHeader(final String title) {
    final int halfLength = title.length() % 2 == 0 ? title.length() / 2 : title.length() / 2 + 1;
    System.out.println("\n" + "=".repeat(CustomUtils.UI_MAX_WIDTH));
    System.out.printf("|| %s %s %s||\n", " ".repeat(45 - halfLength), title,
        " ".repeat(48 - halfLength));
    System.out.println("=".repeat(CustomUtils.UI_MAX_WIDTH));
  }

  public final static void requireAdmin() {
    if (!MainService.getCurrentUser().isAdmin()) {
      throw new IllegalArgumentException("Only Admin Users can perform this action");
    }
  }

}
