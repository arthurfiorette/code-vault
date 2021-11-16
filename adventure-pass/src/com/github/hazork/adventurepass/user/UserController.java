package com.github.hazork.adventurepass.user;

import com.github.hazork.adventurepass.menu.Menu;
import com.github.hazork.adventurepass.menu.MenuView;
import com.github.hazork.adventurepass.utils.SpigotUtils;
import java.util.Optional;
import java.util.UUID;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class UserController implements Listener {

  private UserController() {}

  @EventHandler
  public void onPlayerExit(PlayerQuitEvent e) {
    UUID uuid = e.getPlayer().getUniqueId();
    if (UserFactory.userCache.containsKey(uuid)) {
      UserFactory.userCache.removeSafety(uuid);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Inventory inv = event.getInventory();
    if (inv.getLocation() == null) {
      Player player = SpigotUtils.getPlayer(event.getWhoClicked());
      getMenu(player.getUniqueId(), inv)
        .ifPresent(menu -> {
          menu.onMenuClick(event);
        });
    }
  }

  private static UserController instance;

  public static void registerFor(JavaPlugin plugin) {
    createIfNotExists();
    Bukkit.getPluginManager().registerEvents(instance, plugin);
  }

  private static void createIfNotExists() {
    instance = instance != null ? instance : new UserController();
  }

  private static Optional<Menu> getMenu(UUID uuid, Inventory inv) {
    for (Menu menu : UserFactory.from(uuid).getMenuView().getMenus()) {
      if (menu.getInventory().equals(inv)) {
        return Optional.ofNullable(menu);
      }
    }
    return Optional.empty();
  }

  public static void closeAllMenus() {
    UserFactory.userCache.values().stream().forEach(UserController::closeMenu);
  }

  public static void closeMenu(User user) {
    Player player = user.getPlayer();
    Inventory top = player.getOpenInventory().getTopInventory();
    if (top != null && top.getLocation() == null) {
      MenuView mView = user.getMenuView();
      if (mView.getInventorys().contains(top)) {
        player.closeInventory();
        user.sendMessageSound(
          null,
          ChatMessageType.CHAT,
          "§cOops! §7Seu inventário foi fechado pois ele ficou muito tempo sem interação."
        );
      }
    }
  }
}
