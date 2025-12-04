package com.kratosgado.pms.utils;

import java.util.Scanner;
import java.util.function.Function;

import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;

public class ConsoleMenu {
  private NavigationManager navigationManager;

  private static Scanner scanner = new Scanner(System.in);
  boolean running = true;

  public ConsoleMenu(NavigationManager navigationManager, AuthManager authManager) {
    this.navigationManager = navigationManager;
    authManager.authenticateUser();
  }

  /**
   * # Starts the main loop of the application
   * 1 - Global value for handled choice
   * 0 - Global choice for going back
   * 9 - Global choice for exiting the application
   */
  public void run() {
    do {
      navigationManager.peekService().displayMenu();
      final int choice = Console.getPositiveIntInput("Enter your choice: ");
      final int result = navigationManager.peekService().handleChoice(choice);

      switch (result) {
        case -1:
          break;
        case 0:
          navigateBack();
          break;
        case 9:
          confirmExit();
          break;
        default:
          CustomUtils.displayError("INPUT ERROR", "Invalid choice");
      }
    } while (running);

  }

  /**
   * Navigate back to the previous menu If there is only one menu, exit the
   * application
   */
  private void navigateBack() {
    if (!navigationManager.canNavigateBack()) {
      System.out.println("You can't go back anymore");
      confirmExit();
      return;
    }
    navigationManager.popService();
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
        CustomUtils.displayError(e.getClass().getSimpleName(), e.getMessage());
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

}
