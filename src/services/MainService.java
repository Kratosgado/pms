package services;

import java.util.ArrayList;

import models.Project;
import models.User;
import utils.ConsoleMenu;
import utils.Seed;

public class MainService {
  String title = "MAIN MENU";
  User currentUser;
  ArrayList<Project> projects = Seed.seedProjects();

  public MainService() {
    // TODO: Add logic to log in

  }

  public void displayMenu() {
    ConsoleMenu.displayHeader(title);
    displayOptions();
    System.out.println("0. Go Back");
  }

  void displayOptions() {
    printCurrentUser();
    System.out.println("1. Manage Projects");
    System.out.println("2. Manage Tasks");
    System.out.println("3. View Status Reports");
    System.out.println("4. Switch User");
    System.out.println("9. Exit");
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
        ConsoleMenu.runningServices.add(new ProjectService(projects));
        break;
      case 2:
        ProjectService projectService = new ProjectService(projects);
        System.out.println(projectService.listProjects());
        projectService.askForProject();
        break;
      case 3:
        ReportService.displayReport(projects);
        break;
      case 4:
        return 4;
      default:
        return choice;
    }
    return -1;
  }
}
