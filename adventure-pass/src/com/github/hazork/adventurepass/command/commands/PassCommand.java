package com.github.hazork.adventurepass.command.commands;

import com.github.hazork.adventurepass.command.SpigotCommand;
import com.github.hazork.adventurepass.menu.MenuOpener;
import com.github.hazork.adventurepass.menu.menus.PassMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PassCommand implements SpigotCommand, MenuOpener {

  @Override
  public void handle(CommandSender sender, String[] arguments, String label) {
    open((Player) sender, PassMenu.NAME);
  }

  @Override
  public String getName() {
    return "pass";
  }

  @Override
  public boolean onlyPlayer() {
    return true;
  }
}
