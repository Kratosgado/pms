
package com.kratosgado.pms.models;

import com.kratosgado.pms.utils.enums.UserRole;

public class RegularUser extends User {

  public RegularUser(String id, String name, String email, String password) {
    super(id, name, email, password, UserRole.REGULAR);
  }

}
