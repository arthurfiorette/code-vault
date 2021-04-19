package com.github.hazork.adventurepass.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SpigotUtils {

    public static void callEvent(Event event) {
	Bukkit.getPluginManager().callEvent(event);
    }

    public static <T> boolean isPlayer(T object) {
	return object instanceof Player;
    }

    public static Player getPlayer(Object object) {
	if (isPlayer(object)) return (Player) object;
	else return null;
    }

    public static void sendMessageAll(ChatMessageType type, String msg) {
	Bukkit.getOnlinePlayers().parallelStream().forEach(p -> p.spigot().sendMessage(type, new TextComponent(msg)));
    }

    public static void sendMessage(Player player, ChatMessageType type, String msg) {
	player.spigot().sendMessage(type, new TextComponent(msg));
    }

    public static void sendSoundAll(Sound sound) {
	Bukkit.getOnlinePlayers().stream().forEach(p -> p.playSound(p.getLocation(), sound, 3.0F, 0.5F));
    }

    public static void sendSound(Player player, Sound sound) {
	player.playSound(player.getLocation(), sound, 3.0F, 0.5F);
    }

    public static void sendMessageSound(Player player, ChatMessageType type, Sound sound, String msg) {
	sendMessage(player, type, msg);
	sendSound(player, sound);
    }

    public static void sendMessageSoundAll(ChatMessageType type, Sound sound, String msg) {
	sendMessageAll(type, msg);
	sendSoundAll(sound);
    }

    public static ItemStack fromMaterial(Material material, String displayName, String... lorelines) {
	return fromMaterial(false, material, displayName, lorelines);
    }

    public static ItemStack fromMaterial(boolean glow, Material material, String displayName, String... lorelines) {
	return fromMaterial(glow, material, 1, displayName, lorelines);
    }

    public static ItemStack fromMaterial(boolean glow, Material material, int amount, String displayName,
	    String... lorelines) {
	return setMeta(new ItemStack(material, amount), glow, displayName, lorelines);
    }

    public static ItemStack setMeta(ItemStack item, boolean glow, String displayName, String... lorelines) {
	if (glow) {
	    item.addUnsafeEnchantment(Enchantment.LUCK, 1);
	}
	setItemMeta(item, meta -> {
	    meta.setDisplayName(displayName);
	    meta.addItemFlags(ItemFlag.values());
	    if (lorelines != null) {
		List<String> lores = new ArrayList<>();
		Arrays.stream(lorelines).forEach(lore -> {
		    if (lore.length() > 35) {
			lores.addAll(MessageUtils.splitLoreColor(lore));
		    } else {
			lores.add(lore);
		    }
		});
		meta.setLore(lores);
	    }
	    return meta;
	});
	return item;
    }

    @Deprecated
    public static ItemStack getHead(String name) {
	return getHead(Bukkit.getOfflinePlayer(name));
    }

    public static ItemStack getHead(OfflinePlayer player) {
	return setItemMeta(new ItemStack(Material.PLAYER_HEAD), meta -> {
	    SkullMeta smeta = (SkullMeta) meta;
	    smeta.addItemFlags(ItemFlag.values());
	    smeta.setOwningPlayer(player);
	    return smeta;
	});
    }

    public static ItemStack setItemMeta(ItemStack item, UnaryOperator<ItemMeta> callback) {
	return item.setItemMeta(callback.apply(item.getItemMeta())) ? item : item;
    }

    public static Material getMaterial(ItemStack i) {
	return (i == null) ? Material.AIR : i.getType();
    }

    public static Material getRandomMaterial() {
	return Material.values()[new Random().nextInt(Material.values().length)];
    }

    public static Material getRandomMaterial(Predicate<Material> cond) {
	Material request = getRandomMaterial();
	while (!cond.test(request))
	    request = getRandomMaterial();
	return request;
    }

    public static void runTask(JavaPlugin plugin, Runnable runnable) {
	if (Bukkit.isPrimaryThread()) {
	    runnable.run();
	} else {
	    Bukkit.getScheduler().runTask(plugin, runnable);
	}
    }

}
