
package utils;

import java.util.ArrayList;

import models.AdminUser;
import models.HardwareProject;
import models.Project;
import models.RegularUser;
import models.SoftwareProject;
import models.Task;
import models.User;

public class Seed {

  public static ArrayList<User> seedUsers() {
    ArrayList<User> users = new ArrayList<>();
    String id = CustomUtils.getNextId("U", users.size());
    User newUser = new AdminUser(id, "Kratos", "kratos@gmail.com", "password");
    users.add(newUser);
    id = CustomUtils.getNextId("U", users.size());
    newUser = new RegularUser(id, "Mbeah", "mbeah@gmail.com", "password");
    users.add(newUser);
    id = CustomUtils.getNextId("U", users.size());
    newUser = new RegularUser(id, "Prince", "prince@gmail.com", "password");
    users.add(newUser);
    id = CustomUtils.getNextId("U", users.size());
    newUser = new RegularUser(id, "Gado", "gado@gmail.com", "password");
    users.add(newUser);

    return users;
  }

  public static ArrayList<Project> seedProjects() {
    ArrayList<Project> projects = new ArrayList<>();
    String id = CustomUtils.getNextId("P", projects.size());
    Project project = new SoftwareProject(id, "PMS", 4, 1000.00);
    project.addTasks(Seed.seedTasks());
    projects.add(project);

    id = CustomUtils.getNextId("P", projects.size());
    project = new HardwareProject(id, "Psuedo Interpreter", 4, 2000.00);
    project.addTasks(Seed.seedTasks());
    projects.add(project);
    id = CustomUtils.getNextId("P", projects.size());
    project = new HardwareProject(id, "Psuedo Runner", 4, 4000.00);
    project.addTasks(Seed.seedTasks());
    projects.add(project);
    id = CustomUtils.getNextId("P", projects.size());
    project = new SoftwareProject(id, "Console", 4, 2000.00);

    project.addTasks(Seed.seedTasks());
    projects.add(project);
    id = CustomUtils.getNextId("P", projects.size());
    project = new SoftwareProject(id, "TSM", 4, 2000.00);
    project.addTasks(Seed.seedTasks());
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
