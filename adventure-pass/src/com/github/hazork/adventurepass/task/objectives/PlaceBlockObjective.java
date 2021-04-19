package com.github.hazork.adventurepass.task.objectives;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.hazork.adventurepass.AdventurePlugin;
import com.github.hazork.adventurepass.task.Objective;
import com.github.hazork.adventurepass.task.Task;
import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.user.UserFactory;
import com.github.hazork.adventurepass.utils.MessageUtils;

public class PlaceBlockObjective extends Objective {

    private final Material material;

    public PlaceBlockObjective(Task owner, int times, String variable) {
	super(owner, times);
	this.material = Material.valueOf(variable);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
	UUID uuid = event.getPlayer().getUniqueId();
	if (handlers.contains(uuid)) {
	    if (event.getBlock().getType().equals(material)) {
		UserFactory.from(uuid).getTaskHandler().saveAdvancement(owner, 1);
	    }
	}
    }

    @Override
    protected void load() {
	Bukkit.getPluginManager().registerEvents(this, AdventurePlugin.getPlugin());
    }

    @Override
    public String getName() {
	return "Coloque blocos";
    }

    @Override
    public String getDescription() {
	return String.format("Coloque o bloco %s %s vezes.", MessageUtils.getLocalizedName(material), times);
    }

    @Override
    public String getStepMesage(User user) {
	return String.format("§aBloco colocado! Faltam: §f%s§a/§f%s§a.", user.getTaskHandler().getAdvancement(owner),
		times);
    }
}
