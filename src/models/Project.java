package models;

import java.util.ArrayList;

import utils.TaskStatus;

public abstract class Project {
  private String id;
  private String name;
  private int teamSize;
  private double budget;

  private ArrayList<Task> tasks;

  public Project(String id, String name, int teamSize, double budget) {
    this.id = id;
    this.name = name;
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

  public int getTeamSize() {
    return teamSize;
  }

  public double getBudget() {
    return budget;
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
    return (int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.COMPLETED).count();
  }

  public double getProgress() {
    if (getTasks().size() == 0)
      return 0;
    return ((double) getCompletedTasks() / getTasks().size() * 100);
  }

  public abstract String getProjectType();

  public abstract String getProjectDetails();

  public String displayProject() {
    StringBuilder sb = new StringBuilder();
    sb.append("\tName: ").append(name).append("\n");
    sb.append("\tType: ").append(getProjectType()).append("\n");
    sb.append("\tTeam Size: ").append(teamSize).append("\n");
    sb.append("\tBudget: ").append(budget).append("\n");

    sb.append("\tAssociated Tasks: ").append(tasks.size()).append("\n");
    sb.append("--------------------------------------------------------------------------------\n");
    sb.append("ID\t\t|NAME\t\t|STATUS\n");
    sb.append("--------------------------------------------------------------------------------\n");
    for (Task task : tasks) {
      sb.append(task.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s|%-20s\n", id, name,
        getProjectType(),
        teamSize, budget);
  }
}
