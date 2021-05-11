package ru.smole.status;

import net.minecraft.world.BossInfo;
import ru.smole.render.RColor;

public enum ColorAlias {

    PINK(BossInfo.Color.PINK, RColor.PINK),
    BLUE(BossInfo.Color.BLUE, RColor.AQUA),
    GREEN(BossInfo.Color.GREEN, RColor.GREEN),
    RED(BossInfo.Color.RED, RColor.RED),
    YELLOW(BossInfo.Color.YELLOW, RColor.ORANGE),
    PURPLE(BossInfo.Color.PURPLE, RColor.MAGENTA),
    WHITE(BossInfo.Color.WHITE, RColor.WHITE);

    private BossInfo.Color color;
    private RColor alias;

    ColorAlias(BossInfo.Color color, RColor alias) {
        this.color = color;
        this.alias = alias;
    }

    public RColor getAlias() {
        return alias;
    }

    public static ColorAlias getByColor(BossInfo.Color color) {
        for (ColorAlias value : values()) {
            if (color == value.color)
                return value;
        }
        return WHITE;
    }
}
