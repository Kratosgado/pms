
package com.kratosgado.pms.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kratosgado.pms.interfaces.Persists;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;
import com.kratosgado.pms.utils.factories.ModelFactory;

public class UserInMemoryDatabase extends Repository<User> implements Persists {
  private static final Logger logger = LoggerFactory.getLogger(UserInMemoryDatabase.class);
  private static final String PREFIX = "US";
  private final String fileName;

  public UserInMemoryDatabase() {
    this.fileName = "users.json";
  }

  public UserInMemoryDatabase(String fileName) {
    this.fileName = fileName;
  }

  /**
   * @param fileName File to save and load data from
   * @param entities Entiies to seed the database
   */
  public UserInMemoryDatabase(String fileName, HashMap<String, User> entities) {
    this.fileName = fileName;
    if (!fileExists()) {
      this.entities = entities;
    }
  }

  public User add(String name, String email, String password, UserRole role) throws ConflictException {
    String id = CustomUtils.getNextId(PREFIX, count());
    User user = ModelFactory.createUser(id, name, email, password, role);
    logger.info("Creating new user: {} (ID: {}, Email: {}, Role: {})", name, id, email, role);
    return safeAdd(user);
  }

  public User getByEmail(String email) throws UserNotFoundException {
    return entities.values().stream().filter(user -> user.getEmail().equals(email)).findFirst()
        .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User add(User model) throws ConflictException {
    return safeAdd(model);
  }

  @Override
  public void saveData() throws IOException {
    Files.writeString(Path.of(fileName), entities.values().stream().map(user -> user.toJson()).toList().toString());

    System.out.println("Saved " + count() + " users" + " to " + fileName);

  }

  @Override
  public boolean fileExists() {
    return Files.exists(Path.of(fileName));
  }

  @Override
  public void loadData() throws IOException {
    String json = readFile(fileName);
    entities = new HashMap<>();
    if (json.contains("id"))
      for (String userStr : json.split("},")) {
        User user = ModelFactory.createUserFromJson(userStr);
        entities.put(user.getId(), user);
      }
    System.out.println("Loaded " + count() + " users" + " from " + fileName);
  }

}
