package com.github.hazork.adventurepass.pass;

import java.util.Optional;

import org.bukkit.Material;

public class Level {

    private final Material icon;
    private final int number;
    private final Prize prize;

    public Level(int number, Material icon, Prize prize) {
	this.number = number;
	this.prize = prize;
	this.icon = icon;
    }

    public int getNumber() {
	return number;
    }

    public Prize getPrize() {
	return prize;
    }

    public Optional<Prize> getPrizeOpt() {
	return Optional.ofNullable(prize);
    }

    public Material getMaterial() {
	return icon;
    }
}
