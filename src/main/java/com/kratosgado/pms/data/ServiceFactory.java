
package com.kratosgado.pms.data;

import com.kratosgado.pms.ApplicationContext;
import com.kratosgado.pms.services.MainService;
import com.kratosgado.pms.services.ProjectService;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.services.TaskService;
import com.kratosgado.pms.services.UserService;

public class ServiceFactory implements com.kratosgado.pms.interfaces.ServiceFactory {
  private final UserInMemoryDatabase usersDb;
  private final ProjectInMemoryDatabase projectsDb;
  private final TaskInMemoryDatabase tasksDb;
  private final ApplicationContext applicationContext;

  public ServiceFactory(UserInMemoryDatabase usersDb, ProjectInMemoryDatabase projectsDb,
      TaskInMemoryDatabase tasksDb, ApplicationContext applicationContext) {
    this.usersDb = usersDb;
    this.projectsDb = projectsDb;
    this.tasksDb = tasksDb;
    this.applicationContext = applicationContext;
  }

  @Override
  public TaskService createTaskService(String projectId) {
    return new TaskService(tasksDb, usersDb, applicationContext);
  }

  @Override
  public ProjectService createProjectService() {
    return new ProjectService(projectsDb, tasksDb, usersDb, applicationContext, this);
  }

  @Override
  public UserService createUserService() {
    return new UserService(usersDb, applicationContext);
  }

  @Override
  public ReportService createReportService() {
    return new ReportService(projectsDb);
  }

  @Override
  public MainService createMainService() {
    return new MainService(applicationContext, projectsDb, this);
  }

}
