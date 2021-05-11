package ru.smole.binding;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ru.smole.binding.bind.Bind;
import ru.smole.binding.bind.Category;
import ru.smole.mod.MinecraftMod;
import ru.smole.mod.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyBinding implements KeyBindingProvider {

    private Map<String, Category> categoryMap;

    public KeyBinding(Mod mod) {
        if (!MinecraftMod.isInit())
            throw new RuntimeException("MinecraftMod not initialize");
        mod.registerEvents(this);
        categoryMap = new HashMap<>();
    }

    public Category register(Category category) {
        for (Bind b : category.getBinds()) {
            ClientRegistry.registerKeyBinding(b);
        }
        return categoryMap.put(category.getId(), category);
    }

    public void unregister(String id) {
        categoryMap.remove(id);
    }

    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent e) {
        for (Category c : new ArrayList<>(categoryMap.values())) {
            for (Bind b : c.getBinds()) {
                if (b.isPressed()) {
                    b.on();
                    return;
                }
            }
        }
    }

}
