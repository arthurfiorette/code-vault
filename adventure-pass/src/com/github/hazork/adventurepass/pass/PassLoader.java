package com.github.hazork.adventurepass.pass;

import com.github.hazork.adventurepass.data.yaml.YMLEditor;
import com.github.hazork.adventurepass.data.yaml.YMLFile;
import com.github.hazork.adventurepass.data.yaml.YMLUtils;
import com.google.common.collect.Maps;
import com.udojava.evalex.Expression;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.bukkit.Material;

public class PassLoader {

  private static Map<Integer, Level> levelMap = new HashMap<>();

  private static String func = new String();

  public static Map<Integer, Level> getLevels() {
    return levelMap;
  }

  public static int getNecessaryPoints(int level) {
    return new Expression(
      func.replace("{level}", Integer.valueOf(level).toString())
    )
      .eval()
      .intValue();
  }

  public static Level getLevel(int level) {
    return levelMap.get(level);
  }

  public static int getLevel(long points) {
    long pts = 0;
    for (int i = 0; i >= 0; i++) {
      pts = getNecessaryPoints(i);
      if (pts > points) return i - 1;
    }
    return 0;
  }

  public static Prize getPrize(int level) {
    return getPrize(getLevel(level));
  }

  public static Prize getPrize(Level level) {
    return level.getPrize();
  }

  public static List<Prize> getPrizes(Integer... levels) {
    return getLevels(levels)
      .stream()
      .map(Level::getPrize)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
  }

  public static List<Level> getLevels(Integer... levels) {
    return Arrays
      .stream(levels)
      .map(PassLoader::getLevel)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
  }

  public static int levelsSize() {
    return levelMap.size();
  }

  public static void load() {
    levelMap = Maps.newHashMap();
    YMLFile file = YMLEditor.getFile("levels", true);
    file.copyFrom(null, false);
    func = file.getConfiguration(false).getString("options.level-func");
    YMLUtils
      .getKeys(file.getConfiguration(false), "levels")
      .forEach(PassLoader::putLevel);
  }

  private static void putLevel(String key, Map<String, String> map) {
    int number = Integer.parseInt(key);
    if (number < 0) {
      return;
    } else if (number == 0 || map.containsKey("empty")) {
      levelMap.put(number, new Level(number, null, null));
    } else {
      Material icon = Material.valueOf(map.get("icon"));
      String name = map.get("name");
      Prize prize = new Prize(
        number,
        map.get("description"),
        map.get("command"),
        name
      );
      levelMap.put(number, new Level(number, icon, prize));
    }
  }
}
