package com.kratosgado.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;
import com.kratosgado.pms.utils.enums.TaskStatus;
import com.kratosgado.pms.utils.factories.ServiceFactory;

public class MainTest {
  private final TaskInMemoryDatabase tasksDb = new TaskInMemoryDatabase();
  private final UserInMemoryDatabase usersDb = new UserInMemoryDatabase();
  private final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase(tasksDb);
  private final AuthManager authManager = new AuthManager(usersDb);
  private final NavigationManager navigationManager = new NavigationManager();
  private final ServiceFactory serviceFactory = new ServiceFactory(usersDb, projectsDb, tasksDb, authManager,
      navigationManager);

  @Test
  void testTaskStatus() {
    Task task = new Task("id", "name", "projectId");
    assertEquals(TaskStatus.PENDING, task.getStatus());
    task.setStatus(TaskStatus.IN_PROGRESS);
    assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    task.setStatus(TaskStatus.COMPLETED);
    assertEquals(TaskStatus.COMPLETED, task.getStatus());
  }

  @Test
  void testCalculatCompletionPercentage() {
    SoftwareProject project = new SoftwareProject("id", "test", "test", 40, 10);
    Task[] tasks = { new Task("PJ2", "task1", project.getId()),
        new Task("PJ4", "task1", TaskStatus.IN_PROGRESS),
        new Task("PJ5", "task1", TaskStatus.COMPLETED),
        new Task("PJ8", "task1", TaskStatus.COMPLETED) };
    project.setTasks(tasks);
    double percentege = project.calculateCompletionPercentage();
    assertEquals(50.0, percentege);
  }

  @Test
  void testAverageCompletionPercentage() {
    ArrayList<Project> projects = new ArrayList<>();
    SoftwareProject project = new SoftwareProject("id", "test", "test", 40, 10);
    Task[] tasks = { new Task("PJ2", "task1", project.getId()),
        new Task("PJ4", "task1", TaskStatus.IN_PROGRESS),
        new Task("PJ5", "task1", TaskStatus.COMPLETED),
        new Task("PJ8", "task1", TaskStatus.COMPLETED) };
    project.setTasks(tasks);
    projects.add(project);
    ReportService reportService = serviceFactory.createReportService();
    double percentege = reportService.calculateAverageCompletionPercentage(projects.toArray(new Project[0]));
    assertEquals(50.0, percentege);
  }

}
