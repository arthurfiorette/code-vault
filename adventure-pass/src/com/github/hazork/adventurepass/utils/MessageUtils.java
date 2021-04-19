package com.github.hazork.adventurepass.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MessageUtils {

    public static String getMaterialName(Material material) {
	return WordUtils.capitalizeFully(material.toString().replaceAll("_", " "));
    }

    public static String getLocalizedName(Material material) {
	return getLocalizedName(new ItemStack(material));
    }

    public static String getLocalizedName(ItemStack item) {
	if (item.getItemMeta().hasLocalizedName()) {
	    return item.getItemMeta().getLocalizedName();
	} else {
	    return getMaterialName(item.getType());
	}
    }

    public static List<String> splitLoreColor(String lore) {
	return splitLores(ChatColor.getLastColors(lore), lore);
    }

    public static List<String> splitLores(String nextLine, String... lores) {
	return splitLores(String.join(" ", lores), 35, nextLine);
    }

    public static List<String> splitLores(String text, int width, String newLineText) {
	String nl = "2UJGQYUOFGXTAI123ue12ryt345q92fajgfuytwedgf";
	return Arrays.asList(WordUtils.wrap(text, width, nl + newLineText, false).split(nl));
    }

    public static String progressbar(int width, String incomplete, String complete, double progressPercentage) {
	StringBuilder sb = new StringBuilder();
	int i = 0;
	for (; i < (int) (progressPercentage * width); i++) {
	    sb.append(complete);
	}
	for (; i < width; i++) {
	    sb.append(incomplete);
	}
	return sb.toString();
    }

    public static String progressBar(int width, String incomplete, String complete, long currentValue, long maxValue) {
	return progressbar(width, incomplete, complete, (double) currentValue / maxValue);
    }

    public static String percentage(long value, long max) {
	return new BigDecimal((double) value / max * 100).setScale(2, RoundingMode.DOWN) + "%";
    }
}
