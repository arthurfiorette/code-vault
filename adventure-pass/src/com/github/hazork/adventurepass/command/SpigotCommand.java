package com.github.hazork.adventurepass.command;

import java.util.Optional;
import org.bukkit.command.CommandSender;

public interface SpigotCommand {
  void handle(CommandSender sender, String[] arguments, String label);

  String getName();

  boolean onlyPlayer();

  default Optional<String> getPermission() {
    return Optional.empty();
  }
}
