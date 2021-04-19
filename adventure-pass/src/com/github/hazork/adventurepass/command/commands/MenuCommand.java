package com.github.hazork.adventurepass.command.commands;

import com.github.hazork.adventurepass.command.SpigotCommand;
import com.github.hazork.adventurepass.menu.MenuOpener;
import com.github.hazork.adventurepass.menu.menus.FirstMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements SpigotCommand, MenuOpener {

  @Override
  public void handle(CommandSender sender, String[] arguments, String label) {
    open((Player) sender, FirstMenu.NAME);
  }

  @Override
  public String getName() {
    return "menu";
  }

  @Override
  public boolean onlyPlayer() {
    return true;
  }
}
