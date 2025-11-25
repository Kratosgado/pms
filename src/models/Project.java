package models;

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

  public String getDescription() {
    return description;
  }

  public int getTeamSize() {
    return teamSize;
  }

  public double getBudget() {
    return budget;
  }

  abstract String getProjectDetails();

  public String displayProject() {
    return String.format("%s\t|%s\t\t\t|%s\t\t|%s\t\t|%s\t\t|%s\n", id, name,
        this.getClass().getSimpleName(),
        description,
        teamSize, budget);
  }

  public String toString() {
    return System.out
        .format("%s\t\t|%s\t\t|%s\t\t|%s\t\t|%s\t\t|%s\n", id, name,
            this.getClass().getSimpleName(),
            description,
            teamSize, budget)
        .toString();
  }
}
