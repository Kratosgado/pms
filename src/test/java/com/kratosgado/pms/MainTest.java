package com.kratosgado.pms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.utils.TaskStatus;

public class MainTest {
  // TODO: unit test for task status updates and progress calculation
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
    project.addedTask(new Task("PJ2", "task1", project.getId()));
    project.addedTask(new Task("PJ4", "task1", TaskStatus.IN_PROGRESS));
    project.addedTask(new Task("PJ5", "task1", TaskStatus.COMPLETED));
    project.addedTask(new Task("PJ8", "task1", TaskStatus.COMPLETED));
    double percentege = project.calculateCompletionPercentage();
    assertEquals(50.0, percentege);
  }

  // TODO: unit test for report
  @Test
  void testAverageCompletionPercentage() {
    ArrayList<Project> projects = new ArrayList<>();
    SoftwareProject project = new SoftwareProject("id", "test", "test", 40, 10);
    project.addedTask(new Task("PJ2", "task1", project.getId()));
    project.addedTask(new Task("PJ4", "task1", TaskStatus.IN_PROGRESS));
    project.addedTask(new Task("PJ5", "task1", TaskStatus.COMPLETED));
    project.addedTask(new Task("PJ8", "task1", TaskStatus.COMPLETED));
    projects.add(project);
    ReportService.displayReport(projects);
    // assertEquals(50.0, percentege);
  }

}
