package ru.smole.scheduler.task;

import ru.smole.utils.Cooldown;

public class TimerTask extends Cooldown implements SchedulerTask {

    private final Runnable run;
    private final int id;

    public TimerTask(int id, int delay, Runnable run) {
        super(delay, true);
        this.id = id;
        this.run = run;
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
