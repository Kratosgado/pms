
package com.kratosgado.pms.data;

import java.util.ArrayList;

import com.kratosgado.pms.interfaces.InMemoryDatabase;
import com.kratosgado.pms.models.User;

public class UserInMemoryDatabase implements InMemoryDatabase<User> {
  private ArrayList<User> users;

  public UserInMemoryDatabase() {
    users = new ArrayList<>();
  }

  @Override
  public User add(User model) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public User update(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public ArrayList<User> getAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }

  @Override
  public User getById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getById'");
  }

  public User getByEmail(String email) {
    // for (final User user : MainService.users) {
    // if (user.getEmail().equals(email)) {
    // return user;
    // }
    // }
    throw new IllegalArgumentException("User not found");
  }

  @Override
  public User getWhere(String field, String value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getWhere'");
  }

  @Override
  public void removeById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }

  @Override
  public int count() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }

}
