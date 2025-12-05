
package com.kratosgado.pms.services;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.ValidationUtils;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;

public class UserService extends ConsoleService {
  private UserInMemoryDatabase usersDb;
  private final AuthManager authManager;

  public UserService(UserInMemoryDatabase userDb, AuthManager authManager) {
    this.usersDb = userDb;
    title = "USER MENU";
    this.authManager = authManager;
  }

  private void addUser() {
    authManager.requireAdmin();
    CustomUtils.displayHeader("ADD USER");
    final String name = Console.getString("Enter User Name: ");
    final String email = Console.getEmailInput();
    final String password = Console.getPasswordInput("Enter User Password: ");
    final UserRole role = ConsoleMenu.getInput("Enter User Role(Admin/Regular): ", input -> {
      return ValidationUtils.validateUserRole(input);
    });
    User user = usersDb.add(name, email, password, role);
    System.out.printf("✅User '%s\' added successfully\n", user.getName());
  }

  private void removeUser() {
    authManager.requireAdmin();
    CustomUtils.displayHeader("REMOVE USER");
    final String id = Console.getString("Enter User ID: ");
    if (!usersDb.removeById(id))
      throw new UserNotFoundException();
    System.out.println("✅User Removed successfully");
  }

  private void listUsers() {
    CustomUtils.displayHeader("USER LIST");
    final StringBuilder sb = new StringBuilder();
    CustomUtils.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s|%-20s", "ID", "NAME", "EMAIL", "ROLE"));
    for (final User user : usersDb.getAll()) {
      sb.append(user.toString());
    }
    sb.append("\n");
    System.out.println(sb);
  }

  public void switchUser() {
    CustomUtils.displayHeader("SWITCH USER");
    final String email = Console.getEmailInput();
    final User user = usersDb.getByEmail(email);
    final String password = Console.getPasswordInput("Enter User Password: ");
    if (!user.getPassword().equals(password)) {
      throw new IllegalArgumentException("Invalid Password");
    }
    System.out.println("✅User switched successfully");
    authManager.setCurrentUser(user);
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
      CustomUtils.displayError(e.getClass().getSimpleName(), e.getMessage());
    }
    return -1;
  }

}
