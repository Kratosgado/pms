
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

  private User getUserById(String id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    throw new IllegalArgumentException("User not found");
  }

  public void addUser() {
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

  public void removeUser() {
    ConsoleMenu.displayHeader("REMOVE USER");
    String id = Console.getString("Enter User ID: ");
    User user = getUserById(id);
    users.remove(user);
    System.out.println("✅User Removed successfully");
  }

  public void displayUsers() {
    ConsoleMenu.displayHeader("USER LIST");
    for (User user : users) {
      System.out.printf("|| %s (%s) || %s \n", user.getName(), user.getId(), user.getRole());
    }
    System.out.println("||================================================================================||");
  }

  @Override
  public void displayMenu() {
    System.out.println("1. View Users");
    System.out.println("2. Add User");
    System.out.println("3. Remove User");
    System.out.println("4. View User Details");
  }

  @Override
  public int handleChoice(int choice) {
    try {
      switch (choice) {
        case 1:
          displayUsers();
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
        default:
          return choice;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }

}
