package models;

import java.util.ArrayList;

abstract class Project {
  private String ID;
  private String name;
  private String description;
  private int teamSize;
  private double budget;

  private ArrayList<Task> tasks;

  public Project(String ID, String name, String description, int teamSize, double budget) {
    this.ID = ID;
    this.name = name;
    this.description = description;
    this.teamSize = teamSize;
    this.budget = budget;
    this.tasks = new ArrayList<>();
  }

  public String getID() {
    return ID;
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
}
