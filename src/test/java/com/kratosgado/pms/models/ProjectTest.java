package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.enums.TaskStatus;

public class ProjectTest {

  private SoftwareProject softwareProject;
  private HardwareProject hardwareProject;

  @BeforeEach
  void setUp() {
    softwareProject = new SoftwareProject("PJ001", "Mobile App", "Build a mobile application", 5, 50000.0);
    hardwareProject = new HardwareProject("PJ002", "PCB Board", "Design PCB board", 3, 30000.0);
  }

  @Test
  void testProjectCreation() {
    assertEquals("PJ001", softwareProject.getId());
    assertEquals("Mobile App", softwareProject.getName());
    assertEquals("Build a mobile application", softwareProject.getDescription());
    assertEquals(5, softwareProject.getTeamSize());
    assertEquals(50000.0, softwareProject.getBudget());
  }

  @Test
  void testProjectTypeIdentification() {
    assertEquals(ProjectType.SOFTWARE, softwareProject.getProjectType());
    assertEquals(ProjectType.HARDWARE, hardwareProject.getProjectType());
  }

  @Test
  void testProjectProperties() {
    softwareProject.setName("Updated Name");
    assertEquals("Updated Name", softwareProject.getName());

    softwareProject.setDescription("Updated Description");
    assertEquals("Updated Description", softwareProject.getDescription());

    softwareProject.setTeamSize(10);
    assertEquals(10, softwareProject.getTeamSize());

    softwareProject.setBudget(100000.0);
    assertEquals(100000.0, softwareProject.getBudget());
  }

  private static Stream<Arguments> completionPercentageSource() {
    return Stream.of(
        Arguments.of(List.of(), 0.0, 0),
        Arguments.of(
            List.of(
                new Task("T001", "Task 1", TaskStatus.COMPLETED, "PJ001"),
                new Task("T002", "Task 2", TaskStatus.COMPLETED, "PJ001"),
                new Task("T003", "Task 3", TaskStatus.PENDING, "PJ001"),
                new Task("T004", "Task 4", TaskStatus.IN_PROGRESS, "PJ001")),
            50.0, 2),
        Arguments.of(
            List.of(
                new Task("T001", "Task 1", TaskStatus.COMPLETED, "PJ001"),
                new Task("T002", "Task 2", TaskStatus.COMPLETED, "PJ001")),
            100.0, 2),
        Arguments.of(
            List.of(
                new Task("T001", "Task 1", TaskStatus.PENDING, "PJ001"),
                new Task("T002", "Task 2", TaskStatus.IN_PROGRESS, "PJ001"),
                new Task("T003", "Task 3", TaskStatus.PENDING, "PJ001")),
            0.0, 0));
  }

  @ParameterizedTest
  @MethodSource("completionPercentageSource")
  void testCalculateCompletionPercentage(List<Task> tasks, double expectedPercentage, int expectedCompletedTasks) {
    softwareProject.setTasks(new ArrayList<>(tasks));
    assertEquals(expectedPercentage, softwareProject.calculateCompletionPercentage(),
        "Completion percentage should be calculated correctly");
    assertEquals(expectedCompletedTasks, softwareProject.getCompletedTasks(),
        "Number of completed tasks should be calculated correctly");
  }

  @Test
  void testGetProjectDetails() {
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T001", "Setup Environment", TaskStatus.COMPLETED, "PJ001"));
    softwareProject.setTasks(tasks);

    String details = softwareProject.getProjectDetails();
    assertTrue(details.contains("Mobile App"));
    assertTrue(details.contains("Build a mobile application"));
    assertTrue(details.contains("SOFTWARE"));
    assertTrue(details.contains("5"));
    assertTrue(details.contains("50000"));
  }

  @Test
  void testProjectDetailsDto() {
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T001", "Task 1", TaskStatus.COMPLETED, "PJ001"));
    tasks.add(new Task("T002", "Task 2", TaskStatus.PENDING, "PJ001"));
    softwareProject.setTasks(tasks);

    var dto = softwareProject.getDetails();
    assertEquals("PJ001", dto.id());
    assertEquals("Mobile App", dto.name());
    assertEquals("Build a mobile application", dto.description());
    assertEquals(ProjectType.SOFTWARE, dto.type());
    assertEquals(5, dto.teamSize());
    assertEquals(50000.0, dto.budget());
    assertEquals(50.0, dto.progress());
  }

}
