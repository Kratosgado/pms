package com.kratosgado.pms;

import com.kratosgado.pms.data.ProjectInMemoryDatabase;
import com.kratosgado.pms.data.UserInMemoryDatabase;
import com.kratosgado.pms.utils.ConsoleMenu;
import com.kratosgado.pms.utils.Seed;
import com.kratosgado.pms.utils.context.AuthManager;
import com.kratosgado.pms.utils.context.NavigationManager;
import com.kratosgado.pms.utils.factories.ServiceFactory;

public class Main {

  public static void main(final String[] args) {
    final UserInMemoryDatabase usersDb = new UserInMemoryDatabase(Seed.seedUsers());
    // final TaskInMemoryDatabase tasksDb = new
    // TaskInMemoryDatabase(Seed.seedTasks());
    final ProjectInMemoryDatabase projectsDb = new ProjectInMemoryDatabase(Seed.seedProjects());
    final AuthManager authManager = new AuthManager(usersDb);
    final NavigationManager navigationManager = new NavigationManager();

    final ServiceFactory serviceFactory = new ServiceFactory(usersDb, projectsDb, authManager,
        navigationManager);
    navigationManager.pushService(serviceFactory.createMainService());

    final ConsoleMenu menu = new ConsoleMenu(navigationManager, authManager, serviceFactory);
    menu.run();
  }
}
