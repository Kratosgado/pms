package com.kratosgado.pms.utils.enums;

public enum UserRole {
  REGULAR("Regular"),
  ADMIN("Admin");

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }

}
