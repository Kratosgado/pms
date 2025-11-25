
package models;

public class AdminUser extends User {

  public AdminUser(String id, String name, String email, String password) {
    super(id, name, email, password);
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
