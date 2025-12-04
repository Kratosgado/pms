package com.kratosgado.pms.utils;

import java.util.Scanner;
import java.util.function.Function;

import com.kratosgado.pms.ApplicationContext;
import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.services.MainService;

public class ConsoleMenu {
  private ApplicationContext applicationContext;
  private final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase();
  private final UserInMemoryDatabase usersDb = new UserInMemoryDatabase();
  private final TaskInMemoryDatabase tasksDb = new TaskInMemoryDatabase();

  public static Scanner scanner = new Scanner(System.in);
  boolean running = true;

  public ConsoleMenu() {
    this.applicationContext = new ApplicationContext(usersDb);
    final MainService mainService = new MainService(applicationContext, projectsDb, usersDb, tasksDb);
    applicationContext.getNavigationStack().add(mainService);
    applicationContext.authenticateUser();
  }

  /**
   * Starts the main loop of the application
   */
  public void run() {
    do {
      applicationContext.peekService().displayMenu();
      final int choice = Console.getPositiveIntInput("Enter your choice: ");
      final int result = applicationContext.peekService().handleChoice(choice);

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
          displayError("INPUT ERROR", "Invalid choice");
      }
    } while (running);

  }

  /**
   * Navigate back to the previous menu If there is only one menu, exit the
   * application
   */
  private void navigateBack() {
    if (applicationContext.getNavigationStack().size() == 1) {
      System.out.println("You can't go back anymore");
      confirmExit();
      return;
    }
    applicationContext.popService();
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
        displayError(e.getClass().getSimpleName(), e.getMessage());
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
  public final static void displayError(String name, final String error) {
    System.out.println("\n❌ " + name + ": " + error);
  }

  /**
   * Displays a success message to the user
   * 
   * @param success the success message to display to the user
   */
  public final static void displaySuccess(final String success) {
    System.out.println("\n✅ " + success);
  }

}
