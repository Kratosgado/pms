
package utils;

import java.util.ArrayList;
import java.util.HashMap;

import models.AdminUser;
import models.HardwareProject;
import models.Project;
import models.RegularUser;
import models.SoftwareProject;
import models.Task;
import models.User;

public class Seed {

  public static HashMap<String, User> seedUsers() {
    HashMap<String, User> users = new HashMap<>();
    String id = CustomUtils.getNextId("U", users.size());
    User newUser = new AdminUser(id, "Kratos", "kratos@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);
    id = CustomUtils.getNextId("U", users.size());
    newUser = new RegularUser(id, "Mbeah", "mbeah@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);
    id = CustomUtils.getNextId("U", users.size());
    newUser = new RegularUser(id, "Prince", "prince@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);
    id = CustomUtils.getNextId("U", users.size());
    newUser = new RegularUser(id, "Gado", "gado@gmail.com", "password");
    users.put(newUser.getEmail(), newUser);

    return users;
  }

  public static ArrayList<Project> seedProjects() {
    ArrayList<Project> projects = new ArrayList<>();
    String id = CustomUtils.getNextId("P", projects.size());
    Project project = new SoftwareProject(id, "PMS", "Project management system", 4, 1000.00);
    projects.add(project);

    id = CustomUtils.getNextId("P", projects.size());
    project = new HardwareProject(id, "Psuedo Interpreter", "Project management system", 4, 2000.00);
    projects.add(project);
    id = CustomUtils.getNextId("P", projects.size());
    project = new SoftwareProject(id, "Psuedo Runner", "Project management system", 4, 4000.00);
    projects.add(project);
    id = CustomUtils.getNextId("P", projects.size());
    project = new SoftwareProject(id, "Console", "Project management system", 4, 2000.00);
    projects.add(project);
    id = CustomUtils.getNextId("P", projects.size());
    project = new SoftwareProject(id, "TSM", "Task management system", 4, 2000.00);
    projects.add(project);
    return projects;
  }

  public static ArrayList<Task> seedTasks() {
    ArrayList<Task> tasks = new ArrayList<>();
    String id = CustomUtils.getNextId("T", tasks.size());
    Task task = new Task(id, "Task 1");
    tasks.add(task);
    id = CustomUtils.getNextId("T", tasks.size());
    task = new Task(id, "Task 2");
    tasks.add(task);
    id = CustomUtils.getNextId("T", tasks.size());
    task = new Task(id, "Task 3", TaskStatus.IN_PROGRESS);
    tasks.add(task);
    id = CustomUtils.getNextId("T", tasks.size());
    task = new Task(id, "Task 4", TaskStatus.COMPLETED);
    tasks.add(task);
    return tasks;
  }
}
