package ru.smole.status;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.smole.mod.MinecraftMod;
import ru.smole.mod.Mod;
import ru.smole.render.RColor;
import ru.smole.render.RenderProvider;
import ru.smole.render.Renderer;
import ru.smole.status.bar.Bar;
import ru.smole.status.bar.DefaultBar;
import ru.smole.status.style.Style;
import ru.smole.utils.ReflectUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

public class StatusBars implements StatusBarProvider, Renderer {

    private List<Bar> bars;
    private RenderProvider render;
    private Mod mod;
    private int y = 0;
    private int weight = 182;
    ReflectUtil.FieldAccessor<Map<UUID, BossInfoClient>> barsMap =
            ReflectUtil.fieldAccessor(ReflectUtil.findField(GuiBossOverlay.class, Map.class));

    public StatusBars(Mod mod) {
        if (!MinecraftMod.isInit())
            throw new RuntimeException("MinecraftMod not initialize");
        this.mod = mod;
        render = mod.getRender();
        bars = new LinkedList<>();
        render.registerRenderer(this);
        mod.registerEvents(this);
    }

    @SubscribeEvent
    public void overlay(RenderGameOverlayEvent.BossInfo e) {
        e.setCanceled(true);
    }

    private List<BossInfoClient> getBars() {
        if (barsMap == null) {
            return Collections.emptyList();
        }

        try {
            return new ArrayList<>(barsMap.get(Minecraft.getMinecraft().ingameGUI.getBossOverlay()).values());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Bar create(String name, Style style) {
        Bar bar = new DefaultBar(name, style);
        bars.add(bar);
        return bar;
    }

    public void remove(Bar bar) {
        bars.remove(bar);
    }

    public int getStartY() {
        return y;
    }

    public void on(ScaledResolution rs) {
        y = 0;

        int startX = rs.getScaledWidth() / 2 - weight / 2;

        for (BossInfoClient b : getBars()) {
            drawBar(startX, b.getPercent(), 5, b.getName().getFormattedText(), ColorAlias.getByColor(b.getColor()).getAlias(), RColor.of(new Color(134, 134, 134)));
        }

        for (Bar bar : bars) {
            if (!bar.isVisible())
                return;
                drawBar(startX, bar.getPercent(), bar.getHeight(), bar.getName(), bar.getStyle().getColor(), bar.getStyle().getBackground());
            }
    }

    public void drawBar(int x, float percent, int height, String name, RColor color, RColor bg) {
        FontRenderer fr = render.getFontRenderer();
        y += fr.FONT_HEIGHT + 3;

        render.renderCenteredText(name, y, null);

        y += height + 5;

        render.renderRect(x, y, weight, height, bg);
        render.renderRect(x, y, ((int) (weight * percent)), height, color);

    }
}
