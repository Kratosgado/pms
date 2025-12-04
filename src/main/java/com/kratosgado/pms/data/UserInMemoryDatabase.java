
package com.kratosgado.pms.data;

import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.Seed;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class UserInMemoryDatabase extends Repository<User> {
  private static final String PREFIX = "US";

  public UserInMemoryDatabase() {
    entities = Seed.seedUsers();
  }

  public User add(String name, String email, String password, String role) {
    String id = CustomUtils.getNextId(PREFIX, count());
    return safeAdd(ModelFactory.createUser(id, name, email, password, UserRole.valueOf(role)));
  }

  public User getByEmail(String email) {
    for (final User user : entities) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new UserNotFoundException();
  }

  @Override
  public User add(User model) {
    return safeAdd(model);
  }

}
