package ru.smole.mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.smole.binding.KeyBinding;
import ru.smole.binding.KeyBindingProvider;
import ru.smole.render.Render;
import ru.smole.render.RenderProvider;
import ru.smole.scheduler.Scheduler;
import ru.smole.scheduler.SchedulerProvider;

import java.io.File;

public class MinecraftMod implements Mod {

    private static boolean initialize = false;
    private static MinecraftMod instance;

    private final Minecraft minecraft;
    private final String name;
    private final SchedulerProvider scheduler;
    private final KeyBindingProvider binding;
    private final RenderProvider render;
    private final Logger logger;

    public static MinecraftMod getInstance() {
        return initialize ? instance : null;
    }

    public static boolean isInit() {
        return initialize;
    }

    public MinecraftMod(String name) {
        this.name = name;
        instance = this;
        initialize = true;
        minecraft = Minecraft.getMinecraft();
        logger = LogManager.getLogger(name);
        scheduler = new Scheduler(this);
        render = new Render(this);
        binding = new KeyBinding(this);
    }

    @net.minecraftforge.fml.common.Mod.EventHandler
    public final void preinit(FMLPreInitializationEvent event) {
        preInit();
    }

    @net.minecraftforge.fml.common.Mod.EventHandler
    public final void init(FMLInitializationEvent event) {
        init();
    }

    @net.minecraftforge.fml.common.Mod.EventHandler
    public final void postinit(FMLPostInitializationEvent event) {
        postInit();
    }

    public void preInit() {}

    public void init() {}

    public void postInit() {}


    public final void registerEvents(Object var) {
        MinecraftForge.EVENT_BUS.register(var);
    }

    public final Logger getLogger() {
        return logger;
    }

    public final SchedulerProvider getScheduler() {
        return scheduler;
    }

    public RenderProvider getRender() {
        return render;
    }

    public KeyBindingProvider getBinding() {
        return binding;
    }


    public Minecraft getMinecraft() {
        return minecraft;
    }

    public final String getName() {
        return name;
    }

    public File getModFolder() {
        return new File(name);
    }
}
