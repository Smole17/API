package ru.smole.status.bar;

import ru.smole.status.style.Style;

public interface Bar {

    boolean isVisible();

    Bar hide();

    Bar show();

    Bar setVisible(boolean is);

    Bar setName(String name);

    String getName();

    Style getStyle();

    Bar setStyle(Style style);

    Bar setPercent(float percent);

    float getPercent();

    Bar setHeight(int height);

    int getHeight();

}
