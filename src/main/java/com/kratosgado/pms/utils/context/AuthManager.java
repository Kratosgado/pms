
package com.kratosgado.pms.utils.context;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.exceptions.UnauthorizedException;

public class AuthManager {
  private User currentUser;
  private final UserInMemoryDatabase usersDb;

  public AuthManager(UserInMemoryDatabase usersDb) {
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
          throw new UnauthorizedException("Email or Password is incorrect");
        }
        setCurrentUser(user);
        CustomUtils.displaySuccess("User logged in");
      } catch (final Exception e) {
        CustomUtils.displayError(e.getClass().getSimpleName(), e.getMessage());
      }
    } while (getCurrentUser() == null);
  }

  public void setCurrentUser(User user) {
    currentUser = user;
  }

  /**
   * Verifies that the user is an admin
   */
  public final void requireAdmin() {
    if (!getCurrentUser().isAdmin()) {
      throw new UnauthorizedException("Only Admin Users can perform this action");
    }
  }

}
