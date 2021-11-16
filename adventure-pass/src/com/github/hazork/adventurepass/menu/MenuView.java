package com.github.hazork.adventurepass.menu;

import com.github.hazork.adventurepass.menu.menus.FirstMenu;
import com.github.hazork.adventurepass.menu.menus.PassMenu;
import com.github.hazork.adventurepass.menu.menus.PrizeMenu;
import com.github.hazork.adventurepass.menu.menus.TaskMenu;
import com.github.hazork.adventurepass.user.User;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.inventory.Inventory;

public class MenuView {

  private HashMap<String, Menu> menus = new HashMap<>();

  public MenuView(User user) {
    addMenus(
      new FirstMenu(user),
      new TaskMenu(user),
      new PassMenu(user),
      new PrizeMenu(user)
    );
  }

  public void open(String menuName, boolean load) {
    Menu menu = menus.get(menuName);
    if (menu != null) {
      menu.open(load);
    }
  }

  public Collection<Menu> getMenus() {
    return menus.values();
  }

  public List<Inventory> getInventorys() {
    return getMenus().stream().map(m -> m.getInventory()).collect(Collectors.toList());
  }

  public Menu getMenu(String menuName) {
    return menus.get(menuName);
  }

  private void addMenus(Menu... menus) {
    Arrays.stream(menus).forEach(menu -> this.menus.put(menu.getName(), menu));
  }
}
