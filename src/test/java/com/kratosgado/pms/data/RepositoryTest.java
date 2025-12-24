
package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public class RepositoryTest {
  private Repository<User> repository;

  @BeforeEach
  void setUp() throws ConflictException {
    this.repository = new UserInMemoryDatabase();
    repository.add(new RegularUser("id1", "name", "email", "password"));
    repository.add(new AdminUser("id2", "name", "email", "password"));

  }

  @Test
  void testAddEntity() throws ConflictException {
    User user = new RegularUser("id3", "name", "email", "password");
    repository.add(user);
    assertEquals(3, repository.count());
  }

  @ParameterizedTest
  @ValueSource(strings = { "Design buttons", "Design logo", "Design website" })
  void testEntityUpdate(String newName) throws EntityNotFoundException {
    User task = repository.getById("id1");
    task.setName(newName);
    User updated = repository.update(task);
    assertEquals(task, updated);
    assertEquals(newName, updated.getName());
  }

  @Test
  void testRemoveEntityById() throws EntityNotFoundException {
    repository.removeById("id1");
    assertEquals(1, repository.count());
  }

  @ParameterizedTest
  @CsvSource({ "id1,true", "id2,true", "id5,false", "id3,false" })
  void testEntityExists(String id, boolean expected) {
    assertEquals(expected, repository.exists(id));
  }

  @Test
  void testGetEntityById() throws EntityNotFoundException {
    User user = repository.getById("id1");
    assertNotNull(user);
    assertEquals("id1", user.getId());

  }

}
