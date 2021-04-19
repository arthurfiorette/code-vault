package com.github.hazork.adventurepass.pass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Sound;

import com.github.hazork.adventurepass.task.Task;
import com.github.hazork.adventurepass.user.User;
import com.github.hazork.adventurepass.user.UserDependent;
import com.github.hazork.adventurepass.utils.MessageUtils;

import net.md_5.bungee.api.ChatMessageType;

public class PassHandler implements UserDependent {

    private final User user;

    private List<Prize> prizes = new ArrayList<>();
    private long points = 0;
    private int level;

    public PassHandler(User uuid) {
	this.user = uuid;
    }

    public void complete(Task task) {
	if (getTaskHandler().isCompleted(task)) {
	    points += task.getReward();
	    levelUp();
	}
    }

    private void levelUp() {
	if (points > getPointsToUp()) {
	    System.out.println(String.format("Level: %s para %s, com %s pontos", level, level + 1, points));
	    level++;
	    Optional.ofNullable(getLevel().getPrize()).ifPresent(prizes::add);
	    user.sendMessageSound(Sound.ENTITY_PLAYER_LEVELUP, ChatMessageType.ACTION_BAR,
		    "§aVocê upou para o nível: §f " + level);
	    levelUp();
	}
    }

    public String getProgressBar() {
	return String.format("§a%s %s", MessageUtils.percentage(points, getPointsToUp()),
		MessageUtils.progressBar(10, "§7▌", "§f▌", points, getPointsToUp()));
    }

    public Level getLevel() {
	return PassLoader.getLevel(level);
    }

    public int getPointsToUp() {
	return PassLoader.getNecessaryPoints(level + 1);
    }

    public int getIntLevel() {
	return level;
    }

    public long getPoints() {
	return points;
    }

    public List<Prize> getPrizes() {
	return prizes;
    }

    @Override
    public User getUser() {
	return user;
    }
}
