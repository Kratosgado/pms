
package com.kratosgado.pms.utils.factories;

import java.io.IOException;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.services.MainService;
import com.kratosgado.pms.services.ProjectService;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.services.TaskService;
import com.kratosgado.pms.services.UserService;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public class ServiceFactory implements com.kratosgado.pms.interfaces.ServiceFactory {
  private final UserInMemoryDatabase usersDb;
  private final ProjectInMemoryDatabase projectsDb;
  private final AuthManager authManager;
  private final NavigationManager navigationManager;

  public ServiceFactory(UserInMemoryDatabase usersDb, ProjectInMemoryDatabase projectsDb,
      AuthManager authManager, NavigationManager navigationManager) {
    this.usersDb = usersDb;
    this.projectsDb = projectsDb;
    this.authManager = authManager;
    this.navigationManager = navigationManager;
    loadData();
  }

  @Override
  public TaskService createTaskService(String projectId) throws EntityNotFoundException {
    Project project = projectsDb.getById(projectId);
    return new TaskService(project, usersDb, authManager);
  }

  @Override
  public ProjectService createProjectService() {
    try {
      projectsDb.loadData();
    } catch (IOException e) {
      CustomUtils.displayError(e);
    }
    return new ProjectService(projectsDb, authManager, navigationManager, this);
  }

  @Override
  public UserService createUserService() {
    try {
      usersDb.loadData();
    } catch (IOException e) {
      CustomUtils.displayError(e);
    }
    return new UserService(usersDb, authManager);
  }

  @Override
  public ReportService createReportService() {
    return new ReportService(projectsDb);
  }

  @Override
  public MainService createMainService() {
    return new MainService(navigationManager, projectsDb, authManager, this);
  }

  @Override
  public void saveData() {
    try {
      projectsDb.saveData();
      usersDb.saveData();
    } catch (IOException e) {
      CustomUtils.displayError(e);
    }
  }

  @Override
  public void loadData() {
    try {
      projectsDb.loadData();
      usersDb.loadData();
    } catch (IOException e) {
      CustomUtils.displayError(e);
      saveData();
    }
  }

}
