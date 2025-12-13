package com.kratosgado.pms.utils.factories;

import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.enums.UserRole;
import com.kratosgado.pms.utils.exceptions.InputValidationException;

public class ModelFactory {
  public static Project createProject(String id, String name, String description,
      int teamSize, double budget, ProjectType type) {
    switch (type) {
      case SOFTWARE:
        return new SoftwareProject(id, name, description, teamSize, budget);
      case HARDWARE:
        return new HardwareProject(id, name, description, teamSize, budget);
      default:
        throw new InputValidationException("Unknown project type: " + type);
    }
  }

  public static Project createProjectFromJson(String json) {
    if (Project.getProjectType(json).equals("HARDWARE")) {
      return HardwareProject.fromJson(json);
    }
    return SoftwareProject.fromJson(json);

  }

  public static User createUserFromJson(String json) {
    if (User.isAdmin(json)) {
      return AdminUser.fromJson(json);
    }
    return RegularUser.fromJson(json);

  }

  public static User createUser(String id, String name, String email, String password, UserRole role) {
    switch (role) {
      case ADMIN:
        return new AdminUser(id, name, email, password);
      case REGULAR:
        return new RegularUser(id, name, email, password);
      default:
        throw new InputValidationException("Unknown user role: " + role);
    }
  }
}
