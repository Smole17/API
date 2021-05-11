package ru.smole.module;

public interface ModuleProvider {

    void register(Module module);

    void init(Module.Load load);

    <T extends Module> T get(String name);

}
