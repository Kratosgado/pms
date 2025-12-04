
package com.kratosgado.pms.utils.context;

import java.util.ArrayDeque;
import java.util.Deque;

import com.kratosgado.pms.services.ConsoleService;

public class NavigationManager {
  private final Deque<ConsoleService> navigationStack;

  public NavigationManager() {
    this.navigationStack = new ArrayDeque<>();
  }

  public boolean canNavigateBack() {
    return navigationStack.size() > 1;
  }

  /**
   * Navigate to a new service
   * 
   * @param service
   */
  public void pushService(ConsoleService service) {
    navigationStack.push(service);
  }

  /**
   * Navigate back to the previous service
   */
  public ConsoleService popService() {
    return navigationStack.pop();
  }

  /**
   * Get the current service
   * 
   * @return
   */
  public ConsoleService peekService() {
    return navigationStack.peek();
  }

}
