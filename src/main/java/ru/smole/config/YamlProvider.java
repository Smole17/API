package ru.smole.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import ru.smole.mod.MinecraftMod;
import ru.smole.mod.Mod;
import ru.smole.utils.Cooldown;

import java.io.*;
import java.util.*;

public class YamlProvider extends ConfigurationProvider {

    private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial(() -> {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(options);
    });

    private final Map<Configuration, Cooldown> autoSaves = new HashMap();

    public YamlProvider(Mod mod) {
        if (!MinecraftMod.isInit())
            throw new RuntimeException("MinecraftMod not initialize");
        mod.getScheduler().runTaskTimer(1, () -> {
           autoSaves.entrySet().stream()
                   .filter(c -> c.getValue().is())
                   .forEach(c -> save(c.getKey()));
        });
    }

    public Configuration saveDelayed(Configuration configuration, int delay) {
        autoSaves.put(configuration, new Cooldown(delay, true));
        return configuration;
    }

    public Configuration load(File file) {
        Map<String, Object> map;
        try {
            if ((map = (Map<String, Object>) yaml.get().load(new FileReader(file))) == null)
                map = new LinkedHashMap<>();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Configuration(file);
        }
        return new Configuration(file, map);
    }


    public void save(Configuration configuration) {
        try {
            yaml.get().dump(configuration.getSelf(), new FileWriter(configuration.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration create(File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return load(file);
    }
}
