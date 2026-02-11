package com.kratosgado.pms.utils.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.UnauthorizedException;

public class AuthManagerTest {
  private AuthManager authManager;
  private UserInMemoryDatabase usersDb;
  private User regularUser;
  private User adminUser;

  @BeforeEach
  void setUp() throws ConflictException {
    usersDb = new UserInMemoryDatabase();
    regularUser = new RegularUser("U001", "John Doe", "john@example.com", "password123");
    adminUser = new AdminUser("U002", "Jane Admin", "jane@example.com", "admin123");

    usersDb.add(regularUser);
    usersDb.add(adminUser);

    authManager = new AuthManager(usersDb);
  }

  @Test
  void testAuthManager_initiallyNoUserLoggedIn() {
    assertNull(authManager.getCurrentUser());
    assertFalse(authManager.isLoggedIn());
  }

  @Test
  void testSetCurrentUser_setsUserCorrectly() {
    authManager.setCurrentUser(regularUser);
    assertEquals(regularUser, authManager.getCurrentUser());
    assertTrue(authManager.isLoggedIn());
  }

  @Test
  void testSetCurrentUser_updatesLoggedInStatus() {
    assertFalse(authManager.isLoggedIn());

    authManager.setCurrentUser(regularUser);
    assertTrue(authManager.isLoggedIn());
  }

  @Test
  void testGetCurrentUser_returnsNullWhenNotLoggedIn() {
    assertNull(authManager.getCurrentUser());
  }

  @Test
  void testGetCurrentUser_returnsCorrectUserWhenLoggedIn() {
    authManager.setCurrentUser(adminUser);
    User current = authManager.getCurrentUser();

    assertNotNull(current);
    assertEquals("U002", current.getId());
    assertEquals("Jane Admin", current.getName());
  }

  @Test
  void testIsLoggedIn_returnsFalseInitially() {
    assertFalse(authManager.isLoggedIn());
  }

  @Test
  void testIsLoggedIn_returnsTrueAfterLogin() {
    authManager.setCurrentUser(regularUser);
    assertTrue(authManager.isLoggedIn());
  }

  @Test
  void testRequireAdmin_throwsExceptionWhenNotLoggedIn() {
    assertThrows(UnauthorizedException.class, () -> {
      authManager.requireAdmin();
    });
  }

  @Test
  void testRequireAdmin_throwsExceptionWhenRegularUser() {
    authManager.setCurrentUser(regularUser);

    assertThrows(UnauthorizedException.class, () -> {
      authManager.requireAdmin();
    });
  }

  @Test
  void testRequireAdmin_doesNotThrowExceptionWhenAdminUser() {
    authManager.setCurrentUser(adminUser);

    // Should not throw exception
    authManager.requireAdmin();
  }

  @Test
  void testSetCurrentUser_canSwitchUsers() {
    authManager.setCurrentUser(regularUser);
    assertEquals("U001", authManager.getCurrentUser().getId());

    authManager.setCurrentUser(adminUser);
    assertEquals("U002", authManager.getCurrentUser().getId());
  }

  @Test
  void testSetCurrentUser_withNullUser() {
    authManager.setCurrentUser(regularUser);
    assertTrue(authManager.isLoggedIn());

    authManager.setCurrentUser(null);
    assertNull(authManager.getCurrentUser());
  }
}
