package com.kratosgado.pms.services;

import com.kratosgado.pms.ApplicationContext;
import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;

public class MainService extends ConsoleService {
  private final ProjectInMemoryDatabase projectsDb;
  private final UserInMemoryDatabase usersDb;
  private final TaskInMemoryDatabase tasksDb;
  private final ApplicationContext applicationContext;

  public MainService(ApplicationContext applicationContext, ProjectInMemoryDatabase projectsDb,
      UserInMemoryDatabase usersDb, TaskInMemoryDatabase tasksDb) {
    this.applicationContext = applicationContext;
    this.projectsDb = projectsDb;
    this.usersDb = usersDb;
    this.tasksDb = tasksDb;
    this.title = "MAIN MENU";
  }

  @Override
  protected void displayOptions() {
    printCurrentUser();
    System.out.println("1. Manage Projects");
    System.out.println("2. Manage Tasks");
    System.out.println("3. View Status Reports");
    System.out.println("4. Manage Users");
  }

  private final void printCurrentUser() {
    User currentUser = applicationContext.getCurrentUser();
    System.out.printf("\nCurrent User: %s (%s)\n\n", currentUser.getName(), currentUser.getRole());
  }

  @Override
  public int handleChoice(final int choice) {
    switch (choice) {
      case 1:
        applicationContext.pushService(new ProjectService(projectsDb, tasksDb, usersDb, applicationContext));
        break;
      case 2:
        final ProjectService projectService = new ProjectService(projectsDb, tasksDb, usersDb, applicationContext);
        projectService.listProjects();
        projectService.askForProject();
        break;
      case 3:
        ReportService.displayReport(projectsDb.getAll());
        break;
      case 4:
        applicationContext.pushService(new UserService(usersDb, applicationContext));
        break;
      default:
        return choice;
    }
    return -1;
  }
}
