package com.kratosgado.pms.utils.enums;

public enum ProjectType {
  SOFTWARE("Software"),
  HARDWARE("Hardware");

  private String type;

  ProjectType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

}
