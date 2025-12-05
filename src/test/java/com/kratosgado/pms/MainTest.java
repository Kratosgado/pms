package com.kratosgado.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.enums.TaskStatus;

public class MainTest {
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

  // TODO: unit test for report
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
    // ReportService reportService = new ReportService(projects);
    // double percentege = reportService.getAverageCompletionPercentage();
    // assertEquals(50.0, percentege);
  }

}
