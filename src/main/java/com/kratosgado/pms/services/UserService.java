
package com.kratosgado.pms.services;

import java.net.ConnectException;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.ValidationUtils;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;
import com.kratosgado.pms.utils.exceptions.UnauthorizedException;
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
    try {
      User user = usersDb.add(name, email, password, role);
      System.out.printf("✅User '%s\' added successfully\n", user.getName());
    } catch (ConflictException e) {
      CustomUtils.displayError(new ConnectException("User with email " + email + " already exists"));
    }
  }

  private void removeUser() {
    authManager.requireAdmin();
    CustomUtils.displayHeader("REMOVE USER");
    final String id = Console.getString("Enter User ID: ");
    try {
      usersDb.removeById(id);
      System.out.println("✅User Removed successfully");
    } catch (EntityNotFoundException e) {
      CustomUtils.displayError(new UserNotFoundException());
    }
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

  private void viewUserDetails() {
    String userId = Console.getString("Enter User Id: ");
    try {
      User user = usersDb.getById(userId);
      CustomUtils.displayHeader("USER DETAILS: " + userId);
      StringBuilder sb = new StringBuilder();

      sb.append("ID: ").append(user.getId()).append("\n");
      sb.append("NAME: ").append(user.getName()).append("\n");
      sb.append("EMAIL: ").append(user.getEmail()).append("\n");
      sb.append("ROLE: ").append(user.getRole()).append("\n");

      System.out.println(sb);
    } catch (EntityNotFoundException e) {
      CustomUtils.displayError(new UserNotFoundException());
    }
  }

  private void switchUser() {
    CustomUtils.displayHeader("SWITCH USER");
    final String email = Console.getEmailInput();
    final String password = Console.getPasswordInput("Enter User Password: ");
    try {
      final User user = usersDb.getByEmail(email);
      if (!user.getPassword().equals(password)) {
        throw new UnauthorizedException("Email or Password is incorrect");
      }
      System.out.println("✅User switched successfully");
      authManager.setCurrentUser(user);
    } catch (EntityNotFoundException e) {
      CustomUtils.displayError(new UserNotFoundException());
    }
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
          viewUserDetails();
          break;
        case 5:
          switchUser();
          break;
        default:
          return choice;
      }
    } catch (final Exception e) {
      CustomUtils.displayError(e);
    }
    return -1;
  }

}
