
package services;

import java.util.ArrayList;

import models.AdminUser;
import models.RegularUser;
import models.User;
import utils.Console;
import utils.ConsoleMenu;
import utils.CustomUtils;

public class UserService extends MainService {
  private final ArrayList<User> users;

  public UserService(final ArrayList<User> users) {
    this.users = users;
    title = "USER MENU";
  }

  private User getUserByEmail(final String email) {
    for (final User user : users) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  private User getUserById(final String id) {
    for (final User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  private void addUser() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("ADD USER");
    final String name = Console.getString("Enter User Name: ");
    final String email = Console.getEmailInput();
    final String password = Console.getPasswordInput("Enter User Password: ");
    final String role = Console.getString("Enter User Role(admin/user): ");
    final String id = CustomUtils.getNextId("U", users.size());
    User user;
    if (role == "admin")
      user = new AdminUser(id, name, email, password);
    else if (role == "user")
      user = new RegularUser(id, name, email, password);
    else
      throw new IllegalArgumentException("Invalid User Role");
    users.add(user);
    System.out.printf("✅User '%s\' added successfully\n", user.getName());
  }

  private void removeUser() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("REMOVE USER");
    final String id = Console.getString("Enter User ID: ");
    final User user = getUserById(id);
    users.remove(user);
    System.out.println("✅User Removed successfully");
  }

  private void listUsers() {
    ConsoleMenu.displayHeader("USER LIST");
    final StringBuilder sb = new StringBuilder();
    ConsoleMenu.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "EMAIL", "ROLE"));
    for (final User user : users) {
      sb.append(user.displayUser());
    }
    sb.append("\n");
    System.out.println(sb);
  }

  public void switchUser() {
    ConsoleMenu.displayHeader("SWITCH USER");
    final String email = Console.getEmailInput();
    final User user = getUserByEmail(email);
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
