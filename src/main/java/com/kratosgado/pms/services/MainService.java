package com.kratosgado.pms.services;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.interfaces.ServiceFactory;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;

public class MainService extends ConsoleService {
  private final NavigationManager navigationManager;
  private final AuthManager authManager;
  private final ServiceFactory serviceFactory;

  public MainService(NavigationManager navigationManager, ProjectInMemoryDatabase projectsDb,
      AuthManager authManager,
      ServiceFactory serviceFactory) {
    this.navigationManager = navigationManager;
    this.authManager = authManager;
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
    User currentUser = authManager.getCurrentUser();
    System.out.printf("\nCurrent User: %s (%s)\n\n", currentUser.getName(), currentUser.getRole());
  }

  @Override
  public int handleChoice(final int choice) {
    switch (choice) {
      case 1:
        navigationManager.pushService(serviceFactory.createProjectService());
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
        navigationManager.pushService(serviceFactory.createUserService());
        break;
      default:
        return choice;
    }
    return -1;
  }
}
