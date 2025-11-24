import java.util.ArrayList;

import models.AdminUser;
import models.RegularUser;
import models.User;
import utils.ConsoleMenu;

public class Main {

  public static void main(String[] args) {
    ArrayList<User> users = new ArrayList<>();
    users.add(new RegularUser("EssliFe", "esslifie@gmail.com", "password"));
    users.add(new AdminUser("EssliFe", "esslifie@gmail.com", "password"));
    users.add(new RegularUser("EssliFe", "esslifie@gmail.com", "password"));
    users.add(new AdminUser("EssliFe", "esslifie@gmail.com", "password"));
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
          System.out.println("View Projects");
          break;
        case 3:
          System.out.println("Add Task");
          break;
        case 4:
          System.out.println("View Tasks");
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
