
package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.services.ReportService;
import com.kratosgado.pms.utils.enums.TaskStatus;

public class ReportServiceTest {
  private final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase();
  ReportService reportService = new ReportService(projectsDb);

  @Test
  void testCalculatCompletionPercentage() {
    SoftwareProject project = new SoftwareProject("id", "test", "test", 40, 10);
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("PJ2", "task1", project.getId()));
    tasks.add(new Task("PJ4", "task1", TaskStatus.IN_PROGRESS));
    tasks.add(new Task("PJ5", "task1", TaskStatus.COMPLETED));
    tasks.add(new Task("PJ8", "task1", TaskStatus.COMPLETED));
    project.setTasks(tasks);
    double percentege = project.calculateCompletionPercentage();
    assertEquals(50.0, percentege);
  }

  @Test
  void testAverageCompletionPercentage() {
    ArrayList<Project> projects = new ArrayList<>();
    SoftwareProject project = new SoftwareProject("id", "test", "test", 40, 10);
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("PJ2", "task1", project.getId()));
    tasks.add(new Task("PJ4", "task1", TaskStatus.IN_PROGRESS));
    tasks.add(new Task("PJ5", "task1", TaskStatus.COMPLETED));
    tasks.add(new Task("PJ8", "task1", TaskStatus.COMPLETED));
    project.setTasks(tasks);
    projects.add(project);
    double percentege = reportService.calculateAverageCompletionPercentage(projects.toArray(new Project[0]));
    assertEquals(50.0, percentege);
  }
}
