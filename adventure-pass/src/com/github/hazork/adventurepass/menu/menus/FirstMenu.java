package com.github.hazork.adventurepass.menu.menus;

import com.github.hazork.adventurepass.menu.Menu;
import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.utils.SpigotUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class FirstMenu extends Menu {

  public static final String NAME = "first";

  public FirstMenu(User user) {
    super(user, 3, "Menu inicial");
  }

  @Override
  protected Inventory createInventory(int lines, String title) {
    Inventory inv = super.createInventory(lines, title);
    inv.setItem(
      11,
      SpigotUtils.fromMaterial(
        Material.CAMPFIRE,
        "§bTasks disponíveis",
        "§7Veja todas as task disponíveis",
        "§7para coletar pontos no passe.",
        "",
        "§bClique para abrir."
      )
    );
    inv.setItem(
      13,
      SpigotUtils.fromMaterial(
        Material.CHEST_MINECART,
        "§aItens recebidos",
        "§7Os itens que você ganhar,",
        "§7aparecerão aqui.",
        "",
        "§aClique para abrir."
      )
    );
    inv.setItem(
      15,
      SpigotUtils.fromMaterial(
        Material.FILLED_MAP,
        "§eProgresso do passe",
        "§7Veja seu passe de aventura",
        "§7e os prêmios disponíveis.",
        "",
        "§eClique para abrir."
      )
    );
    return inv;
  }

  @Override
  protected void load() {
    inventory.setItem(
      13,
      SpigotUtils.fromMaterial(
        getPassHandler().getPrizes().size() > 0,
        Material.CHEST_MINECART,
        "§aItens recebidos",
        "§7Os itens que você ganhar,",
        "§7aparecerão aqui.",
        "",
        "§aClique para abrir."
      )
    );
  }

  @Override
  protected String getName() {
    return NAME;
  }

  @Override
  public void onMenuClick(InventoryClickEvent event) {
    super.onMenuClick(event);

    switch (SpigotUtils.getMaterial(event.getCurrentItem())) {
      case CAMPFIRE:
        getMenuView().open(TaskMenu.NAME, true);
        break;
      case CHEST_MINECART:
        getMenuView().open(PrizeMenu.NAME, true);
        break;
      case FILLED_MAP:
        getMenuView().open(PassMenu.NAME, true);
        break;
      default:
        break;
    }
  }
}
