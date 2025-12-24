package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("T001", task.getId(), "Task ID should be set correctly");
        assertEquals("Implement Login", task.getName(), "Task name should be set correctly");
        assertEquals(TaskStatus.PENDING, task.getStatus(), "Task status should be PENDING by default");
        assertEquals("PJ001", task.getProjectId(), "Project ID should be set correctly");
        assertEquals(0, task.getHours(), "Task hours should be 0 by default");
    }

    @Test
    void testTaskCreationWithoutStatus() {
        Task newTask = new Task("T002", "Database Setup", "PJ001");
        assertEquals("T002", newTask.getId(), "Task ID should be set correctly");
        assertEquals("Database Setup", newTask.getName(), "Task name should be set correctly");
        assertEquals(TaskStatus.PENDING, newTask.getStatus(), "Task status should be PENDING when not specified");
        assertEquals("PJ001", newTask.getProjectId(), "Project ID should be set correctly");
    }

    @Test
    void testTaskCreationWithoutProjectId() {
        Task newTask = new Task("T003", "Research", TaskStatus.IN_PROGRESS);
        assertEquals("T003", newTask.getId(), "Task ID should be set correctly");
        assertEquals("Research", newTask.getName(), "Task name should be set correctly");
        assertEquals(TaskStatus.IN_PROGRESS, newTask.getStatus(), "Task status should be set correctly");
        assertNull(newTask.getProjectId(), "Project ID should be null when not specified");
    }

    @Test
    void testUpdateTaskName() {
        task.setName("Updated Task Name");
        assertEquals("Updated Task Name", task.getName(), "Task name should be updatable");
    }

    @Test
    void testUpdateTaskStatus() {
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus(), "Task status should be updatable to IN_PROGRESS");

        task.setStatus(TaskStatus.COMPLETED);
        assertEquals(TaskStatus.COMPLETED, task.getStatus(), "Task status should be updatable to COMPLETED");
    }

    @Test
    void testUpdateTaskHours() {
        task.setHours(8);
        assertEquals(8, task.getHours(), "Task hours should be updatable");

        task.setHours(10);
        assertEquals(10, task.getHours(), "Task hours should be updatable again");
    }

    @Test
    void testUpdateProjectId() {
        task.setProjectId("PJ002");
        assertEquals("PJ002", task.getProjectId(), "Project ID should be updatable");
    }

    @Test
    void testUpdateUserId() {
        task.setUserId("USR001");
        assertEquals("USR001", task.getUserId(), "User ID should be updatable");
    }

    @Test
    void testTaskCompletion() {
        task.setStatus(TaskStatus.COMPLETED);
        assertTrue(task.isCompleted(), "isCompleted should return true for COMPLETED status");

        task.setStatus(TaskStatus.PENDING);
        assertFalse(task.isCompleted(), "isCompleted should return false for PENDING status");

        task.setStatus(TaskStatus.IN_PROGRESS);
        assertFalse(task.isCompleted(), "isCompleted should return false for IN_PROGRESS status");
    }
}
