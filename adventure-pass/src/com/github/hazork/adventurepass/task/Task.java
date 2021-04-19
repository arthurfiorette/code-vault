package com.github.hazork.adventurepass.task;

import org.bukkit.Material;

public class Task {

  private final String key;
  private final int reward;
  private final Material icon;
  private Objective objective;

  public Task(String key, int reward, Material icon) {
    this.key = key;
    this.reward = reward;
    this.icon = icon;
  }

  void setObjective(Objective obj) {
    objective = (objective == null) ? obj : objective;
  }

  public String getKey() {
    return key;
  }

  public int getReward() {
    return reward;
  }

  public Material getIcon() {
    return icon;
  }

  public Objective getObjective() {
    return objective;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((icon == null) ? 0 : icon.hashCode());
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    result = prime * result + ((objective == null) ? 0 : objective.hashCode());
    result = prime * result + reward;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Task other = (Task) obj;
    if (icon != other.icon) return false;
    if (key == null) {
      if (other.key != null) return false;
    } else if (!key.equals(other.key)) return false;
    if (objective == null) {
      if (other.objective != null) return false;
    } else if (!objective.equals(other.objective)) return false;
    if (reward != other.reward) return false;
    return true;
  }
}
