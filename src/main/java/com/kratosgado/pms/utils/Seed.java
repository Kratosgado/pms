
package com.kratosgado.pms.utils;

import java.util.ArrayList;

import com.kratosgado.pms.models.AdminUser;
import com.kratosgado.pms.models.HardwareProject;
import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.RegularUser;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.models.User;

public class Seed {

  public static User[] seedUsers() {
    final ArrayList<User> users = new ArrayList<>();
    final String prefix = "US";
    String id = CustomUtils.getNextId(prefix, users.size());
    User newUser = new AdminUser(id, "Kratos", "kratos@gmail.com", "password");
    users.add(newUser);
    id = CustomUtils.getNextId(prefix, users.size());
    newUser = new RegularUser(id, "Mbeah", "mbeah@gmail.com", "password");
    users.add(newUser);
    id = CustomUtils.getNextId(prefix, users.size());
    newUser = new RegularUser(id, "Prince", "prince@gmail.com", "password");
    users.add(newUser);
    id = CustomUtils.getNextId(prefix, users.size());
    newUser = new RegularUser(id, "Gado", "gado@gmail.com", "password");
    users.add(newUser);

    return users.toArray(new User[0]);
  }

  public static Project[] seedProjects() {
    final ArrayList<Project> projects = new ArrayList<>();
    final String prefix = "PJ";
    String id = CustomUtils.getNextId(prefix, projects.size());
    Project project = new SoftwareProject(id, "PMS", "Project management system", 4, 1000.00);
    projects.add(project);

    id = CustomUtils.getNextId(prefix, projects.size());
    project = new HardwareProject(id, "Psuedo Interpreter", "Project management system", 7, 2000.00);
    projects.add(project);
    id = CustomUtils.getNextId(prefix, projects.size());
    project = new HardwareProject(id, "Psuedo Runner", "Project management system", 11, 13000.00);
    projects.add(project);
    id = CustomUtils.getNextId(prefix, projects.size());
    project = new SoftwareProject(id, "Console", "Project management system", 9, 2000.00);

    projects.add(project);
    id = CustomUtils.getNextId(prefix, projects.size());
    project = new SoftwareProject(id, "TSM", "Task management system", 4, 5000.00);
    projects.add(project);
    return projects.toArray(new Project[0]);
  }

  public static Task[] seedTasks() {
    final ArrayList<Task> tasks = new ArrayList<>();
    final String prefix = "TS";
    for (int i = 0; i < 5; i++) {
      String projectId = CustomUtils.getNextId("PJ", i);
      for (int j = 0; j < 3; j++) {
        String id = CustomUtils.getNextId(prefix, tasks.size());
        Task task = new Task(id, "Task " + (i * 3 + j), projectId);
        tasks.add(task);
      }
    }
    return tasks.toArray(new Task[0]);
  }
}
