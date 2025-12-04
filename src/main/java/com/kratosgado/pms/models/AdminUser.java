
package com.kratosgado.pms.models;

import com.kratosgado.pms.utils.enums.UserRole;

public class AdminUser extends User {

  public AdminUser(String id, String name, String email, String password) {
    super(id, name, email, password, UserRole.ADMIN);
  }
}
