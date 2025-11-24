package models;

import java.util.ArrayList;

import utils.CustomUtils;

public abstract class Project {
  private String id;
  private String name;
  private String description;
  private int teamSize;
  private double budget;

  private ArrayList<Task> tasks;

  public Project(String name, String description, int teamSize, double budget) {
    this.id = CustomUtils.generateID();
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
    return toString();

  }

  public String toString() {
    return System.out
        .format("\n %s | %s | %s | %s | %s | %s", id, name,
            this.getClass().getSimpleName(),
            description,
            teamSize, budget)
        .toString();
  }
}
