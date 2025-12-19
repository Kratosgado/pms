
package com.kratosgado.pms.services;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.kratosgado.pms.models.Project;
import com.kratosgado.pms.models.SoftwareProject;
import com.kratosgado.pms.models.Task;
import com.kratosgado.pms.utils.enums.TaskStatus;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.TaskNotFoundException;

public class TaskServiceTest {

  @Test
  void testParallelTaskSimulation() throws TaskNotFoundException, ConflictException {
    Project project = new SoftwareProject("id", "test", "test", 40, 10);
    project.addTask(new Task("TS001", "task1", TaskStatus.PENDING));
    project.addTask(new Task("TS002", "task1", TaskStatus.PENDING));
    project.addTask(new Task("TS003", "task1", TaskStatus.PENDING));

    BiFunction<String, TaskStatus, Void> updateFunction = (id, status) -> {
      System.out.println(Thread.currentThread().getName() + " updating task " + id + " -> " + status);
      try {
        project.updateTaskStatus(id, status);
      } catch (TaskNotFoundException e) {
        e.printStackTrace();
      }
      return null;
    };
    Thread t1 = new Thread(() -> updateFunction.apply("TS001", TaskStatus.PENDING), "Thread 1");
    Thread t2 = new Thread(() -> updateFunction.apply("TS002", TaskStatus.IN_PROGRESS), "Thread 2");
    Thread t3 = new Thread(() -> updateFunction.apply("TS003", TaskStatus.COMPLETED), "Thread 3");
    t1.start();
    t2.start();
    t3.start();
    try {
      t1.join();
      t2.join();
      t3.join();

      assert (project.findTaskById("TS001").getStatus() == TaskStatus.PENDING);
      assert (project.findTaskById("TS002").getStatus() == TaskStatus.IN_PROGRESS);
      assert (project.findTaskById("TS003").getStatus() == TaskStatus.COMPLETED);
    } catch (InterruptedException e) {
      e.printStackTrace();

    }

  }
}
