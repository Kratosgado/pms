package com.kratosgado.pms.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kratosgado.pms.data.dto.ProjectDetailDto;
import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.interfaces.JsonSerializable;
import com.kratosgado.pms.utils.enums.ProjectType;

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

  public void addTask(Task task) {
    this.tasks.add(task);
  }

  public Task findTaskById(String taskId) {
    return tasks.stream().filter(t -> t.getId().equals(taskId)).findFirst().orElse(null);
  }

  public boolean removeTaskById(String taskId) {
    return tasks.removeIf(t -> t.getId().equals(taskId));
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
    return "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"description\":\"" + description
        + "\",\"projectType\":\"" + getProjectType() + "\",\"teamSize\":" + teamSize + ",\"budget\":\""
        + budget + "\",\"tasks\":" + tasks.stream().map(task -> task.toJson()).toList().toString() + "}";
  }

}
