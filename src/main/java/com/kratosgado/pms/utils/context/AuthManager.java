
package com.kratosgado.pms.utils.context;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.exceptions.UnauthorizedException;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;

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
      CustomUtils.displayHeader("AUTHENTICATION");
      final String email = Console.getEmailInput();
      final String password = Console.getPasswordInput("Enter User Password: ");
      try {
        user = usersDb.getByEmail(email);
        if (!user.getPassword().equals(password)) {
          throw new UnauthorizedException("Email or Password is incorrect");
        }
        setCurrentUser(user);
        CustomUtils.displaySuccess("User logged in");
      } catch (UserNotFoundException | UnauthorizedException e) {
        CustomUtils.displayError(e);
      }
    } while (getCurrentUser() == null);
  }

  public void setCurrentUser(User user) {
    currentUser = user;
  }

  /**
   * Verifies that the user is an admin
   */
  public final void requireAdmin() throws UnauthorizedException {
    if (!getCurrentUser().isAdmin()) {
      throw new UnauthorizedException("Only Admin Users can perform this action");
    }
  }

}
