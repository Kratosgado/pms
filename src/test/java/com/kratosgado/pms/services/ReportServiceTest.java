
package com.kratosgado.pms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.enums.TaskStatus;

public class ReportServiceTest {
  private final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase();
  ReportService reportService = new ReportService(projectsDb);

  private Project project;

  @BeforeEach
  void setUp() {
    project = new SoftwareProject("id", "test", "test", 40, 10);
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("PJ2", "task1", project.getId()));
    tasks.add(new Task("PJ4", "task1", TaskStatus.IN_PROGRESS));
    tasks.add(new Task("PJ5", "task1", TaskStatus.COMPLETED));
    tasks.add(new Task("PJ8", "task1", TaskStatus.COMPLETED));
    project.setTasks(tasks);

  }

  @Test
  void testCalculatCompletionPercentage() {
    double percentege = project.calculateCompletionPercentage();
    assertEquals(50.0, percentege);
  }

  @Test
  void testAverageCompletionPercentage() {
    ArrayList<Project> projects = new ArrayList<>();
    projects.add(project);
    double percentege = reportService.calculateAverageCompletionPercentage(projects);
    assertEquals(50.0, percentege);
  }
}
