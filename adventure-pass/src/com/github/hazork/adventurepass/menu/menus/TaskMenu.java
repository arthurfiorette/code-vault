package com.github.hazork.adventurepass.menu.menus;

import com.github.hazork.adventurepass.menu.ItemMenu;
import com.github.hazork.adventurepass.menu.Menu;
import com.github.hazork.adventurepass.task.Task;
import com.github.hazork.adventurepass.task.TaskHandler;
import com.github.hazork.adventurepass.task.TaskLoader;
import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.utils.SpigotUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TaskMenu extends Menu {

  private static UnaryOperator<Integer> slotFunction = i ->
    (i == 26 || i == 35) ? i + 2 : i;
  private static final ItemStack COMPLETED_TASK = SpigotUtils.fromMaterial(
    Material.LIME_STAINED_GLASS_PANE,
    "§cTask completa",
    "§7Esta task já foi",
    "§7completada."
  );
  private static final ItemStack EMPTY_TASK = SpigotUtils.fromMaterial(
    Material.GLASS_PANE,
    "§cTask vazia",
    "§7Sem mais tasks."
  );

  public static final String NAME = "task";

  private List<List<Task>> taskList = new ArrayList<>();
  private Map<ItemMenu, Task> taskLocal = new HashMap<>();
  private int page = 0;

  public TaskMenu(User user) {
    super(user, 6, "Menu");
  }

  @Override
  protected String getName() {
    return NAME;
  }

  @Override
  protected Inventory createInventory(int lines, String title) {
    Inventory inv = super.createInventory(lines, title);
    inv.setItem(
      0,
      SpigotUtils.fromMaterial(
        Material.CHEST,
        "§bInício",
        "§7Voltar ao menu principal.",
        "",
        "§bClique para ir ao início."
      )
    );
    inv.setItem(
      49,
      SpigotUtils.fromMaterial(
        Material.BOOK,
        "§bAjuda",
        "§7Vejas as tasks disponíves",
        "§7e ao completá-las",
        "§7ganhe pontos!"
      )
    );
    for (int i = 2; i < 7; i++) {
      if (i == 4) continue;
      inv.setItem(
        i,
        SpigotUtils.fromMaterial(
          Material.YELLOW_STAINED_GLASS_PANE,
          "§6Task Semanal 2",
          "§7As tasks semanais resetam toda segunda feira as 00h00",
          "",
          "§6Em desenvolvimento"
        )
      );
    }
    return inv;
  }

  @Override
  protected void load() {
    taskList = Lists.partition(new ArrayList<>(TaskLoader.getTasks()), 21);
    setDefault();
    if (!taskList.isEmpty()) {
      setInventory();
    }
  }

  @Override
  public void onMenuClick(InventoryClickEvent event) {
    super.onMenuClick(event);
    switch (SpigotUtils.getMaterial(event.getCurrentItem())) {
      case RED_DYE:
        previousPage();
        break;
      case LIME_DYE:
        nextPage();
        break;
      case CHEST:
        if (event.getSlot() == 0) {
          getMenuView().open("first", true);
        }
        break;
      default:
        ItemMenu local = new ItemMenu(event.getSlot(), page, event.getCurrentItem());
        if (taskLocal.containsKey(local)) {
          TaskHandler handler = getTaskHandler();
          Task clicked = taskLocal.get(local);
          Task active = handler.getActive();
          if (clicked.equals(active)) {
            handler.activate(null);
          } else {
            handler.activate(clicked);
          }
          load();
        }
        break;
    }
  }

  private void setInventory() {
    int i = 19;
    TaskHandler handler = getTaskHandler();
    for (Task task : taskList.get(page)) {
      i = slotFunction.apply(i);
      ItemStack item = taskToItem(
        handler.getCompletedTasks().contains(task),
        task.equals(handler.getActive()),
        task
      );
      inventory.setItem(i, item);
      taskLocal.put(new ItemMenu(i, page, item), task);
      i++;
    }
  }

  private void setDefault() {
    inventory.clear(50);
    inventory.clear(48);
    inventory.setItem(
      4,
      SpigotUtils.setMeta(
        SpigotUtils.getHead(getPlayer()),
        false,
        "§3Status",
        "§7Nível: §f" + getPassHandler().getIntLevel(),
        "§7Pontos: §f" + getPassHandler().getPoints(),
        "§7Prêmium: §fNão"
      )
    );
    for (int i = 19; i < 44; i++) {
      i = slotFunction.apply(i);
      inventory.setItem(i, EMPTY_TASK);
    }
    if ((page + 1) < taskList.size()) {
      inventory.setItem(
        50,
        SpigotUtils.fromMaterial(
          Material.LIME_DYE,
          "§aAvançar",
          "§7Veja as tasks da próxima página.",
          "",
          "§aClique para avançar"
        )
      );
    }
    if (page > 0) {
      inventory.setItem(
        48,
        SpigotUtils.fromMaterial(
          Material.RED_DYE,
          "§cVoltar",
          "§7Veja as tasks da página anterior.",
          "",
          "§cClique para voltar"
        )
      );
    }
  }

  private ItemStack taskToItem(boolean complete, boolean glow, Task task) {
    String cor = glow ? "§a" : "§f";
    return complete
      ? COMPLETED_TASK
      : SpigotUtils.fromMaterial(
        glow,
        task.getIcon(),
        cor + task.getObjective().getName(),
        "§7Complete esta task para ganhar pontos.",
        "",
        cor + "Pontos: §7" + task.getReward(),
        cor + "Requísitos: §7" + task.getObjective().getDescription(),
        "",
        cor + (glow ? "Clique esquerdo para desativar." : "Clique esquerdo para ativar.")
      );
  }

  private void nextPage() {
    if ((page + 1) < taskList.size()) {
      page++;
      load();
    }
  }

  private void previousPage() {
    if (page > 0) {
      page--;
      load();
    }
  }
}
