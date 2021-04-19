package com.github.hazork.adventurepass.data.yaml;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YMLFile {

    private final JavaPlugin plugin;
    private final String name;
    private final File file;
    private FileConfiguration configuration;

    YMLFile(JavaPlugin jpl, File folder, String name) {
	this.plugin = jpl;
	this.name = name + ".yml";
	this.file = new File(folder, this.name);
    }

    public void load() {
	configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void reset() {
	copyFrom(getName(), true);
    }

    public void copyFrom(String path, boolean replace) {
	if (!file.exists() && replace) plugin.saveResource(path == null ? name : path, replace);
	load();
    }

    public void setConfiguration(FileConfiguration configuration) {
	this.configuration = configuration;
    }

    public FileConfiguration getConfiguration(boolean reload) {
	if (reload) load();
	return configuration;
    }

    public String getName() {
	return name;
    }

    public File getFile() {
	return file;
    }
}