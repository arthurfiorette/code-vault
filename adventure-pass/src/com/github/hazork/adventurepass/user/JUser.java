package com.github.hazork.adventurepass.user;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.hazork.adventurepass.pass.PassHandler;
import com.github.hazork.adventurepass.pass.PassLoader;
import com.github.hazork.adventurepass.pass.Prize;
import com.github.hazork.adventurepass.task.Task;
import com.github.hazork.adventurepass.task.TaskHandler;
import com.github.hazork.adventurepass.task.TaskLoader;
import com.google.gson.Gson;

public final class JUser {

    private static final Gson gson = new Gson();

    private transient User user;

    private String uuid;
    private String active;
    private long points;
    private Map<String, Integer> tasksAdvancements;
    private Set<String> completeTasks;
    private List<Integer> prizes;

    public JUser(User user) {
	this.user = user;
	this.active = user.getTaskHandler().getActive() == null ? null : user.getTaskHandler().getActive().getKey();
	this.uuid = user.getUUID().toString();
	this.points = user.getPassHandler().getPoints();
	this.tasksAdvancements = user.getTaskHandler().getTaskAdvancements().entrySet().stream()
		.collect(Collectors.toMap(e -> e.getKey().getKey(), Map.Entry::getValue));
	this.completeTasks = user.getTaskHandler().getCompletedTasks().stream().map(Task::getKey)
		.collect(Collectors.toSet());
	this.prizes = user.getPassHandler().getPrizes().stream().map(Prize::getLevel).collect(Collectors.toList());
    }

    public JUser(String json) {
	JUser juser = gson.fromJson(json, JUser.class);
	this.uuid = juser.uuid;
	this.active = juser.active;
	this.points = juser.points;
	this.tasksAdvancements = juser.tasksAdvancements;
	this.completeTasks = juser.completeTasks;
	this.prizes = juser.prizes;
    }

    public String toJson() {
	return gson.toJson(this);
    }

    public User toUser() {
	try {
	    if (user != null) return user;
	    user = new User(UUID.fromString(this.uuid));

	    if (active != null) {
		Field active = TaskHandler.class.getDeclaredField("active");
		active.setAccessible(true);
		active.set(user.getTaskHandler(), TaskLoader.getTask(this.active));
	    }
	    Field level = PassHandler.class.getDeclaredField("level");
	    level.setAccessible(true);
	    level.setInt(user.getPassHandler(), PassLoader.getLevel(this.points));

	    Field points = PassHandler.class.getDeclaredField("points");
	    points.setAccessible(true);
	    points.setLong(user.getPassHandler(), this.points);

	    Field prizes = PassHandler.class.getDeclaredField("prizes");
	    prizes.setAccessible(true);
	    prizes.set(user.getPassHandler(),
		    this.prizes.stream().map(PassLoader::getPrize).collect(Collectors.toList()));

	    Field taskAdvancements = TaskHandler.class.getDeclaredField("taskAdvancements");
	    taskAdvancements.setAccessible(true);
	    taskAdvancements.set(user.getTaskHandler(), this.tasksAdvancements.entrySet().stream()
		    .collect(Collectors.toMap(e -> TaskLoader.getTask(e.getKey()), Map.Entry::getValue)));

	    Field completeTasks = TaskHandler.class.getDeclaredField("completeTasks");
	    completeTasks.setAccessible(true);
	    completeTasks.set(user.getTaskHandler(),
		    this.completeTasks.stream().map(TaskLoader::getTask).collect(Collectors.toSet()));

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return user;
    }
}
