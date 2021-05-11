package ru.smole.binding.bind;

import net.minecraft.client.settings.KeyBinding;

public class Bind extends KeyBinding {

    private Runnable click;

    protected Bind(String description, int keyCode, String category, Runnable click) {
        super(description, keyCode, category);
        this.click = click;
    }

    public void on() {
        if (click != null)
            click.run();
    }

}
