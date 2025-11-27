
package models;

public abstract class User {
  private String id;
  private String name;
  private String email;
  private String password;
  private Role role;

  protected enum Role {
    ADMIN, REGULAR
  }

  public User(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = "user123";
    this.role = Role.REGULAR;
  }

  public User(String id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = Role.REGULAR;
  }

  public User(String id, String name, String email, String password, Role role) {
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

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean isAdmin() {
    return role == Role.ADMIN;
  }

  public String getRole() {
    return role.toString();
  }

  public String displayUser() {
    return String.format("%s\t|%s\t\t\t|%s\t\t|%s\n", id, name, email, role);
  }

  public String toString() {
    return System.out
        .format("\nUser [name: %s, email: %s, role: %s]", getName(), getEmail(), getRole())
        .toString();
  }
}
