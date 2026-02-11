package com.kratosgado.pms.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kratosgado.pms.utils.enums.UserRole;

public class UserTest {
  private User regularUser;
  private User adminUser;

  @BeforeEach
  void setUp() {
    regularUser = new RegularUser("U001", "John Doe", "john@example.com", "password123");
    adminUser = new AdminUser("U002", "Jane Admin", "jane@example.com", "admin123");
  }

  @Test
  void testUserCreation_withAllParameters() {
    assertNotNull(regularUser);
    assertEquals("U001", regularUser.getId());
    assertEquals("John Doe", regularUser.getName());
    assertEquals("john@example.com", regularUser.getEmail());
    assertEquals("password123", regularUser.getPassword());
  }

  @Test
  void testUserCreation_multipleUsers() {
    User user1 = new RegularUser("U003", "Bob", "bob@example.com", "pass123");
    User user2 = new AdminUser("U004", "Alice", "alice@example.com", "adminpass");

    assertNotNull(user1);
    assertNotNull(user2);
    assertEquals("REGULAR", user1.getRole());
    assertEquals("ADMIN", user2.getRole());
  }

  @Test
  void testGetId_returnsCorrectId() {
    assertEquals("U001", regularUser.getId());
    assertEquals("U002", adminUser.getId());
  }

  @Test
  void testGetName_returnsCorrectName() {
    assertEquals("John Doe", regularUser.getName());
    assertEquals("Jane Admin", adminUser.getName());
  }

  @Test
  void testSetName_updatesName() {
    regularUser.setName("John Updated");
    assertEquals("John Updated", regularUser.getName());
  }

  @Test
  void testGetEmail_returnsCorrectEmail() {
    assertEquals("john@example.com", regularUser.getEmail());
    assertEquals("jane@example.com", adminUser.getEmail());
  }

  @Test
  void testGetPassword_returnsCorrectPassword() {
    assertEquals("password123", regularUser.getPassword());
    assertEquals("admin123", adminUser.getPassword());
  }

  @Test
  void testIsAdmin_returnsFalseForRegularUser() {
    assertFalse(regularUser.isAdmin());
  }

  @Test
  void testIsAdmin_returnsTrueForAdminUser() {
    assertTrue(adminUser.isAdmin());
  }

  @Test
  void testGetRole_returnsCorrectRole() {
    assertEquals("REGULAR", regularUser.getRole());
    assertEquals("ADMIN", adminUser.getRole());
  }

  @Test
  void testIsAdmin_staticMethod_returnsTrueForAdminJson() {
    String adminJson = "{\"id\":\"U001\",\"role\":\"ADMIN\"}";
    assertTrue(User.isAdmin(adminJson));
  }

  @Test
  void testIsAdmin_staticMethod_returnsFalseForRegularJson() {
    String regularJson = "{\"id\":\"U001\",\"role\":\"REGULAR\"}";
    assertFalse(User.isAdmin(regularJson));
  }

  @Test
  void testToString_formatsCorrectly() {
    String result = regularUser.toString();
    assertNotNull(result);
    assertTrue(result.contains("U001"));
    assertTrue(result.contains("John Doe"));
    assertTrue(result.contains("john@example.com"));
    assertTrue(result.contains("REGULAR"));
  }

  @Test
  void testToJson_formatsCorrectly() {
    String json = regularUser.toJson();
    assertNotNull(json);
    assertTrue(json.contains("\"id\":\"U001\""));
    assertTrue(json.contains("\"name\":\"John Doe\""));
    assertTrue(json.contains("\"email\":\"john@example.com\""));
    assertTrue(json.contains("\"password\":\"password123\""));
    assertTrue(json.contains("\"role\":\"REGULAR\""));
  }

  @Test
  void testToJson_containsAllRequiredFields() {
    String json = adminUser.toJson();
    assertTrue(json.contains("id"));
    assertTrue(json.contains("name"));
    assertTrue(json.contains("email"));
    assertTrue(json.contains("password"));
    assertTrue(json.contains("role"));
  }

  @Test
  void testAdminUser_hasAdminRole() {
    assertEquals("ADMIN", adminUser.getRole());
    assertTrue(adminUser.isAdmin());
  }

  @Test
  void testRegularUser_hasRegularRole() {
    assertEquals("REGULAR", regularUser.getRole());
    assertFalse(regularUser.isAdmin());
  }
}
