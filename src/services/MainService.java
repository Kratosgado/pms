package services;

import java.util.ArrayList;

import models.Project;
import models.User;
import utils.Console;
import utils.ConsoleMenu;
import utils.Seed;

public class MainService {
  String title = "MAIN MENU";
  private static User currentUser;
  ArrayList<Project> projects = Seed.seedProjects();
  ArrayList<User> users = Seed.seedUsers();

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

  protected static void setCurrentUser(User user) {
    currentUser = user;
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  private final void printCurrentUser() {
    System.out.printf("\nCurrent User: %s (%s)\n\n", currentUser.getName(), currentUser.getRole());
  }

  private User getUserByEmail(String email) {
    for (User user : users) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  public final void authenticateUser() {
    do {
      try {
        ConsoleMenu.displayHeader("AUTHENTICATION");
        String email = Console.getEmailInput();
        User user = getUserByEmail(email);
        String password = Console.getPasswordInput("Enter User Password: ");
        if (!user.getPassword().equals(password)) {
          throw new IllegalArgumentException("Invalid Password");
        }
        System.out.println("✅User switched successfully");
        setCurrentUser(user);
      } catch (Exception e) {
        ConsoleMenu.displayError(e.getMessage());
      }
    } while (currentUser == null);
  }

  public int handleChoice(int choice) {
    switch (choice) {
      case 1:
        ConsoleMenu.runningServices.add(new ProjectService(projects));
        break;
      case 2:
        ProjectService projectService = new ProjectService(projects);
        projectService.listProjects();
        projectService.askForProject();
        break;
      case 3:
        ReportService.displayReport(projects);
        break;
      case 4:
        ConsoleMenu.runningServices.add(new UserService(Seed.seedUsers()));
        break;
      default:
        return choice;
    }
    return -1;
  }
}
