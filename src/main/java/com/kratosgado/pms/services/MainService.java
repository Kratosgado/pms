package com.kratosgado.pms.services;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;

public class MainService {
  String title = "MAIN MENU";
  private static User currentUser;
  final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase();
  final UserInMemoryDatabase usersDb = new UserInMemoryDatabase();

  public final void displayMenu() {
    ConsoleMenu.displayHeader(title);
    displayOptions();
    System.out.println("9. Exit");
    System.out.println("0. Go Back");
  }

  protected void displayOptions() {
    printCurrentUser();
    System.out.println("1. Manage Projects");
    System.out.println("2. Manage Tasks");
    System.out.println("3. View Status Reports");
    System.out.println("4. Manage Users");
  }

  protected static void setCurrentUser(final User user) {
    currentUser = user;
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  private final void printCurrentUser() {
    System.out.printf("\nCurrent User: %s (%s)\n\n", currentUser.getName(), currentUser.getRole());
  }

  public final void authenticateUser() {
    do {
      try {
        ConsoleMenu.displayHeader("AUTHENTICATION");
        final String email = Console.getEmailInput();
        final User user = usersDb.getByEmail(email);
        final String password = Console.getPasswordInput("Enter User Password: ");
        if (!user.getPassword().equals(password)) {
          throw new IllegalArgumentException("Invalid Password");
        }
        ConsoleMenu.displaySuccess("User logged in");
        setCurrentUser(user);
      } catch (final Exception e) {
        ConsoleMenu.displayError(e.getMessage());
      }
    } while (currentUser == null);
  }

  public int handleChoice(final int choice) {
    switch (choice) {
      case 1:
        ConsoleMenu.runningServices.add(new ProjectService(projectsDb));
        break;
      case 2:
        final ProjectService projectService = new ProjectService(projectsDb);
        projectService.listProjects();
        projectService.askForProject();
        break;
      case 3:
        ReportService.displayReport(projectsDb.getAll());
        break;
      case 4:
        ConsoleMenu.runningServices.add(new UserService(usersDb));
        break;
      default:
        return choice;
    }
    return -1;
  }
}
