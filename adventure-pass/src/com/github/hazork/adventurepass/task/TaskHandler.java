package com.github.hazork.adventurepass.task;

import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.user.UserDependent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Sound;

public class TaskHandler implements UserDependent {

  private final User user;

  private Map<Task, Integer> taskAdvancements = new HashMap<>();
  private Set<Task> completeTasks = new HashSet<>();
  private volatile Task active;

  public TaskHandler(User user) {
    this.user = user;
  }

  public void activate(Task task) {
    if (!completeTasks.contains(task)) {
      if (active != null) {
        active.getObjective().unregisterFor(user);
      }
      active = task;
      if (task != null) {
        task.getObjective().registerFor(user);
      }
    }
  }

  public void saveAdvancement(Task task, int advancement) {
    if (task.equals(active)) {
      taskAdvancements.merge(task, 1, Integer::sum);
      user.sendMessageSound(
        Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
        ChatMessageType.ACTION_BAR,
        active.getObjective().getStepMesage(user)
      );
      tryComplete();
    }
  }

  private void tryComplete() {
    if (taskAdvancements.get(active) >= active.getObjective().getTimes()) {
      user.sendMessageSound(
        Sound.ENTITY_PLAYER_LEVELUP,
        ChatMessageType.ACTION_BAR,
        "§aVoce completou a task:§f " + active.getObjective().getName()
      );
      completeTasks.add(active);
      taskAdvancements.remove(active);
      getPassHandler().complete(active);
      activate(null);
    }
  }

  public boolean isCompleted(Task task) {
    return completeTasks.contains(task);
  }

  public void recalculate() {
    Collection<Task> tasks = TaskLoader.getTasks();
    taskAdvancements =
      taskAdvancements
        .entrySet()
        .stream()
        .filter(e -> tasks.contains(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    completeTasks =
      completeTasks
        .stream()
        .filter(task -> tasks.contains(task))
        .collect(Collectors.toSet());
    active = (tasks.contains(active)) ? active : null;
  }

  public Integer getAdvancement(Task task) {
    return taskAdvancements.get(task);
  }

  public Map<Task, Integer> getTaskAdvancements() {
    return taskAdvancements;
  }

  public Set<Task> getCompletedTasks() {
    return completeTasks;
  }

  public Task getActive() {
    return active;
  }

  @Override
  public User getUser() {
    return user;
  }
}
