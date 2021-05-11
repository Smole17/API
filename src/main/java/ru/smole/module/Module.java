package ru.smole.module;

import ru.smole.mod.Mod;

public interface Module {

    void init(Mod mod);

    static @interface Info {

        String name();

        Load load() default Load.INIT;

    }

    static enum Load {
        PRE,
        INIT,
        POST;
    }
}
