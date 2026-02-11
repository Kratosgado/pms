package com.kratosgado.pms.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kratosgado.pms.data.dto.ProjectDetailDto;
import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.JsonSerializable;
import com.kratosgado.pms.utils.enums.ProjectType;
import com.kratosgado.pms.utils.enums.TaskStatus;
import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.TaskNotFoundException;

public abstract class Project implements HasId, JsonSerializable {

  private final String id;
  private String name;
  private String description;
  private int teamSize;
  private double budget;

  private ArrayList<Task> tasks;

  public Project(String id, String name, String description, int teamSize, double budget) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.teamSize = teamSize;
    this.budget = budget;
    this.tasks = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getTeamSize() {
    return teamSize;
  }

  public void setTeamSize(int teamSize) {
    this.teamSize = teamSize;
  }

  public double getBudget() {
    return budget;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public void addTask(Task task) throws ConflictException {
    if (tasks.stream().anyMatch(t -> t.getId().equals(task.getId())))
      throw new ConflictException("Task with id " + task.getId() + " already exists");
    this.tasks.add(task);
  }

  public Task findTaskById(String taskId) throws TaskNotFoundException {
    return tasks.stream().filter(t -> t.getId().equals(taskId)).findFirst().orElseThrow(TaskNotFoundException::new);
  }

  public void removeTaskById(String taskId) throws TaskNotFoundException {

    boolean removed = tasks.removeIf(t -> t.getId().equals(taskId));
    if (!removed) {
      throw new TaskNotFoundException();
    }
  }

  synchronized public void updateTaskStatus(String taskId, TaskStatus status) throws TaskNotFoundException {
    Task task = findTaskById(taskId);
    task.setStatus(status);
  }

  public void updateTask(Task task) {
    for (int i = 0; i < tasks.size(); i++) {
      if (tasks.get(i).getId().equals(task.getId())) {
        tasks.set(i, task);
        return;
      }
    }
  }

  public List<Task> getTasks() {
    return Collections.unmodifiableList(tasks);
  }

  public void setTasks(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  public int getCompletedTasks() {
    return (int) tasks.stream().filter(task -> task.isCompleted()).count();
  }

  public double calculateCompletionPercentage() {
    if (getTasks().size() == 0)
      return 0;
    return ((double) getCompletedTasks() / getTasks().size() * 100);
  }

  /**
   * Filter tasks by status
   *
   * @param status The task status to filter by
   * @return List of tasks matching the status
   */
  public List<Task> getTasksByStatus(TaskStatus status) {
    return tasks.stream()
        .filter(task -> task.getStatus().equals(status))
        .toList();
  }

  /**
   * Filter tasks by assigned user
   *
   * @param userId The user ID to filter by
   * @return List of tasks assigned to the user
   */
  public List<Task> getTasksByUser(String userId) {
    return tasks.stream()
        .filter(task -> task.getUserId() != null && task.getUserId().equals(userId))
        .toList();
  }

  /**
   * Search tasks by name or description (partial match, case-insensitive)
   *
   * @param searchTerm The term to search for
   * @return List of tasks matching the search term
   */
  public List<Task> searchTasks(String searchTerm) {
    String lowerSearch = searchTerm.toLowerCase();
    return tasks.stream()
        .filter(task -> task.getName().toLowerCase().contains(lowerSearch))
        .toList();
  }

  /**
   * Get tasks with combined filters
   *
   * @param status     Optional status filter (null to skip)
   * @param userId     Optional user ID filter (null to skip)
   * @param searchTerm Optional search term (null to skip)
   * @return List of tasks matching all provided filters
   */
  public List<Task> getFilteredTasks(TaskStatus status, String userId, String searchTerm) {
    return tasks.stream()
        .filter(task -> status == null || task.getStatus().equals(status))
        .filter(task -> userId == null || (task.getUserId() != null && task.getUserId().equals(userId)))
        .filter(task -> searchTerm == null || task.getName().toLowerCase().contains(searchTerm.toLowerCase()))
        .toList();
  }

  /**
   * Get unassigned tasks
   *
   * @return List of tasks without assigned user
   */
  public List<Task> getUnassignedTasks() {
    return tasks.stream()
        .filter(task -> task.getUserId() == null || task.getUserId().isEmpty())
        .toList();
  }

  public abstract String getProjectDetails();

  public abstract ProjectType getProjectType();

  public static String getProjectType(String json) {
    int projectTypeStart = json.indexOf("\"projectType\":\"") + 15;
    int projectTypeEnd = json.indexOf("\",", projectTypeStart + 1);
    return json.substring(projectTypeStart, projectTypeEnd);
  }

  public ProjectDetailDto getDetails() {
    return new ProjectDetailDto(
        getId(),
        getName(),
        getDescription(),
        getProjectType(),
        getTeamSize(),
        getBudget(),
        calculateCompletionPercentage());

  }

  @Override
  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s|%-20s\n", id, name,
        getProjectType(),
        teamSize, budget);
  }

  public String toJson() {
    return "\n{\n\"id\":\"" + id +
        "\",\n\"name\":\"" + name +
        "\",\n\"description\":\"" + description
        + "\",\n\"projectType\":\"" + getProjectType() +
        "\",\n\"teamSize\": " + teamSize +
        ",\n\"budget\":\"" + budget +
        "\",\n\"tasks\":" + tasks.stream().map(task -> task.toJson()).toList().toString() + "\n}";
  }

}
