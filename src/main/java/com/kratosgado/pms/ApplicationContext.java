
package com.kratosgado.pms;

import java.util.ArrayDeque;
import java.util.Deque;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.services.ConsoleService;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.CustomUtils;

public class ApplicationContext {

  private User currentUser;
  private final Deque<ConsoleService> navigationStack;
  private final UserInMemoryDatabase usersDb;
  // Provide thread-safe getters/setters

  public ApplicationContext(UserInMemoryDatabase usersDb) {
    this.navigationStack = new ArrayDeque<>();
    this.usersDb = usersDb;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public final void authenticateUser() {
    User user = null;
    do {
      try {
        CustomUtils.displayHeader("AUTHENTICATION");
        final String email = Console.getEmailInput();
        user = usersDb.getByEmail(email);
        final String password = Console.getPasswordInput("Enter User Password: ");
        if (!user.getPassword().equals(password)) {
          throw new IllegalArgumentException("Invalid Password");
        }
        setCurrentUser(user);
        ConsoleMenu.displaySuccess("User logged in");
      } catch (final Exception e) {
        ConsoleMenu.displayError(e.getClass().getSimpleName(), e.getMessage());
      }
    } while (getCurrentUser() == null);
  }

  public void setCurrentUser(User user) {
    currentUser = user;
  }

  public Deque<ConsoleService> getNavigationStack() {
    return navigationStack;
  }

  public void pushService(ConsoleService service) {
    navigationStack.push(service);
  }

  public ConsoleService popService() {
    return navigationStack.pop();
  }

  public ConsoleService peekService() {
    return navigationStack.peek();
  }

  /**
   * Verifies that the user is an admin
   */
  public final void requireAdmin() {
    if (!getCurrentUser().isAdmin()) {
      throw new IllegalArgumentException("Only Admin Users can perform this action");
    }
  }

}
