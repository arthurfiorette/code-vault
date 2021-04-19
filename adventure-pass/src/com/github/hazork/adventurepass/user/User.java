package com.github.hazork.adventurepass.user;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.hazork.adventurepass.menu.MenuView;
import com.github.hazork.adventurepass.pass.PassHandler;
import com.github.hazork.adventurepass.task.TaskHandler;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class User {

    private final UUID uuid;

    private TaskHandler tasks;
    private PassHandler levels;
    private MenuView view;

    public User(UUID uuid) {
	this.uuid = uuid;
	tasks = new TaskHandler(this);
	levels = new PassHandler(this);
	view = new MenuView(this);
    }

    public void sendMessageSound(Sound sound, ChatMessageType type, String msg) {
	getPlayer().spigot().sendMessage(type, new TextComponent(msg));
	if (sound != null) {
	    getPlayer().playSound(getPlayer().getLocation(), sound, 3.0F, 0.5F);
	}
    }

    public UUID getUUID() {
	return uuid;
    }

    public TaskHandler getTaskHandler() {
	return tasks;
    }

    public PassHandler getPassHandler() {
	return levels;
    }

    public MenuView getMenuView() {
	return view;
    }

    public String getName() {
	return getPlayer().getName();
    }

    public Player getPlayer() {
	return (Bukkit.getPlayer(uuid) != null) ? Bukkit.getPlayer(uuid) : (Player) Bukkit.getOfflinePlayer(uuid);
    }
}
