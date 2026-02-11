
package com.kratosgado.pms.utils.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.Console;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.exceptions.UnauthorizedException;
import com.kratosgado.pms.utils.exceptions.UserNotFoundException;

public class AuthManager {
  private static final Logger logger = LoggerFactory.getLogger(AuthManager.class);
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
        logger.info("Authentication attempt for user: {}", email);
        user = usersDb.getByEmail(email);
        if (!user.getPassword().equals(password)) {
          logger.warn("Failed authentication attempt for user: {} - Invalid password", email);
          throw new UnauthorizedException("Email or Password is incorrect");
        }
        setCurrentUser(user);
        logger.info("User successfully authenticated: {} (ID: {}, Role: {})", user.getName(), user.getId(), user.getRole());
        CustomUtils.displaySuccess("User logged in");
      } catch (UserNotFoundException e) {
        logger.warn("Failed authentication attempt - User not found: {}", email);
        CustomUtils.displayError(e);
      } catch (UnauthorizedException e) {
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
    if (getCurrentUser() == null || !getCurrentUser().isAdmin()) {
      logger.warn("Unauthorized access attempt - Admin privileges required. Current user: {}",
          getCurrentUser() != null ? getCurrentUser().getName() : "None");
      throw new UnauthorizedException("Only Admin Users can perform this action");
    }
    logger.debug("Admin access granted for user: {}", getCurrentUser().getName());
  }

  /**
   * Checks if a user is currently logged in
   *
   * @return true if a user is logged in, false otherwise
   */
  public boolean isLoggedIn() {
    return currentUser != null;
  }

}
