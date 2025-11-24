package utils;

import java.util.Scanner;

public class ConsoleMenu {
  int choice;
  String title = "Main Menu";
  Scanner scanner = new Scanner(System.in);

  public ConsoleMenu() {
    choice = 0;
  }

  public int getChoice() {
    System.out.print("Enter your choice: ");
    choice = scanner.nextInt();
    return choice;
  }

  public void setChoice(int choice) {
    this.choice = choice;
  }

  public void setTitle(String title) {
    this.title = title;
    this.printMenu();

  }

  public void printMenu() {
    this.printTitle();
    System.out.println("1. Add Project");
    System.out.println("2. View Projects");
    System.out.println("3. Add Task");
    System.out.println("4. View Tasks");
    System.out.println("5. Exit");
  }

  public void printTitle() {
    System.out.println("||================================================================================||");
    System.out.printf("|| Project Management System:  %s %s||\n", title, " ".repeat(50 - title.length()));
    System.out.println("||================================================================================||");
  }
}
