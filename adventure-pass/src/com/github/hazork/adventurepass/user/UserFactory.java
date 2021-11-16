package com.github.hazork.adventurepass.user;

import static com.github.hazork.adventurepass.AdventurePlugin.getPlugin;

import com.github.hazork.adventurepass.AdventurePlugin;
import com.github.hazork.adventurepass.data.sql.SQLite;
import com.github.hazork.adventurepass.utils.CacheMap;
import com.github.hazork.adventurepass.utils.SpigotUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class UserFactory implements Listener {

  static CacheMap<UUID, User> userCache = new CacheMap<>(250, 10, ((K, V) -> save(V)));

  public static User from(Player player) {
    return from(player.getUniqueId());
  }

  public static User from(UUID uuid) {
    if (!userCache.containsKey(uuid)) {
      getPlugin().sendMessage("§5Load from data", false);
      load(uuid);
    }
    getPlugin().sendMessage("§5Cache return.", false);
    return userCache.get(uuid);
  }

  private static void load(UUID uuid) {
    try {
      PreparedStatement ps = SQLite.getPreparedStatement(
        "SELECT * FROM 'users' WHERE uuid = ?"
      );
      ps.setString(1, uuid.toString());
      ResultSet rs = ps.executeQuery();

      User request = null;
      if (!rs.isClosed()) {
        request = new JUser(rs.getString("user")).toUser();
      } else {
        request = new User(uuid);
      }
      userCache.put(uuid, request);
    } catch (SQLException e) {
      e.printStackTrace();
      getPlugin()
        .sendMessage(
          "§cErro ao carregar usuario da database, fechando plugin. Erro: " +
          e.getErrorCode(),
          true
        );
    }
  }

  private static void save(User user) {
    getPlugin().sendMessage("§5Saved user.", false);
    try {
      SpigotUtils.runTask(
        AdventurePlugin.getPlugin(),
        () -> UserController.closeMenu(user)
      );
      PreparedStatement ps = SQLite.getPreparedStatement(
        "INSERT OR REPLACE INTO 'users' (uuid, user) VALUES (?, ?)"
      );
      ps.setString(1, user.getUUID().toString());
      ps.setString(2, new JUser(user).toJson());
      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
      getPlugin()
        .sendMessage(
          "§cErro ao carregar usuario na database, fechando plugin. Erro: " +
          e.getErrorCode(),
          true
        );
    }
  }

  public static void close() {
    userCache.close();
  }
}
