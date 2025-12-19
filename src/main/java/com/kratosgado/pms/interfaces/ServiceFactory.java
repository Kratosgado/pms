package com.kratosgado.pms.interfaces;

import com.kratosgado.pms.services.MainService;
import com.kratosgado.pms.services.ProjectService;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.services.TaskService;
import com.kratosgado.pms.services.UserService;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public interface ServiceFactory {

  TaskService createTaskService(String projectId) throws EntityNotFoundException;

  ProjectService createProjectService();

  UserService createUserService();

  ReportService createReportService();

  MainService createMainService();

  void saveData();

  void loadData();
}
