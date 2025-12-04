package com.kratosgado.pms;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.ServiceFactory;
import com.kratosgado.pms.data.TaskInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.utils.ConsoleMenu;

public class Main {

  public static void main(final String[] args) {
    final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase();
    final UserInMemoryDatabase usersDb = new UserInMemoryDatabase();
    final TaskInMemoryDatabase tasksDb = new TaskInMemoryDatabase();
    ApplicationContext applicationContext = new ApplicationContext(usersDb);

    final ServiceFactory serviceFactory = new ServiceFactory(usersDb, projectsDb, tasksDb, applicationContext);

    final ConsoleMenu menu = new ConsoleMenu(serviceFactory, applicationContext);
    menu.run();
  }
}
