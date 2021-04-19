package com.github.hazork.adventurepass.menu;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.user.UserDependent;

public abstract class Menu implements UserDependent {

    protected final User user;
    protected final Inventory inventory;

    protected abstract String getName();

    protected abstract void load();

    public Menu(User user, int lines, String title) {
	this.user = user;
	this.inventory = createInventory(lines, title);
    }

    public void open(boolean load) {
	if (load) load();
	getPlayer().openInventory(inventory);
    }

    public Inventory getInventory() {
	return inventory;
    }

    public void onMenuClick(InventoryClickEvent event) {
	event.setCancelled(true);
    }

    protected Inventory createInventory(int lines, String title) {
	return Bukkit.createInventory(getPlayer(), lines * 9, title);
    }

    @Override
    public User getUser() {
	return user;
    }
}
