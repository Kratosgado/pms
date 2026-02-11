package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.utils.enums.TaskStatus;
import com.kratosgado.pms.utils.exceptions.ConflictException;

public class ProjectFilteringTest {
  private Project project;

  @BeforeEach
  void setUp() throws ConflictException {
    project = new SoftwareProject("P001", "Test Project", "A test project", 5, 10000.0);

    // Add tasks with different statuses and assignments
    Task task1 = new Task("T001", "Implement login", TaskStatus.COMPLETED);
    task1.setUserId("U001");
    project.addTask(task1);

    Task task2 = new Task("T002", "Create database schema", TaskStatus.IN_PROGRESS);
    task2.setUserId("U001");
    project.addTask(task2);

    Task task3 = new Task("T003", "Design UI mockups", TaskStatus.PENDING);
    task3.setUserId("U002");
    project.addTask(task3);

    Task task4 = new Task("T004", "Write API documentation", TaskStatus.PENDING);
    task4.setUserId("U003");
    project.addTask(task4);

    Task task5 = new Task("T005", "Setup deployment", TaskStatus.PENDING);
    // Unassigned task
    project.addTask(task5);

    Task task6 = new Task("T006", "Implement logout feature", TaskStatus.COMPLETED);
    task6.setUserId("U002");
    project.addTask(task6);
  }

  // US-1: Enhanced Task Filtering - Status Filter Tests

  @Test
  void testGetTasksByStatus_completed() {
    List<Task> completedTasks = project.getTasksByStatus(TaskStatus.COMPLETED);

    assertEquals(2, completedTasks.size());
    assertTrue(completedTasks.stream().allMatch(t -> t.getStatus() == TaskStatus.COMPLETED));
  }

  @Test
  void testGetTasksByStatus_inProgress() {
    List<Task> inProgressTasks = project.getTasksByStatus(TaskStatus.IN_PROGRESS);

    assertEquals(1, inProgressTasks.size());
    assertEquals("T002", inProgressTasks.get(0).getId());
  }

  @Test
  void testGetTasksByStatus_pending() {
    List<Task> pendingTasks = project.getTasksByStatus(TaskStatus.PENDING);

    assertEquals(3, pendingTasks.size());
    assertTrue(pendingTasks.stream().allMatch(t -> t.getStatus() == TaskStatus.PENDING));
  }

  // US-3: Task Assignment - User Filter Tests

  @Test
  void testGetTasksByUser_user001() {
    List<Task> userTasks = project.getTasksByUser("U001");

    assertEquals(2, userTasks.size());
    assertTrue(userTasks.stream().allMatch(t -> "U001".equals(t.getUserId())));
  }

  @Test
  void testGetTasksByUser_user002() {
    List<Task> userTasks = project.getTasksByUser("U002");

    assertEquals(2, userTasks.size());
    assertTrue(userTasks.stream().allMatch(t -> "U002".equals(t.getUserId())));
  }

  @Test
  void testGetTasksByUser_user003() {
    List<Task> userTasks = project.getTasksByUser("U003");

    assertEquals(1, userTasks.size());
    assertEquals("T004", userTasks.get(0).getId());
  }

  @Test
  void testGetTasksByUser_nonExistentUser() {
    List<Task> userTasks = project.getTasksByUser("U999");

    assertEquals(0, userTasks.size());
  }

  @Test
  void testGetUnassignedTasks() {
    List<Task> unassignedTasks = project.getUnassignedTasks();

    assertEquals(1, unassignedTasks.size());
    assertEquals("T005", unassignedTasks.get(0).getId());
  }

  // US-1: Enhanced Task Filtering - Search Tests

  @Test
  void testSearchTasks_byName_partialMatch() {
    List<Task> results = project.searchTasks("implement");

    assertEquals(2, results.size());
    assertTrue(results.stream().anyMatch(t -> t.getId().equals("T001")));
    assertTrue(results.stream().anyMatch(t -> t.getId().equals("T006")));
  }

  @Test
  void testSearchTasks_byName_caseInsensitive() {
    List<Task> results = project.searchTasks("IMPLEMENT");

    assertEquals(2, results.size());
  }

  @Test
  void testSearchTasks_byName_exactWord() {
    List<Task> results = project.searchTasks("API");

    assertEquals(1, results.size());
    assertEquals("T004", results.get(0).getId());
  }

  @Test
  void testSearchTasks_noMatch() {
    List<Task> results = project.searchTasks("nonexistent");

    assertEquals(0, results.size());
  }

  // US-1: Combined Filters Tests

  @Test
  void testGetFilteredTasks_statusOnly() {
    List<Task> results = project.getFilteredTasks(TaskStatus.PENDING, null, null);

    assertEquals(3, results.size());
    assertTrue(results.stream().allMatch(t -> t.getStatus() == TaskStatus.PENDING));
  }

  @Test
  void testGetFilteredTasks_userOnly() {
    List<Task> results = project.getFilteredTasks(null, "U001", null);

    assertEquals(2, results.size());
    assertTrue(results.stream().allMatch(t -> "U001".equals(t.getUserId())));
  }

  @Test
  void testGetFilteredTasks_searchOnly() {
    List<Task> results = project.getFilteredTasks(null, null, "implement");

    assertEquals(2, results.size());
  }

  @Test
  void testGetFilteredTasks_statusAndUser() {
    List<Task> results = project.getFilteredTasks(TaskStatus.COMPLETED, "U001", null);

    assertEquals(1, results.size());
    assertEquals("T001", results.get(0).getId());
  }

  @Test
  void testGetFilteredTasks_statusAndSearch() {
    List<Task> results = project.getFilteredTasks(TaskStatus.PENDING, null, "design");

    assertEquals(1, results.size());
    assertEquals("T003", results.get(0).getId());
  }

  @Test
  void testGetFilteredTasks_userAndSearch() {
    List<Task> results = project.getFilteredTasks(null, "U001", "implement");

    assertEquals(1, results.size());
    assertEquals("T001", results.get(0).getId());
  }

  @Test
  void testGetFilteredTasks_allFilters() {
    List<Task> results = project.getFilteredTasks(TaskStatus.COMPLETED, "U001", "login");

    assertEquals(1, results.size());
    assertEquals("T001", results.get(0).getId());
  }

  @Test
  void testGetFilteredTasks_noFilters_returnsAll() {
    List<Task> results = project.getFilteredTasks(null, null, null);

    assertEquals(6, results.size());
  }

  @Test
  void testGetFilteredTasks_noMatch() {
    List<Task> results = project.getFilteredTasks(TaskStatus.COMPLETED, "U003", null);

    assertEquals(0, results.size());
  }

  // Edge Cases

  @Test
  void testSearchTasks_emptyString_returnsAll() {
    List<Task> results = project.searchTasks("");

    assertEquals(6, results.size());
  }

  @Test
  void testGetTasksByUser_nullUserId_returnsEmpty() {
    List<Task> results = project.getTasksByUser(null);

    assertEquals(0, results.size());
  }
}
