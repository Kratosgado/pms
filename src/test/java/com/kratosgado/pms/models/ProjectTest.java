package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  @Test
  void testCompletionPercentageWithNoTasks() {
    ArrayList<Task> tasks = new ArrayList<>();
    softwareProject.setTasks(tasks);
    assertEquals(0.0, softwareProject.calculateCompletionPercentage());
  }

  @Test
  void testCompletionPercentageWithTasks() {
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T001", "Task 1", TaskStatus.COMPLETED, "PJ001"));
    tasks.add(new Task("T002", "Task 2", TaskStatus.COMPLETED, "PJ001"));
    tasks.add(new Task("T003", "Task 3", TaskStatus.PENDING, "PJ001"));
    tasks.add(new Task("T004", "Task 4", TaskStatus.IN_PROGRESS, "PJ001"));
    softwareProject.setTasks(tasks);

    assertEquals(50.0, softwareProject.calculateCompletionPercentage());
    assertEquals(2, softwareProject.getCompletedTasks());
  }

  @Test
  void testAllTasksCompleted() {
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T001", "Task 1", TaskStatus.COMPLETED, "PJ001"));
    tasks.add(new Task("T002", "Task 2", TaskStatus.COMPLETED, "PJ001"));
    softwareProject.setTasks(tasks);

    assertEquals(100.0, softwareProject.calculateCompletionPercentage());
    assertEquals(2, softwareProject.getCompletedTasks());
  }

  @Test
  void testNoTasksCompleted() {
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("T001", "Task 1", TaskStatus.PENDING, "PJ001"));
    tasks.add(new Task("T002", "Task 2", TaskStatus.IN_PROGRESS, "PJ001"));
    tasks.add(new Task("T003", "Task 3", TaskStatus.PENDING, "PJ001"));

    softwareProject.setTasks(tasks);

    assertEquals(0.0, softwareProject.calculateCompletionPercentage());
    assertEquals(0, softwareProject.getCompletedTasks());
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
