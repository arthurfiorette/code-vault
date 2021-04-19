package com.github.hazork.adventurepass.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.hazork.adventurepass.command.SpigotCommand;
import com.github.hazork.adventurepass.menu.MenuOpener;
import com.github.hazork.adventurepass.menu.menus.PrizeMenu;

public class PrizeCommand implements SpigotCommand, MenuOpener {

    @Override
    public void handle(CommandSender sender, String[] arguments, String label) {
	open((Player) sender, PrizeMenu.NAME);
    }

    @Override
    public String getName() {
	return "prizes";
    }

    @Override
    public boolean onlyPlayer() {
	return true;
    }
}
