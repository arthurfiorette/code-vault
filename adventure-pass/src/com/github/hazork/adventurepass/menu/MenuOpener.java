package com.github.hazork.adventurepass.menu;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.user.UserFactory;

public interface MenuOpener {

    default void open(User user, String name) {
	open(user.getMenuView(), name);
    }

    default void open(UUID uuid, String name) {
	open(UserFactory.from(uuid), name);
    }

    default void open(Player player, String name) {
	open(UserFactory.from(player), name);
    }

    default void open(MenuView view, String name) {
	view.open(name, true);
    }
}
