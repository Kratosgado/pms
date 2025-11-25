package utils;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import models.User;
import services.MainService;

public class ConsoleMenu {
  public static Scanner scanner = new Scanner(System.in);
  HashMap<String, User> users = Seed.seedUsers();
  public static Stack<MainService> runningServices = new Stack<>();
  boolean running = true;

  public ConsoleMenu() {
    runningServices.add(new MainService());
    runningServices.peek().setCurrentUser(users.get("kratos@gmail.com"));
  }

  public void run() {
    while (running) {
      runningServices.peek().displayMenu();
      System.out.println("0. Go Back");
      int choice = getChoice();
      int result = runningServices.peek().handleChoice(choice);
      if (result == -1)
        continue;
      switch (result) {
        case 0:
          goBack();
          break;
        case 5:
          System.out.println("Exiting...");
          confirmExit();
          running = false;
          break;
        default:
          System.out.println("Invalid Choice");
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

  private int getChoice() {
    int choice;
    do {
      System.out.print("Enter your choice: ");
      String choiceStr = scanner.nextLine();
      try {
        choice = Integer.parseInt(choiceStr);
      } catch (Exception e) {
        System.out.println("ERROR: Input must be an integer");
        choice = -1;
      }
    } while (choice < 0);
    return choice;
  }

  private void confirmExit() {
    System.out.print("Are you sure you want to exit? (y/n): ");
    String choice = scanner.nextLine();
    if (choice.equals("y")) {
      running = false;
    }
  }

}
