package com.github.hazork.adventurepass.user;

import com.github.hazork.adventurepass.menu.MenuView;
import com.github.hazork.adventurepass.pass.PassHandler;
import com.github.hazork.adventurepass.task.TaskHandler;
import java.util.UUID;
import org.bukkit.entity.Player;

public interface UserDependent {
  User getUser();

  default MenuView getMenuView() {
    return getUser().getMenuView();
  }

  default PassHandler getPassHandler() {
    return getUser().getPassHandler();
  }

  default Player getPlayer() {
    return getUser().getPlayer();
  }

  default TaskHandler getTaskHandler() {
    return getUser().getTaskHandler();
  }

  default UUID getUUID() {
    return getUser().getUUID();
  }
}
