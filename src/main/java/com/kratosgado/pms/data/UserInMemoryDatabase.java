
package com.kratosgado.pms.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import com.kratosgado.pms.interfaces.Persists;
import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class UserInMemoryDatabase extends Repository<User> implements Persists {
  private static final String PREFIX = "US";
  private final String fileName;

  public UserInMemoryDatabase() {
    this.fileName = "users.json";
  }

  public UserInMemoryDatabase(String fileName) {
    this.fileName = fileName;
  }

  public UserInMemoryDatabase(HashMap<String, User> entities) {
    super(entities);
    this.fileName = "users.json";
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

  @Override
  public void saveData() throws IOException {
    Files.writeString(Path.of(fileName), entities.values().stream().map(user -> user.toJson()).toList().toString());

  }

  @Override
  public boolean dataExists() {
    return Files.exists(Path.of(fileName));
  }

  @Override
  public void loadData() throws IOException {
    String json = Files.readString(Path.of(fileName)).replace("\n", "").replace(" ", "");
    entities = new HashMap<>();
    for (String userStr : json.split("},")) {
      User user;
      if (User.isAdmin(userStr)) {
        user = AdminUser.fromJson(userStr);
        entities.put(user.getId(), user);
        continue;
      }
      user = RegularUser.fromJson(userStr);
      entities.put(user.getId(), user);
    }
  }

}
