package services;

import models.User;
import utils.ConsoleMenu;
import utils.Seed;

public class MainService {
  String title = "Main Menu";
  User currentUser;

  public void displayMenu() {
    printTitle();
    printCurrentUser();
    System.out.println("1. Manage Projects");
    System.out.println("2. Manage Tasks");
    System.out.println("3. View Status Reports");
    System.out.println("4. Switch User");
    System.out.println("5. Exit");
  }

  public void setCurrentUser(User user) {
    currentUser = user;
  }

  private void printTitle() {
    System.out.println("||================================================================================||");
    System.out.printf("|| Project Management System:  %s %s||\n", title, " ".repeat(50 - title.length()));
    System.out.println("||================================================================================||");
  }

  private void printCurrentUser() {
    System.out.printf("\nCurrent User: %s (%s)\n\n", currentUser.getName(), currentUser.getRole());
  }

  public int handleChoice(int choice) {
    switch (choice) {
      case 1:
        ConsoleMenu.runningService = new ProjectService(Seed.seedProjects());
        break;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      default:
        System.out.println("Invalid Choice");
    }
    return -1;
  }
}
