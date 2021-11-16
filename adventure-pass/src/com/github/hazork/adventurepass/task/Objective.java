package com.github.hazork.adventurepass.task;

import com.github.hazork.adventurepass.task.objectives.BreakBlockObjective;
import com.github.hazork.adventurepass.task.objectives.PlaceBlockObjective;
import com.github.hazork.adventurepass.user.User;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class Objective implements Listener {

  protected final Task owner;
  protected final int times;

  protected Set<UUID> handlers = new HashSet<>();

  public Objective(Task owner, int times) {
    this.times = times;
    this.owner = owner;
  }

  public void registerFor(User user) {
    if (handlers.isEmpty()) load();
    handlers.add(user.getUUID());
  }

  public void unregisterFor(User user) {
    handlers.remove(user.getUUID());
    if (handlers.isEmpty()) unload();
  }

  protected void unload() {
    HandlerList.unregisterAll(this);
  }

  protected abstract void load();

  public abstract String getName();

  public abstract String getDescription();

  public abstract String getStepMesage(User user);

  public Task getOwner() {
    return owner;
  }

  public int getTimes() {
    return times;
  }

  public static enum Type {
    PLACE_BLOCK {
      @Override
      public Objective getObjective(Task owner, int times, String variable) {
        return new PlaceBlockObjective(owner, times, variable);
      }
    },
    BREAK_BLOCK {
      @Override
      public Objective getObjective(Task owner, int times, String variable) {
        return new BreakBlockObjective(owner, times, variable);
      }
    };

    public abstract Objective getObjective(Task owner, int times, String variable);
  }
}
