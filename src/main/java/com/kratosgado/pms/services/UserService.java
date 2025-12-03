
package com.kratosgado.pms.services;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.CustomUtils;

public class UserService extends MainService {
  private UserInMemoryDatabase usersDb;

  public UserService(UserInMemoryDatabase userInMemoryDatabase) {
    this.usersDb = userInMemoryDatabase;
    title = "USER MENU";
  }

  private void addUser() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("ADD USER");
    final String name = Console.getString("Enter User Name: ");
    final String email = Console.getEmailInput();
    final String password = Console.getPasswordInput("Enter User Password: ");
    final String role = Console.getString("Enter User Role(admin/user): ");
    final String id = CustomUtils.getNextId("US", usersDb.count());
    User user;
    if (role.equals("admin"))
      user = new AdminUser(id, name, email, password);
    else if (role.equals("user"))
      user = new RegularUser(id, name, email, password);
    else
      throw new IllegalArgumentException("Invalid User Role");
    usersDb.add(user);
    System.out.printf("✅User '%s\' added successfully\n", user.getName());
  }

  private void removeUser() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("REMOVE USER");
    final String id = Console.getString("Enter User ID: ");
    usersDb.removeById(id);
    System.out.println("✅User Removed successfully");
  }

  private void listUsers() {
    ConsoleMenu.displayHeader("USER LIST");
    final StringBuilder sb = new StringBuilder();
    ConsoleMenu.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "EMAIL", "ROLE"));
    for (final User user : usersDb.getAll()) {
      sb.append(user.displayUser());
    }
    sb.append("\n");
    System.out.println(sb);
  }

  public void switchUser() {
    ConsoleMenu.displayHeader("SWITCH USER");
    final String email = Console.getEmailInput();
    final User user = usersDb.getByEmail(email);
    final String password = Console.getPasswordInput("Enter User Password: ");
    if (!user.getPassword().equals(password)) {
      throw new IllegalArgumentException("Invalid Password");
    }
    System.out.println("✅User switched successfully");
    MainService.setCurrentUser(user);
  }

  @Override
  protected void displayOptions() {
    System.out.println("1. View Users");
    System.out.println("2. Add User");
    System.out.println("3. Remove User");
    System.out.println("4. View User Details");
    System.out.println("5. Switch User");
  }

  @Override
  public int handleChoice(final int choice) {
    try {
      switch (choice) {
        case 1:
          listUsers();
          break;
        case 2:
          addUser();
          break;
        case 3:
          removeUser();
          break;
        case 4:
          System.out.println("User Details");
          break;
        case 5:
          switchUser();
          break;
        default:
          return choice;
      }
    } catch (final Exception e) {
      ConsoleMenu.displayError(e.getMessage());
    }
    return -1;
  }

}
