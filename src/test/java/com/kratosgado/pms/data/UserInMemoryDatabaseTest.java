package com.kratosgado.pms.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;

public class UserInMemoryDatabaseTest {
  private UserInMemoryDatabase usersDb;

  @BeforeEach
  void setUp() {
    usersDb = new UserInMemoryDatabase();
  }

  @Test
  void testAdd_user_successfully() throws ConflictException {
    User user = new RegularUser("U001", "John Doe", "john@example.com", "password123");
    usersDb.add(user);

    assertEquals(1, usersDb.count());
    assertTrue(usersDb.exists("U001"));
  }

  @Test
  void testAdd_withParameters_createsUserSuccessfully() throws ConflictException {
    User user = usersDb.add("John Doe", "john@example.com", "password123", UserRole.REGULAR);

    assertNotNull(user);
    assertEquals("John Doe", user.getName());
    assertEquals("john@example.com", user.getEmail());
    assertNotNull(user.getId());
    assertEquals(1, usersDb.count());
  }

  @Test
  void testAdd_adminUser_createsAdminSuccessfully() throws ConflictException {
    User user = usersDb.add("Admin User", "admin@example.com", "admin123", UserRole.ADMIN);

    assertNotNull(user);
    assertTrue(user.isAdmin());
    assertEquals("ADMIN", user.getRole());
  }

  @Test
  void testAdd_duplicateId_throwsConflictException() throws ConflictException {
    User user1 = new RegularUser("U001", "User One", "user1@example.com", "pass1");
    User user2 = new RegularUser("U001", "User Two", "user2@example.com", "pass2");

    usersDb.add(user1);

    assertThrows(ConflictException.class, () -> {
      usersDb.add(user2);
    });
  }

  @Test
  void testGetByEmail_existingUser_returnsUser() throws ConflictException, UserNotFoundException {
    usersDb.add("John Doe", "john@example.com", "password123", UserRole.REGULAR);

    User user = usersDb.getByEmail("john@example.com");

    assertNotNull(user);
    assertEquals("john@example.com", user.getEmail());
    assertEquals("John Doe", user.getName());
  }

  @Test
  void testGetByEmail_nonExistentUser_throwsException() {
    assertThrows(UserNotFoundException.class, () -> {
      usersDb.getByEmail("nonexistent@example.com");
    });
  }

  @Test
  void testGetById_existingUser_returnsUser() throws ConflictException, EntityNotFoundException {
    User addedUser = usersDb.add("John Doe", "john@example.com", "password123", UserRole.REGULAR);
    String userId = addedUser.getId();

    User retrievedUser = usersDb.getById(userId);

    assertNotNull(retrievedUser);
    assertEquals(userId, retrievedUser.getId());
    assertEquals("John Doe", retrievedUser.getName());
  }

  @Test
  void testGetById_nonExistentUser_throwsException() {
    assertThrows(EntityNotFoundException.class, () -> {
      usersDb.getById("INVALID_ID");
    });
  }

  @Test
  void testRemoveById_existingUser_removesSuccessfully() throws ConflictException, EntityNotFoundException {
    User user = usersDb.add("John Doe", "john@example.com", "password123", UserRole.REGULAR);
    String userId = user.getId();

    assertEquals(1, usersDb.count());

    usersDb.removeById(userId);

    assertEquals(0, usersDb.count());
    assertFalse(usersDb.exists(userId));
  }

  @Test
  void testRemoveById_nonExistentUser_throwsException() {
    assertThrows(EntityNotFoundException.class, () -> {
      usersDb.removeById("INVALID_ID");
    });
  }

  @Test
  void testUpdate_existingUser_updatesSuccessfully() throws ConflictException, EntityNotFoundException {
    User user = usersDb.add("John Doe", "john@example.com", "password123", UserRole.REGULAR);
    user.setName("John Updated");

    User updatedUser = usersDb.update(user);

    assertEquals("John Updated", updatedUser.getName());
    assertEquals(user.getId(), updatedUser.getId());
  }

  @Test
  void testExists_existingUser_returnsTrue() throws ConflictException {
    User user = usersDb.add("John Doe", "john@example.com", "password123", UserRole.REGULAR);

    assertTrue(usersDb.exists(user.getId()));
  }

  @Test
  void testExists_nonExistentUser_returnsFalse() {
    assertFalse(usersDb.exists("INVALID_ID"));
  }

  @Test
  void testCount_initiallyZero() {
    assertEquals(0, usersDb.count());
  }

  @Test
  void testCount_afterAddingUsers() throws ConflictException {
    usersDb.add("User One", "user1@example.com", "pass1", UserRole.REGULAR);
    usersDb.add("User Two", "user2@example.com", "pass2", UserRole.REGULAR);
    usersDb.add("User Three", "user3@example.com", "pass3", UserRole.ADMIN);

    assertEquals(3, usersDb.count());
  }

  @Test
  void testGetAll_returnsAllUsers() throws ConflictException {
    usersDb.add("User One", "user1@example.com", "pass1", UserRole.REGULAR);
    usersDb.add("User Two", "user2@example.com", "pass2", UserRole.ADMIN);

    var allUsers = usersDb.getAll();

    assertEquals(2, allUsers.size());
  }

  @Test
  void testGetAll_emptyDatabase_returnsEmptyList() {
    var allUsers = usersDb.getAll();

    assertNotNull(allUsers);
    assertEquals(0, allUsers.size());
  }

  @Test
  void testGenerateUserId_generatesUniqueIds() throws ConflictException {
    User user1 = usersDb.add("User One", "user1@example.com", "pass1", UserRole.REGULAR);
    User user2 = usersDb.add("User Two", "user2@example.com", "pass2", UserRole.REGULAR);

    assertNotNull(user1.getId());
    assertNotNull(user2.getId());
    assertFalse(user1.getId().equals(user2.getId()));
  }
}
