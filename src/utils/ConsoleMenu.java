package utils;

import java.util.HashMap;
import java.util.Scanner;

import models.User;
import services.MainService;
import services.ProjectService;

public class ConsoleMenu {
  int choice;
  public static Scanner scanner = new Scanner(System.in);
  HashMap<String, User> users = Seed.seedUsers();
  ProjectService projectService = new ProjectService(Seed.seedProjects());
  public static MainService runningService = new MainService();
  boolean running = true;

  public ConsoleMenu() {
    choice = 0;
    runningService.setCurrentUser(users.get("kratos@gmail.com"));
  }

  public void run() {
    while (running) {
      runningService.displayMenu();
      System.out.println("0. Go Back");
      int choice = getChoice();
      int result = runningService.handleChoice(choice);
      if (result == -1)
        continue;
      switch (choice) {
        case 5:
          System.out.println("Exit");
          break;
        default:
          System.out.println("Invalid Choice");
      }
    }
  }

  public int getChoice() {
    System.out.print("Enter your choice: ");
    choice = scanner.nextInt();
    return choice;
  }

  public void setChoice(int choice) {
    this.choice = choice;
  }

}
