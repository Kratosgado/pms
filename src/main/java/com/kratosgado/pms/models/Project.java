package com.kratosgado.pms.models;

import java.util.ArrayList;

public abstract class Project {

  private String id;
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

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  public void addedTask(Task task) {
    tasks.add(task);
  }

  public void addTasks(ArrayList<Task> tasks) {
    this.tasks.addAll(tasks);
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

  public abstract String getProjectType();
}
