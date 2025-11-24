
package utils;

import java.util.HashMap;

import models.AdminUser;
import models.HardwareProject;
import models.Project;
import models.RegularUser;
import models.SoftwareProject;
import models.User;

public class Seed {

  public static HashMap<String, User> seedUsers() {
    HashMap<String, User> users = new HashMap<>();
    User newUser = new AdminUser("Kratos", "kratos@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);
    newUser = new RegularUser("Mbeah", "mbeah@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);
    newUser = new RegularUser("Prince", "prince@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);
    newUser = new RegularUser("Gado", "gado@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);

    return users;
  }

  public static HashMap<String, Project> seedProjects() {
    HashMap<String, Project> projects = new HashMap<>();
    Project project = new SoftwareProject("PMS", "Project management system", 4, 2000.00);
    projects.put(project.getId(), project);

    project = new HardwareProject("Psuedo Interpreter", "Project management system", 4, 2000.00);
    projects.put(project.getId(), project);
    project = new SoftwareProject("Psuedo Runner", "Project management system", 4, 2000.00);
    projects.put(project.getId(), project);
    project = new SoftwareProject("Console", "Project management system", 4, 2000.00);
    projects.put(project.getId(), project);
    return projects;
  }
}
