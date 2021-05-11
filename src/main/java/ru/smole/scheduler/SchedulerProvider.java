package ru.smole.scheduler;

import ru.smole.scheduler.task.SchedulerTask;

public interface SchedulerProvider {

    SchedulerTask runTaskTimer(int delay, Runnable run);

    SchedulerTask runTaskLater(int delay, Runnable run);

    SchedulerTask runTask(Runnable run);

    boolean cancelTask(int id);

    boolean cancelAllTasks();

    boolean isRunningTask(int id);

}
