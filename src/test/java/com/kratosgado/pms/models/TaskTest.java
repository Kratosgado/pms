package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.utils.enums.TaskStatus;

public class TaskTest {

  private Task task;

  @BeforeEach
  void setUp() {
    task = new Task("T001", "Implement Login", TaskStatus.PENDING, "PJ001");
  }

  @Test
  void testTaskCreation() {
    assertEquals("T001", task.getId());
    assertEquals("Implement Login", task.getName());
    assertEquals(TaskStatus.PENDING, task.getStatus());
    assertEquals("PJ001", task.getProjectId());
    assertEquals(0, task.getHours());
  }

  @Test
  void testTaskCreationWithoutStatus() {
    Task newTask = new Task("T002", "Database Setup", "PJ001");
    assertEquals("T002", newTask.getId());
    assertEquals("Database Setup", newTask.getName());
    assertEquals(TaskStatus.PENDING, newTask.getStatus());
    assertEquals("PJ001", newTask.getProjectId());
  }

  @Test
  void testTaskCreationWithoutProjectId() {
    Task newTask = new Task("T003", "Research", TaskStatus.IN_PROGRESS);
    assertEquals("T003", newTask.getId());
    assertEquals("Research", newTask.getName());
    assertEquals(TaskStatus.IN_PROGRESS, newTask.getStatus());
    assertNull(newTask.getProjectId());
  }

  @Test
  void testUpdateTaskName() {
    task.setName("Updated Task Name");
    assertEquals("Updated Task Name", task.getName());
  }

  @Test
  void testUpdateTaskStatus() {
    task.setStatus(TaskStatus.IN_PROGRESS);
    assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

    task.setStatus(TaskStatus.COMPLETED);
    assertEquals(TaskStatus.COMPLETED, task.getStatus());
  }

  @Test
  void testUpdateTaskHours() {
    task.setHours(8);
    assertEquals(8, task.getHours());

    task.setHours(10);
    assertEquals(10, task.getHours());
  }

  @Test
  void testUpdateProjectId() {
    task.setProjectId("PJ002");
    assertEquals("PJ002", task.getProjectId());
  }

  @Test
  void testUpdateUserId() {
    task.setUserId("USR001");
    assertEquals("USR001", task.getUserId());
  }

  @Test
  void testTaskCompletion() {
    task.setStatus(TaskStatus.COMPLETED);
    assertTrue(task.isCompleted());

    task.setStatus(TaskStatus.PENDING);
    assertFalse(task.isCompleted());

    task.setStatus(TaskStatus.IN_PROGRESS);
    assertFalse(task.isCompleted());
  }

  @Test
  void testFilterCmpletedTasksUsingStreams() {
    List<Task> tasks = List.of(
        new Task("T001", "Task 1", TaskStatus.COMPLETED),
        new Task("T002", "Task 2", TaskStatus.PENDING));
    double completedTasks = tasks.stream().filter(Task::isCompleted).count();
    assertEquals(1, completedTasks);
  }

}
