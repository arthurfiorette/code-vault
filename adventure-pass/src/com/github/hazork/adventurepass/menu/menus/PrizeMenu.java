package com.github.hazork.adventurepass.menu.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.hazork.adventurepass.menu.ItemMenu;
import com.github.hazork.adventurepass.menu.Menu;
import com.github.hazork.adventurepass.pass.Prize;
import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.utils.SpigotUtils;
import com.google.common.collect.Lists;

public class PrizeMenu extends Menu {

    public static final String NAME = "prizes";

    private static UnaryOperator<Integer> slotFunction = i -> (i == 17 || i == 26) ? i + 2 : i;
    private static final ItemStack EMPTY_ITEM = SpigotUtils.fromMaterial(false, Material.GLASS_PANE, "§cSlot vazio",
	    "§7Sem mais itens para", "§7coletar.");

    private List<List<Prize>> prizeList = new ArrayList<>();
    private Map<ItemMenu, Prize> prizeLocal = new HashMap<>();
    private int page;

    public PrizeMenu(User user) {
	super(user, 5, "Prêmios");
    }

    @Override
    protected Inventory createInventory(int lines, String title) {
	Inventory inv = super.createInventory(lines, title);
	inv.setItem(0, SpigotUtils.fromMaterial(Material.CHEST, "§bInício", "§7Voltar ao menu principal.", "",
		"§bClique para ir ao início."));
	inv.setItem(40, SpigotUtils.fromMaterial(Material.BOOK, "§bAjuda", "§7Vejas as tasks disponíves",
		"§7e ao completá-las", "§7ganhe pontos!"));
	return inv;
    }

    @Override
    protected String getName() {
	return NAME;
    }

    @Override
    public void onMenuClick(InventoryClickEvent event) {
	super.onMenuClick(event);
	switch (event.getSlot()) {
	case 0:
	    getMenuView().open("first", true);
	    break;
	case 38:
	    previousPage();
	    break;
	case 42:
	    nextPage();
	    break;
	default:
	    ItemMenu local = new ItemMenu(event.getSlot(), page, event.getCurrentItem());
	    System.out.println(2);
	    if (prizeLocal.containsKey(local)) {
		System.out.println(1);
		Prize prize = prizeLocal.get(local);
		getPassHandler().getPrizes().remove(prize);
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
			prize.getCommand().replace("{player}", user.getName()));
	    }
	    load();
	    break;
	}
    }

    @Override
    protected void load() {
	prizeList = Lists.partition(getPassHandler().getPrizes(), 21);
	setDefault();
	if (!prizeList.isEmpty()) {
	    setInventory();
	}
    }

    private void setDefault() {
	inventory.clear(42);
	inventory.clear(38);
	for (int i = 10; i < 35; i++) {
	    i = slotFunction.apply(i);
	    inventory.setItem(i, EMPTY_ITEM);
	}
	if ((page + 1) < prizeList.size()) {
	    inventory.setItem(42, SpigotUtils.fromMaterial(Material.LIME_DYE, "§aAvançar",
		    "§7Veja os prêmios disponíveis da próxima página.", "", "§aClique para avançar"));
	}
	if (page > 0) {
	    inventory.setItem(38, SpigotUtils.fromMaterial(Material.RED_DYE, "§cVoltar",
		    "§7Veja os prêmios disponíveis da página anterior.", "", "§cClique para voltar"));
	}
    }

    private void setInventory() {
	int i = 10;
	for (Prize prize : prizeList.get(page)) {
	    i = slotFunction.apply(i);
	    ItemStack item = prizeToItem(prize);
	    inventory.setItem(i, item);
	    prizeLocal.put(new ItemMenu(i, page, item), prize);
	    i++;
	}
    }

    private ItemStack prizeToItem(Prize prize) {
	return SpigotUtils.fromMaterial(Material.CHEST, "§a" + prize.getName(), "§fPrêmio: §7" + prize.getDescription(),
		"", "§aClique para recolher.");

    }

    private void nextPage() {
	if ((page + 1) < prizeList.size()) {
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
