
package com.kratosgado.pms.data;

import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.Seed;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;

public class UserInMemoryDatabase extends Repository<User> {
  private static final String PREFIX = "US";

  public UserInMemoryDatabase() {
    entities = Seed.seedUsers();
  }

  @Override
  public User add(User model) {
    String id = CustomUtils.getNextId(PREFIX, count());
    model.setId(id);
    return innerAdd(model);
  }

  public User getByEmail(String email) {
    for (final User user : entities) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new UserNotFoundException();
  }

}
