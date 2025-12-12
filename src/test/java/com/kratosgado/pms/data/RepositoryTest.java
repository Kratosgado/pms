
package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.User;
import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;

public class RepositoryTest {
  private Repository<User> repository;

  @BeforeEach
  void setUp() {
    this.repository = new UserInMemoryDatabase();
    repository.add(new RegularUser("id1", "name", "email", "password"));
    repository.add(new AdminUser("id2", "name", "email", "password"));

  }

  @Test
  void testAddEntity() {
    assertEquals(2, repository.count());
  }

  @Test
  void testEntityUpdate() {
    User task = repository.getById("id1").get();
    task.setName("newName");
    User updated = repository.update(task);
    assertEquals(task, updated);
  }

  @Test
  void testRemoveEntityById() {
    repository.removeById("id1");
    assertEquals(1, repository.count());
  }

  @Test
  void testEntityExists() {
    assertTrue(repository.exists("id1"));
    assertFalse(repository.exists("id5"));
  }

  @Test
  void testGetEntityById() {
    assertTrue(repository.getById("id1").isPresent());
  }

}
