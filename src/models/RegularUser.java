
package models;

public class RegularUser extends User {

  public RegularUser(String id, String name, String email, String password) {
    super(id, name, email, password, User.Role.REGULAR);
  }

}
