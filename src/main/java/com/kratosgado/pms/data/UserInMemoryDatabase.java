
package com.kratosgado.pms.data;

import java.util.HashMap;

import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class UserInMemoryDatabase extends Repository<User> {
  private static final String PREFIX = "US";

  public UserInMemoryDatabase() {
  }

  public UserInMemoryDatabase(HashMap<String, User> entities) {
    super(entities);
  }

  public User add(String name, String email, String password, UserRole role) {
    String id = CustomUtils.getNextId(PREFIX, count());
    return safeAdd(ModelFactory.createUser(id, name, email, password, role));
  }

  public User getByEmail(String email) throws UserNotFoundException {
    return entities.values().stream().filter(user -> user.getEmail().equals(email)).findFirst()
        .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User add(User model) {
    return safeAdd(model);
  }

}
