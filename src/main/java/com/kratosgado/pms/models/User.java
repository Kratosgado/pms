package com.kratosgado.pms.models;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.utils.enums.UserRole;

public abstract class User implements HasId {
  private final String id;
  private String name;
  private String email;
  private String password;
  private UserRole role;

  public User(String id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = UserRole.REGULAR;
  }

  public User(String id, String name, String email, String password, UserRole role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean isAdmin() {
    return role.equals(UserRole.ADMIN);
  }

  public String getRole() {
    return role.toString();
  }

  @Override
  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s\n", id, name, email, role);
  }
}
