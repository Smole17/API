package ru.smole.utils;

public class Cooldown {

    protected final int delay;
    protected long stop;
    protected final boolean autoUpd;

    public Cooldown(int delay) {
        this(delay, false);
    }

    public Cooldown(int delay, boolean autoUpd) {
        this.delay = delay;
        this.autoUpd = autoUpd;
        restart();
    }

    public long restart() {
        return stop = System.currentTimeMillis() + delay * 50;
    }

    public int getTickDelay() {
        return delay;
    }

    public long getMSDelay() {
        return delay * 50;
    }

    public long getLeft() {
        return stop - System.currentTimeMillis();
    }

    public long getPassed() {
        return System.currentTimeMillis() - stop - delay * 50l;
    }

    public double getPercent() {
        double p = getLeft() / (double) getMSDelay();
        return p < 0 ? 0 : p > 1 ? 1 : p;
    }

    public boolean is() {
        if (stop <= System.currentTimeMillis()) {
            if (autoUpd)
                restart();
            return true;
        }

        return false;
    }

}
