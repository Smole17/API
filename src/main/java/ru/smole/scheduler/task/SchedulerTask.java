package ru.smole.scheduler.task;

import ru.smole.mod.MinecraftMod;

public interface SchedulerTask {

    int getId();

    int getDelay();

    boolean needUpdate();

    Runnable getRunnable();

    default boolean cancel() {
        if (MinecraftMod.isInit())
            return MinecraftMod.getInstance().getScheduler().cancelTask(getId());
        return false;
    }

    default boolean isCancelled() {
        if (MinecraftMod.isInit())
            return MinecraftMod.getInstance().getScheduler().isRunningTask(getId());
        return false;
    }

    default void update() {
        getRunnable().run();
    }
}
