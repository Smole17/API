package ru.smole.mod;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import ru.smole.binding.KeyBindingProvider;
import ru.smole.config.ConfigurationProvider;
import ru.smole.render.RenderProvider;
import ru.smole.scheduler.SchedulerProvider;

import java.io.File;

public interface Mod {

    void preInit();

    void init();

    void postInit();

    void registerEvents(Object var);

    Logger getLogger();

    String getName();

    SchedulerProvider getScheduler();

    ConfigurationProvider getConfigurations();

    RenderProvider getRender();

    KeyBindingProvider getBinding();

    File getModFolder();

    Minecraft getMinecraft();
}
