package com.github.hazork.adventurepass.pass;

public class Prize {

  private final String name;
  private final String description;
  private final String command;
  private final int level;

  public Prize(int level, String description, String command, String name) {
    this.level = level;
    this.name = name;
    this.description = description;
    this.command = command;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getCommand() {
    return command;
  }

  public Integer getLevel() {
    return Integer.valueOf(level);
  }
}
