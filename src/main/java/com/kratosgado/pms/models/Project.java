package com.kratosgado.pms.models;

import java.util.Arrays;

import com.kratosgado.pms.interfaces.HasId;
import com.kratosgado.pms.utils.CustomUtils;
import com.kratosgado.pms.utils.enums.ProjectType;

public abstract class Project implements HasId {

  private final String id;
  private String name;
  private String description;
  private int teamSize;
  private double budget;

  private Task[] tasks;

  public Project(String id, String name, String description, int teamSize, double budget) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.teamSize = teamSize;
    this.budget = budget;
    this.tasks = new Task[CustomUtils.DEFAULT_MEMORY_CAPACITY];
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

  public Task[] getTasks() {
    return Arrays.copyOf(tasks, tasks.length);
  }

  public void setTasks(Task[] tasks) {
    this.tasks = tasks;
  }

  public int getCompletedTasks() {
    return (int) Arrays.stream(tasks).filter(task -> task.isCompleted()).count();
  }

  public double calculateCompletionPercentage() {
    if (getTasks().length == 0)
      return 0;
    return ((double) getCompletedTasks() / getTasks().length * 100);
  }

  public abstract String getProjectDetails();

  public abstract ProjectType getProjectType();

  @Override
  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s|%-20s\n", id, name,
        getProjectType(),
        teamSize, budget);
  }

}
