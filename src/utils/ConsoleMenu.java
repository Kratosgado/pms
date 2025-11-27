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
    MainService mainService = new MainService();
    runningServices.add(mainService);
    mainService.authenticateUser();
  }

  public void run() {
    while (running) {
      runningServices.peek().displayMenu();
      int choice = Console.getPositiveIntInput("Enter your choice: ");
      int result = runningServices.peek().handleChoice(choice);
      if (result == -1)
        continue;
      switch (result) {
        case 0:
          goBack();
          break;
        case 9:
          confirmExit();
          break;
        default:
          displayError("Invalid Choice");
      }
    }
  }

  private void goBack() {
    if (runningServices.size() == 1) {
      System.out.println("You can't go back anymore");
      confirmExit();
      return;
    }
    runningServices.removeLast();
  }

  public static <T> T getInput(String prompt, Function<String, T> function) {
    T input;
    do {
      System.out.print(prompt);
      String choiceStr = scanner.nextLine().trim();
      try {
        input = function.apply(choiceStr);
      } catch (Exception e) {
        displayError(e.getMessage());
        input = null;
      }
    } while (input == null);
    return input;
  }

  private void confirmExit() {
    System.out.print("Are you sure you want to exit? (y/n): ");
    String choice = scanner.nextLine();
    if (choice.equals("y")) {
      running = false;
    }
  }

  public final static void displayError(String error) {
    System.out.println("\n❌ ERROR: " + error);
  }

  public final static void displaySuccess(String success) {
    System.out.println("\n✅ " + success);
  }

  public final static void displayHeader(String title) {
    int halfLength = title.length() / 2;
    System.out.println("\n||=====================================================================================");
    System.out.printf("|| %s %s %s||\n", " ".repeat(40 - halfLength), title,
        " ".repeat(40 - halfLength));
    System.out.println("||=====================================================================================\n");
  }

  public final static void requireAdmin() {
    if (!MainService.getCurrentUser().isAdmin()) {
      throw new IllegalArgumentException("Only Admin Users can perform this action");
    }
  }

}
