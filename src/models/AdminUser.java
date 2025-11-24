
package models;

public class AdminUser extends User {

  public AdminUser(String name, String email, String password) {
    super(name, email, password);
  }

  @Override
  public boolean canUpdateProject() {
    return true;
  }

  @Override
  public boolean canDeleteProject() {
    return true;
  }

}
