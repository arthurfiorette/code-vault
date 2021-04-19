package com.github.hazork.adventurepass.data.yaml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.Maps;

public class YMLUtils {

    public static Map<String, Map<String, String>> getKeys(FileConfiguration fc, String section) {
	Map<String, Map<String, String>> request = new HashMap<>();
	ConfigurationSection cSection = fc.getConfigurationSection(section);
	cSection.getKeys(false).stream().forEach(key -> request.put(key, Maps.newHashMap()));
	cSection.getKeys(true).stream()
		.forEach(key -> request.get(getFirstKey(key)).put(getLastKeys(key), fc.getString(section + "." + key)));
	return request;
    }

    private static Object getFirstKey(String key) {
	return key.split("\\.")[0];
    }

    private static String getLastKeys(String key) {
	String[] keys = key.split("\\.");
	return String.join(".", Arrays.copyOfRange(keys, 1, keys.length));
    }

}
