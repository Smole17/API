package ru.smole.module;


import ru.smole.mod.Mod;

import java.util.*;

public class Modules implements ModuleProvider {

    private List<Module> toReg;
    private Map<String, Module> modules;
    private Mod mod;

    public Modules(Mod mod) {
        this.mod = mod;
        toReg = new ArrayList<>();
        modules = new HashMap<>();
    }

    public <T extends Module> T get(String name) {
        return (T) modules.getOrDefault(name.toLowerCase(), null);
    }

    public void register(Module module) {
        toReg.add(module);
    }

    public void init(Module.Load load) {
        for (Module m : new ArrayList<>(toReg)) {
            Module.Info info = m.getClass().getDeclaredAnnotation(Module.Info.class);
            if (info == null)
                continue;

            if (info.load() == load) {
                modules.put(info.name(), m);
                toReg.remove(m);
                try {
                    m.init(mod);
                } catch (Exception e) {
                    e.printStackTrace();
                    mod.getLogger().error("Module " + info.name() + " mot initialize", e);
                }
            }
        }
    }
}
