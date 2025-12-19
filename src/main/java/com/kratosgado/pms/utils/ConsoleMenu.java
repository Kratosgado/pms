package com.kratosgado.pms.utils;

import java.util.Scanner;
import java.util.function.Function;

import com.kratosgado.pms.interfaces.ServiceFactory;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;
import com.kratosgado.pms.utils.exceptions.InputValidationException;

public class ConsoleMenu {
  private NavigationManager navigationManager;
  private final ServiceFactory serviceFactory;

  private static Scanner scanner = new Scanner(System.in);
  boolean running = true;

  public ConsoleMenu(NavigationManager navigationManager, AuthManager authManager, ServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
    this.navigationManager = navigationManager;
    authManager.authenticateUser();
  }

  /**
   * # Starts the main loop of the application <br>
   * -1 - Global value for handled choice <br>
   * 0 - Global choice for going back <br>
   * 9 - Global choice for exiting the application <br>
   * 100 - Global choice for saving data <br>
   * 101 - Global choice for loading data <br>
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
        case 100:
          serviceFactory.saveData();
          break;
        case 101:
          serviceFactory.loadData();
          break;
        default:
          CustomUtils.displayError(new InputValidationException());
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
   * @param prompt    the text to display to the user
   * @param validator the validation function to apply to the user's input
   * @return
   */
  public static <T> T getInput(final String prompt, final Function<String, T> validator) {
    T input;
    do {
      System.out.print(prompt);
      final String choiceStr = scanner.nextLine().trim();
      try {
        input = validator.apply(choiceStr);
      } catch (InputValidationException e) {
        CustomUtils.displayError(e);
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
      serviceFactory.saveData();
      running = false;
    }
  }

}
