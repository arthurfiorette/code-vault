package com.github.hazork.adventurepass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.hazork.adventurepass.command.CommandReader;
import com.github.hazork.adventurepass.data.sql.SQLite;
import com.github.hazork.adventurepass.data.yaml.YMLEditor;
import com.github.hazork.adventurepass.pass.PassLoader;
import com.github.hazork.adventurepass.task.TaskLoader;
import com.github.hazork.adventurepass.user.UserController;
import com.github.hazork.adventurepass.user.UserFactory;

public final class AdventurePlugin extends JavaPlugin {

    private static AdventurePlugin PLUGIN = null;

    public AdventurePlugin() {
	if (PLUGIN != null) throw new RuntimeException("ADVENTUREPASS ERROR: PLUGIN ALREADY RUNNING");
	PLUGIN = this;
    }

    @Override
    public void onEnable() {
	SQLite.open();
	YMLEditor.registerFor(this);

	TaskLoader.load();
	PassLoader.load();

	UserController.registerFor(this);
	CommandReader.registerFor(this);

    }

    @Override
    public void onDisable() {
	UserFactory.close();
	UserController.closeAllMenus();
	SQLite.close();
    }

    public void sendMessage(String message, boolean disable) {
	Bukkit.getConsoleSender().sendMessage(message);
	if (disable) getPluginLoader().disablePlugin(this);
    }

    public static AdventurePlugin getPlugin() {
	return PLUGIN;
    }
}
