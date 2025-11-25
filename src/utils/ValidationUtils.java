
package utils;

public class ValidationUtils {

  public static TaskStatus validateTaskStatus(String status) {
    switch (status) {
      case "Pending":
        return TaskStatus.PENDING;
      case "In Progress":
        return TaskStatus.IN_PROGRESS;
      case "Completed":
        return TaskStatus.COMPLETED;
    }
    throw new IllegalArgumentException("Invalid Task Status");
  }
}
