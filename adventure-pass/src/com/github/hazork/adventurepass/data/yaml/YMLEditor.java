package com.github.hazork.adventurepass.data.yaml;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class YMLEditor {

  private static JavaPlugin plugin;
  private static Map<String, YMLFile> filemap = new HashMap<>();

  private YMLEditor() {}

  public static FileConfiguration getConfigYml() {
    return plugin.getConfig();
  }

  public static void registerFor(JavaPlugin plugin) {
    YMLEditor.plugin = plugin;
    plugin.saveDefaultConfig();
  }

  public static YMLFile createFile(File folder, String name) {
    YMLFile file = new YMLFile(
      plugin,
      ((folder == null) ? plugin.getDataFolder() : folder),
      name
    );
    file.load();
    addFiles(file);
    return file;
  }

  public static void addFiles(YMLFile... files) {
    Arrays.stream(files).forEach(file -> filemap.put(file.getName(), file));
  }

  public static YMLFile getFile(String name) {
    return getFile(name, false);
  }

  public static YMLFile getFile(String name, boolean createIfNotExists) {
    YMLFile file = filemap.get(name);
    if (createIfNotExists) file =
      (file == null) ? createFile(null, name) : file;
    return file;
  }

  public static void load() {
    filemap.values().stream().forEach(yml -> yml.load());
  }
}
