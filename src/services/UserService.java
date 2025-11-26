
package services;

import java.util.ArrayList;

import models.RegularUser;
import models.User;
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
    System.out.println("Add User");
    System.out.print("Enter User Name: ");
    String name = ConsoleMenu.scanner.nextLine();
    System.out.print("Enter User Email: ");
    String email = ConsoleMenu.scanner.nextLine();
    System.out.print("Enter User Role: ");
    String role = ConsoleMenu.scanner.nextLine();
    String id = CustomUtils.getNextId("U", users.size());
    User user = new RegularUser(id, name, email, role);
    users.add(user);
    System.out.printf("✅User '%s\' added successfully\n", user.getName());
  }

  public void removeUser() {
    System.out.println("Remove User");
    System.out.print("Enter User ID: ");
    String id = ConsoleMenu.scanner.nextLine();
    User user = getUserById(id);
    users.remove(user);
    System.out.println("User Removed");
  }

  public void displayUsers() {
    System.out.println("||================================================================================||");
    System.out.println("|| User List");
    System.out.println("||================================================================================||\n");
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
    System.out.println("5. Exit");
  }

  @Override
  public int handleChoice(int choice) {
    // TODO Auto-generated method stub
    return super.handleChoice(choice);
  }

}
