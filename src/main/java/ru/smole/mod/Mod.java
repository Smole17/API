package ru.smole.mod;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import ru.smole.binding.KeyBindingProvider;
import ru.smole.config.ConfigurationProvider;
import ru.smole.notify.NotificationProvider;
import ru.smole.render.RenderProvider;
import ru.smole.scheduler.SchedulerProvider;
import ru.smole.status.StatusBarProvider;

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

    NotificationProvider getNotifies();

    KeyBindingProvider getBinding();

    StatusBarProvider getBars();

    File getModFolder();

    Minecraft getMinecraft();

}
