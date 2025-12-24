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
import com.kratosgado.pms.utils.exceptions.ConflictException;

public class ReportServiceTest {
  private ProjectInMemoryDatabase projectsDb;
  private ReportService reportService;

  @BeforeEach
  void setUp() {
    projectsDb = new ProjectInMemoryDatabase();
    reportService = new ReportService(projectsDb);
  }

  @Test
  void testAverageCompletionPercentage() throws ConflictException {

    Project project1 = new SoftwareProject("p1", "Project 1", "Desc 1", 1, 1);
    ArrayList<Task> tasks1 = new ArrayList<>();
    tasks1.add(new Task("t1", "task1", TaskStatus.COMPLETED));
    tasks1.add(new Task("t2", "task2", TaskStatus.PENDING));
    project1.setTasks(tasks1);

    Project project2 = new SoftwareProject("p2", "Project 2", "Desc 2", 1, 1);
    ArrayList<Task> tasks2 = new ArrayList<>();
    tasks2.add(new Task("t3", "task3", TaskStatus.COMPLETED));
    tasks2.add(new Task("t4", "task4", TaskStatus.COMPLETED));
    project2.setTasks(tasks2);

    projectsDb.add(project1);
    projectsDb.add(project2);

    double percentage = reportService.calculateAverageCompletionPercentage(projectsDb.getAll());

    assertEquals(75.0, percentage, "The average completion percentage should be correctly calculated.");
  }

  @Test
  void testAverageCompletionPercentageWithNoProjects() {
    double percentage = reportService.calculateAverageCompletionPercentage(new ArrayList<>());

    assertEquals(0.0, percentage, "The average completion percentage of no projects should be 0.");
  }
}
