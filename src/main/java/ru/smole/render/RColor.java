package ru.smole.render;

import java.awt.*;

public class RColor {

    public static final RColor
            WHITE = of(Color.WHITE),
            BLACK = of(Color.BLACK),
            GRAY = of(Color.GRAY),
            DARK_GRAY = of(Color.DARK_GRAY),
            LIGHT_GRAY = of(Color.LIGHT_GRAY),
            RED = of(Color.RED),
            PINK = of(Color.PINK),
            ORANGE = of(Color.ORANGE),
            YELLOW = of(Color.YELLOW),
            GREEN = of(Color.GREEN),
            DARK_GREEN = of(new Color(5, 109, 18)),
            MAGENTA = of(Color.MAGENTA),
            BLUE = of(Color.BLUE),
            AQUA = of(Color.CYAN);

    private final Color color;

    protected RColor(Color color) {
        this.color = color;
    }

    public int getRGB() {
        return color.getRGB();
    }

    public Color getColor() {
        return color;
    }

    public static RColor of(Color color) {
        return new RColor(color);
    }

    public static RColor of(int r, int g, int b, int a) {
        return new RColor(new Color(r, g, b, a));
    }

    public static RColor of(int r, int g, int b) {
        return new RColor(new Color(r, g, b));
    }

}
