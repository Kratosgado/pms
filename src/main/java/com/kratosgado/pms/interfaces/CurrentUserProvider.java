
package com.kratosgado.pms.interfaces;

import com.kratosgado.pms.models.User;

public interface CurrentUserProvider {

  User getCurrentUser();

  void setCurrentUser(User user);
}
