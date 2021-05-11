package ru.smole.status;

import ru.smole.status.bar.Bar;
import ru.smole.status.style.Style;

public interface StatusBarProvider {

    Bar create(String name, Style style);

    void remove(Bar bar);

    int getStartY();

}
