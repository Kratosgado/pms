
package com.kratosgado.pms.models;

import com.kratosgado.pms.utils.enums.UserRole;

public class AdminUser extends User {

  public AdminUser(String id, String name, String email, String password) {
    super(id, name, email, password, UserRole.ADMIN);
  }

  @Override
  public User fromJson(String json) {
    int idStart = json.indexOf("\"id\":\"") + 6;
    int idEnd = json.indexOf("\",", idStart + 1);
    String id = json.substring(idStart, idEnd);

    int nameStart = json.indexOf("\"name\":\"") + 8;
    int nameEnd = json.indexOf("\",", nameStart + 1);
    String name = json.substring(nameStart, nameEnd);

    int emailStart = json.indexOf("\"email\":\"") + 9;
    int emailEnd = json.indexOf("\",", emailStart + 1);
    String email = json.substring(emailStart, emailEnd);

    int passwordStart = json.indexOf("\"password\":\"") + 12;
    int passwordEnd = json.indexOf("\",", passwordStart + 1);
    String password = json.substring(passwordStart, passwordEnd);

    int roleStart = json.indexOf("\"role\":\"") + 8;
    int roleEnd = json.indexOf("\",", roleStart + 1);
    String role = json.substring(roleStart, roleEnd);

    User user = new AdminUser(id, name, email, password);
    return user;
  }
}
