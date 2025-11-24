
package models;

public class AdminUser extends User {

  public AdminUser(String name, String email, String password) {
    super(name, email, password);
  }

  public boolean canUpdateProject() {
    return true;
  }

  public boolean canDeleteProject() {
    return true;
  }

}
