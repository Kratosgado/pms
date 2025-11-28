
package services;

import java.util.ArrayList;

import models.AdminUser;
import models.RegularUser;
import models.User;
import utils.Console;
import utils.ConsoleMenu;
import utils.CustomUtils;

public class UserService extends MainService {
  private ArrayList<User> users;

  public UserService(ArrayList<User> users) {
    this.users = users;
    title = "USER MENU";
  }

  private User getUserByEmail(String email) {
    for (User user : users) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  private User getUserById(String id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  private void addUser() {
    ConsoleMenu.requireAdmin();
    ConsoleMenu.displayHeader("ADD USER");
    String name = Console.getString("Enter User Name: ");
    String email = Console.getEmailInput();
    String password = Console.getPasswordInput("Enter User Password: ");
    String role = Console.getString("Enter User Role(admin/user): ");
    String id = CustomUtils.getNextId("U", users.size());
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
    String id = Console.getString("Enter User ID: ");
    User user = getUserById(id);
    users.remove(user);
    System.out.println("✅User Removed successfully");
  }

  private void listUsers() {
    ConsoleMenu.displayHeader("USER LIST");
    StringBuilder sb = new StringBuilder();
    ConsoleMenu.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "EMAIL", "ROLE"));
    for (User user : users) {
      sb.append(user.displayUser());
    }
    sb.append("\n");
    System.out.println(sb);
  }

  public void switchUser() {
    ConsoleMenu.displayHeader("SWITCH USER");
    String email = Console.getEmailInput();
    User user = getUserByEmail(email);
    String password = Console.getPasswordInput("Enter User Password: ");
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
  public int handleChoice(int choice) {
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
    } catch (Exception e) {
      ConsoleMenu.displayError(e.getMessage());
    }
    return -1;
  }

}
