
package models;

import utils.CustomUtils;

public abstract class User {
  private String id;
  private String name;
  private String email;
  private String password;

  public User(String name, String email, String password) {
    id = CustomUtils.generateID();
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean canUpdateProject() {
    return false;
  }

  public boolean canDeleteProject() {
    return false;
  }

  public String getType() {
    return this.getClass().getSimpleName();
  }

  public String toString() {
    return System.out
        .format("\nUser [name: %s, email: %s, isAdmin: %s]", this.name, this.email, this.canDeleteProject())
        .toString();
  }
}
