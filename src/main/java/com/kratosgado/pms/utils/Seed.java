
package com.kratosgado.pms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.models.User;
import com.kratosgado.pms.utils.enums.TaskStatus;

public class Seed {
  private static final Random random = new Random();

  public static HashMap<String, User> seedUsers() {
    final HashMap<String, User> users = new HashMap<>();
    final String prefix = "US";
    String id = CustomUtils.getNextId(prefix, users.size());
    User newUser = new AdminUser(id, "Kratos", "kratos@gmail.com", "password");
    users.put(newUser.getId(), newUser);
    id = CustomUtils.getNextId(prefix, users.size());
    newUser = new RegularUser(id, "Mbeah", "mbeah@gmail.com", "password");
    users.put(newUser.getId(), newUser);
    id = CustomUtils.getNextId(prefix, users.size());
    newUser = new RegularUser(id, "Prince", "prince@gmail.com", "password");
    users.put(newUser.getId(), newUser);
    id = CustomUtils.getNextId(prefix, users.size());
    newUser = new RegularUser(id, "Gado", "gado@gmail.com", "password");
    users.put(newUser.getId(), newUser);

    return users;
  }

  public static HashMap<String, Project> seedProjects() {
    final HashMap<String, Project> projects = new HashMap<>();
    final String prefix = "PJ";
    String id = CustomUtils.getNextId(prefix, projects.size());
    Project project = new SoftwareProject(id, "PMS", "Project management system", 4, 1000.00);
    project.setTasks(seedTasks(project.getName()));
    projects.put(project.getId(), project);

    id = CustomUtils.getNextId(prefix, projects.size());
    project = new SoftwareProject(id, "Steganography", "Hiding information in images", 4, 1000.00);
    project.setTasks(seedTasks(project.getName()));
    projects.put(project.getId(), project);

    id = CustomUtils.getNextId(prefix, projects.size());
    project = new HardwareProject(id, "Psuedo Interpreter", "Project management system", 7, 2000.00);
    project.setTasks(seedTasks(project.getName()));
    projects.put(project.getId(), project);
    id = CustomUtils.getNextId(prefix, projects.size());
    project = new HardwareProject(id, "Psuedo Runner", "Project management system", 11, 13000.00);
    project.setTasks(seedTasks(project.getName()));
    projects.put(project.getId(), project);
    id = CustomUtils.getNextId(prefix, projects.size());
    project = new SoftwareProject(id, "Console", "Project management system", 9, 2000.00);
    project.setTasks(seedTasks(project.getName()));
    projects.put(project.getId(), project);

    return projects;
  }

  private static TaskStatus getRandomTaskStatus() {
    return TaskStatus.values()[random.nextInt(0, TaskStatus.values().length)];
  }

  private static ArrayList<Task> seedTasks(String project) {
    final ArrayList<Task> tasks = new ArrayList<>();
    String id = CustomUtils.getNextId("TS", tasks.size());
    tasks.add(new Task(id, project + " task 1", getRandomTaskStatus()));
    id = CustomUtils.getNextId("TS", tasks.size());
    tasks.add(new Task(id, project + " task 2", getRandomTaskStatus()));
    id = CustomUtils.getNextId("TS", tasks.size());
    tasks.add(new Task(id, project + " task 3", getRandomTaskStatus()));
    id = CustomUtils.getNextId("TS", tasks.size());
    tasks.add(new Task(id, project + " task 4", getRandomTaskStatus()));
    id = CustomUtils.getNextId("TS", tasks.size());
    tasks.add(new Task(id, project + " task 5", getRandomTaskStatus()));
    return tasks;
  }
}
