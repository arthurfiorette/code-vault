package com.github.hazork.adventurepass.command;

import com.github.hazork.adventurepass.command.commands.MenuCommand;
import com.github.hazork.adventurepass.command.commands.TaskCommand;
import com.github.hazork.adventurepass.utils.SpigotUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandReader implements CommandExecutor {

  private static final String COMMAND_NAME = "adventure";
  private static final String DEFAULT_ARGUMENT = "menu";
  private static CommandReader instance;

  private final Map<String, SpigotCommand> commandMap = new HashMap<>();

  private CommandReader() {
    registerCommand(new MenuCommand(), new TaskCommand());
  }

  private void registerCommand(SpigotCommand... cmds) {
    Arrays.stream(cmds).forEach(cmd -> commandMap.put(cmd.getName(), cmd));
  }

  @Override
  public boolean onCommand(
    CommandSender sender,
    Command command,
    String label,
    String[] args
  ) {
    SpigotCommand cmd = commandMap.get(
      args.length == 0 ? DEFAULT_ARGUMENT : args[0]
    );
    if (cmd != null) {
      if (cmd.onlyPlayer() && !SpigotUtils.isPlayer(sender)) {
        sender.sendMessage("§cSomente players podem executar este comando.");
      } else if (cmd.getPermission().isPresent()) {
        if (!sender.hasPermission(cmd.getPermission().get())) {
          sender.sendMessage(command.getPermissionMessage());
        }
      } else {
        cmd.handle(sender, args, label);
      }
    } else {
      sender.sendMessage("§cArgumento inválido, tente novamente.");
    }
    return true;
  }

  public static void registerFor(JavaPlugin plugin) {
    createIfNotExists();
    plugin.getCommand(COMMAND_NAME).setExecutor(instance);
  }

  private static void createIfNotExists() {
    instance = instance != null ? instance : new CommandReader();
  }
}
