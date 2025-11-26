package services;

import models.User;
import utils.ConsoleMenu;
import utils.Seed;

public class MainService {
  String title = "MAIN MENU";
  User currentUser;

  public void displayMenu() {
    ConsoleMenu.displayHeader(title);
    displayOptions();
  }

  void displayOptions() {
    printCurrentUser();
    System.out.println("1. Manage Projects");
    System.out.println("2. Manage Tasks");
    System.out.println("3. View Status Reports");
    System.out.println("4. Switch User");
    System.out.println("5. Exit");
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setCurrentUser(User user) {
    currentUser = user;
  }

  private void printCurrentUser() {
    System.out.printf("\nCurrent User: %s (%s)\n\n", currentUser.getName(), currentUser.getRole());
  }

  public int handleChoice(int choice) {
    switch (choice) {
      case 1:
        ConsoleMenu.runningServices.add(new ProjectService(Seed.seedProjects()));
        break;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      default:
        return choice;
    }
    return -1;
  }
}
