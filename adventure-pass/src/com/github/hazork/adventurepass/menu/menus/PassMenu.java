package com.github.hazork.adventurepass.menu.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.hazork.adventurepass.menu.Menu;
import com.github.hazork.adventurepass.pass.Level;
import com.github.hazork.adventurepass.pass.PassLoader;
import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.utils.SpigotUtils;

public class PassMenu extends Menu {

    public static final String NAME = "pass";
    public static Function<Integer, ItemStack> EMPTY = (i) -> SpigotUtils.fromMaterial(Material.GLASS_PANE,
	    "§bNível: §f" + i, "§cNível sem prêmio.", "",
	    "§aPontos nescessários: §f" + PassLoader.getNecessaryPoints(i));
    public static Function<Integer, ItemStack> EMPTY_PREMIUM = (i) -> SpigotUtils.fromMaterial(
	    Material.YELLOW_STAINED_GLASS_PANE, "§bNível §ePremium§b: §f" + i, "§cNível sem prêmio.", "",
	    "§eVire um usuário premium para poder coletar.");

    private List<Level> showing = new ArrayList<>();
    private int index = 0;

    public PassMenu(User user) {
	super(user, 6, "Níveis");
    }

    @Override
    protected Inventory createInventory(int lines, String title) {
	Inventory inv = super.createInventory(lines, title);
	inv.setItem(0, SpigotUtils.fromMaterial(Material.CHEST, "§bInício", "§7Voltar ao menu principal.", "",
		"§bClique para ir ao início."));
	inv.setItem(49, SpigotUtils.fromMaterial(Material.BOOK, "§bAjuda", "§7Vejas as tasks disponíves",
		"§7e ao completá-las", "§7ganhe pontos!"));
	return inv;
    }

    @Override
    protected String getName() {
	return NAME;
    }

    @Override
    protected void load() {
	showing = PassLoader.getLevels(getIndexes());
	setDefault();
	if (!showing.isEmpty()) {
	    setInventory();
	}
    }

    @Override
    public void onMenuClick(InventoryClickEvent event) {
	super.onMenuClick(event);
	switch (SpigotUtils.getMaterial(event.getCurrentItem())) {
	case LIME_DYE:
	    nextIndex();
	    break;
	case RED_DYE:
	    previousIndex();
	    break;
	case CHEST:
	    if (event.getSlot() == 0) {
		getMenuView().open("first", true);
	    }
	    break;
	default:
	    break;
	}
    }

    private void setDefault() {
	inventory.clear(50);
	inventory.clear(48);
	inventory.setItem(4,
		SpigotUtils.setMeta(SpigotUtils.getHead(getPlayer()), false, "§3Status",
			"§7Nível: §f" + getPassHandler().getIntLevel(), "§7Pontos: §f" + getPassHandler().getPoints(),
			"§7Prêmium: §fNão"));
	if ((index + 1) < PassLoader.levelsSize() - 8) {
	    inventory.setItem(50, SpigotUtils.fromMaterial(Material.LIME_DYE, "§aAvançar",
		    "§7Veja as tasks da próxima página.", "", "§aClique para avançar"));
	}
	if (index > 0) {
	    inventory.setItem(48, SpigotUtils.fromMaterial(Material.RED_DYE, "§cVoltar",
		    "§7Veja as tasks da página anterior.", "", "§cClique para voltar"));
	}

	for (int i = 18; i <= 35; i++) {
	    if (i <= 26) {
		inventory.setItem(i, EMPTY.apply(i));
	    } else {
		inventory.setItem(i, EMPTY_PREMIUM.apply(i - 18));
	    }
	}
    }

    @SuppressWarnings("deprecation")
    private void setInventory() {
	for (int i = 0; i < 9; i++) {
	    Level level = showing.get(i);
	    inventory.setItem(18 + i, levelToItem(level));
	    if (level.equals(getPassHandler().getLevel())) {
		inventory.setItem(9 + i, SpigotUtils.setMeta(SpigotUtils.getHead("MHF_ArrowDown"), false,
			"§aEste é o seu nível atual", "§7Progresso:", getPassHandler().getProgressBar()));
	    } else if (inventory.getItem(9 + i) != null) {
		inventory.clear(9 + i);
	    }
	}
    }

    private ItemStack levelToItem(Level level) {
	return level == null ? EMPTY.apply(Integer.MIN_VALUE)
		: level.getPrize() == null ? EMPTY.apply(level.getNumber())
			: SpigotUtils.fromMaterial(getPassHandler().getIntLevel() >= level.getNumber(),
				level.getMaterial(), "§bNível: §f" + level.getNumber(),
				"§fNome: §7" + level.getPrize().getName(),
				"§fDescrição: §7" + level.getPrize().getDescription(), "",
				"§aPontos nescessários: §f" + PassLoader.getNecessaryPoints(level.getNumber()));
    }

    private Integer[] getIndexes() {
	Integer[] indexes = new Integer[9];
	for (int i = 0; i < 9; i++) {
	    indexes[i] = i + index;
	}
	return indexes;
    }

    private void nextIndex() {
	if ((index + 1) < PassLoader.levelsSize() - 8) {
	    index++;
	    load();
	}
    }

    private void previousIndex() {
	if (index > 0) {
	    index--;
	    load();
	}
    }

}
