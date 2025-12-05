
package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.Task;

public class RepositoryTest {

  @Test
  void testAdd() {
    Repository<Task> repository = new TaskInMemoryDatabase();
    repository.add(new Task("id1", "name", "projectId"));
    repository.add(new Task("id2", "name", "projectId"));
    assertEquals(2, repository.count());
  }

  @Test
  void testUpdate() {
    Repository<Task> repository = new TaskInMemoryDatabase();
    repository.add(new Task("id1", "name", "projectId"));
    repository.add(new Task("id2", "name", "projectId"));
    Task task = repository.getById("id1").get();
    task.setName("newName");
    Task updated = repository.update(task);
    assertEquals(task, updated);
  }

  @Test
  void testRemoveById() {
    Repository<Task> repository = new TaskInMemoryDatabase();
    repository.add(new Task("id1", "name", "projectId"));
    repository.add(new Task("id2", "name", "projectId"));
    repository.removeById("id1");
    assertEquals(1, repository.count());
  }

  @Test
  void testExists() {
    Repository<Task> repository = new TaskInMemoryDatabase();
    repository.add(new Task("id1", "name", "projectId"));
    repository.add(new Task("id2", "name", "projectId"));
    assertTrue(repository.exists("id1"));
    assertFalse(repository.exists("id5"));
  }

  @Test
  void testGetById() {
    Repository<Task> repository = new TaskInMemoryDatabase();
    repository.add(new Task("id1", "name", "projectId"));
    repository.add(new Task("id2", "name", "projectId"));
    assertTrue(repository.getById("id1").isPresent());
  }

}
