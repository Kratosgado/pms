package com.kratosgado.pms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

  private static Stream<Arguments> averageCompletionPercentageSource() {
    return Stream.of(
        Arguments.of(new ArrayList<>(), 0.0),
        Arguments.of(createProjectList(50.0), 50.0),
        Arguments.of(createProjectList(75.0), 75.0),
        Arguments.of(createProjectList(100.0), 100.0));
  }

  private static ArrayList<Project> createProjectList(double completionPercentage) {
    ArrayList<Project> projects = new ArrayList<>();
    if (completionPercentage == 50.0) {
      Project project1 = new SoftwareProject("p1", "Project 1", "Desc 1", 1, 1);
      ArrayList<Task> tasks1 = new ArrayList<>();
      tasks1.add(new Task("t1", "task1", TaskStatus.COMPLETED));
      tasks1.add(new Task("t2", "task2", TaskStatus.PENDING));
      project1.setTasks(tasks1);
      projects.add(project1);
    } else if (completionPercentage == 75.0) {
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

      projects.add(project1);
      projects.add(project2);
    } else if (completionPercentage == 100.0) {
      Project project = new SoftwareProject("p1", "Project 1", "Desc 1", 1, 1);
      ArrayList<Task> tasks = new ArrayList<>();
      tasks.add(new Task("t1", "task1", TaskStatus.COMPLETED));
      tasks.add(new Task("t2", "task2", TaskStatus.COMPLETED));
      project.setTasks(tasks);
      projects.add(project);
    }
    return projects;
  }

  @ParameterizedTest
  @MethodSource("averageCompletionPercentageSource")
  void testCalculateAverageCompletionPercentage(ArrayList<Project> projects, double expected)
      throws ConflictException {
    for (Project project : projects) {
      projectsDb.add(project);
    }

    double percentage = reportService.calculateAverageCompletionPercentage(projectsDb.getAll());

    assertEquals(expected, percentage, "The average completion percentage should be correctly calculated.");
  }
}
