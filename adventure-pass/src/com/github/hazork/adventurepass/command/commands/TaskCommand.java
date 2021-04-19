package com.github.hazork.adventurepass.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.hazork.adventurepass.command.SpigotCommand;
import com.github.hazork.adventurepass.menu.MenuOpener;
import com.github.hazork.adventurepass.menu.menus.TaskMenu;

public class TaskCommand implements SpigotCommand, MenuOpener {

    @Override
    public void handle(CommandSender sender, String[] arguments, String label) {
	open((Player) sender, TaskMenu.NAME);
    }

    @Override
    public String getName() {
	return "tasks";
    }

    @Override
    public boolean onlyPlayer() {
	return true;
    }
}
