package ru.smole.scheduler.task;

import ru.smole.utils.Cooldown;

public class LaterTask extends Cooldown implements SchedulerTask {

    private final Runnable run;
    private final int id;

    public LaterTask(int id, int delay, Runnable run) {
        super(delay, false);
        this.id = id;
        this.run = run;
    }

    public void update() {
        cancel();
        SchedulerTask.super.update();
    }

    public int getId() {
        return id;
    }

    public int getDelay() {
        return delay;
    }

    public boolean needUpdate() {
        return is();
    }

    public Runnable getRunnable() {
        return run;
    }
}
