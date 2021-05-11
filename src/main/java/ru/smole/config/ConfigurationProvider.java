package ru.smole.config;

import java.io.File;

public abstract class ConfigurationProvider {

    public abstract Configuration create(File file);

    public abstract Configuration saveDelayed(Configuration configuration, int delay);

    public abstract Configuration load(File paramFile);

    public abstract void save(Configuration paramConfiguration);


}
