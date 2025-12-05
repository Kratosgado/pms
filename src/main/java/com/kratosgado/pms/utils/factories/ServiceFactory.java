
package com.kratosgado.pms.utils.factories;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.services.MainService;
import com.kratosgado.pms.services.ProjectService;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.services.TaskService;
import com.kratosgado.pms.services.UserService;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;

public class ServiceFactory implements com.kratosgado.pms.interfaces.ServiceFactory {
  private final UserInMemoryDatabase usersDb;
  private final ProjectInMemoryDatabase projectsDb;
  private final TaskInMemoryDatabase tasksDb;
  private final AuthManager authManager;
  private final NavigationManager navigationManager;

  public ServiceFactory(UserInMemoryDatabase usersDb, ProjectInMemoryDatabase projectsDb,
      TaskInMemoryDatabase tasksDb, AuthManager authManager, NavigationManager navigationManager) {
    this.usersDb = usersDb;
    this.projectsDb = projectsDb;
    this.tasksDb = tasksDb;
    this.authManager = authManager;
    this.navigationManager = navigationManager;
  }

  @Override
  public TaskService createTaskService(String projectId) {
    tasksDb.setProjectId(projectId);
    return new TaskService(tasksDb, usersDb, authManager, navigationManager);
  }

  @Override
  public ProjectService createProjectService() {
    return new ProjectService(projectsDb, authManager, navigationManager, this);
  }

  @Override
  public UserService createUserService() {
    return new UserService(usersDb, tasksDb, authManager);
  }

  @Override
  public ReportService createReportService() {
    return new ReportService(projectsDb);
  }

  @Override
  public MainService createMainService() {
    return new MainService(navigationManager, projectsDb, authManager, this);
  }
}
