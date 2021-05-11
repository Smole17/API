package ru.smole.scheduler;

import org.apache.logging.log4j.Logger;
import ru.smole.mod.MinecraftMod;
import ru.smole.mod.Mod;
import ru.smole.scheduler.task.LaterTask;
import ru.smole.scheduler.task.SchedulerTask;
import ru.smole.scheduler.task.TimerTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scheduler extends Thread implements SchedulerProvider {

    private Map<Integer, SchedulerTask> tasks;
    private Logger logger;
    private int next_id = 0;

    public Scheduler(Mod mod) {
        super("Scheduler-Thread");
        if (!MinecraftMod.isInit())
            throw new RuntimeException("MinecraftMod not initialize");
        tasks = new HashMap<>();
        logger = mod.getLogger();
        start();
    }

    public SchedulerTask runTaskTimer(int delay, Runnable run) {
        int id = next_id++;
        return tasks.put(id, new TimerTask(id, delay, run));
    }

    public SchedulerTask runTaskLater(int delay, Runnable run) {
        int id = next_id++;
        return tasks.put(id, new LaterTask(id, delay, run));
    }

    public SchedulerTask runTask(Runnable run) {
        return runTaskLater(20, run);
    }

    public boolean cancelTask(int id) {
        return tasks.containsKey(id) && tasks.remove(id).isCancelled();
    }

    public boolean cancelAllTasks() {
        return new ArrayList<>(tasks.keySet()).stream().filter(id -> !cancelTask(id)).count() == 0;
    }

    public boolean isRunningTask(int id) {
        return tasks.containsKey(id);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (SchedulerTask task : new ArrayList<>(tasks.values())) {
                try {
                    if (task.needUpdate())
                        task.update();
                } catch (Exception e) {
                    logger.error("Error while executing task " + task.getId() + ":", e);
                }
            }

        }
    }
}
