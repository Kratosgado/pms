
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.CustomUtils;

public class UserInMemoryDatabase implements InMemoryDatabase<User> {
  private ArrayList<User> users;
  private static final String PREFIX = "US";

  public UserInMemoryDatabase() {
    users = new ArrayList<>();
  }

  @Override
  public User add(User model) {
    String id = CustomUtils.getNextId("USR", count());
    // TODO: User user = new User(id, model.getName(), model.getEmail(),
    // model.getPassword(), model.getRole());
    users.add(model);
    return model;
  }

  @Override
  public User update(User model) {
    // TODO: User user = new User(model.getId(), model.getName(), model.getEmail(),
    User user = getById(model.getId());
    user.setName(model.getName());
    return user;
  }

  @Override
  public ArrayList<User> getAll() {
    return new ArrayList<>(users);
  }

  @Override
  public User getById(String id) {
    for (final User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  public User getByEmail(String email) {
    for (final User user : users) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  @Override
  public User getWhere(String field, String value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getWhere'");
  }

  @Override
  public void removeById(String id) {
    User user = getById(id);
    users.remove(user);
  }

  @Override
  public int count() {
    return users.size();
  }

}
