
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

  @Test
  void testAverageCompletionPercentage_multipleProjects() {
    // Project 1: 50% complete (2 of 4 tasks)
    Project project1 = new SoftwareProject("id1", "Project 1", "test", 40, 10);
    ArrayList<Task> tasks1 = new ArrayList<>();
    tasks1.add(new Task("T1", "task1", TaskStatus.COMPLETED));
    tasks1.add(new Task("T2", "task2", TaskStatus.COMPLETED));
    tasks1.add(new Task("T3", "task3", TaskStatus.PENDING));
    tasks1.add(new Task("T4", "task4", TaskStatus.IN_PROGRESS));
    project1.setTasks(tasks1);

    // Project 2: 100% complete (2 of 2 tasks)
    Project project2 = new SoftwareProject("id2", "Project 2", "test", 30, 20);
    ArrayList<Task> tasks2 = new ArrayList<>();
    tasks2.add(new Task("T5", "task5", TaskStatus.COMPLETED));
    tasks2.add(new Task("T6", "task6", TaskStatus.COMPLETED));
    project2.setTasks(tasks2);

    ArrayList<Project> projects = new ArrayList<>();
    projects.add(project1);
    projects.add(project2);

    // Average of 50% and 100% = 75%
    double percentage = reportService.calculateAverageCompletionPercentage(projects);
    assertEquals(75.0, percentage);
  }

  @Test
  void testAverageCompletionPercentage_noCompletedTasks() {
    Project emptyProject = new SoftwareProject("id", "Empty Project", "test", 40, 10);
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T1", "task1", TaskStatus.PENDING));
    tasks.add(new Task("T2", "task2", TaskStatus.IN_PROGRESS));
    emptyProject.setTasks(tasks);

    ArrayList<Project> projects = new ArrayList<>();
    projects.add(emptyProject);

    double percentage = reportService.calculateAverageCompletionPercentage(projects);
    assertEquals(0.0, percentage);
  }

  @Test
  void testAverageCompletionPercentage_allTasksCompleted() {
    Project fullProject = new SoftwareProject("id", "Full Project", "test", 40, 10);
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T1", "task1", TaskStatus.COMPLETED));
    tasks.add(new Task("T2", "task2", TaskStatus.COMPLETED));
    tasks.add(new Task("T3", "task3", TaskStatus.COMPLETED));
    fullProject.setTasks(tasks);

    ArrayList<Project> projects = new ArrayList<>();
    projects.add(fullProject);

    double percentage = reportService.calculateAverageCompletionPercentage(projects);
    assertEquals(100.0, percentage);
  }
}
