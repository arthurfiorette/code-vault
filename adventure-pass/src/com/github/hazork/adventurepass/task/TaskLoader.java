package com.github.hazork.adventurepass.task;

import com.github.hazork.adventurepass.data.yaml.YMLEditor;
import com.github.hazork.adventurepass.data.yaml.YMLFile;
import com.github.hazork.adventurepass.data.yaml.YMLUtils;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.Material;

public class TaskLoader {

  private static Map<String, Task> taskMap = Maps.newHashMap();

  public static Collection<Task> getTasks() {
    return taskMap.values();
  }

  public static List<Task> getTasks(String... keys) {
    return Arrays
      .stream(keys)
      .map(TaskLoader::getTask)
      .collect(Collectors.toList());
  }

  public static Task getTask(String key) {
    return taskMap.get(key);
  }

  public static int getTaskSize() {
    return taskMap.size();
  }

  public static void load() {
    taskMap = Maps.newHashMap();
    YMLFile file = YMLEditor.getFile("tasks", true);
    file.copyFrom(null, false);
    YMLUtils
      .getKeys(file.getConfiguration(true), "tasks")
      .forEach(
        (key, map) -> {
          Material icon = Material.valueOf(map.get("icon"));
          int reward = Integer.parseInt(map.get("reward"));
          Task task = new Task(key, reward, icon);
          task.setObjective(
            Objective.Type
              .valueOf(map.get("detector.variant"))
              .getObjective(
                task,
                Integer.parseInt(map.get("detector.multiplier")),
                map.get("detector.variable")
              )
          );
          taskMap.put(key, task);
        }
      );
  }
}
