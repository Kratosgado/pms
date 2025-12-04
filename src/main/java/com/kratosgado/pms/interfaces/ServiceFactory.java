package com.kratosgado.pms.interfaces;

import com.kratosgado.pms.services.MainService;
import com.kratosgado.pms.services.ProjectService;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.services.TaskService;
import com.kratosgado.pms.services.UserService;

public interface ServiceFactory {

  TaskService createTaskService(String projectId);

  ProjectService createProjectService();

  UserService createUserService();

  ReportService createReportService();

  MainService createMainService();
  //
  // AuthManager getAuthManager();
  //
  // NavigationManager getNavigationManager();
  //
  // TaskInMemoryDatabase getTasksDb();
  //
  // ProjectInMemoryDatabase getProjectsDb();
  //
  // UserInMemoryDatabase getUsersDb();
}
