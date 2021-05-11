package ru.smole.status.bar;

import ru.smole.status.style.Style;

public class DefaultBar implements Bar {

    private boolean visible;
    private String name;
    private float percent;
    private Style style;
    private static final int defHeight = 5;
    private int height;

    public DefaultBar(String name, Style style) {
        visible = true;
        this.name = name;
        percent = 1f;
        this.style = style;
        height = defHeight;
    }

    public boolean isVisible() {
        return visible;
    }

    public Bar hide() {
        visible = false;
        return this;
    }

    public Bar show() {
        visible = true;
        return this;
    }

    public Bar setVisible(boolean is) {
        visible = is;
        return this;
    }

    public Bar setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Style getStyle() {
        return style;
    }

    public Bar setStyle(Style style) {
        this.style = style;
        return this;
    }

    public Bar setPercent(float percent) {
        this.percent = percent > 1 || percent < 0 ? 1 : percent;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public Bar setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getHeight() {
        return height;
    }
}
