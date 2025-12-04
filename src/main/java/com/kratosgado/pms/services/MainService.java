package com.kratosgado.pms.services;

import com.kratosgado.pms.ApplicationContext;
import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.interfaces.ServiceFactory;
import com.kratosgado.pms.models.User;

public class MainService extends ConsoleService {
  private final ApplicationContext applicationContext;
  private final ServiceFactory serviceFactory;

  public MainService(ApplicationContext applicationContext, ProjectInMemoryDatabase projectsDb,
      ServiceFactory serviceFactory) {
    this.applicationContext = applicationContext;
    this.title = "MAIN MENU";
    this.serviceFactory = serviceFactory;
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
        applicationContext.pushService(serviceFactory.createProjectService());
        break;
      case 2:
        final ProjectService projectService = serviceFactory.createProjectService();
        projectService.listProjects();
        projectService.askForProject();
        break;
      case 3:
        final ReportService reportService = serviceFactory.createReportService();
        reportService.displayReport();
        break;
      case 4:
        applicationContext.pushService(serviceFactory.createUserService());
        break;
      default:
        return choice;
    }
    return -1;
  }
}
