package models;

import java.util.ArrayList;
import utils.ConsoleMenu;

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

  public String getDescription() {
    return description;
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
    return (int) tasks.stream().filter(task -> task.isCompleted()).count();
  }

  public double getProgress() {
    if (getTasks().size() == 0)
      return 0;
    return ((double) getCompletedTasks() / getTasks().size() * 100);
  }

  public abstract String getProjectType();

  public abstract String getProjectDetails();

  public String displayProject() {
    ConsoleMenu.displayHeader("PROJECT DETAILS: " + getId());
    StringBuilder sb = new StringBuilder();
    sb.append("\tName: ").append(name).append("\n");
    sb.append("\tDescription: ").append(description).append("\n");
    sb.append("\tType: ").append(getProjectType()).append("\n");
    sb.append("\tTeam Size: ").append(teamSize).append("\n");
    sb.append("\tBudget: ").append(budget).append("\n");

    sb.append("\tAssociated Tasks: ").append(tasks.size()).append("\n");

    ConsoleMenu.appendTableHeader(sb, String.format("%-20s|%-20s|%-20s", "ID", "NAME", "STATUS"));
    for (Task task : tasks) {
      sb.append(task.toString());
      sb.append("\n");
    }

    sb.append(String.format("Completion Rate (%): %.2f\n", getProgress()));
    return sb.toString();
  }

  public String toString() {
    return String.format("%-20s|%-20s|%-20s|%-20s|%-20s\n", id, name,
        getProjectType(),
        teamSize, budget);
  }
}
