
package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;
import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;

public class RepositoryTest {
  private Repository<User> repository;

  @BeforeEach
  void setUp() throws ConflictException {
    this.repository = new UserInMemoryDatabase();
    repository.add(new RegularUser("id1", "name", "email", "password"));
    repository.add(new AdminUser("id2", "name", "email", "password"));

  }

  @Test
  void testAddEntity() {
    assertEquals(2, repository.count());
  }

  @Test
  void testEntityUpdate() throws EntityNotFoundException {
    User task = repository.getById("id1");
    task.setName("newName");
    User updated = repository.update(task);
    assertEquals(task, updated);
  }

  @Test
  void testRemoveEntityById() throws EntityNotFoundException {
    repository.removeById("id1");
    assertEquals(1, repository.count());
  }

  @Test
  void testEntityExists() {
    assertTrue(repository.exists("id1"));
    assertFalse(repository.exists("id5"));
  }

  @Test
  void testGetEntityById() throws EntityNotFoundException {
    User user = repository.getById("id1");
    assertNotNull(user);
    assertEquals("id1", user.getId());

  }

}
