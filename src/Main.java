import java.util.HashMap;

import models.Project;
import models.User;
import utils.ConsoleMenu;
import utils.Seed;

public class Main {

  public static void main(String[] args) {
    HashMap<String, User> users = Seed.seedUsers();
    HashMap<String, Project> projects = Seed.seedProjects();
    // ProjectService projectService = new ProjectService(users);
    // ReportService reportService = new ReportService(users);
    // TaskService taskService = new TaskService(users);
    ConsoleMenu menu = new ConsoleMenu();
    menu.printMenu();
    int choice = menu.getChoice();

    while (choice != 5) {
      switch (choice) {
        case 1:
          menu.setTitle("Add Project");
          break;
        case 2:
          projects.toString();
          System.out.println();
          break;
        case 3:
          System.out.println("Add Task");
          break;
        case 4:
          users.toString();
          System.out.println();
          break;
        case 5:
          System.out.println("Exit");
          break;
        default:
          System.out.println("Invalid Choice");
      }
      choice = menu.getChoice();
    }
  }
}
