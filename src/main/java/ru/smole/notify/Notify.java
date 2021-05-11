package ru.smole.notify;

import ru.smole.render.RColor;
import ru.smole.utils.Cooldown;

import java.awt.*;

public class Notify {

    public String name;
    protected int x, y;
    protected Cooldown cd;
    protected boolean enabled;
    protected final String[] msg;
    protected final Type type;

    public Notify(Type type, String text, int duration, String... msg) {
        this.msg = msg;
        this.name = text;
        this.type = type;
        this.cd = new Cooldown(duration);
    }

    public enum Type {
        ERROR(RColor.RED),
        INFO(RColor.of(new Color(0, 138, 195))),
        CONFIRM(RColor.GREEN),
        WARN(RColor.YELLOW);

        public final RColor color;

        Type(RColor color) {
            this.color = color;
        }
    }
}
